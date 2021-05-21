FROM openjdk:latest
WORKDIR /
COPY build/libs/gamefully-service-1.0-SNAPSHOT-all.jar gamefully-service.jar
COPY client.cert /
COPY server.cert /
COPY keystore_client /
COPY keystore_server /
COPY truststore_client /
COPY truststore_server /
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "gamefully-service.jar"]