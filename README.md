# Energy Manager Spring

## Tecnologias utilizadas
- Java 21
- Spring Boot
- Oracle Database
- Flyway
- Docker
- JWT Authentication

## Executar localmente

mvn spring-boot:run

## Gerar build

mvn clean package

## Executar com Docker

docker build -t energymanager .

docker run -p 8080:8080 energymanager