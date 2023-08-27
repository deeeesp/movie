FROM openjdk:17
EXPOSE 8080
ADD target/servermoviegenie-1.0.0.jar movie-genie-server.jar
ENTRYPOINT ["java", "-jar", "/movie-genie-server.jar"]