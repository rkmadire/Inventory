FROM openjdk:8
COPY  target/  target/
RUN  mv target/*.jar   target/catalog-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-Dspring.profiles.active=cloud", "-jar", "target/catalog-service.jar"]
