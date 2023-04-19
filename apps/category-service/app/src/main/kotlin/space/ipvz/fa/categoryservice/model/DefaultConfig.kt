package space.ipvz.fa.categoryservice.model

import space.ipvz.fa.cloud.model.OperationType

data class DefaultConfig(
    var categories: List<DefaultCategory> = listOf(),
)

data class DefaultCategory(
    var name: String,
    var type: OperationType
)