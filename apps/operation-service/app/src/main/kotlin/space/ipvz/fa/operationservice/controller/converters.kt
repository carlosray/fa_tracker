package space.ipvz.fa.operationservice.controller

import space.ipvz.fa.operationservice.model.dto.OperationEventDto
import space.ipvz.fa.operationservice.model.entity.OperationEntity

fun OperationEntity.toDto() = OperationEventDto(
    this.id,
    this.groupId,
    this.description,
    this.detail,
    this.created,
    this.createdBy,
    this.modified,
    this.modifiedBy
)
