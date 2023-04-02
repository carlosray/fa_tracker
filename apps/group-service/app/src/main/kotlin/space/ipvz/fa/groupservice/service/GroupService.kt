package space.ipvz.fa.groupservice.service;

import space.ipvz.fa.authservice.base.model.User
import space.ipvz.fa.groupservice.model.GroupCreateDto
import space.ipvz.fa.groupservice.model.GroupDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GroupService {
    fun getGroupsByIds(ids: Set<Long>): Flux<GroupDto>
    fun createGroup(dto: GroupCreateDto, owner: User): Mono<GroupDto>
    fun deleteGroup(id: Long): Mono<Void>
}
