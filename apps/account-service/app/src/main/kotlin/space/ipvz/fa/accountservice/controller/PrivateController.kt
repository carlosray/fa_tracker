package space.ipvz.fa.accountservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import space.ipvz.fa.accountservice.service.AccountService
import space.ipvz.fa.cloud.model.Currency

@RestController
@RequestMapping("private")
class PrivateController(
    private val accountService: AccountService
) {

    @GetMapping("accounts/group/{groupId}/{accountId}/currency")
    fun getAccountCurrency(@PathVariable groupId: Long, @PathVariable accountId: Long): Mono<Currency> =
        accountService.getConfig(groupId, accountId).map { it.currency }
}