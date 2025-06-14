# -------------------------------
# STEP 1: Build the Spring Boot App using Maven + Java 21
# -------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy project files into image
COPY . .

# Build Spring Boot app without running tests
RUN mvn clean package -DskipTests

# -------------------------------
# STEP 2: Minimal image with only JDK 21 to run the app
# -------------------------------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the jar built from previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (optional)
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
