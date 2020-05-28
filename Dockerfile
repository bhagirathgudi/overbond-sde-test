FROM gradle:4.7.0-jdk8-alpine AS build

COPY --chown=gradle:gradle . /submission

WORKDIR /submission

RUN gradle build --no-daemon

ENTRYPOINT ["java", "-jar", "./build/libs/sde-test-1.1.0-SNAPSHOT.jar"]