package ml.ipvz.fa.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ml.ipvz.fa.authservice.model.token.AccessRefreshToken;
import ml.ipvz.fa.authservice.model.token.AccessToken;
import ml.ipvz.fa.authservice.service.AuthenticationService;
import ml.ipvz.fa.authservice.util.TokenUtils;
import ml.ipvz.fa.userservice.model.LoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public Mono<AccessRefreshToken> login(@Valid @RequestBody Mono<LoginDto> loginDto) {
        return authenticationService.login(loginDto);
    }

    @PutMapping("refresh")
    public Mono<AccessRefreshToken> refresh(@Valid @RequestBody Mono<AccessRefreshToken> accessRefreshToken) {
        return authenticationService.refresh(accessRefreshToken);
    }

    @GetMapping("check")
    public Mono<AccessToken> checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return authenticationService.check(Mono.just(new AccessToken(TokenUtils.parseToken(authHeader))));
    }
}
