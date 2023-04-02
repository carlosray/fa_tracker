package space.ipvz.fa.authservice.model.token;

public record AccessToken(String accessToken) implements Token {
    @Override
    public String getAccessToken() {
        return accessToken;
    }
}
