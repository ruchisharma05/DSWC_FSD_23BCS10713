package com.fortress.auth.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtProviderTest {

    private final JwtProvider jwtProvider = new JwtProvider();

    @Test
    void shouldGenerateJwtWithExpectedClaimsAndFifteenMinuteValidity() {
        String token = jwtProvider.generateToken("arthur", List.of("ROLE_USER", "ROLE_ADMIN"));

        var claims = jwtProvider.parseClaims(token);

        assertEquals("arthur", claims.getSubject());
        assertIterableEquals(List.of("ROLE_USER", "ROLE_ADMIN"), claims.get("authorities", List.class));
        assertEquals(Duration.ofMinutes(15), Duration.between(claims.getIssuedAt().toInstant(), claims.getExpiration().toInstant()));
    }

    @Test
    void shouldExtractUsernameFromValidToken() {
        String token = jwtProvider.generateToken("guinevere", List.of("ROLE_USER"));

        assertEquals("guinevere", jwtProvider.extractUsername(token));
    }

    @Test
    void shouldPropagateExpiredJwtExceptionForExpiredTokens() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtProvider.BASE64_SECRET));
        Instant issuedAt = Instant.now().minus(20, ChronoUnit.MINUTES);
        Instant expiration = issuedAt.plus(15, ChronoUnit.MINUTES);

        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .subject("lancelot")
                .claim("authorities", List.of("ROLE_USER"))
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(key)
                .compact();

        assertThrows(ExpiredJwtException.class, () -> jwtProvider.extractUsername(expiredToken));
    }

    @Test
    void shouldPropagateSignatureExceptionForTamperedTokens() {
        String token = jwtProvider.generateToken("merlin", List.of("ROLE_USER"));
        String[] parts = token.split("\\.");
        String signature = parts[2];
        String tamperedSignature = (signature.charAt(0) == 'A' ? 'B' : 'A') + signature.substring(1);
        String tamperedToken = parts[0] + "." + parts[1] + "." + tamperedSignature;

        assertThrows(SignatureException.class, () -> jwtProvider.extractUsername(tamperedToken));
    }
}
