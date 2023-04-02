package space.ipvz.fa.balanceservice.model

import space.ipvz.fa.cloud.model.Balance

data class BalanceDto(
    val id: Long,
    val balance: Balance
)