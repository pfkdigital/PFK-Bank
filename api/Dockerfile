# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /workspace/app

COPY . /workspace/app

RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]