FROM openjdk:17-jdk-alpine
WORKDIR /opt
EXPOSE 8765
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar