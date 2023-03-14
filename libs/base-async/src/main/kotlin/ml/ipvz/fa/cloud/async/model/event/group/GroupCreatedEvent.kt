package ml.ipvz.fa.cloud.async.model.event.group

import ml.ipvz.fa.cloud.async.model.Event
import java.time.Instant

@JvmRecord
data class GroupCreatedEvent(val groupId: Long, val time: Instant) : Event {
    companion object {
        const val TOPIC = "group-created-events"
    }
}