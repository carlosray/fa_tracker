package ml.ipvz.fa.operationservice.exception

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

abstract class BaseResponseStatusException(status: HttpStatusCode, override val message: String) :
    ResponseStatusException(status, message)