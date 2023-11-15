FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-pruebas-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/backend-pruebas-service-0.0.1-SNAPSHOT.jar"]