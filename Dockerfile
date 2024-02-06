FROM openjdk:latest
COPY target/KiiiProject-0.0.1.jar KiiiProject-0.0.1.jar
EXPOSE 80
CMD ["java", "-jar", "/KiiiProject-0.0.1.jar"]