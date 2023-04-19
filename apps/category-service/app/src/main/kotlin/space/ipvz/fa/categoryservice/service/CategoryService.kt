package space.ipvz.fa.categoryservice.service;

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.categoryservice.model.CategoryDto
import space.ipvz.fa.categoryservice.model.CreateCategoryDto
import space.ipvz.fa.categoryservice.model.entity.CategoryEntity

interface CategoryService {
    fun get(groupId: Long): Flux<CategoryEntity>
    fun get(groupId: Long, categoryId: Long): Mono<CategoryEntity>
    fun create(groupId: Long, categories: List<CreateCategoryDto>): Flux<CategoryEntity>
    fun update(category: CategoryDto): Mono<CategoryEntity>
    fun delete(id: Long): Mono<Void>
}
