package space.ipvz.fa.operationservice.client

import reactor.core.publisher.Mono
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto

interface OperationServiceClient {
    fun getBalancesUpdate(): Mono<UpdateAmountsDto>
}