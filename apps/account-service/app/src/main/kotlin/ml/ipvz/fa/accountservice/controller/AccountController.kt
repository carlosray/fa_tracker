package ml.ipvz.fa.accountservice.controller

import ml.ipvz.fa.accountservice.model.AccountDto
import ml.ipvz.fa.accountservice.service.AccountService
import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import ml.ipvz.fa.authservice.base.permission.model.Resource
import ml.ipvz.fa.authservice.base.permission.model.Role
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("group/{groupId}/{accountId}")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupIdFieldName = "groupId")
    fun getAccount(@PathVariable groupId: Long, @PathVariable accountId: Long): Mono<AccountDto> =
        accountService.getAccount(groupId, accountId)
}