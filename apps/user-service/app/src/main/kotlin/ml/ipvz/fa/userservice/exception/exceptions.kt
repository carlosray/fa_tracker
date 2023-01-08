package ml.ipvz.fa.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserNotFoundException(login: String) : ResponseStatusException(HttpStatus.NOT_FOUND, "User $login not found")

class IncorrectPasswordException : ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password incorrect")