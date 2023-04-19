package space.ipvz.fa.categoryservice.repository

import space.ipvz.fa.categoryservice.model.entity.CategoryEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CategoryRepository : R2dbcRepository<CategoryEntity, Long> {
    fun findByGroupId(groupId: Long): Flux<CategoryEntity>
    fun deleteByGroupId(groupId: Long): Mono<Void>
}