package ml.ipvz.fa.balanceservice.controller

import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import ml.ipvz.fa.authservice.base.permission.model.Resource
import ml.ipvz.fa.authservice.base.permission.model.Role
import ml.ipvz.fa.balanceservice.model.BalanceDto
import ml.ipvz.fa.balanceservice.service.BalanceService
import ml.ipvz.fa.cloud.model.Currency
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("balance")
class BalanceController(
    private val balanceService: BalanceService
) {

    @GetMapping("group/{groupId}")
    @CheckPermission(resource = Resource.GROUP, role = Role.VIEWER, groupId = "#groupId")
    fun getGroupBalance(
        @PathVariable groupId: Long,
        @RequestParam(required = false) currency: Currency?
    ): Mono<BalanceDto> =
        balanceService.getGroupBalance(groupId, currency).map { BalanceDto(groupId, it) }

    @GetMapping("group/{groupId}/account/{accountId}")
    @CheckPermission(resource = Resource.ACCOUNT, role = Role.VIEWER, groupId = "#groupId")
    fun getAccountBalance(
        @PathVariable groupId: Long,
        @PathVariable accountId: Long,
        @RequestParam(required = false) currency: Currency?
    ): Mono<BalanceDto> =
        balanceService.getAccountBalance(groupId, accountId, currency).map { BalanceDto(accountId, it) }
}