package ml.ipvz.fa.groupservice.model.entity

import ml.ipvz.fa.cloud.model.Currency

data class GroupConfig(
    val isCurrent: Boolean = false,
    val currency: Currency
)