# 🚦 API Rate Limiter — Microservices Backend

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2022.0.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Gateway](https://img.shields.io/badge/Cloud_Gateway-Reactive-0AA7B4?style=for-the-badge&logo=cloudflare&logoColor=white)
![Eureka](https://img.shields.io/badge/Service_Discovery-Eureka-4285F4?style=for-the-badge&logo=google&logoColor=white)
![OpenFeign](https://img.shields.io/badge/OpenFeign-HTTP_Client-FF6C37?style=for-the-badge&logo=apache&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-Testing-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

---

## 🧭 Overview

A **developer-friendly, microservices-based platform** to **enforce API rate limits** across services using:

- **Reactive API Gateway Filter** for request throttling  
- **Centralized Rule Management** stored in PostgreSQL  
- **Optional Analytics Service** for usage insights  
- **Service Discovery via Eureka**

No Docker or Redis required — runs **locally with minimal setup** and is easily extensible.

---

## 🏗 Architecture

### **Request Flow**
1. **Client** → sends request to **API Gateway** with header `X-User-ID`
2. **Gateway** → calls `Rate Limiter Service` (`POST /check-limit`)
3. **Rate Limiter** → fetches rules from `Configuration Service` via **Feign**
4. **Decision**:
   - ✅ **Allowed** → Forward to downstream service  
   - ❌ **Denied** → HTTP **429 Too Many Requests**
5. *(Optional)* **Analytics Service** records the event

### **Services & Ports**
| Service                         | Port  |
|---------------------------------|-------|
| Service Registry (Eureka)       | 8761  |
| API Gateway                     | 8765  |
| Configuration Service (PostgreSQL) | 8083 |
| Rate Limiter Service            | 8084  |
| Analytics Service (optional)    | 8085  |

---

## ✨ Features
- 🔹 **Per-user rate limit rules** (`limit`, `windowInSeconds`)
- 🔹 **Reactive enforcement** at API Gateway
- 🔹 **In-memory limiter** (no Redis)
- 🔹 **Service discovery** with Eureka
- 🔹 **Clean separation** using OpenFeign
- 🔹 **Optional analytics** for monitoring
- 🔹 **Postman-friendly API testing**

---

## 🧰 Tech Stack
- **Java 17**, **Maven**
- **Spring Boot 3.1.x**
- **Spring Cloud 2022.0.x** (Gateway, Eureka, OpenFeign)
- **Spring Data JPA**, **Hibernate**
- **PostgreSQL** (rules, analytics)
- **WebFlux** (Gateway), **Spring MVC** (other services)

---

## ✅ Compatibility Matrix
| Component       | Version    |
|----------------|------------|
| Spring Boot    | 3.1.x      |
| Spring Cloud   | 2022.0.x   |

---

## ⚙️ Local Setup (No Docker)

### **Prerequisites**
- JDK 17+
- Maven 3.8+
- PostgreSQL (running locally on port 5432)
- IDE (IntelliJ recommended)

## Update DB Credentials

In `configuration-service` & `analytics-service` → `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/ratelimiterdb
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASS
spring.jpa.hibernate.ddl-auto=update
```

---

## Start Services (in order)

1. **Service Registry** → http://localhost:8761
2. **Configuration Service**
3. **Rate Limiter Service**
4. **API Gateway**
5. **(Optional) Analytics Service**

---

## 🔧 Gateway Config (`api-gateway/application.properties`)
```
server.port=8765
spring.application.name=api-gateway
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
ratelimiter.service.url=http://localhost:8084
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

⚠ Do NOT include `spring-boot-starter-web` in the gateway module (WebFlux only).

---

## 🚀 Testing with Postman

### Headers
```
X-User-ID: user1
```

### Create a Rule
```
POST http://localhost:8083/rules
Content-Type: application/json

{
  "userId": "user1",
  "limit": 5,
  "windowInSeconds": 60
}
```

### Test Rate Limiter Directly
```
POST http://localhost:8084/check-limit
Content-Type: application/json

{
  "userId": "user1"
}
```

✅ First 5 → `{ "allowed": true }`  
❌ After limit → `{ "allowed": false }` until window resets

---

### Test via Gateway
```
GET http://localhost:8765/configuration-service/rules/user1
Header: X-User-ID: user1
```

First 5 rapid requests → **200 OK**  
6th+ within 60s → **429 Too Many Requests**

---

## 🔍 Key Implementation Notes

- No Redis — uses `ConcurrentHashMap` for counters
- PostgreSQL reserved keyword `limit` → mapped as `req_limit`
- Feign — ensure `@EnableFeignClients` scans Feign interfaces
- Gateway filter method:
```java
Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
```

---

## 🧪 Troubleshooting

| Issue | Solution |
|-------|----------|
| Spring Boot & Spring Cloud version mismatch | Use Boot `3.1.x` & Cloud `2022.0.x` |
| Gateway detects MVC | Remove `spring-boot-starter-web` from gateway |
| PostgreSQL auth failed | Check username/password & Hibernate dialect |
| Syntax error near 'limit' | Use `@Column(name="req_limit")` |
| Gateway returns 400 | Ensure `X-User-ID` header is present |

---
