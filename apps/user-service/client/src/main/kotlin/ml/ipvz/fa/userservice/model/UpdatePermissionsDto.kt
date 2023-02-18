package ml.ipvz.fa.userservice.model

import ml.ipvz.fa.authservice.base.permission.Permission

data class UpdatePermissionsDto(
    val add: Set<Permission> = setOf(),
    val delete: Set<Permission> = setOf()
)
