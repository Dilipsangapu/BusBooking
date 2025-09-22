# 🚌 Bus Management System

<div align="center">

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-red?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-success?style=for-the-badge)](https://github.com/yourusername/bus-management-system)

*A comprehensive Java-based web application for efficient bus service management*

[Features](#-features) • [Quick Start](#-quick-start) • [API Documentation](#-api-documentation) • [Contributing](#-contributing)

</div>

---

## 📌 Project Overview

The **Bus Management System** is a robust Java-based web application built with **Spring Boot** and **Maven**. It provides a complete solution for automating and managing bus services, including scheduling, route management, passenger handling, and ticketing operations.

### 🏗️ Architecture

This project implements a **clean layered architecture**:
- **Controllers** → Handle HTTP requests and responses
- **Services** → Business logic implementation  
- **Repositories** → Data access layer
- **Entities** → Database models

### 🔧 Technology Stack

- **Backend**: Java 17+, Spring Boot 3.x
- **Database**: H2 (development), MySQL/PostgreSQL (production)
- **Build Tool**: Maven 3.8+
- **ORM**: JPA/Hibernate
- **Testing**: JUnit 5, Spring Boot Test
- **Documentation**: SpringDoc OpenAPI (Swagger)

---

## ✨ Features

### 🎯 Core Features
- **🚍 Bus Fleet Management** - Add, update, and manage bus information
- **🗺️ Route Management** - Create and manage bus routes with stops
- **📅 Schedule Management** - Automated scheduling system with conflict detection
- **👥 Passenger Management** - Customer registration and profile management
- **🎫 Ticket Booking** - Real-time seat booking with availability checking
- **💰 Fare Management** - Dynamic pricing based on routes and seasons

### 🚀 Advanced Features
- **📊 Analytics Dashboard** - Real-time insights and reporting
- **🔔 Notification System** - SMS/Email alerts for bookings and updates
- **🌐 Multi-language Support** - Internationalization (i18n)
- **🔐 Role-based Access Control** - Admin, Operator, and Customer roles
- **📱 Mobile-responsive Design** - Works seamlessly on all devices
- **🔍 Advanced Search & Filtering** - Find buses by route, time, price
- **📈 Revenue Tracking** - Financial reports and revenue analytics
- **🛡️ Data Security** - Encrypted data storage and secure API endpoints

### 🔮 Upcoming Features
- **💳 Payment Integration** - Multiple payment gateways
- **📍 Real-time GPS Tracking** - Live bus location tracking
- **🤖 AI-powered Recommendations** - Smart route suggestions
- **📊 Predictive Analytics** - Demand forecasting and optimization
- **🌤️ Weather Integration** - Weather-based schedule adjustments

---

## 🗂️ Project Structure

```
Bus/
├── 📄 pom.xml                          # Maven configuration
├── 🔧 mvnw, mvnw.cmd                   # Maven wrapper scripts  
├── 📋 README.md                        # Project documentation
├── 📝 HELP.md                          # Spring Boot help
├── 🚫 .gitignore                       # Git ignore rules
├── ⚙️ .gitattributes                   # Git attributes
├── 📂 src/
│   ├── 📂 main/
│   │   ├── ☕ java/com/busmanagement/
│   │   │   ├── 🎮 controller/          # REST Controllers
│   │   │   │   ├── BusController.java
│   │   │   │   ├── RouteController.java
│   │   │   │   ├── BookingController.java
│   │   │   │   └── UserController.java
│   │   │   ├── 🔧 service/             # Business Logic
│   │   │   │   ├── BusService.java
│   │   │   │   ├── RouteService.java
│   │   │   │   ├── BookingService.java
│   │   │   │   └── UserService.java
│   │   │   ├── 🗄️ repository/          # Data Access
│   │   │   │   ├── BusRepository.java
│   │   │   │   ├── RouteRepository.java
│   │   │   │   ├── BookingRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── 🏗️ entity/              # Database Models
│   │   │   │   ├── Bus.java
│   │   │   │   ├── Route.java
│   │   │   │   ├── Booking.java
│   │   │   │   ├── User.java
│   │   │   │   └── Schedule.java
│   │   │   ├── 📋 dto/                 # Data Transfer Objects
│   │   │   ├── 🛡️ security/            # Security Configuration
│   │   │   ├── ⚙️ config/              # Application Configuration
│   │   │   └── 🚀 BusManagementApplication.java
│   │   └── 📂 resources/
│   │       ├── ⚙️ application.properties
│   │       ├── 📊 data.sql             # Sample data
│   │       ├── 🗄️ schema.sql           # Database schema
│   │       ├── 🌐 static/              # Static resources
│   │       └── 📄 templates/           # Thymeleaf templates
│   └── 🧪 test/                        # Test cases
│       ├── java/                       # Unit & Integration tests
│       └── resources/                  # Test resources
└── 📂 docs/                            # Documentation
    ├── API.md                          # API documentation
    ├── SETUP.md                        # Detailed setup guide
    └── CONTRIBUTING.md                 # Contribution guidelines
```

---

## ⚙️ Prerequisites

| Requirement | Version | Download Link |
|------------|---------|---------------|
| **Java JDK** | 17+ | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) |
| **Maven** | 3.8+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **IDE** | Any | [IntelliJ IDEA](https://www.jetbrains.com/idea/) / [VS Code](https://code.visualstudio.com/) |
| **Database** | Optional | [MySQL](https://www.mysql.com/) / [PostgreSQL](https://www.postgresql.org/) |

---

## 🚀 Quick Start

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/bus-management-system.git
cd bus-management-system
```

### 2️⃣ Build the Project
```bash
# Using Maven Wrapper (recommended)
./mvnw clean install

# Or using system Maven
mvn clean install
```

### 3️⃣ Run the Application
```bash
# Development mode with auto-reload
./mvnw spring-boot:run

# Or run the JAR file
java -jar target/bus-management-system-1.0.0.jar
```

### 4️⃣ Access the Application
- **Web Interface**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **H2 Database Console**: http://localhost:8080/h2-console
- **Actuator Health**: http://localhost:8080/actuator/health

---

## 🐳 Docker Support

### Run with Docker
```bash
# Build Docker image
docker build -t bus-management-system .

# Run container
docker run -p 8080:8080 bus-management-system
```

### Docker Compose (with MySQL)
```bash
docker-compose up -d
```

---

## 🧪 Testing

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Categories
```bash
# Unit tests only
./mvnw test -Dtest="*UnitTest"

# Integration tests only  
./mvnw test -Dtest="*IntegrationTest"

# Generate test coverage report
./mvnw jacoco:report
```

### Test Coverage
View coverage report at: `target/site/jacoco/index.html`

---

## 📊 Database Configuration

### H2 Database (Default - Development)
```properties
spring.datasource.url=jdbc:h2:mem:busdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### MySQL Configuration (Production)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bus_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### PostgreSQL Configuration (Production)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bus_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

---

## 📖 API Documentation

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### Key API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/buses` | GET | Get all buses |
| `/api/buses` | POST | Create new bus |
| `/api/buses/{id}` | GET | Get bus by ID |
| `/api/routes` | GET | Get all routes |
| `/api/bookings` | POST | Create booking |
| `/api/users/register` | POST | User registration |
| `/api/auth/login` | POST | User login |

### Sample API Requests

#### Create a Bus
```bash
curl -X POST http://localhost:8080/api/buses \
  -H "Content-Type: application/json" \
  -d '{
    "busNumber": "BUS001",
    "capacity": 50,
    "busType": "AC_SLEEPER"
  }'
```

#### Book a Ticket
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "routeId": 1,
    "userId": 1,
    "seatNumbers": ["A1", "A2"],
    "journeyDate": "2024-01-15"
  }'
```

---

## 🔧 Configuration

### Application Profiles

#### Development Profile (`application-dev.properties`)
```properties
server.port=8080
logging.level.com.busmanagement=DEBUG
spring.jpa.show-sql=true
```

#### Production Profile (`application-prod.properties`)
```properties
server.port=80
logging.level.com.busmanagement=INFO
spring.jpa.show-sql=false
```

#### Run with specific profile
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 🔐 Security Features

- **JWT Authentication** - Secure token-based authentication
- **Role-based Authorization** - Different access levels for users
- **Password Encryption** - BCrypt password hashing
- **HTTPS Support** - SSL/TLS encryption
- **CORS Configuration** - Cross-origin request handling
- **Rate Limiting** - API request throttling

---

## 📈 Monitoring & Observability

### Spring Boot Actuator
- **Health Check**: `/actuator/health`
- **Metrics**: `/actuator/metrics`
- **Info**: `/actuator/info`
- **Environment**: `/actuator/env`

### Application Metrics
The system provides comprehensive metrics for:
- Response times
- Error rates  
- Database connection pool
- Custom business metrics

---

## 🚀 Deployment

### JAR Deployment
```bash
# Build executable JAR
./mvnw clean package

# Run in production
java -jar target/bus-management-system-1.0.0.jar --spring.profiles.active=prod
```

### Cloud Deployment
- **AWS**: Deploy using Elastic Beanstalk or ECS
- **Google Cloud**: Use App Engine or Cloud Run
- **Azure**: Deploy via App Service
- **Heroku**: Simple git-based deployment

---

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Workflow
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Maintain test coverage above 80%

---

## 🐛 Issue Reporting

Found a bug? Have a feature request? Please use our issue templates:

- [🐛 Bug Report](https://github.com/yourusername/bus-management-system/issues/new?template=bug_report.md)
- [✨ Feature Request](https://github.com/yourusername/bus-management-system/issues/new?template=feature_request.md)

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Contributors who have helped improve this project
- Open source community for inspiration and guidance

---

## 📞 Support

- **Documentation**: [Wiki](https://github.com/yourusername/bus-management-system/wiki)
- **Issues**: [GitHub Issues](https://github.com/yourusername/bus-management-system/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/bus-management-system/discussions)
- **Email**: support@busmanagementsystem.com

---

<div align="center">

**Made with ❤️ by the Bus Management System Team**

⭐ **Star this repository if it helped you!**

</div>
