FROM maven:3.9.8-amazoncorretto-21-al2023 AS build
COPY src /home/app/src
COPY pom.xml /home/app
ARG SKIP_TESTS
RUN mvn -f /home/app/pom.xml -Dmaven.test.skip=${SKIP_TESTS} clean package

FROM amazoncorretto:21.0.4-al2023-headless
COPY --from=build /home/app/target/gateway-0.0.1-SNAPSHOT.jar /usr/local/lib/gateway.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/gateway.jar"]