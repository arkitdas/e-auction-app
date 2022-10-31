FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8005
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -XX:+UseContainerSupport
COPY target/*.jar /opt/seller-service.jar
WORKDIR /opt
ENTRYPOINT exec java -jar seller-service.jar