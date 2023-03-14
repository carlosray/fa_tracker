package ml.ipvz.fa.cloud.async.service

import ml.ipvz.fa.cloud.async.model.event.group.GroupCreatedEvent

interface EventServiceFactory {
    val group: Group
    val user: User
    val account: Account
    val category: Category

    interface Group {
        val created: EventService<GroupCreatedEvent>
    }

    interface User
    interface Account
    interface Category
}