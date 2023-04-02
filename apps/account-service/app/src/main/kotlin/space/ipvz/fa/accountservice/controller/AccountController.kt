package space.ipvz.fa.accountservice.controller

import space.ipvz.fa.accountservice.model.AccountDto
import space.ipvz.fa.accountservice.service.AccountService
import space.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import space.ipvz.fa.authservice.base.permission.model.Resource
import space.ipvz.fa.authservice.base.permission.model.Role
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("group/{groupId}/{accountId}")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupId = "#groupId")
    fun getAccount(@PathVariable groupId: Long, @PathVariable accountId: Long): Mono<AccountDto> =
        accountService.getAccount(groupId, accountId)

    @GetMapping("group/{groupId}")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupId = "#groupId")
    fun getAccounts(@PathVariable groupId: Long): Flux<AccountDto> =
        accountService.getAccounts(groupId)
}