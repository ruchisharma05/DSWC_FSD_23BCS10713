package com.gatekeeper.filter.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private static final String BASE64_SECRET = "R0FURUtFRVBFUi1BVVRILUZJTFRFUi1LRVktMjAyNi1GT1ItSFMyNTYtVE9LRU5T";

    private final SecretKey signingKey;

    public JwtProvider() {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(BASE64_SECRET));
    }

    public String generateToken(String username, List<String> roles) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(15, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(username)
                .claim("authorities", roles)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public List<String> extractAuthorities(String token) {
        return parseClaims(token).get("authorities", List.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
