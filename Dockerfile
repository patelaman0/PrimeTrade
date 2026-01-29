
# Stage 1: Build the Spring Boot application
# Maven + JDK used only for build
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app

# Copy Maven config & source
COPY pom.xml .
COPY src ./src

# Build the JAR, skip tests for faster build
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime image
FROM eclipse-temurin:22-jre
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port for Render (default 8080)
EXPOSE 8080

# Runtime environment variables
# Render automatically injects PORT, DB_PASSWORD etc.
# Use -e flag locally or set in Render dashboard
# ENV DB_PASSWORD=default_value  # optional default

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]