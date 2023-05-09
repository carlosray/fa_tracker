package space.ipvz.fa.balanceservice.service.impl

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import space.ipvz.fa.balanceservice.logging.LoggerDelegate
import space.ipvz.fa.balanceservice.service.BalanceService
import space.ipvz.fa.operationservice.client.OperationServiceClient
import java.time.Duration
import java.time.Instant
import java.util.stream.Collectors

private const val UID_SIZE = 8

@Component
class BalanceUpdater(
    private val operationServiceClient: OperationServiceClient,
    private val balanceService: BalanceService,
) {
    private val log by LoggerDelegate()

    @Scheduled(fixedDelayString = "\${update.accounts.delay}")
    fun updateAccountsBalance() {
        val start = Instant.now()
        val uid = RandomStringUtils.randomAlphabetic(UID_SIZE)
        log.info("[$uid] Balance sync has started")
        operationServiceClient.getBalancesUpdate()
            .flatMap { balanceService.applyAmounts(it.accounts) }
            .subscribe {
                log.info("[$uid] Balance sync has ended. took ${Duration.between(start, Instant.now()).toMillis()}ms")
            }
    }

    @Scheduled(fixedDelayString = "\${update.groups.delay}")
    fun updateGroupsBalance() {
        balanceService.findAllGroupIds()
            .collect(Collectors.toSet())
            .flatMap { balanceService.recalculateGroupBalance(it) }
            .subscribe()
    }
}