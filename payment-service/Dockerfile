# ====== 1. BUILD STAGE ======
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy parent pom and module pom to take advantage of Maven caching
COPY pom.xml ./pom.xml
COPY payment-service/pom.xml payment-service/pom.xml
COPY notification-service/pom.xml notification-service/pom.xml
RUN mvn -q -B -pl payment-service -am dependency:go-offline

# Copy source code
COPY payment-service/src payment-service/src

# Build the Spring Boot JAR
RUN mvn -q -B -pl payment-service -am package -DskipTests

# ====== 2. RUNTIME STAGE ======
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/payment-service/target/*.jar app.jar

# Expose service port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
