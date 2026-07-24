FROM gradle:9.1.0-jdk21 AS builder
WORKDIR /app

COPY gradle gradle
COPY gradlew gradlew
COPY settings.gradle build.gradle ./
COPY src src

RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
RUN addgroup --system app \
 && adduser --system --ingroup app app
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]