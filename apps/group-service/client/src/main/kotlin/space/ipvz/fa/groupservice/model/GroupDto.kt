package space.ipvz.fa.groupservice.model

import space.ipvz.fa.cloud.model.Balance

data class GroupDto(
    val id: Long,
    val name: String,
    val description: String,
    val balance: Balance
)