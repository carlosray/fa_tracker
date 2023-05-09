package space.ipvz.fa.operationservice.client.impl

import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import space.ipvz.fa.operationservice.client.OperationServiceClient
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto
import java.time.Duration

class OperationServiceClientImpl(
    private val operationServiceClient: WebClient,
    private val defaultTimeout: Duration
) : OperationServiceClient {
    override fun getBalancesUpdate(): Mono<UpdateAmountsDto> =
        operationServiceClient.get()
            .uri("/private/operations/balance")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToMono(UpdateAmountsDto::class.java)
            .timeout(defaultTimeout)
}