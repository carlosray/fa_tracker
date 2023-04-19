package space.ipvz.fa.async.model.event

import space.ipvz.fa.async.model.EventEntity
import java.time.Instant

data class GroupDeletedEvent(val groupId: Long, val time: Instant = Instant.now()) : EventEntity
data class GroupCreatedEvent(val groupId: Long, val createDefaultCategories: Boolean = true) : EventEntity

data class PermissionUpdatedEvent(val permission: String, val granted: Boolean, val userId: Long) : EventEntity

data class AccountDeletedEvent(val groupId: Long, val accountId: Long, val time: Instant = Instant.now()) : EventEntity
