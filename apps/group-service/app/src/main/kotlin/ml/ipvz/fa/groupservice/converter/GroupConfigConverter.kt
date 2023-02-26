package ml.ipvz.fa.groupservice.converter

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import ml.ipvz.fa.groupservice.model.entity.GroupConfig
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

class GroupConfigConverter {

    @ReadingConverter
    class Reading(private val mapper: ObjectMapper) : Converter<Json, GroupConfig> {
        override fun convert(source: Json): GroupConfig = mapper.readValue(source.asArray(), GroupConfig::class.java)
    }

    @WritingConverter
    class Writing(private val mapper: ObjectMapper) : Converter<GroupConfig, Json> {
        override fun convert(source: GroupConfig): Json = Json.of(mapper.writeValueAsBytes(source))
    }
}