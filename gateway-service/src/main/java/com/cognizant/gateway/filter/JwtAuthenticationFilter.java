package com.cognizant.gateway.filter;

import com.cognizant.gateway.payload.ApiResponse;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.cognizant.gateway.util.JwtUtil;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Flux;

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
                ApiResponse response = ApiResponse.ofFailure(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access, No Authorization header found");

                ServerHttpResponse httpResponse = exchange.getResponse();
                httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                httpResponse.writeWith(Flux.just(
                        new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(response))
                ));
                return httpResponse.setComplete();
            };

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            try {
                jwtUtil.validateToken(token);
            } catch (Exception ex) {
                ApiResponse response = ApiResponse.ofFailure(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access, Invalid token");

                ServerHttpResponse httpResponse = exchange.getResponse();
                httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                httpResponse.writeWith(Flux.just(
                        new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(response))
                ));
                return httpResponse.setComplete();
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
