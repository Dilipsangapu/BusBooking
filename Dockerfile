# Stage 1: Build the application using Maven
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy only the JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Set the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
