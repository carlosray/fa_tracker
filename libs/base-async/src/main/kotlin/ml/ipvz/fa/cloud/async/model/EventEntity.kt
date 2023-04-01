package ml.ipvz.fa.cloud.async.model

interface EventEntity {
    val eventKey: String
        get() = this.javaClass.name
}