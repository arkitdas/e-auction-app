FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8001
COPY target/*.jar /opt/discovery-service.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar discovery-service.jar