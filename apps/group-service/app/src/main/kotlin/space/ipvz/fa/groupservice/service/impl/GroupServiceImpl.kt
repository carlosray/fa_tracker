package space.ipvz.fa.groupservice.service.impl

import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.service.send
import space.ipvz.fa.authservice.base.model.User
import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.balanceservice.client.BalanceServiceClient
import space.ipvz.fa.balanceservice.model.BalanceDto
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.groupservice.model.GroupCreateDto
import space.ipvz.fa.groupservice.model.GroupDto
import space.ipvz.fa.groupservice.model.entity.GroupConfig
import space.ipvz.fa.groupservice.model.entity.GroupEntity
import space.ipvz.fa.groupservice.repository.GroupRepository
import space.ipvz.fa.groupservice.service.GroupService
import space.ipvz.fa.userservice.client.UserServiceClient
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.Instant

@Service
class GroupServiceImpl(
    private val balanceServiceClient: BalanceServiceClient,
    private val groupRepository: GroupRepository,
    private val userServiceClient: UserServiceClient
) : GroupService {

    override fun getGroupsByIds(ids: Set<Long>): Flux<GroupDto> =
        groupRepository.findAllById(ids).flatMap(::withBalance)

    private fun withBalance(entity: GroupEntity): Mono<GroupDto> =
        balanceServiceClient.getGroupBalance(entity.id!!, entity.config.currency)
            .defaultIfEmpty(BalanceDto(entity.id, Balance.empty(entity.config.currency)))
            .map { GroupDto(entity.id, entity.name, entity.description, it.balance) }

    @Transactional
    override fun createGroup(dto: GroupCreateDto, owner: User): Mono<GroupDto> {
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

            userServiceClient.updatePermissions(listOf(update)).thenReturn(group)
        }
    }

    override fun deleteGroup(id: Long): Mono<Void> = groupRepository.deleteById(id)
        .then(GroupDeletedEvent(id, Instant.now()).send())
}