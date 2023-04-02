package space.ipvz.fa.balanceservice.service;

import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency

interface CurrencyService {
    fun applyCurrency(balance: Balance, currency: Currency): Balance
}
