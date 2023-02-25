package ml.ipvz.fa.accountservice.client.impl

import ml.ipvz.fa.accountservice.client.AccountServiceClient
import ml.ipvz.fa.accountservice.model.AccountDto
import ml.ipvz.fa.authservice.base.util.TokenUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration

class AccountServiceClientImpl(private val accountServiceClient: WebClient) : AccountServiceClient {
    override fun getAccount(groupId: Long, id: Long): Mono<AccountDto> = TokenUtils.withAuthContextMono { auth ->
        accountServiceClient.get()
            .uri("/accounts/group/$groupId/$id")
            .header(HttpHeaders.AUTHORIZATION, auth)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToMono(AccountDto::class.java)
            .timeout(Duration.ofSeconds(30))
    }
}