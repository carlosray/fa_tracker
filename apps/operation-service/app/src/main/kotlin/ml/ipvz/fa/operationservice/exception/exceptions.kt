package ml.ipvz.fa.operationservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

abstract class BaseResponseStatusException(status: HttpStatusCode, override val message: String) :
    ResponseStatusException(status, message)

class IdMustBeNullException :
    BaseResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be null to create new operation")