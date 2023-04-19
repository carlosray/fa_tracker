package space.ipvz.fa.categoryservice.model

import space.ipvz.fa.cloud.model.OperationType

data class CreateCategoryDto(
    val name: String,
    val type: OperationType
)