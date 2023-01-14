package ml.ipvz.fa.groupservice.repository

import ml.ipvz.fa.groupservice.model.entity.GroupEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : R2dbcRepository<GroupEntity, Long> {
}