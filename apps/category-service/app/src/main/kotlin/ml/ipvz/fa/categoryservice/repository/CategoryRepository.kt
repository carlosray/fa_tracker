package ml.ipvz.fa.categoryservice.repository

import ml.ipvz.fa.categoryservice.model.entity.CategoryEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : R2dbcRepository<CategoryEntity, Long> {
}