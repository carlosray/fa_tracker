package ml.ipvz.fa.authservice.service.impl;

import ml.ipvz.fa.authservice.service.RefreshTokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Override
    public Mono<String> generate(int length) {
        return Mono.just(RandomStringUtils.randomAlphanumeric(length));
    }
}
