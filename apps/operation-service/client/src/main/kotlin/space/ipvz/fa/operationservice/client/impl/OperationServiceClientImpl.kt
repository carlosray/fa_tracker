package space.ipvz.fa.operationservice.client.impl

import space.ipvz.fa.operationservice.client.OperationServiceClient
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

class OperationServiceClientImpl(
    private val operationServiceClient: WebClient,
    private val defaultTimeout: Duration) : OperationServiceClient