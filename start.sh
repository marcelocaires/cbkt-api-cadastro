#!/bin/bash

# Script para inicializar a aplicação CBKT Cadastro

echo "🚀 Iniciando CBKT API Cadastro com Java 25..."

# Verificar se o JAR existe
if [ ! -f "target/cbkt-api-cadastro-0.0.1-SNAPSHOT.jar" ]; then
    echo "⚠️  JAR não encontrado. Executando mvn clean package..."
    ./mvnw clean package -DskipTests
    
    if [ $? -ne 0 ]; then
        echo "❌ Falha ao compilar o projeto"
        exit 1
    fi
fi

# Verificar se o arquivo .env existe
if [ ! -f ".env" ]; then
    echo "ℹ️  Arquivo .env não encontrado. Copiando do exemplo..."
    cp .env.example .env
    echo "✅ Arquivo .env criado. Por favor, ajuste as variáveis conforme necessário."
fi

# Parar containers existentes
echo "🛑 Parando containers existentes..."
docker-compose down

# Tentar construir com Java 25
echo "🔨 Tentando construir com Java 25..."
docker-compose build cbkt-api-cadastro

if [ $? -ne 0 ]; then
    echo "⚠️  Falha ao construir com Java 25. Tentando com Java 21 LTS..."
    echo "🔄 Usando Dockerfile alternativo com Java 21..."
    
    # Usar Dockerfile alternativo
    docker build -f Dockerfile.java21 -t cbkt-api-cadastro_cbkt-api-cadastro .
    
    if [ $? -ne 0 ]; then
        echo "❌ Falha ao construir com Java 21 também. Verifique os logs acima."
        exit 1
    fi
fi

# Iniciar os serviços
echo "▶️  Iniciando os serviços..."
docker-compose up -d

# Aguardar os serviços ficarem prontos
echo "⏳ Aguardando os serviços ficarem prontos..."
sleep 10

# Verificar status dos containers
echo "📊 Status dos containers:"
docker-compose ps

# Mostrar logs da aplicação
echo "📝 Logs da aplicação (últimas 20 linhas):"
docker-compose logs --tail=20 cbkt-api-cadastro

echo ""
echo "✅ Aplicação iniciada!"
echo "🌐 API disponível em: http://localhost:8082"
echo "🗄️  PostgreSQL disponível em: localhost:5433"
echo ""
echo "Comandos úteis:"
echo "  docker-compose logs -f cbkt-api-cadastro  # Ver logs em tempo real"
echo "  docker-compose down                       # Parar todos os serviços"
echo "  docker-compose restart                    # Reiniciar todos os serviços"