package space.ipvz.fa.accountservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import space.ipvz.fa.accountservice.exception.AccountNotFoundException
import space.ipvz.fa.accountservice.exception.InvalidGroupIdException
import space.ipvz.fa.accountservice.logging.LoggerDelegate
import space.ipvz.fa.accountservice.model.AccountDto
import space.ipvz.fa.accountservice.model.UpdateAccountDto
import space.ipvz.fa.accountservice.model.entity.AccountConfig
import space.ipvz.fa.accountservice.model.entity.AccountEntity
import space.ipvz.fa.accountservice.repository.AccountRepository
import space.ipvz.fa.accountservice.service.AccountService
import space.ipvz.fa.async.model.event.AccountDeletedEvent
import space.ipvz.fa.async.service.send
import space.ipvz.fa.balanceservice.client.BalanceServiceClient
import space.ipvz.fa.balanceservice.model.BalanceDto
import space.ipvz.fa.cloud.model.Balance

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val balanceServiceClient: BalanceServiceClient
) : AccountService {
    private val log by LoggerDelegate()

    override fun get(groupId: Long, accountId: Long): Mono<AccountDto> =
        accountRepository.findById(accountId)
            .doOnNext { if (it.groupId != groupId) throw InvalidGroupIdException(groupId) }
            .flatMap(::withBalance)

    override fun get(groupId: Long): Flux<AccountDto> =
        accountRepository.findAllByGroupId(groupId)
            .flatMap(::withBalance)

    override fun getConfig(groupId: Long, accountId: Long): Mono<AccountConfig> =
        accountRepository.findById(accountId)
            .doOnNext { if (it.groupId != groupId) throw InvalidGroupIdException(groupId) }
            .map { it.config }

    override fun create(groupId: Long, account: UpdateAccountDto): Mono<AccountDto> {
        val entity = AccountEntity(
            name = account.name,
            groupId = groupId,
            config = AccountConfig(currency = account.currency)
        )
        return accountRepository.save(entity).flatMap(::withBalance)
    }

    override fun update(account: UpdateAccountDto): Mono<AccountDto> = accountRepository.findById(account.id!!)
        .switchIfEmpty { Mono.error { AccountNotFoundException(account.id!!) } }
        .flatMap { accountRepository.save(it.copy(config = it.config.copy(currency = account.currency), name = account.name)) }
        .flatMap(::withBalance)

    override fun delete(accountId: Long): Mono<Void> = accountRepository.findById(accountId)
        .flatMap {
            accountRepository.deleteById(it.id!!)
                .doOnNext { log.info("Deleted account $accountId") }
                .then(AccountDeletedEvent(it.groupId, it.id).send())
        }

    private fun withBalance(entity: AccountEntity): Mono<AccountDto> =
        balanceServiceClient.getAccountBalance(entity.groupId, entity.id!!, entity.config.currency)
            .defaultIfEmpty(BalanceDto(entity.id, Balance.empty(entity.config.currency)))
            .map { AccountDto(entity.id, entity.groupId, entity.name, it.balance) }
}