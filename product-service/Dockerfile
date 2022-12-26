FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
ENV PORT 8002
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006 -XX:+UseContainerSupport
COPY target/*.jar /opt/product-service.jar
#main/resources/entrypoint.sh /opt
WORKDIR /opt
#RUN ["chmod", "+x", "entrypoint.sh"]
ENTRYPOINT exec java $JAVA_OPTS -jar product-service.jar
