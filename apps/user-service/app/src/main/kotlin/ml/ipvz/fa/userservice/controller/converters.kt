package ml.ipvz.fa.userservice.controller

import ml.ipvz.fa.userservice.model.dto.UserDto
import ml.ipvz.fa.userservice.model.entity.UserEntity

fun UserEntity.toDto(): UserDto = UserDto(this.id!!, this.login, this.email, this.firstName, this.lastName, this.created)