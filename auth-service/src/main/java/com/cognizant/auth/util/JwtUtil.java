package com.eauction.authservice.util;

import com.eauction.authservice.entity.Signin;
import com.eauction.authservice.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public Claims getClaims(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return body;
        } catch (Exception ex) {
            log.error("Exception while getting claims info {}", ex.getMessage());
        }
        return null;
    }

    public String generateToken(Signin signin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", signin.getId());
        claims.put("username", signin.getUsername());
        claims.put("role", signin.getRole());

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenValidity;

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(signin.getUsername())
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException ex) {
            log.error("Exception while validating token info {}", ex.getMessage());
            throw new JwtAuthenticationException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Exception while validating token info {}", ex.getMessage());
            throw new JwtAuthenticationException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Exception while validating token info {}", ex.getMessage());
            throw new JwtAuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Exception while validating token info {}", ex.getMessage());
            throw new JwtAuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("Exception while validating token info {}", ex.getMessage());
            throw new JwtAuthenticationException("JWT claims string is empty.");
        }
    }
}
