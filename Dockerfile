FROM ubuntu:latest

FROM maven:3.8.5-openjdk-17 AS BUILD
COPY . .
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=BUILD /target/expenseTracker-0.0.1-SNAPSHOT.jar expenseApp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "expenseApp.jar"]