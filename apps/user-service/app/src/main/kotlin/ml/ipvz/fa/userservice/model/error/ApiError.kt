package ml.ipvz.fa.userservice.model.error

import java.time.Instant

data class ApiError(
    val status: Int,
    val message: String?,
    val timestamp: Instant
)