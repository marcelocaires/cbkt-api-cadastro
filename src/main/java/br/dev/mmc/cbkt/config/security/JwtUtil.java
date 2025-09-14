package br.dev.mmc.cbkt.config.security;

import javax.crypto.SecretKey;  // <- importante

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secretBase64;

    @Value("${app.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }

    public String validateAndGetSubject(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
