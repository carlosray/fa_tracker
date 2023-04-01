package ml.ipvz.fa.groupservice.model

import ml.ipvz.fa.cloud.model.Currency

data class GroupCreateDto(
    val name: String,
    val description: String,
    val currency: Currency,
    val users: List<String>
)