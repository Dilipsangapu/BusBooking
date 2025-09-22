# 🚌 Bus Booking System

<div align="center">

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-red?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Templates-green?style=for-the-badge&logo=thymeleaf)](https://www.thymeleaf.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

*A Spring Boot web application for managing bus booking, routes, and users*

</div>

---

## 📌 Project Overview  

The **Bus Booking System** is a **Spring Boot web application** that simplifies and automates the process of managing buses, routes, and bookings.  
It integrates **Spring Security** for authentication, uses **Thymeleaf templates** for a web UI, and persists data using **Spring Data JPA**.  

This project supports both **administrative features** (managing buses, routes, users) and **customer features** (searching buses, booking tickets, viewing schedules).  

---

## 🏗️ Architecture  

The project follows a **layered architecture**:  
- **Controller Layer** → Handles HTTP requests and responses (REST + Thymeleaf)  
- **Service Layer** → Business logic implementation  
- **Repository Layer** → Database access with Spring Data JPA  
- **Model/DTO Layer** → Entity models and data transfer objects  
- **Security Layer** → Handles authentication, authorization, and password encryption  

---

## ✨ Features  

- **User Management**: Register, login, role-based access (Admin/User)  
- **Bus Management**: Add and update bus details  
- **Route Management**: Define and manage bus routes  
- **Booking System**: Ticket booking with validation  
- **Thymeleaf UI**: Dynamic templates for user interaction  
- **Spring Security**: Secure login with role-based authorization  
- **Seeder Utility**: Preloads sample data for testing  

---

## 🗂️ Project Structure  

```
Bus/
├── pom.xml
├── mvnw, mvnw.cmd
├── src/
│   ├── main/java/com/BusBooking/Bus/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # REST + Web controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── model/          # Entity models
│   │   ├── repository/     # Spring Data JPA repositories
│   │   ├── security/       # Authentication & Authorization
│   │   ├── seeder/         # Data initialization
│   │   ├── service/        # Business services
│   │   └── util/           # Utility classes
│   └── main/resources/
│       ├── application.properties
│       ├── static/         # CSS, JS
│       └── templates/      # Thymeleaf templates
└── src/test/java/          # Unit & Integration tests
```

---

## ⚙️ Prerequisites  

- **Java 17+**  
- **Maven 3.8+**  
- **Spring Boot 3.x**  
- **H2/MySQL** (configurable in `application.properties`)  

---

## 🚀 Quick Start  

1. Clone the repository  
   ```bash
   git clone <repo-url>
   cd Bus
   ```

2. Build the project  
   ```bash
   ./mvnw clean install
   ```

3. Run the application  
   ```bash
   ./mvnw spring-boot:run
   ```

4. Access in browser  
   - Web App → http://localhost:8080  
   - H2 Console → http://localhost:8080/h2-console  

---

## 🔐 Security  

- **Spring Security with JWT/Session**  
- Role-based access:  
  - `ADMIN` → Manage buses, routes, users  
  - `USER` → Book tickets, view schedules  

---

## 🧪 Testing  

Run tests with:  
```bash
./mvnw test
```

---

## 📜 License  

This project is licensed under the MIT License.  
