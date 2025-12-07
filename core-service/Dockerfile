#======1. BUILD STAGE ======
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

#======2. COPY STAGE ======
COPY core-service/pom.xml core-service/pom.xml
RUN mvn -q -B -f core-service/pom.xml dependency:go-offline

COPY core-service/src core-service/src
RUN mvn -q -B -f core-service/pom.xml package -DskipTests


#======3. RUNTIME STAGE ======
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/core-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]


