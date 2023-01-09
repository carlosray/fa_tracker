FROM openjdk:17.0.1-jdk-slim
ARG JAR_DIR
COPY "${JAR_DIR}/*.jar" "/usr/local/lib/app.jar"
ENTRYPOINT java -jar /usr/local/lib/app.jar