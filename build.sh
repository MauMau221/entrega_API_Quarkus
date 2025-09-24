#!/bin/bash

# Instalar Java 21
apt-get update
apt-get install -y openjdk-21-jdk

# Configurar JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Dar permissão ao mvnw
chmod +x ./mvnw

# Build da aplicação
./mvnw clean package -DskipTests
