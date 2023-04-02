package space.ipvz.fa.groupservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import space.ipvz.fa.groupservice.converter.GroupConfigConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
class DbConfig {
    @Bean
    fun customConversions(mapper: ObjectMapper): R2dbcCustomConversions? {
        val converters = mutableListOf<Converter<*, *>>()
        converters.add(GroupConfigConverter.Reading(mapper))
        converters.add(GroupConfigConverter.Writing(mapper))
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters)
    }
}