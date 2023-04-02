package space.ipvz.fa.userservice.service.impl

import space.ipvz.fa.userservice.exception.IncorrectPasswordException
import space.ipvz.fa.userservice.exception.UserNotFoundException
import space.ipvz.fa.userservice.model.LoginDto
import space.ipvz.fa.userservice.model.RegisterDto
import space.ipvz.fa.userservice.model.entity.UserEntity
import space.ipvz.fa.userservice.repository.UserRepository
import space.ipvz.fa.userservice.service.PasswordService
import space.ipvz.fa.userservice.service.UserService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Instant

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordService: PasswordService
) : UserService {
    override fun login(auth: LoginDto): Mono<UserEntity> =
        findUser(auth.login)
            .switchIfEmpty { Mono.error(UserNotFoundException(auth.login)) }
            .filter { passwordService.matches(auth.password, it.password) }
            .switchIfEmpty { Mono.error(IncorrectPasswordException()) }

    private fun findUser(login: String, includeDeleted: Boolean = false): Mono<UserEntity> =
        userRepository.findByLogin(login).filter { includeDeleted || it.deleted == null }

    override fun register(register: RegisterDto): Mono<UserEntity> {
        val newUser = UserEntity(
            id = null,
            login = register.login,
            email = register.email,
            password = passwordService.encode(register.password),
            firstName = register.firstName,
            lastName = register.lastName,
            deleted = null,
            created = Instant.now()
        )
        return userRepository.save(newUser)
    }

    override fun getAll(): Flux<UserEntity> = userRepository.findAll()

    override fun get(id: Long): Mono<UserEntity> = userRepository.findById(id)
}