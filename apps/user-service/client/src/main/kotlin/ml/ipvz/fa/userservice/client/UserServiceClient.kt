package ml.ipvz.fa.userservice.client

import ml.ipvz.fa.authservice.base.permission.Permission
import ml.ipvz.fa.userservice.model.LoginDto
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import ml.ipvz.fa.userservice.model.UserDto
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserServiceClient {
    fun login(loginDto: Mono<LoginDto>): Mono<UserDto>
    fun getUser(id: Long): Mono<UserDto>
    fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<ResponseEntity<Void>>
    fun getPermissions(userId: Long): Flux<Permission>
}