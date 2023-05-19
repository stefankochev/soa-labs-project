

FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY --from=build /app/target/notifications-service-0.0.1.jar .

CMD ["java", "-jar", "notifications-service-0.0.1.jar"]



