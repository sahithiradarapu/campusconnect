# Stage 1: build the JAR with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: small runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/campusconnect-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
