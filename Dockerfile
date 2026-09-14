# build stage
FROM eclipse-temurin:25-jdk AS builder
LABEL authors="canureal"

WORKDIR /app

# copy gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

# making gradle wrapper runnable
RUN chmod +x ./gradlew

# get dependencies before, for caching
RUN ./gradlew dependencies --no-daemon || true

# copy source code
COPY src src

# build app, ignore tests(should i do this? idk? i mean skipping tests. im talking like i wrote a test)
RUN ./gradlew bootJar --no-daemon -x test

# runtime stage
FROM eclipse-temurin:25-jre

WORKDIR /app

# creating non-root users
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# get the jar file from build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# spring boot default port i did not change it in the app so
EXPOSE 8080

# start the whole thing
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

