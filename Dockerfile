FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
ENV PORT=10000
EXPOSE 10000
ENTRYPOINT ["sh","-c","java -Dserver.port=$PORT -jar /app/app.jar"]