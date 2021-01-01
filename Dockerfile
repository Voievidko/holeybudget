FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]