package space.ipvz.fa.accountservice.converter

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import space.ipvz.fa.accountservice.model.entity.AccountConfig

class AccountConfigConverter {

    @ReadingConverter
    class Reading(private val mapper: ObjectMapper) : Converter<Json, AccountConfig> {
        override fun convert(source: Json): AccountConfig =
            mapper.readValue(source.asArray(), AccountConfig::class.java)
    }

    @WritingConverter
    class Writing(private val mapper: ObjectMapper) : Converter<AccountConfig, Json> {
        override fun convert(source: AccountConfig): Json = Json.of(mapper.writeValueAsBytes(source))
    }
}