package space.ipvz.fa.authservice.service.impl.token;

import java.time.Clock;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.ipvz.fa.authservice.base.model.User;
import space.ipvz.fa.authservice.exception.RefreshTokenExpiredException;
import space.ipvz.fa.authservice.exception.RefreshTokenInvalidException;
import space.ipvz.fa.authservice.model.config.TokenConfig;
import space.ipvz.fa.authservice.model.entity.ClientEntity;
import space.ipvz.fa.authservice.service.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class RandomRefreshTokenService implements RefreshTokenService {

    private final TokenConfig config;
    private final Clock clock;

    @Override
    public Mono<String> generate(User user) {
        return Mono.just(RandomStringUtils.randomAlphanumeric(config.getRefreshLength()));
    }

    @Override
    public void validate(String token, ClientEntity client) throws RuntimeException {
        if (!client.refreshToken().equals(token)) {
            throw new RefreshTokenInvalidException(token);
        } else if (clock.instant().isAfter(client.updated().plus(config.getValidDuration()).plus(config.getRefreshDuration()))) {
            throw new RefreshTokenExpiredException();
        }
    }

}
