package ml.ipvz.fa.cloud.async.model

interface Topic<E : Event?> {
    fun get(): String?
}