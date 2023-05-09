package space.ipvz.fa.operationservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto
import space.ipvz.fa.operationservice.service.BalanceService

@RestController
@RequestMapping("private")
class PrivateController(
    private val balanceService: BalanceService
) {

    @GetMapping("operations/balance")
    fun getBalances(): Mono<UpdateAmountsDto> = balanceService.getBalances()
}