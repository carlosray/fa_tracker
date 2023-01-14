package ml.ipvz.fa.operationservice.client.impl

import ml.ipvz.fa.operationservice.client.OperationServiceClient
import org.springframework.web.reactive.function.client.WebClient

class OperationServiceClientImpl(private val operationServiceClient: WebClient) : OperationServiceClient