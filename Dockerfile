FROM gradle:6.6.0-jdk8
EXPOSE 80
COPY ./ ./
RUN gradle clean build
ARG JAR_FILE=build/libs/*.jar
RUN cp ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]