package ml.ipvz.fa.groupservice.service;

import ml.ipvz.fa.authservice.base.model.User
import ml.ipvz.fa.groupservice.model.GroupCreateDto
import ml.ipvz.fa.groupservice.model.GroupDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GroupService {
    fun getGroupsByIds(ids: Set<Long>): Flux<GroupDto>
    fun createGroup(dto: GroupCreateDto, owner: User): Mono<GroupDto>
    fun deleteGroup(id: Long): Mono<Void>
}
