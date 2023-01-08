package ml.ipvz.fa.authservice.util;

public class TokenUtils {
    private TokenUtils() {
    }

    public static String parseToken(String authHeader) {
        return authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
    }
}
