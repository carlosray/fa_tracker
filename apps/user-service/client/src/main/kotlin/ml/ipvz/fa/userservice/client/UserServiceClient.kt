package ml.ipvz.fa.userservice.client

import ml.ipvz.fa.userservice.model.LoginDto
import ml.ipvz.fa.userservice.model.UserDto
import reactor.core.publisher.Mono

interface UserServiceClient {
    fun login(loginDto: Mono<LoginDto>): Mono<UserDto>
}