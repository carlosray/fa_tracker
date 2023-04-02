package space.ipvz.fa.categoryservice.repository

import space.ipvz.fa.categoryservice.model.entity.CategoryEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : R2dbcRepository<CategoryEntity, Long> {
}