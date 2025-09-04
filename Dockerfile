FROM gradle:8.14.3-jdk21-alpine AS builder
#FROM gradle:8.8-jdk21 AS builder
WORKDIR /workspace
COPY . /workspace
RUN gradle --no-daemon clean bootJar

FROM eclipse-temurin:21.0.8_9-jdk-alpine
#FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
