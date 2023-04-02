package space.ipvz.fa.userservice.exception

import space.ipvz.fa.error.handling.exception.BaseResponseStatusException
import org.springframework.http.HttpStatus

class UserNotFoundException(login: String) : BaseResponseStatusException(HttpStatus.NOT_FOUND, "User $login not found")

class IncorrectPasswordException : BaseResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password incorrect")