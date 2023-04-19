package space.ipvz.fa.categoryservice.controller

import space.ipvz.fa.categoryservice.model.CategoryDto
import space.ipvz.fa.categoryservice.model.entity.CategoryEntity

fun CategoryEntity.toDto() = CategoryDto(this.id!!, this.name, this.type)
