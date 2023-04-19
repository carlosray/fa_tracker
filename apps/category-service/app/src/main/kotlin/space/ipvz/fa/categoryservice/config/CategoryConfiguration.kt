package space.ipvz.fa.categoryservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import space.ipvz.fa.categoryservice.model.DefaultConfig

@Configuration
class CategoryConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "default")
    fun default(): DefaultConfig = DefaultConfig()
}