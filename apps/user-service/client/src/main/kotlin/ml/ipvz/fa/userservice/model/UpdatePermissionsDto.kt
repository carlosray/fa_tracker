package ml.ipvz.fa.userservice.model

import ml.ipvz.fa.authservice.base.permission.Permission

data class UpdatePermissionsDto(
    val permission: Permission,
    val action: Action,
    val userId: Long
) {

    enum class Action {
        GRANT, DELETE;

        fun isGrant(): Boolean = this == GRANT
    }
}

