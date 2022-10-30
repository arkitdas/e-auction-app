FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8080
COPY target/*.jar /opt/gateway-service.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar gateway-service.jar