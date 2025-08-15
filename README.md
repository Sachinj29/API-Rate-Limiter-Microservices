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

> 💡 _Note: Optional Notification Service is planned separately and **not** included in this README._

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

### **Database Setup**
```sql
CREATE DATABASE ratelimiterdb;


spring.datasource.url=jdbc:postgresql://localhost:5432/ratelimiterdb
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASS
spring.jpa.hibernate.ddl-auto=update


server.port=8765
spring.application.name=api-gateway
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
ratelimiter.service.url=http://localhost:8084
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
