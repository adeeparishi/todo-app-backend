FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /todo_be
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /todo_be
LABEL authors="Deeparishi"
RUN apk add --no-cache curl
COPY --from=builder /todo_be/target/*.jar todo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todo.jar"]