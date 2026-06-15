<div align="center">

# 🏢 Client Service Demo

### A Spring Boot Microservice for Client Registration & Authentication

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2023.0.3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)

</div>

---

## 📖 Overview

**Client Service Demo** is a production-ready microservice built with **Spring Boot 3** that handles client onboarding and authentication within a larger CRM ecosystem. It integrates with a dedicated **Authentication Service** via **OpenFeign** and registers itself with a **Eureka Discovery Server**, making it a well-behaved citizen of a microservices architecture.

> 🔗 Part of the **Startup CRM** platform — designed to manage and authenticate company clients at scale.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🧾 **Client Registration** | Register companies with profile details, logo, and certificates |
| 🔐 **Secure Login** | Authenticate using email, password, and a unique client code |
| 🪙 **JWT Token Issuance** | Delegates token generation to a dedicated Auth Service |
| 🗂️ **File Uploads** | Supports certificate and company logo storage (up to 10MB) |
| ☁️ **Service Discovery** | Registers with Eureka for seamless inter-service communication |
| 🔄 **Feign Client** | Communicates with the Auth Service via declarative HTTP |
| 🛡️ **Password Encryption** | Uses BCrypt for secure password hashing |
| ✅ **Input Validation** | Request validation with Spring Validation |
| 🗃️ **JPA + MySQL** | Persistent data storage with Hibernate auto-schema management |

---

## 🏗️ Project Structure

```
client-demo/
├── 📁 src/main/java/com/example/clientservicedemo/
│   ├── 🚀 ClientservicedemoApplication.java     # App entry point
│   ├── 📁 clients/
│   │   └── AuthServiceClient.java               # Feign client → Auth Service
│   ├── 📁 config/
│   │   └── Configurations.java                  # Beans: ModelMapper, BCryptEncoder
│   ├── 📁 controller/
│   │   └── ClientController.java                # REST endpoints
│   ├── 📁 dtos/
│   │   ├── ApiResponse.java                     # Generic API wrapper
│   │   ├── ClientDTO.java                       # Registration request DTO
│   │   ├── LoginClientRequest.java              # Login request DTO
│   │   ├── LoginResponse.java                   # Login response DTO
│   │   ├── ResponseData.java                    # Generic response data
│   │   ├── SignupClientRequest.java             # Signup DTO
│   │   └── TokenRequest.java                    # Auth service token DTO
│   ├── 📁 entity/
│   │   └── ClientUser.java                      # JPA entity → client_users table
│   ├── 📁 exceptions/
│   │   ├── CentralExceptions.java               # Global exception handler
│   │   └── CustomException.java                 # Custom domain exception
│   ├── 📁 repo/
│   │   └── ClientUserRepo.java                  # Spring Data JPA repository
│   ├── 📁 service/
│   │   ├── ClientService.java                   # Business logic
│   │   └── FileOps.java                         # File save/read operations
│   └── 📁 util/
│       ├── MessageConstant.java                 # Centralized string constants
│       └── Utility.java                         # Helper methods (e.g., code gen)
├── 📁 src/main/resources/
│   └── application.properties                   # App configuration
├── 📁 uploads/
│   └── certificates/                            # Uploaded certificate storage
└── pom.xml                                      # Maven build descriptor
```

---

## 🔌 API Endpoints

Base URL: `http://localhost:8085/client`

### 📝 Register a Client

```http
POST /client/register
Content-Type: multipart/form-data
```

| Field | Type | Required | Description |
|---|---|---|---|
| `companyName` | `String` | ✅ | Company name |
| `email` | `String` | ✅ | Unique email address |
| `mobile` | `String` | ✅ | Contact number |
| `password` | `String` | ✅ | Account password |
| `domainName` | `String` | ❌ | Company domain |
| `address` | `String` | ❌ | Full address |
| `country` | `String` | ❌ | Country |
| `state` | `String` | ❌ | State |
| `postalCode` | `String` | ❌ | Postal/ZIP code |
| `certificate` | `File` | ❌ | Business certificate |
| `logo` | `File` | ❌ | Company logo |

