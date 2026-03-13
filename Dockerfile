# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /build

COPY pom.xml ./

RUN mvn dependency:go-offline -q

COPY src src

RUN mvn package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /build/target/api.jar api.jar

COPY wait-for-it.sh wait-for-it.sh
COPY wait-for-mysql.sh wait-for-mysql.sh

RUN sed -i 's/\r$//' wait-for-it.sh wait-for-mysql.sh \
	&& chmod +x wait-for-it.sh wait-for-mysql.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-mysql.sh", "java", "-jar", "/app/api.jar"]
