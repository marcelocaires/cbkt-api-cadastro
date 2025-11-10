# ----------------------------------------------------------------------
# STAGE 1: BUILD (Compilação - Ambiente de Desenvolvimento)
# Usa Maven e um JDK completo para compilar o código.
# Recomendação: Usar a versão LTS mais recente (Java 21) para estabilidade.
# ----------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Variável de ambiente para o build
ENV PROJECT_DIR=/app

# Definir diretório de trabalho
WORKDIR ${PROJECT_DIR}

# Copiar apenas os arquivos de configuração para aproveitar o cache do Docker
# Se o pom.xml não mudar, a compilação abaixo será ignorada
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e compila
COPY src ./src

# Executa o build final e empacota o JAR
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------
# STAGE 2: RUN (Execução - Imagem de Produção Mínima)
# Usa apenas o JRE (Java Runtime Environment) estável.
# Usando Eclipse Temurin JRE 21 LTS (mais robusta para produção).
# ----------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine AS runtime

# Variável de ambiente para o health check e o JAR
ENV APP_HOME=/app
ENV APP_JAR=app.jar

# Instalar 'wget' para health checks
# Usamos o 'apk' (gerenciador de pacotes do Alpine) por estarmos na imagem alpine
RUN apk update && apk add wget && rm -rf /var/cache/apk/*

# Definir diretório de trabalho
WORKDIR ${APP_HOME}

# CORREÇÃO: Usar o caminho absoluto /app/target/*.jar no estágio 'builder'
COPY --from=builder /app/target/*.jar ${APP_HOME}/${APP_JAR}

# --- Configurações de Segurança Não-Root ---
# CORREÇÃO: Cria o grupo 'appgroup' antes do usuário 'appuser' no Alpine.
# O chown usará appuser:appgroup
RUN addgroup -g 1001 appgroup && \
    adduser -S -D -u 1001 -G appgroup appuser 

# Mudar ownership de todo o diretório para o usuário não-root
# CORREÇÃO: Agora usa appuser:appgroup
RUN chown -R appuser:appgroup ${APP_HOME}

# Trocar para usuário não-root para execução
USER appuser

# Expor a porta da aplicação (8080 é o padrão Spring)
EXPOSE 8081

# Configurar JVM para container (ajustado para remoção de flags não suportadas)
# Flags otimizadas para JRE 21. O uso de flags -XX:+EnableDynamicAgentLoading não é padrão
# em produção, mas foi mantido por ser consistente com a versão anterior.
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# Comando de inicialização: usa as variáveis de ambiente e o usuário não-root
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar ${APP_JAR}"]