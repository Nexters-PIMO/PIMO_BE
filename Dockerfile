FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE=/build/libs/pimo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","-XX:MaxRAMPercentage=80.0", "-XX:MinRAMPercentage=50.0","/app.jar"]