# Use Eclipse Temurin JDK 21 base image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Add the jar file to the container
COPY target/BusBooking-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
