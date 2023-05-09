package space.ipvz.fa.accountservice.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.accountservice.model.AccountDto
import space.ipvz.fa.accountservice.model.UpdateAccountDto
import space.ipvz.fa.accountservice.service.AccountService
import space.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import space.ipvz.fa.authservice.base.permission.model.Resource
import space.ipvz.fa.authservice.base.permission.model.Role
import space.ipvz.fa.cloud.model.Currency

@RestController
@RequestMapping("accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("group/{groupId}/{accountId}")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupId = "#groupId")
    fun getAccount(@PathVariable groupId: Long, @PathVariable accountId: Long): Mono<AccountDto> =
        accountService.get(groupId, accountId)

    @GetMapping("group/{groupId}/{accountId}/currency")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupId = "#groupId")
    fun getAccountCurrency(@PathVariable groupId: Long, @PathVariable accountId: Long): Mono<Currency> =
        accountService.getConfig(groupId, accountId).map { it.currency }

    @GetMapping("group/{groupId}")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupId = "#groupId")
    fun getAccounts(@PathVariable groupId: Long): Flux<AccountDto> =
        accountService.get(groupId)

    @PostMapping("group/{groupId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.ADMIN, groupId = "#groupId")
    fun createAccount(
        @PathVariable groupId: Long,
        @RequestBody account: UpdateAccountDto
    ): Mono<AccountDto> = accountService.create(groupId, account)

    @PutMapping("group/{groupId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.EDITOR, groupId = "#groupId")
    fun updateAccount(@PathVariable groupId: Long, @RequestBody account: UpdateAccountDto): Mono<AccountDto> =
        accountService.update(account)

    @DeleteMapping("group/{groupId}/{accountId}")
    @CheckPermission(resource = Resource.CATEGORY, role = Role.ADMIN, groupId = "#groupId")
    fun deleteAccount(@PathVariable groupId: Long, @PathVariable accountId: Long): Mono<Void> =
        accountService.delete(accountId)
}