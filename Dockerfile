FROM openjdk:21-jdk-slim

# Instalar Maven e ferramentas necessárias
RUN apt-get update && \
    apt-get install -y maven curl && \
    rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração primeiro (para cache do Docker)
COPY pom.xml .
COPY mvnw* ./

# Copiar código fonte
COPY src ./src

# Dar permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Build da aplicação
RUN ./mvnw clean package -DskipTests

# Expor porta
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar"]
