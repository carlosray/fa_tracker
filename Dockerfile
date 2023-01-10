FROM openjdk:17.0.1-jdk-slim
ARG JAR_DIR
ARG APP_PROFILE="prod"
ENV SPRING_PROFILES_ACTIVE=$APP_PROFILE
COPY "${JAR_DIR}/*.jar" "/usr/local/lib/app.jar"
ENTRYPOINT java -jar /usr/local/lib/app.jar