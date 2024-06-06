#FROM ubuntu:latest
#LABEL authors="karee"
#
#ENTRYPOINT ["top", "-b"]
#FROM maven:3-openjdk-17 AS build
#COPY . .
#RUN  mvn clean package -DskipTests

#FROM amazoncorretto:17
#VOLUME /tmp
#COPY target/*.jar app.jar
#COPY --from=build /target/spring-0.0.1-SNAPSHOT.jar spring.jar
#ENTRYPOINT ["java","-jar","/app.jar"]



FROM maven:3-openjdk-17 AS build
COPY . .
RUN  mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/spring-0.0.1-SNAPSHOT.jar spring.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring.jar"]