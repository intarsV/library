FROM openjdk:8
ADD library-0.0.1-SNAPSHOT.jar library-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-0.0.1-SNAPSHOT.jar"]