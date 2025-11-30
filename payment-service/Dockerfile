# ====== 1. BUILD STAGE ======
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first and download all dependencies (cache layer)
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot JAR
RUN mvn -q -B package -DskipTests


# ====== 2. RUNTIME STAGE ======
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose service port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
