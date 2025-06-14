# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy everything and build the app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app with a smaller JDK image
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
