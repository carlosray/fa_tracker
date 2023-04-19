package space.ipvz.fa.categoryservice.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import space.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import space.ipvz.fa.authservice.base.permission.model.Resource
import space.ipvz.fa.authservice.base.permission.model.Role
import space.ipvz.fa.categoryservice.model.CategoryDto
import space.ipvz.fa.categoryservice.model.CreateCategoryDto
import space.ipvz.fa.categoryservice.service.CategoryService

@RestController
@RequestMapping("categories")
class CategoryController(
    private val categoryService: CategoryService,
) {

    @GetMapping("group/{groupId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.VIEWER, groupId = "#groupId")
    fun getCategories(@PathVariable groupId: Long): Flux<CategoryDto> =
        categoryService.get(groupId).map { it.toDto() }

    @GetMapping("group/{groupId}/{categoryId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.VIEWER, groupId = "#groupId")
    fun getCategory(@PathVariable groupId: Long, @PathVariable categoryId: Long): Mono<CategoryDto> =
        categoryService.get(groupId, categoryId).map { it.toDto() }

    @PostMapping("group/{groupId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.ADMIN, groupId = "#groupId")
    fun createCategory(
        @PathVariable groupId: Long,
        @RequestBody category: CreateCategoryDto
    ): Mono<CategoryDto> = categoryService.create(groupId, listOf(category)).map { it.toDto() }.toMono()

    @PutMapping("group/{groupId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.EDITOR, groupId = "#groupId")
    fun updateCategory(@PathVariable groupId: Long, @RequestBody category: CategoryDto): Mono<CategoryDto> =
        categoryService.update(category).map { it.toDto() }

    @DeleteMapping("group/{groupId}/{categoryId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.ADMIN, groupId = "#groupId")
    fun deleteCategory(@PathVariable groupId: Long, @PathVariable categoryId: Long): Mono<Void> =
        categoryService.delete(categoryId)
}