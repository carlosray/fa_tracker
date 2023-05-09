package space.ipvz.fa.groupservice.client

import reactor.core.publisher.Flux
import space.ipvz.fa.groupservice.model.GroupDto

interface GroupServiceClient {
    fun getAllGroupsPrivate(withBalance: Boolean = false): Flux<GroupDto>
    fun getGroupsPrivate(ids: Set<Long>, withBalance: Boolean = false): Flux<GroupDto>
}