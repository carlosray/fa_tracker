package ml.ipvz.fa.balanceservice.service.impl

import ml.ipvz.fa.balanceservice.service.CurrencyService
import ml.ipvz.fa.cloud.model.Balance
import ml.ipvz.fa.cloud.model.Currency
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl : CurrencyService {

    override fun applyCurrency(balance: Balance, currency: Currency): Balance {
        //TODO
        return Balance(balance.amount, currency, balance.lastUpdate)
    }
}