FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/Aston_REST-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]



