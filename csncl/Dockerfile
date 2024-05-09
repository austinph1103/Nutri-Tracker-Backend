FROM openjdk:21

WORKDIR /app

ARG JAR_FILE=./target/Test-1.0-SNAPSHOT.jar

COPY ${JAR_FILE} /app

EXPOSE 8080

CMD ["java", "-jar", "Test-1.0-SNAPSHOT.jar"]

#FROM adoptopenjdk/openjdk13:jdk-13.0.2_8-alpine
#
#VOLUME /tmp
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","/app.jar"]
ARG BUILD_IMAGE=maven:3.6-jdk-8-alpine
ARG RUNTIME_IMAGE=openjdk:8-alpine

FROM ${BUILD_IMAGE} as dependencies
COPY pom.xml ./
RUN mvn -B dependency:go-offline

FROM dependencies as build
COPY src ./src
RUN mvn -B clean package

FROM ${RUNTIME_IMAGE}
COPY --from=build /app/target/eventstore-*.jar /app/standalone.jar

CMD ["/usr/bin/java", "-jar", "/app/standalone.jar"]