FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8011
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5011 -XX:+UseContainerSupport
COPY target/*.jar /opt/auth-service.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar auth-service.jar