package ml.ipvz.fa.userservice.controller

import ml.ipvz.fa.authservice.base.permission.Permission
import ml.ipvz.fa.userservice.model.UserDto
import ml.ipvz.fa.userservice.model.entity.UserEntity

fun UserEntity.toDto(permissions: List<Permission> = listOf()) =
    UserDto(this.id!!, this.login, this.email, this.firstName, this.lastName, this.created, permissions)