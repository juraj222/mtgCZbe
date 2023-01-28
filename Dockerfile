FROM openjdk:8-jdk-alpine

COPY ./target/demo-0.0.1-SNAPSHOT.jar /usr/src/demo/

WORKDIR /usr/src/demo

EXPOSE 8087

CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]