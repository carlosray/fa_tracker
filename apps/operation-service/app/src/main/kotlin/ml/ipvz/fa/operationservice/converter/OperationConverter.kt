package ml.ipvz.fa.operationservice.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ml.ipvz.fa.cloud.model.OperationType
import ml.ipvz.fa.operationservice.model.OperationTypeMapping
import ml.ipvz.fa.operationservice.model.dto.Operation
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

class OperationConverter {

    @ReadingConverter
    class Reading(private val mapper: ObjectMapper) : Converter<Map<String, Any>, Operation> {
        override fun convert(source: Map<String, Any>): Operation? = source["type"]
            ?.let { OperationType.values().firstOrNull { o -> o.name == it.toString() } }
            ?.let { OperationTypeMapping.TypeToOperation[it] }
            ?.let { mapper.convertValue(source, it.java) }
    }

    @WritingConverter
    class Writing(private val mapper: ObjectMapper) : Converter<Operation, Map<String, Any>> {
        override fun convert(source: Operation): Map<String, Any> =
            mapper.convertValue(source, object : TypeReference<Map<String, Any>>() {})
    }
}