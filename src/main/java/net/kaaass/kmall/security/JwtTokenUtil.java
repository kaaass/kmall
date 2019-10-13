package net.kaaass.kmall.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.kaaass.kmall.dto.AuthTokenDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtTokenUtil implements Serializable {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.header}")
    private static String tokenHeader;

    public Optional<String> getUsernameFromToken(String token) {
        return getClaimsFromToken(token).map(Claims::getSubject);
    }

    public Optional<Date> getCreatedDateFromToken(String token) {
        return getClaimsFromToken(token).map(claims -> new Date((long) claims.get(CLAIM_KEY_CREATED)));
    }

    public Optional<Date> getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).map(Claims::getExpiration);
    }

    private Optional<Claims> getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(claims);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private boolean isTokenExpired(String token) {
        Optional<Date> expiration = getExpirationDateFromToken(token);
        return expiration.map(time -> time.before(new Date())).orElse(true);
    }

    public AuthTokenDto generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private AuthTokenDto generateToken(Map<String, Object> claims) {
        Date expiration = generateExpirationDate();
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return new AuthTokenDto(token, expiration);
    }

    public boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public Optional<AuthTokenDto> refreshToken(String token) {
        return getClaimsFromToken(token)
                .map(claims -> {
                    claims.put(CLAIM_KEY_CREATED, new Date());
                    return claims;
                })
                .map(this::generateToken);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        return getUsernameFromToken(token)
                .map(username -> username.equals(user.getUsername()) && !isTokenExpired(token))
                .orElse(false);
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(tokenHeader);
    }

    public String encryptPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }
}

