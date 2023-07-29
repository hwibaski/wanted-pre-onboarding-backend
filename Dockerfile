FROM amd64/gradle:7.6.2-jdk11-alpine AS builder
WORKDIR /build
RUN gradle --version && java -version
COPY build.gradle settings.gradle /build/
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY ./ /build/
RUN gradle clean build -x test --no-daemon

FROM openjdk:11.0-slim
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar /app.jar
USER nobody
ENTRYPOINT ["java","-jar","/app.jar"]
