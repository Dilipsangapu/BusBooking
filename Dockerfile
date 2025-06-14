# Dockerfile

# Step 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy the entire project to the image
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Step 2: Create a smaller image with only the jar file
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy only the built jar from the builder image
COPY --from=builder /app/target/Bus-0.0.1-SNAPSHOT.jar app.jar

# Expose port if needed (optional)
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
