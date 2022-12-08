package com.cognizant.gateway.filter;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.cognizant.gateway.payload.ApiResponse;
import com.cognizant.gateway.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Flux;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey("Authorization")) {
                ApiResponse response = ApiResponse.ofFailure(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access, No Authorization header found");

                ServerHttpResponse httpResponse = exchange.getResponse();
                httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                try {
					return httpResponse.writeWith(Flux.just(
//                        new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(response))
							new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(objectMapper.writeValueAsBytes(response)))
					));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//                return httpResponse.setComplete();
            };

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            try {
                jwtUtil.validateToken(token);
            } catch (Exception ex) {
                ApiResponse response = ApiResponse.ofFailure(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access, Invalid token");

                ServerHttpResponse httpResponse = exchange.getResponse();
                httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                try {
					return httpResponse.writeWith(Flux.just(
//                        new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(response))
							new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(objectMapper.writeValueAsBytes(response)))
					));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//                return httpResponse.setComplete();
            }

            Claims claims = jwtUtil.getClaims(token);

            String role = "ROLE_"+String.valueOf(claims.get("role"));
            String path = request.getURI().getPath();
            if (!(path.contains("/seller") && role.equals("ROLE_SELLER")) &&
                    !(path.contains("/buyer") && role.equals("ROLE_BUYER")) && 
                    !(path.contains("/product") && (role.equals("ROLE_BUYER") || role.equals("ROLE_SELLER"))) && 
                    !(path.contains("/user") && (role.equals("ROLE_BUYER") || role.equals("ROLE_SELLER"))) &&
                    !(path.contains("/v2/api-docs"))) {
            	ApiResponse response = ApiResponse.ofFailure(HttpStatus.FORBIDDEN.value(), "Unauthorized Access, Invalid token");
            	
                ServerHttpResponse httpResponse = exchange.getResponse();
                httpResponse.setStatusCode(HttpStatus.FORBIDDEN);
                try {
					return httpResponse.writeWith(Flux.just(
//                        new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(response))
							new DefaultDataBufferFactory().wrap(SerializationUtils.serialize(objectMapper.writeValueAsBytes(response)))
					));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//                return response.setComplete();
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
