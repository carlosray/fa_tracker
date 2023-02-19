package ml.ipvz.fa.accountservice.service.impl

import ml.ipvz.fa.accountservice.exception.InvalidGroupIdException
import ml.ipvz.fa.accountservice.model.entity.AccountEntity
import ml.ipvz.fa.accountservice.repository.AccountRepository
import ml.ipvz.fa.accountservice.service.AccountService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository
) : AccountService {
    override fun getAccount(groupId: Long, accountId: Long): Mono<AccountEntity> =
        accountRepository.findById(accountId)
            .doOnNext { if (it.groupId != groupId) throw InvalidGroupIdException(groupId) }
}