package ml.ipvz.fa.operationservice.model

import ml.ipvz.fa.cloud.model.OperationType
import ml.ipvz.fa.operationservice.model.dto.Expense
import ml.ipvz.fa.operationservice.model.dto.Income
import ml.ipvz.fa.operationservice.model.dto.Operation
import ml.ipvz.fa.operationservice.model.dto.Transfer
import kotlin.reflect.KClass

class OperationTypeMapping {
    companion object TypeToOperation {
        private val mapping: Map<OperationType, KClass<out Operation>> = mapOf(
            OperationType.INCOME to Income::class,
            OperationType.EXPENSE to Expense::class,
            OperationType.TRANSFER to Transfer::class
        )

        operator fun get(type: OperationType): KClass<out Operation>? = mapping[type]
    }
}