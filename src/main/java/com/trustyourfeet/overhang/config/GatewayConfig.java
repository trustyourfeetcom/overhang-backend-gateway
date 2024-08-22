package com.trustyourfeet.overhang.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private static final String DEFAULT_AUTH_SERVICE_URL = "http://overhang-backend-auth:8081";
    private static final String DEFAULT_IDENTITY_SERVICE_URL = "http://overhang-backend-identity:8082";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        String authServiceUrl = System.getenv("AUTH_SERVICE_URL") != null
                ? System.getenv("AUTH_SERVICE_URL")
                : DEFAULT_AUTH_SERVICE_URL;
        String identityServiceUrl = System.getenv("IDENTITY_SERVICE_URL") != null
                ? System.getenv("IDENTITY_SERVICE_URL")
                : DEFAULT_IDENTITY_SERVICE_URL;

        return builder.routes()
                .route("auth_route", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("authServiceCircuitBreaker")
                                        .setFallbackUri("forward:/authFallback")))
                        .uri(authServiceUrl))
                .route("identity_route", r -> r
                        .path("/api/identity/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("identityServiceCircuitBreaker")
                                        .setFallbackUri("forward:/identityFallback")))
                        .uri(identityServiceUrl))
                .build();
    }
}
