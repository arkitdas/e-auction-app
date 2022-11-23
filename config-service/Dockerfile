FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8088
COPY target/*.jar /opt/config-service.jar
WORKDIR /opt
VOLUME /tmp
ENTRYPOINT exec java $JAVA_OPTS -jar config-service.jar