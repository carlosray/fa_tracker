package space.ipvz.fa.accountservice.model.entity

import space.ipvz.fa.cloud.model.Currency

data class AccountConfig(
    val color: String = "",
    val currency: Currency
)