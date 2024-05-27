FROM openjdk:17-jdk-slim

RUN mkdir /app

WORKDIR /app

#COPY ./src/main/resources/application.properties /app/application.properties

EXPOSE 8080

#COPY --chmod=644 ./src/main/java/com/csncl/nutritracker/sql /app/sql

ADD ./target/nutritracker-1.0-SNAPSHOT.jar /app/app.jar

#ENTRYPOINT ["java", "-jar",  "app.jar", "-Dspring.config.location=file:application.properties"]
ENTRYPOINT ["java", "-jar",  "app.jar"]
