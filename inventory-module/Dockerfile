FROM openjdk:8
COPY  target/  target/
RUN  mv target/*.jar   target/inventory-module.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "target/inventory-module.jar"]
