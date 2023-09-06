FROM openjdk:17

ARG GITHUB_REPO
LABEL org.opencontainers.image.source=https://github.com/${GITHUB_REPO}

ADD target/moviegenie-server-1.0.0.jar /server.jar

ENTRYPOINT ["java", "--enable-preview", "-jar", "server.jar"]