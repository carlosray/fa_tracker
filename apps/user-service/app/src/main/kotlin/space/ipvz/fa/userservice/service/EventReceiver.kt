package space.ipvz.fa.userservice.service

import jakarta.annotation.PostConstruct
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.service.EventService
import org.springframework.stereotype.Service

@Service
class EventReceiver(
    private val eventService: EventService,
    private val permissionService: PermissionService
) {
    @PostConstruct
    fun postConstruct() {
        eventService.group.deleted.receive().subscribe(::onGroupDeleted)
    }

    private fun onGroupDeleted(event: GroupDeletedEvent) {
        permissionService.deleteByGroupId(event.groupId).subscribe()
    }
}