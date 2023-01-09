package ml.ipvz.fa.userservice.client.impl

import ml.ipvz.fa.userservice.client.UserServiceClient
import ml.ipvz.fa.userservice.model.LoginDto
import ml.ipvz.fa.userservice.model.UserDto
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration

class UserServiceClientImpl(private val userServiceClient: WebClient) : UserServiceClient {

    @Override
    override fun login(loginDto: Mono<LoginDto>): Mono<UserDto> =
        userServiceClient.post()
            .uri("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginDto, LoginDto::class.java)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToMono(UserDto::class.java)
            .timeout(Duration.ofSeconds(10))
}