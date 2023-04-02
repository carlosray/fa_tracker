package space.ipvz.fa.userservice.repository;

import space.ipvz.fa.userservice.model.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono

@Repository
interface UserRepository : R2dbcRepository<UserEntity, Long> {
    fun findByLogin(login: String): Mono<UserEntity>
}
