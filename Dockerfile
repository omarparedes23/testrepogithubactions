FROM eclipse-temurin:21-jre-alpine
RUN apk update && apk upgrade --no-cache
COPY target/gestiontareas-0.0.2-SNAPSHOT.jar /app.jar
CMD ["java","-jar","/app.jar"]
