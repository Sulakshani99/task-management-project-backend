FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/api.jar api.jar

COPY wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "/app/api.jar"]
