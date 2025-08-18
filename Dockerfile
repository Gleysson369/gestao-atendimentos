# Usamos uma imagem base do Maven para construir o projeto
FROM maven:3.8.5-openjdk-17-slim AS build

# Copia o arquivo pom.xml para o container
COPY pom.xml .

# Baixa as dependências para acelerar o processo
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila o projeto e cria o arquivo JAR
RUN mvn clean package -DskipTests

# Usamos uma imagem base leve do Java para o resultado final
FROM openjdk:17-jdk-slim

# Copia o arquivo JAR do estágio de build para o estágio final
COPY --from=build target/gestao-atendimentos-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar a aplicação quando o container for iniciado
ENTRYPOINT ["java","-jar","/app.jar"]