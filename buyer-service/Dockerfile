FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8004
COPY target/*.jar /opt/buyer-service.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar buyer-service.jar