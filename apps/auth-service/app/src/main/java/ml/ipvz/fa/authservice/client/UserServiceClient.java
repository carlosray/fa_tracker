package ml.ipvz.fa.authservice.client;

import ml.ipvz.fa.authservice.model.dto.LoginDto;
import ml.ipvz.fa.authservice.model.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserServiceClient {
    Mono<UserDto> login(Mono<LoginDto> loginDto);
}
