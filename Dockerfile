FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN chmod 777 mvnw
RUN ./mvnw install -DskipTests

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /target/webhooks-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