**Success Response `200 OK`:**
```json
{
  "message": "Client Registered Successfully",
  "data": "CLT-A1B2C3"
}
```

---

### 🔐 Client Login

```http
POST /client/login
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "company@example.com",
  "password": "your_password",
  "code": "CLT-A1B2C3"
}
```

**Success Response `200 OK`:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "clientCode": "CLT-A1B2C3",
  "companyName": "Acme Corp"
}
```

---

## ⚙️ Configuration

Edit `src/main/resources/application.properties`:

```properties
# Application
spring.application.name=client-service-demo
server.port=8085

# Eureka Discovery
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/startup_crm
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
spring.jpa.hibernate.ddl-auto=update

# File Upload Limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| 🖥️ **Runtime** | Java 17 |
| 🌱 **Framework** | Spring Boot 3.3.5 |
| ☁️ **Cloud** | Spring Cloud 2023.0.3 |
| 🔗 **Service Discovery** | Netflix Eureka Client |
| 📡 **HTTP Client** | OpenFeign |
| 🗄️ **Database** | MySQL 8.x + Spring Data JPA |
| 🔒 **Security** | BCrypt Password Encoder |
| 🗂️ **Mapping** | ModelMapper 3.1.1 |
| 📦 **Build Tool** | Maven |
| 🧹 **Boilerplate** | Lombok |

---

## 🚀 Getting Started

### Prerequisites

- ☕ **Java 17+** installed
- 🐬 **MySQL 8.x** running locally
- 🔍 **Eureka Server** running on port `8761`
- 🔑 **Auth Service** (`STARTUP-AUTHENTICATION-SERVICE`) running and registered with Eureka

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/client-demo.git
cd client-demo
```

### 2️⃣ Set Up the Database

```sql
CREATE DATABASE startup_crm;
```

> Hibernate will auto-create the `client_users` table on first run (`ddl-auto=update`).

### 3️⃣ Configure the Application

Update the credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
```

### 4️⃣ Build & Run

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or build a JAR first
./mvnw clean package
java -jar target/clientservicedemo-0.0.1-SNAPSHOT.jar
```

The service will start on **`http://localhost:8085`** and register with Eureka. 🎉

---

## 🗃️ Database Schema

The service manages the `client_users` table in the `startup_crm` schema:

| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | PK, AUTO_INCREMENT |
| `client_code` | VARCHAR(10) | UNIQUE, NOT NULL |
| `company_name` | VARCHAR(255) | NOT NULL |
| `email` | VARCHAR(255) | UNIQUE, NOT NULL |
| `mobile` | VARCHAR(20) | NOT NULL |
| `password` | VARCHAR(255) | NOT NULL (BCrypt) |
| `domain_name` | VARCHAR(255) | nullable |
| `address` | TEXT | nullable |
| `country` | VARCHAR(100) | nullable |
| `state` | VARCHAR(100) | nullable |
| `postal_code` | VARCHAR(20) | nullable |
| `certificate_path` | VARCHAR(500) | nullable |
| `logo_path` | VARCHAR(500) | nullable |
| `is_active` | BOOLEAN | default: true |
| `created_at` | TIMESTAMP | auto-set on insert |

---

## 🔗 Service Dependencies

This microservice depends on the following services being available:

```
┌─────────────────────────┐
│     Eureka Server       │  ← Port 8761
│  (Service Registry)     │
└────────────┬────────────┘
             │ registers
    ┌────────┴────────────────────────────┐
    │                                     │
    ▼                                     ▼
┌───────────────────┐        ┌────────────────────────────┐
│   Client Service  │──────▶ │  Auth Service              │
│   (this service)  │ Feign  │  STARTUP-AUTHENTICATION-   │
│   Port: 8085      │        │  SERVICE                   │
└───────────────────┘        └────────────────────────────┘
```

---

## 📂 File Upload Storage

Uploaded files are stored locally under the `uploads/` directory at the project root:

```
uploads/
└── certificates/   ← Business certificates
└── logos/          ← (if configured) Company logos
```

---

