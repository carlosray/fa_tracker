package space.ipvz.fa.operationservice.service

import reactor.core.publisher.Mono
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto

interface BalanceService {
    fun getBalances(): Mono<UpdateAmountsDto>
}