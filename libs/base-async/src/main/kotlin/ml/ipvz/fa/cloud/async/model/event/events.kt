package ml.ipvz.fa.cloud.async.model.event

import ml.ipvz.fa.cloud.async.model.EventEntity
import java.time.Instant

data class GroupCreatedEvent(val groupId: Long, val time: Instant) : EventEntity

data class PermissionUpdatedEvent(val permission: String, val granted: Boolean, val userId: Long) : EventEntity