# Etapa 1: Build com Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências primeiro (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final leve com apenas o JAR
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/gestao-atendimentos-1.0.17-Beta.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]