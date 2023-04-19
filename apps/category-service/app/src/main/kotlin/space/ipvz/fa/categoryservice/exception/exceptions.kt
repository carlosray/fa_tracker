package space.ipvz.fa.categoryservice.exception

import org.springframework.http.HttpStatus
import space.ipvz.fa.error.handling.exception.BaseResponseStatusException

class CategoryNotFoundException(id: Long) : BaseResponseStatusException(HttpStatus.NOT_FOUND, "Category $id not found")