package ml.ipvz.fa.userservice.service;

import ml.ipvz.fa.userservice.model.dto.AuthDto
import ml.ipvz.fa.userservice.model.dto.RegisterDto
import ml.ipvz.fa.userservice.model.entity.UserEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
    fun login(auth: AuthDto): Mono<UserEntity>
    fun register(register: RegisterDto): Mono<UserEntity>
    fun getAll(): Flux<UserEntity>
}
