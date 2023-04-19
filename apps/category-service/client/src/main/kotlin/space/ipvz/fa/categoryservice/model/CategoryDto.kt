package space.ipvz.fa.categoryservice.model

import space.ipvz.fa.cloud.model.OperationType

data class CategoryDto(
    val id: Long,
    val name: String,
    val type: OperationType
)