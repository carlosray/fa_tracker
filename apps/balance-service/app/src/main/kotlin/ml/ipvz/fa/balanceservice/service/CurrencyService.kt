package ml.ipvz.fa.balanceservice.service;

import ml.ipvz.fa.cloud.model.Balance
import ml.ipvz.fa.cloud.model.Currency

interface CurrencyService {
    fun applyCurrency(balance: Balance, currency: Currency): Balance
}
