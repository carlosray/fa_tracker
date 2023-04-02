package space.ipvz.fa.async.model

interface EventEntity {
    val eventKey: String
        get() = this.javaClass.name
}