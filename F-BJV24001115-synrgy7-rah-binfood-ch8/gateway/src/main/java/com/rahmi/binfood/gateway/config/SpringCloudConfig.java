package com.rahmi.binfood.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r-> r.path("/users/**")
                        .uri("http://localhost:8083/user-service")

                )
                .route("order-service", r-> r.path("/orders/**")
                        .uri("http://localhost:8084/order-service")
                )
                .build();
    }
}
