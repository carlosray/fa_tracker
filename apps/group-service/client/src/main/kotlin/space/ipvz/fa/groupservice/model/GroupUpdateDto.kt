package space.ipvz.fa.groupservice.model

import space.ipvz.fa.cloud.model.Currency

data class GroupUpdateDto(
    val id: Long,
    val name: String,
    val description: String,
    val currency: Currency,
    val users: List<String>,
)