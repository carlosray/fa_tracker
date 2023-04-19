package space.ipvz.fa.accountservice.model

import space.ipvz.fa.cloud.model.Currency

data class CreateAccountDto(
    val name: String,
    val currency: Currency
)