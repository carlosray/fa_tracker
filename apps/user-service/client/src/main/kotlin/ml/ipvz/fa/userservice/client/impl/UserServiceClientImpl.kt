package ml.ipvz.fa.userservice.client.impl

import ml.ipvz.fa.authservice.base.util.TokenUtils
import ml.ipvz.fa.userservice.client.UserServiceClient
import ml.ipvz.fa.userservice.model.LoginDto
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import ml.ipvz.fa.userservice.model.UserDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration

class UserServiceClientImpl(
    private val userServiceClient: WebClient,
    private val defaultTimeout: Duration
) : UserServiceClient {

    override fun login(loginDto: Mono<LoginDto>): Mono<UserDto> =
        userServiceClient.post()
            .uri("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginDto, LoginDto::class.java)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToMono(UserDto::class.java)
            .timeout(defaultTimeout)

    override fun getUser(id: Long): Mono<UserDto> =
        TokenUtils.withAuthContextMono { auth ->
            userServiceClient.get()
                .uri("/users/$id")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .onStatus(HttpStatusCode::isError) { response -> response.createError() }
                .bodyToMono(UserDto::class.java)
                .timeout(defaultTimeout)
        }

    override fun updatePermissions(userId: Long, update: UpdatePermissionsDto): Mono<ResponseEntity<Void>> =
        TokenUtils.withAuthContextMono { auth ->
            userServiceClient.put()
                .uri("/users/$userId/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(update, UpdatePermissionsDto::class.java)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .onStatus(HttpStatusCode::isError) { response -> response.createError() }
                .toBodilessEntity()
                .timeout(defaultTimeout)
        }
}