package com.cognizant.auth.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cognizant.auth.entity.Signin;
import com.cognizant.auth.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public Claims getClaims(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtSecret)
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
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public void validateToken(String token) {
        try {
        	Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
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
