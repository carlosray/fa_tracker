package space.ipvz.fa.userservice.client

import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.userservice.model.LoginDto
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import space.ipvz.fa.userservice.model.UserDto
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserServiceClient {
    fun login(loginDto: Mono<LoginDto>): Mono<UserDto>
    fun getUser(id: Long): Mono<UserDto>
    fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<ResponseEntity<Void>>
    fun getPermissionsPrivate(userId: Long): Flux<Permission>
}