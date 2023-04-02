package space.ipvz.fa.authservice.model.token;

import jakarta.validation.constraints.NotBlank;

public record AccessRefreshToken(
        @NotBlank
        String accessToken,
        @NotBlank
        String refreshToken
) implements Token {
    @Override
    public String getAccessToken() {
        return accessToken;
    }
}
