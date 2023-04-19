package space.ipvz.fa.groupservice.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import space.ipvz.fa.async.model.event.GroupCreatedEvent
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.service.send
import space.ipvz.fa.authservice.base.model.User
import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.balanceservice.client.BalanceServiceClient
import space.ipvz.fa.balanceservice.model.BalanceDto
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.groupservice.exception.GroupNotFoundException
import space.ipvz.fa.groupservice.logging.LoggerDelegate
import space.ipvz.fa.groupservice.model.GroupCreateDto
import space.ipvz.fa.groupservice.model.GroupDto
import space.ipvz.fa.groupservice.model.GroupUpdateDto
import space.ipvz.fa.groupservice.model.entity.GroupConfig
import space.ipvz.fa.groupservice.model.entity.GroupEntity
import space.ipvz.fa.groupservice.repository.GroupRepository
import space.ipvz.fa.groupservice.service.GroupService
import space.ipvz.fa.userservice.client.UserServiceClient
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import java.math.BigDecimal
import java.time.Instant

@Service
class GroupServiceImpl(
    private val balanceServiceClient: BalanceServiceClient,
    private val groupRepository: GroupRepository,
    private val userServiceClient: UserServiceClient,
) : GroupService {
    private val log by LoggerDelegate()

    override fun get(ids: Set<Long>): Flux<GroupDto> =
        groupRepository.findAllById(ids).flatMap(::withBalance)

    private fun withBalance(entity: GroupEntity): Mono<GroupDto> =
        balanceServiceClient.getGroupBalance(entity.id!!, entity.config.currency)
            .defaultIfEmpty(BalanceDto(entity.id, Balance.empty(entity.config.currency)))
            .map { GroupDto(entity.id, entity.name, entity.description, it.balance) }

    @Transactional
    override fun create(dto: GroupCreateDto, owner: User): Mono<GroupDto> {
        val entity = GroupEntity(
            name = dto.name,
            description = dto.description,
            config = GroupConfig(dto.currency)
        )

        return groupRepository.save(entity).map {
            GroupDto(
                it.id!!,
                it.name,
                it.description,
                Balance(BigDecimal.ZERO, it.config.currency, Instant.now())
            )
        }.flatMap { group ->
            val permission = Permission.builder(group.id).group().admin()
            val update = UpdatePermissionsDto(permission, UpdatePermissionsDto.Action.GRANT, owner.id)

            return@flatMap userServiceClient.updatePermissions(listOf(update))
                .then(GroupCreatedEvent(group.id, dto.createDefaultCategories).send())
                .thenReturn(group)
        }
    }

    override fun update(dto: GroupUpdateDto): Mono<GroupDto> = groupRepository.findById(dto.id)
        .switchIfEmpty { Mono.error { GroupNotFoundException(dto.id) } }
        .flatMap {
            groupRepository.save(
                it.copy(
                    name = it.name,
                    description = it.description,
                    config = it.config.copy(currency = dto.currency)
                )
            )
        }.flatMap(::withBalance)

    override fun delete(id: Long): Mono<Void> = groupRepository.deleteById(id)
        .doOnNext { log.info("Deleted group $id") }
        .then(GroupDeletedEvent(id).send())
}