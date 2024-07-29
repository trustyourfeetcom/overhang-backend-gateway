# Build
FROM maven:3.9.8-amazoncorretto-21-al2023 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Execution Stage
FROM amazoncorretto:21-alpine3.18-jdk
COPY --from=build /home/app/target/overhang-backend-gateway-0.0.1-SNAPSHOT.jar /usr/local/lib/overhang-backend-gateway.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/overhang-backend-gateway.jar"]