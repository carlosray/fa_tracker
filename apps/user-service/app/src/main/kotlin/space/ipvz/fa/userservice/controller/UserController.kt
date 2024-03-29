package space.ipvz.fa.userservice.controller

import jakarta.validation.Valid
import space.ipvz.fa.userservice.model.LoginDto
import space.ipvz.fa.userservice.model.RegisterDto
import space.ipvz.fa.userservice.model.UserDto
import space.ipvz.fa.userservice.service.PermissionService
import space.ipvz.fa.userservice.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("users")
class UserController(
    val userService: UserService,
    val permissionService: PermissionService
) {

    @PostMapping("login")
    fun login(@RequestBody @Valid authDto: LoginDto): Mono<UserDto> =
        userService.login(authDto)
            .flatMap { user ->
                permissionService.findByUser(user.id!!).collectList()
                    .map { user.toDto(it) }
            }

    @PostMapping
    fun register(@RequestBody @Valid registerDto: RegisterDto): Mono<UserDto> =
        userService.register(registerDto).map { it.toDto() }

    @GetMapping
    fun getAll(): Flux<UserDto> = userService.getAll().map { it.toDto() }

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): Mono<UserDto> = userService.get(id).map { it.toDto() }
}