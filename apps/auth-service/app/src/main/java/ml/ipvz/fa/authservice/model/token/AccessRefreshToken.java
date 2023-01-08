package ml.ipvz.fa.authservice.model.token;

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

    public static AccessRefreshToken fromHeaders(String authHeader, String refreshToken) {
        return new AccessRefreshToken(
                authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader,
                refreshToken
        );
    }
}
