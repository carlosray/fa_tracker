package ml.ipvz.fa.cloud.async.model

interface Event {
    val eventKey: String
        get() = this.javaClass.name
}