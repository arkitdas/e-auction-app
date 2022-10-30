FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8005
COPY target/*.jar /opt/seller-service.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar seller-service.jar