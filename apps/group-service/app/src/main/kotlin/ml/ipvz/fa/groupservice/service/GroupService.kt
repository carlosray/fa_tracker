package ml.ipvz.fa.groupservice.service;

import ml.ipvz.fa.groupservice.model.GroupDto
import reactor.core.publisher.Flux

interface GroupService {
    fun getGroupsByIds(ids: Set<Long>): Flux<GroupDto>
}
