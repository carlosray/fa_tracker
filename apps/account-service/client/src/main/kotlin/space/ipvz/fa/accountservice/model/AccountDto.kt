package space.ipvz.fa.accountservice.model

import space.ipvz.fa.cloud.model.Balance

data class AccountDto(
    val id: Long? = null,
    val groupId: Long,
    val name: String,
    val balance: Balance,
)