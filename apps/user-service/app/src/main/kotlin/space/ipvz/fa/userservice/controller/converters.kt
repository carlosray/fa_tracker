package space.ipvz.fa.userservice.controller

import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.userservice.model.UserDto
import space.ipvz.fa.userservice.model.entity.UserEntity

fun UserEntity.toDto(permissions: List<Permission> = listOf()) =
    UserDto(this.id!!, this.login, this.email, this.firstName, this.lastName, this.created, permissions)