package ml.ipvz.fa.cloud.async.service

import ml.ipvz.fa.cloud.async.model.Event
import ml.ipvz.fa.cloud.async.model.event.GroupCreatedEvent
import ml.ipvz.fa.cloud.async.model.event.PermissionUpdatedEvent

interface EventService {
    val group: Group
    val user: User
    val account: Account
    val category: Category

    interface Group {
        val created: Event<GroupCreatedEvent>
    }

    interface User {
        val permissionUpdated: Event<PermissionUpdatedEvent>
    }

    interface Account
    interface Category
}