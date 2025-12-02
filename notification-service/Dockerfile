FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy parent and module POMs to leverage Maven cache layers
COPY pom.xml ./pom.xml
COPY notification-service/pom.xml notification-service/pom.xml
# Include sibling module pom so the aggregator build sees all declared modules
COPY payment-service/pom.xml payment-service/pom.xml
RUN mvn -q -B -pl notification-service -am dependency:go-offline

# Copy source code
COPY notification-service/src notification-service/src

# Build the module
RUN mvn -q -B -pl notification-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/notification-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]