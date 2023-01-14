package ml.ipvz.fa.groupservice.model.entity

data class GroupConfig(
    val whoCanModify: Set<Long> = setOf()
)