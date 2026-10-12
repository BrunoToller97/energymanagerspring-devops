# ==============================
# ETAPA 1 - BUILD
# ==============================

FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# ==============================
# ETAPA 2 - EXECUCAO
# ==============================

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/energymanagerspring-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir -p /app/logs

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]