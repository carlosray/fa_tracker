package ml.ipvz.fa.userservice.controller

import jakarta.validation.Valid
import ml.ipvz.fa.userservice.model.dto.AuthDto
import ml.ipvz.fa.userservice.model.dto.RegisterDto
import ml.ipvz.fa.userservice.model.dto.UserDto
import ml.ipvz.fa.userservice.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("users")
class UserController(val userService: UserService) {

    @PostMapping("login")
    fun login(@RequestBody @Valid authDto: AuthDto): Mono<UserDto> =
        userService.login(authDto).map { it.toDto() }

    @PostMapping
    fun register(@RequestBody @Valid registerDto: RegisterDto): Mono<UserDto> =
        userService.register(registerDto).map { it.toDto() }

    @GetMapping
    fun getAll(): Flux<UserDto> = userService.getAll().map { it.toDto() }
}