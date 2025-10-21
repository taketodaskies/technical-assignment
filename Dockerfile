FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

RUN adduser java
USER java

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
