FROM openjdk:17

COPY ./build/libs/courier-service-0.0.1-SNAPSHOT.jar courier-service-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/courier-service-1.0.0.jar"]