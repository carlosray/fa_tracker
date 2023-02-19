package ml.ipvz.fa.groupservice.service.impl

import ml.ipvz.fa.balanceservice.client.BalanceServiceClient
import ml.ipvz.fa.groupservice.model.GroupDto
import ml.ipvz.fa.groupservice.model.entity.GroupEntity
import ml.ipvz.fa.groupservice.repository.GroupRepository
import ml.ipvz.fa.groupservice.service.GroupService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GroupServiceImpl(
    private val balanceServiceClient: BalanceServiceClient,
    private val groupRepository: GroupRepository
) : GroupService {

    override fun getGroupsByIds(ids: Set<Long>): Flux<GroupDto> =
        groupRepository.findAllById(ids).flatMap(::withBalance)

    private fun withBalance(entity: GroupEntity): Mono<GroupDto> =
        balanceServiceClient.getGroupBalance(entity.id!!, entity.config.currency)
            .map { GroupDto(entity.id, entity.name, entity.config.isCurrent, it.balance) }
}