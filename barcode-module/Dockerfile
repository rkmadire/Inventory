FROM openjdk:8
COPY  target/  target/
RUN  mv target/*.jar   target/Barcode-module.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "target/Barcode-module.jar"]
