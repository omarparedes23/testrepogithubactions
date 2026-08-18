FROM eclipse-temurin:21-jre-alpine
RUN apk update && apk upgrade --no-cache
COPY target/*.jar /app.jar
CMD ["java","-jar","/app.jar"]
