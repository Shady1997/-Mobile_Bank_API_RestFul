# ---------------------------------------------------------
# 1️⃣ Stage 1: Build the application using Maven
# ---------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ---------------------------------------------------------
# 2️⃣ Stage 2: Run the Spring Boot application
# ---------------------------------------------------------
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (change if your app runs on a different one)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
