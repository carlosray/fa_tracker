package ml.ipvz.fa.async.model.event

import ml.ipvz.fa.async.model.EventEntity
import java.time.Instant

data class GroupDeletedEvent(val groupId: Long, val time: Instant) : EventEntity

data class PermissionUpdatedEvent(val permission: String, val granted: Boolean, val userId: Long) : EventEntity