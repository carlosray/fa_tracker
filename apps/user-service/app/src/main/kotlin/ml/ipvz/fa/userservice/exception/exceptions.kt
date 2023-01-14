package ml.ipvz.fa.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

abstract class BaseResponseStatusException(status: HttpStatusCode, override val message: String) :
    ResponseStatusException(status, message)

class UserNotFoundException(login: String) : BaseResponseStatusException(HttpStatus.NOT_FOUND, "User $login not found")

class IncorrectPasswordException : BaseResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password incorrect")