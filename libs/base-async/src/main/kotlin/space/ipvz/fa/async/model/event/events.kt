package space.ipvz.fa.async.model.event

import space.ipvz.fa.async.model.EventEntity
import space.ipvz.fa.cloud.model.Currency
import space.ipvz.fa.operationservice.model.dto.Operation
import java.time.Instant

//group events
data class GroupDeletedEvent(val groupId: Long, val time: Instant = Instant.now()) : EventEntity
data class GroupCreatedEvent(val groupId: Long, val createDefaultCategories: Boolean = true) : EventEntity
data class GroupCurrencyChangedEvent(val groupId: Long, val newCurrency: Currency) : EventEntity

//user events
data class PermissionUpdatedEvent(val permission: String, val granted: Boolean, val userId: Long) : EventEntity

//account events
data class AccountDeletedEvent(val groupId: Long, val accountId: Long, val time: Instant = Instant.now()) : EventEntity
data class AccountCreatedEvent(
    val groupId: Long,
    val accountId: Long,
    val currency: Currency,
    val time: Instant = Instant.now()
) : EventEntity

//operation events
data class OperationCreatedEvent(
    val groupId: Long,
    val operation: Operation,
    val time: Instant = Instant.now()
) : EventEntity
