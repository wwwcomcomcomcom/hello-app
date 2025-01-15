FROM openjdk:17

WORKDIR /deploy

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} hello-app.jar

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "hello-app.jar"]