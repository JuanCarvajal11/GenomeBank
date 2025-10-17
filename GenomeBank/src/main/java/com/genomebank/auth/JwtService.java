package com.genomebank.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey secretKey;

    private final long expMinutes;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.exp-min}") long expMinutes) {
        byte[] raw = secret.matches("^[A-Za-z0-9+/=]+$") ? Decoders.BASE64.decode(secret) : secret.getBytes();
        this.secretKey = Keys.hmacShaKeyFor(raw);
        this.expMinutes = expMinutes;
    }

    public String generate(String subject, List<String> roles) {
        Instant now = Instant.now();
        List<String> safeRoles = roles == null ? List.of() : roles;
        return Jwts.builder()
                .subject(subject)
                .claims(Map.of("roles", safeRoles))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
                .signWith(secretKey)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Claims parse(String token) {
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
