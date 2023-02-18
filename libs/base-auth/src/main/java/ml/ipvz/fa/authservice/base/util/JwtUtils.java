package ml.ipvz.fa.authservice.base.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import ml.ipvz.fa.authservice.base.model.CustomClaims;
import ml.ipvz.fa.authservice.base.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private JwtUtils() {
    }

    public static String generate(String issuer, User user, Duration validDuration, byte[] key) {
        Map<String, Object> claims = Map.of(
                CustomClaims.USER_ID, user.id(),
                CustomClaims.ROLES, user.roles(),
                Claims.ID, UUID.randomUUID(),
                Claims.SUBJECT, user.login()
        );

        Instant now = Instant.now();
        Instant expire = now.plus(validDuration);

        return Jwts.builder()
                .setIssuer(issuer)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expire))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public static User parse(String token, byte[] key) throws JwtException {
        Claims claims = parseClaims(token, key);
        return getUserFromClaims(claims);
    }

    private static Claims parseClaims(String token, byte[] key) throws JwtException {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();
    }

    public static User getUserFromClaims(Claims claims) {
        return new User(
                claims.get(CustomClaims.USER_ID, Long.class),
                claims.getSubject(),
                (List<String>) claims.get(CustomClaims.ROLES, List.class)
        );
    }

    public static Authentication getAuthentication(String token, byte[] key) {
        User user = parse(token, key);

        List<? extends GrantedAuthority> authorities = user.roles().stream().map(SimpleGrantedAuthority::new).toList();

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    public static boolean validate(String token, byte[] key) {
        try {
            parseClaims(token, key);
            return true;
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace.", e);
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token.");
            log.trace("Expired JWT token trace.", e);
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace.", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace.", e);
        } catch (JwtException e) {
            log.warn(e.getMessage());
            log.trace("JWT error trace.", e);
        }
        return false;
    }
}
