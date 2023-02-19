package ml.ipvz.fa.accountservice.controller

import ml.ipvz.fa.accountservice.model.AccountDto
import ml.ipvz.fa.accountservice.model.entity.AccountEntity

fun AccountEntity.toDto() = AccountDto(this.id, this.name, this.config.currency, this.groupId)
