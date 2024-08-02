# Etapa de build usando a imagem do Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar o restante dos diretórios para resolver as dependências
COPY . .

# Fazer o download das dependências do projeto em modo offline e construir o módulo rpm-proposal
# RUN mvn -pl rpm-proposal clean install -DskipTests
RUN mvn clean install -DskipTests

# Etapa final usando a imagem do OpenJDK para produção
FROM openjdk:17-slim
WORKDIR /app

# Copiando o JAR do módulo rpm-proposa
COPY --from=build /app/rpm-proposal/target/rpm-proposal-1.0.0-SNAPSHOT.jar app.jar

# Expondo a porta da aplicação
EXPOSE 8080

# Comando de inicialização da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
