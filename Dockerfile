FROM gradle:8.14.3-jdk21-alpine AS builder
WORKDIR /workspace
COPY . /workspace
RUN gradle --no-daemon bootJar -x test

FROM eclipse-temurin:21.0.8_9-jdk-alpine
WORKDIR /app
RUN addgroup -S smartgroup && adduser -S smartuser -G smartgroup
COPY --from=builder --chown=smartuser:smartgroup /workspace/build/libs/*.jar /app/app.jar
USER smartuser
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
