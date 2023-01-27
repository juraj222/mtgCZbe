FROM openjdk:11-jdk-alpine

COPY ./target/lifegame-0.0.1-SNAPSHOT.jar /usr/src/demo/

WORKDIR /usr/src/demo

EXPOSE 8081

CMD ["java", "-jar", "lifegame-0.0.1-SNAPSHOT.jar"]