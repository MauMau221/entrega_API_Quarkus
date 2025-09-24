FROM openjdk:21-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar todos os arquivos do projeto
COPY . .

# Build da aplicação usando Maven diretamente
RUN mvn clean package -DskipTests

# Expor porta
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar"]
