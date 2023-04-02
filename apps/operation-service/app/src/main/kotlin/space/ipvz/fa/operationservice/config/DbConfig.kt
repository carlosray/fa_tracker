package space.ipvz.fa.operationservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import space.ipvz.fa.operationservice.converter.OperationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["space.ipvz.fa.operationservice.repository"])
class DbConfig {

    @Bean
    fun mongoCustomConversions(mapper: ObjectMapper): MongoCustomConversions = MongoCustomConversions(
        listOf(
            OperationConverter.Reading(mapper),
            OperationConverter.Writing(mapper),
        )
    )
}