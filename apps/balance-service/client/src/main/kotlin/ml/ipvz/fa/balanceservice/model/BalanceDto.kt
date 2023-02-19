package ml.ipvz.fa.balanceservice.model

import ml.ipvz.fa.cloud.model.Balance

data class BalanceDto(
    val id: Long,
    val balance: Balance
)