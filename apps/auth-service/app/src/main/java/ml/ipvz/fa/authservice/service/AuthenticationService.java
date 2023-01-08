package ml.ipvz.fa.authservice.service;

import ml.ipvz.fa.authservice.model.dto.LoginDto;
import ml.ipvz.fa.authservice.model.token.AccessRefreshToken;
import ml.ipvz.fa.authservice.model.token.AccessToken;
import ml.ipvz.fa.authservice.model.token.Token;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<AccessRefreshToken> login(Mono<LoginDto> loginDto);

    Mono<AccessRefreshToken> refresh(Mono<AccessRefreshToken> accessRefreshToken);

    Mono<AccessToken> check(Mono<Token> token);
}
