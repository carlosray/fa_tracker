package space.ipvz.fa.userservice.client.impl

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.authservice.base.util.TokenUtils
import space.ipvz.fa.userservice.client.UserServiceClient
import space.ipvz.fa.userservice.model.LoginDto
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import space.ipvz.fa.userservice.model.UserDto
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

    override fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<ResponseEntity<Void>> =
        TokenUtils.withAuthContextMono { auth ->
            userServiceClient.put()
                .uri("users/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updates)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .onStatus(HttpStatusCode::isError) { response -> response.createError() }
                .toBodilessEntity()
                .timeout(defaultTimeout)
        }

    override fun getPermissionsPrivate(userId: Long): Flux<Permission> =
        userServiceClient.get()
            .uri("/private/users/permissions/$userId")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToFlux(Permission::class.java)
            .timeout(defaultTimeout)
}