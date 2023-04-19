package space.ipvz.fa.accountservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import space.ipvz.fa.accountservice.converter.AccountConfigConverter
import space.ipvz.fa.authservice.base.util.SecurityContextUtils

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
class DbConfig {
    @Bean
    fun customConversions(mapper: ObjectMapper): R2dbcCustomConversions {
        val converters = mutableListOf<Converter<*, *>>()
        converters.add(AccountConfigConverter.Reading(mapper))
        converters.add(AccountConfigConverter.Writing(mapper))
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters)
    }

    @Bean
    fun auditorAware(): ReactiveAuditorAware<String> {
        return ReactiveAuditorAware { SecurityContextUtils.getUsername() }
    }
}