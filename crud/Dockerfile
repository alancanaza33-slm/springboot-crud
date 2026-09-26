# Etapa 1: Descargar Maven ejecutable y compilar
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Descargar Maven de forma ligera
RUN apk add --no- contraband --no-cache maven

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]