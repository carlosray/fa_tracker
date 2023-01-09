package ml.ipvz.fa.authservice.service;

import ml.ipvz.fa.authservice.model.token.AccessRefreshToken;
import ml.ipvz.fa.authservice.model.token.AccessToken;
import ml.ipvz.fa.authservice.model.token.Token;
import ml.ipvz.fa.userservice.model.LoginDto;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<AccessRefreshToken> login(Mono<LoginDto> loginDto);

    Mono<AccessRefreshToken> refresh(Mono<AccessRefreshToken> accessRefreshToken);

    Mono<AccessToken> check(Mono<Token> token);
}
