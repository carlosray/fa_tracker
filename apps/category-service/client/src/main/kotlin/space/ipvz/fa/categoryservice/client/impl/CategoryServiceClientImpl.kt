package space.ipvz.fa.categoryservice.client.impl

import space.ipvz.fa.categoryservice.client.CategoryServiceClient
import org.springframework.web.reactive.function.client.WebClient

class CategoryServiceClientImpl(private val categoryServiceClient: WebClient) : CategoryServiceClient