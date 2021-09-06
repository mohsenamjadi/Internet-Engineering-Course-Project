FROM maven:3.8.1-openjdk-15 AS MAVEN_BUILD

MAINTAINER Ali Javidan & Mohse Amjadi

COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build/

RUN mvn package

FROM openjdk:15-jdk-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/*.war /app/app.war

ENTRYPOINT ["java", "-jar", "app.war"]