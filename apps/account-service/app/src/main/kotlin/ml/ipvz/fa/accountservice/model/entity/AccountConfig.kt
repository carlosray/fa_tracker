package ml.ipvz.fa.accountservice.model.entity

import ml.ipvz.fa.cloud.model.Currency

data class AccountConfig(
    val color: String = "",
    val currency: Currency
)