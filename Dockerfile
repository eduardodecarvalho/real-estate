FROM adoptopenjdk/openjdk11:ubi
RUN apk add --no-cache openssl bash mysql-client nodejs npm alpine-sdk autoconf librdkafka-dev vim nginx openrc
MAINTAINER decarvalho.dev@gmail.com
COPY target/real-estate-0.0.1-SNAPSHOT.jar real-estate-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/real-estate-0.0.1-SNAPSHOT.jar"]