package ml.ipvz.fa.accountservice.client.impl

import ml.ipvz.fa.accountservice.client.AccountServiceClient
import org.springframework.web.reactive.function.client.WebClient

class AccountServiceClientImpl(private val categoryServiceClient: WebClient) : AccountServiceClient