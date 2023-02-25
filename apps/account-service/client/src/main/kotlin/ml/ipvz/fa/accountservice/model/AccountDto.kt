package ml.ipvz.fa.accountservice.model

import ml.ipvz.fa.cloud.model.Balance

data class AccountDto(
    val id: Long? = null,
    val groupId: Long,
    val name: String,
    val balance: Balance,
)