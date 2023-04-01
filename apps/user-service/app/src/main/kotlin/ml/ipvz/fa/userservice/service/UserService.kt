package ml.ipvz.fa.userservice.service;

import ml.ipvz.fa.userservice.model.LoginDto
import ml.ipvz.fa.userservice.model.RegisterDto
import ml.ipvz.fa.userservice.model.entity.UserEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
    fun login(auth: LoginDto): Mono<UserEntity>
    fun register(register: RegisterDto): Mono<UserEntity>
    fun getAll(): Flux<UserEntity>
    fun get(id: Long): Mono<UserEntity>
}
