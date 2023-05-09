package space.ipvz.fa.accountservice.model

import space.ipvz.fa.cloud.model.Currency

data class UpdateAccountDto(
    val id: Long?,
    val name: String,
    val currency: Currency
)