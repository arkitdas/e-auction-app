package com.cognizant.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.cognizant.gateway.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey("Authorization")) {

//                ServerHttpResponse response = exchange.getResponse();
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//
//                return response.setComplete();
                throw new AuthenticationServiceException("Failed to authorize, no authorization header found");
            };

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            try {
                jwtUtil.validateToken(token);
            } catch (Exception ex) {
//                ServerHttpResponse response = exchange.getResponse();
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//
//                return response.setComplete();
                throw new AuthenticationServiceException("Failed to authorize, invalid jwt token provided");
            }

            Claims claims = jwtUtil.getClaims(token);

            String role = String.valueOf(claims.get("role"));
            String path = request.getURI().getPath();
            if (!(path.contains("/seller") && role.equals("ROLE_SELLER")) &&
                    !(path.contains("/buyer") && role.equals("ROLE_BUYER"))) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);

                return response.setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

        private String name;
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
