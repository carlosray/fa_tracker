package space.ipvz.fa.accountservice.service.impl

import space.ipvz.fa.accountservice.exception.InvalidGroupIdException
import space.ipvz.fa.accountservice.model.AccountDto
import space.ipvz.fa.accountservice.model.entity.AccountEntity
import space.ipvz.fa.accountservice.repository.AccountRepository
import space.ipvz.fa.accountservice.service.AccountService
import space.ipvz.fa.balanceservice.client.BalanceServiceClient
import space.ipvz.fa.balanceservice.model.BalanceDto
import space.ipvz.fa.cloud.model.Balance
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val balanceServiceClient: BalanceServiceClient
) : AccountService {
    override fun getAccount(groupId: Long, accountId: Long): Mono<AccountDto> =
        accountRepository.findById(accountId)
            .doOnNext { if (it.groupId != groupId) throw InvalidGroupIdException(groupId) }
            .flatMap(::withBalance)

    override fun getAccounts(groupId: Long): Flux<AccountDto> =
        accountRepository.findAllByGroupId(groupId)
            .flatMap(::withBalance)

    private fun withBalance(entity: AccountEntity): Mono<AccountDto> =
        balanceServiceClient.getAccountBalance(entity.groupId, entity.id!!, entity.config.currency)
            .defaultIfEmpty(BalanceDto(entity.id, Balance.empty(entity.config.currency)))
            .map { AccountDto(entity.id, entity.groupId, entity.name, it.balance) }
}