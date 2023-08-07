package space.ipvz.fa.balanceservice.client.impl

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import space.ipvz.fa.authservice.base.util.TokenUtils
import space.ipvz.fa.balanceservice.client.BalanceServiceClient
import space.ipvz.fa.balanceservice.model.BalanceDto
import space.ipvz.fa.cloud.model.Currency
import java.time.Duration

class BalanceServiceClientImpl(
    private val balanceServiceClient: WebClient,
    private val defaultTimeout: Duration
) : BalanceServiceClient {

    override fun getGroupBalance(groupId: Long, currency: Currency?): Mono<BalanceDto> =
        getBalance("group/$groupId", currency)

    override fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency?): Mono<BalanceDto> =
        getBalance("group/$groupId/account/$accountId", currency)

    private fun getBalance(path: String, currency: Currency?): Mono<BalanceDto> =
        TokenUtils.withAuthContextMono { auth ->
            balanceServiceClient.get()
                .uri("/balance/$path?currency=$currency")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .onStatus(HttpStatusCode::isError) { response -> response.createError() }
                .bodyToMono(BalanceDto::class.java)
                .timeout(defaultTimeout)
        }
}