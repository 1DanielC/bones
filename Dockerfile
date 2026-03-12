FROM gradle:8.14-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :service:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/service/build/libs/*.jar app.jar
EXPOSE 26637
ENTRYPOINT ["java", "-jar", "app.jar"]
