package space.ipvz.fa.groupservice.service;

import space.ipvz.fa.authservice.base.model.User
import space.ipvz.fa.groupservice.model.GroupCreateDto
import space.ipvz.fa.groupservice.model.GroupDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.groupservice.model.GroupUpdateDto

interface GroupService {
    fun get(ids: Set<Long>): Flux<GroupDto>
    fun getAll(ids: Set<Long>?, withBalance: Boolean): Flux<GroupDto>
    fun create(dto: GroupCreateDto, owner: User): Mono<GroupDto>
    fun update(dto: GroupUpdateDto): Mono<GroupDto>
    fun delete(id: Long): Mono<Void>
}
