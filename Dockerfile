FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw -B dependency:go-offline

COPY src src

RUN ./mvnw package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /workspace/app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]