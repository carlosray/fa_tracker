package ml.ipvz.fa.accountservice.exception

import ml.ipvz.fa.error.handling.exception.BaseResponseStatusException
import org.springframework.http.HttpStatus

class InvalidGroupIdException(groupId: Long) :
    BaseResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid group id $groupId")
