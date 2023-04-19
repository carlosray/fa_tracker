package space.ipvz.fa.groupservice.exception

import org.springframework.http.HttpStatus
import space.ipvz.fa.error.handling.exception.BaseResponseStatusException

class GroupNotFoundException(id: Long) : BaseResponseStatusException(HttpStatus.NOT_FOUND, "Group $id not found")