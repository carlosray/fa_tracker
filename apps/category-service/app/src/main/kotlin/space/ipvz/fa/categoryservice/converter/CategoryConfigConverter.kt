package space.ipvz.fa.categoryservice.converter

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import space.ipvz.fa.categoryservice.model.entity.CategoryConfig

class CategoryConfigConverter {

    @ReadingConverter
    class Reading(private val mapper: ObjectMapper) : Converter<Json, CategoryConfig> {
        override fun convert(source: Json): CategoryConfig =
            mapper.readValue(source.asArray(), CategoryConfig::class.java)
    }

    @WritingConverter
    class Writing(private val mapper: ObjectMapper) : Converter<CategoryConfig, Json> {
        override fun convert(source: CategoryConfig): Json = Json.of(mapper.writeValueAsBytes(source))
    }
}