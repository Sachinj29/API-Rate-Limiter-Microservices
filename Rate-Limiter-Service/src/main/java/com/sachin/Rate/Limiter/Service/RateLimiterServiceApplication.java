package com.sachin.Rate.Limiter.Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.sachin.Rate.Limiter.Service.client")
public class RateLimiterServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RateLimiterServiceApplication.class, args);
	}
}