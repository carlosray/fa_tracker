package space.ipvz.fa.groupservice.client.impl

import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import space.ipvz.fa.groupservice.client.GroupServiceClient
import space.ipvz.fa.groupservice.model.GroupDto

class GroupServiceClientImpl(private val groupServiceClient: WebClient) : GroupServiceClient {
    override fun getAllGroupsPrivate(withBalance: Boolean): Flux<GroupDto> =
        groupServiceClient.get()
            .uri("/private/groups?withBalance=$withBalance")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToFlux(GroupDto::class.java)

    override fun getGroupsPrivate(ids: Set<Long>, withBalance: Boolean): Flux<GroupDto> =
        groupServiceClient.get()
            .uri("/private/groups?withBalance=$withBalance&ids=${ids.joinToString(",")}")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToFlux(GroupDto::class.java)
}