package space.ipvz.fa.operationservice.model

import space.ipvz.fa.cloud.model.OperationType
import space.ipvz.fa.operationservice.model.dto.Expense
import space.ipvz.fa.operationservice.model.dto.Income
import space.ipvz.fa.operationservice.model.dto.Operation
import space.ipvz.fa.operationservice.model.dto.Transfer
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