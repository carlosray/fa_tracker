package space.ipvz.fa.groupservice.client.impl

import space.ipvz.fa.groupservice.client.GroupServiceClient
import org.springframework.web.reactive.function.client.WebClient

class GroupServiceClientImpl(private val groupServiceClient: WebClient) : GroupServiceClient