package ml.ipvz.fa.groupservice.model

import ml.ipvz.fa.cloud.model.Balance

data class GroupDto(
    val id: Long,
    val name: String,
    val isCurrent: Boolean,
    val balance: Balance
)