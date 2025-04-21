FROM ubuntu:latest

FROM maven:3.8.5-openjdk-17 AS BUILD
WORKDIR /app
COPY expense-tracker-service /app
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=BUILD /app/target/expenseTracker-0.0.1-SNAPSHOT.jar expenseApp.jar
EXPOSE 9876
ENTRYPOINT ["java", "-jar", "expenseApp.jar"]