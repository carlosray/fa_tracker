package space.ipvz.fa.balanceservice.service.impl

import space.ipvz.fa.balanceservice.service.CurrencyService
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl : CurrencyService {

    override fun applyCurrency(balance: Balance, currency: Currency): Balance {
        //TODO
        return Balance(balance.amount, currency, balance.lastUpdate)
    }
}