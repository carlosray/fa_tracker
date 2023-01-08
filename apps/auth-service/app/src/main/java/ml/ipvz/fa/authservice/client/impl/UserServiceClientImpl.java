package ml.ipvz.fa.authservice.client.impl;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.ipvz.fa.authservice.client.UserServiceClient;
import ml.ipvz.fa.authservice.model.dto.LoginDto;
import ml.ipvz.fa.authservice.model.dto.UserDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceClientImpl implements UserServiceClient {

    private final WebClient userServiceClient;

    @Override
    public Mono<UserDto> login(Mono<LoginDto> loginDto) {
        return userServiceClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(loginDto, LoginDto.class)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return response.createError();
                })
                .bodyToMono(UserDto.class)
                .timeout(Duration.ofSeconds(10));
    }
}
