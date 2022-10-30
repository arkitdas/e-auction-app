FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8002
COPY target/*.jar /opt/product-service.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar product-service.jar