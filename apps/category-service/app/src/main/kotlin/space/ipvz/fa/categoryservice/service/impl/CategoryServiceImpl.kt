package space.ipvz.fa.categoryservice.service.impl

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import space.ipvz.fa.async.service.EventService
import space.ipvz.fa.categoryservice.exception.CategoryNotFoundException
import space.ipvz.fa.categoryservice.model.CategoryDto
import space.ipvz.fa.categoryservice.model.CreateCategoryDto
import space.ipvz.fa.categoryservice.model.DefaultConfig
import space.ipvz.fa.categoryservice.model.entity.CategoryConfig
import space.ipvz.fa.categoryservice.model.entity.CategoryEntity
import space.ipvz.fa.categoryservice.repository.CategoryRepository
import space.ipvz.fa.categoryservice.service.CategoryService

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val eventService: EventService,
    private val defaultConfig: DefaultConfig
) : CategoryService {

    @PostConstruct
    private fun postConstruct() {
        //on group created
        eventService.group.created.receive().subscribe { event ->
            if (event.createDefaultCategories) {
                val categories = defaultConfig.categories.map { CreateCategoryDto(it.name, it.type) }
                create(event.groupId, categories).subscribe()
            }
        }

        //on group deleted
        eventService.group.deleted.receive().subscribe { event ->
            categoryRepository.deleteByGroupId(event.groupId).subscribe()
        }
    }

    override fun get(groupId: Long): Flux<CategoryEntity> = categoryRepository.findByGroupId(groupId)

    override fun get(groupId: Long, categoryId: Long): Mono<CategoryEntity> =
        categoryRepository.findById(categoryId).filter { it.groupId == groupId }

    override fun create(
        groupId: Long,
        categories: List<CreateCategoryDto>
    ): Flux<CategoryEntity> = categoryRepository.saveAll(categories.map {
        CategoryEntity(
            name = it.name,
            config = CategoryConfig(),
            type = it.type,
            groupId = groupId
        )
    })

    override fun update(category: CategoryDto): Mono<CategoryEntity> =
        categoryRepository.findById(category.id)
            .flatMap { categoryRepository.save(it.copy(name = category.name, type = category.type)) }
            .switchIfEmpty { Mono.error { CategoryNotFoundException(category.id) } }

    override fun delete(id: Long): Mono<Void> = categoryRepository.deleteById(id)
}