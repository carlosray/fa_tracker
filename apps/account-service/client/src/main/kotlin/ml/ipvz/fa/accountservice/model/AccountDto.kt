package ml.ipvz.fa.accountservice.model

import ml.ipvz.fa.cloud.model.Currency

data class AccountDto(
    val id: Long? = null,
    val name: String,
    val currency: Currency,
    val groupId: Long
)