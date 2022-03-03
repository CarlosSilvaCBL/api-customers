FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean -DskipTests=true package

FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/api-customers**.jar api-customers.jar
ENTRYPOINT exec java -jar api-customers.jar