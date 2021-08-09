FROM adoptopenjdk/openjdk11:ubi
RUN apk add --no-cache openssl bash
AUTHOR decarvalho.dev@gmail.com
COPY target/real-estate-0.0.1-SNAPSHOT.jar real-estate-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/real-estate-0.0.1-SNAPSHOT.jar"]