package com.sachin.API.Gateway.filter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RateLimitFilter implements GlobalFilter {

    private final WebClient.Builder webClientBuilder;

    @Value("${ratelimiter.service.url}")
    private String rateLimiterServiceUrl;

    public RateLimitFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        if (userId == null || userId.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        return webClientBuilder.build()
                .post()
                .uri(rateLimiterServiceUrl + "/check-limit")
                .bodyValue(new CheckLimitRequest(userId))
                .retrieve()
                .bodyToMono(CheckLimitResponse.class)
                .flatMap(response -> {
                    if (!response.isAllowed()) {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }
                    return chain.filter(exchange);
                });
    }

    // DTO Classes
    static class CheckLimitRequest {
        private String userId;
        public CheckLimitRequest(String userId) { this.userId = userId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }
    static class CheckLimitResponse {
        private boolean allowed;
        public boolean isAllowed() { return allowed; }
        public void setAllowed(boolean allowed) { this.allowed = allowed; }
    }
}
