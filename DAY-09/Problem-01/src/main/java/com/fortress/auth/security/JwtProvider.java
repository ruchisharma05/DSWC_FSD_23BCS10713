package com.fortress.auth.security;

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

    static final String BASE64_SECRET = "Rk9SVFJFU1MtQVVUSC1TSUdOSU5HLUtFWS0yMDI2LUZPUi1IUzI1Ni1UT0tFTlM=";

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

    Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    SecretKey getSigningKey() {
        return signingKey;
    }
}
