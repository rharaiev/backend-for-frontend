package com.course.bff.gateway.config;

import com.course.bff.gateway.filter.ThrottleGatewayFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class Config {

    @Value("${author.url}")
    private String authorUrl;

    @Value("${book.url}")
    private String bookUrl;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${websocket.url}")
    private String websocketUrl;


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/authors")
                        .uri(authorUrl))
                .route(p -> p
                        .path("/api/v1/books")
                        .uri(bookUrl))
                .route(p -> p
                        .path("/api/v1/details")
                        .uri(frontendUrl))
                .route(p -> p
                        .path("/push")
                        .uri(websocketUrl))
                .build();
    }

    @Bean
    public GatewayFilter throttleFilter() {
        return new ThrottleGatewayFilter()
                .setCapacity(5)
                .setRefillTokens(5)
                .setRefillPeriod(10)
                .setRefillUnit(TimeUnit.SECONDS);
    }
}
