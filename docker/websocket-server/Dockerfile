FROM openjdk:8
ADD ./websocket/build/libs/websocket-0.0.1-SNAPSHOT.jar websocket-server.jar
RUN bash -c 'touch /websocket-server.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/websocket-server.jar"]