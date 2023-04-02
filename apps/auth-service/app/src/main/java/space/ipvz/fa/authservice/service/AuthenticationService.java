package space.ipvz.fa.authservice.service;

import space.ipvz.fa.authservice.model.token.AccessRefreshToken;
import space.ipvz.fa.authservice.model.token.AccessToken;
import space.ipvz.fa.authservice.model.token.Token;
import space.ipvz.fa.userservice.model.LoginDto;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<AccessRefreshToken> login(Mono<LoginDto> loginDto);

    Mono<AccessRefreshToken> refresh(Mono<AccessRefreshToken> accessRefreshToken);

    Mono<AccessToken> check(Mono<Token> token);
}
