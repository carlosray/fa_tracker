ARG CONTAINER_HOME="/home/app"
ARG APP_NAME

#
# Build stage
#
FROM maven:3.8.1-openjdk-17-slim AS build
ARG CONTAINER_HOME
ARG APP_NAME
COPY src ${CONTAINER_HOME}/src
COPY pom.xml ${CONTAINER_HOME}
RUN mvn -f ${CONTAINER_HOME}/pom.xml clean install

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
ARG CONTAINER_HOME
ARG APP_NAME
ENV APP_NAME=$APP_NAME
COPY --from=build "${CONTAINER_HOME}/target/${APP_NAME}*.jar" "/usr/local/lib/${APP_NAME}.jar"
ENTRYPOINT java -jar /usr/local/lib/${APP_NAME}.jar
