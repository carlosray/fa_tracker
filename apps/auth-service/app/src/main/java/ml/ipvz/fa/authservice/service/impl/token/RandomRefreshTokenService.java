package ml.ipvz.fa.authservice.service.impl.token;

import java.time.Clock;

import lombok.RequiredArgsConstructor;
import ml.ipvz.fa.authservice.base.model.User;
import ml.ipvz.fa.authservice.exception.RefreshTokenExpiredException;
import ml.ipvz.fa.authservice.exception.RefreshTokenInvalidException;
import ml.ipvz.fa.authservice.model.config.TokenConfig;
import ml.ipvz.fa.authservice.model.entity.ClientEntity;
import ml.ipvz.fa.authservice.service.RefreshTokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        } else if (clock.instant().isAfter(client.updated().plus(config.getRefreshDuration()))) {
            throw new RefreshTokenExpiredException();
        }
    }

}
