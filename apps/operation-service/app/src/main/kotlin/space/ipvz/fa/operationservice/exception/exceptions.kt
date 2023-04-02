package space.ipvz.fa.operationservice.exception

import space.ipvz.fa.error.handling.exception.BaseResponseStatusException
import org.springframework.http.HttpStatus

class IdMustBeNullException :
    BaseResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be null to create new operation")