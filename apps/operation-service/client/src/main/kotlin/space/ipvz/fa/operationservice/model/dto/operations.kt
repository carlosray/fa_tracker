package space.ipvz.fa.operationservice.model.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import space.ipvz.fa.cloud.model.OperationType
import java.math.BigDecimal

private const val INCOME = "INCOME"
private const val EXPENSE = "EXPENSE"
private const val TRANSFER = "TRANSFER"

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Income::class, name = INCOME),
    JsonSubTypes.Type(value = Expense::class, name = EXPENSE),
    JsonSubTypes.Type(value = Transfer::class, name = TRANSFER),
)
sealed class Operation(val type: OperationType)

data class Income(
    val fromCategory: Long,
    val toAccount: Long,
    val amount: BigDecimal
) : Operation(OperationType.INCOME)

data class Expense(
    val fromAccount: Long,
    val toCategory: Long,
    val amount: BigDecimal
) : Operation(OperationType.EXPENSE)

data class Transfer(
    val fromAccount: Long,
    val toAccount: Long,
    val amount: BigDecimal,
    val commission: BigDecimal,
    val rate: BigDecimal?,
    val resultAmount: BigDecimal
) : Operation(OperationType.TRANSFER)

