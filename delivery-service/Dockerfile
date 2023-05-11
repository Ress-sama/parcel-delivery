FROM openjdk:17

COPY ./build/libs/delivery-service-0.0.1-SNAPSHOT.jar delivery-service-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/delivery-service-1.0.0.jar"]