FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/orchestrator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app.jar"]
