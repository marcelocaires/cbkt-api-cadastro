# CBKT API Cadastro - Docker Setup

## 📋 Visão Geral

Esta é a API de cadastro da CBKT, configurada para rodar com Docker Compose incluindo PostgreSQL.
**Configurado para Java 25** conforme especificado no pom.xml.

## ☕ Versões Java Suportadas

- **Dockerfile principal**: Java 25 Early Access (conforme pom.xml)
- **Dockerfile.java21**: Java 21 LTS (fallback estável)

O script `start.sh` tenta automaticamente usar Java 25 e faz fallback para Java 21 se houver problemas.

## 🚀 Início Rápido

### 1. Usar o script de inicialização (Recomendado)

```bash
./start.sh
```

### 2. Inicialização manual

```bash
# 1. Compilar o projeto
./mvnw clean package -DskipTests

# 2. Configurar variáveis de ambiente
cp .env.example .env
# Edite o arquivo .env conforme necessário

# 3. Iniciar os serviços
docker-compose up --build -d
```

## 🔧 Configuração

### Variáveis de Ambiente

Copie `.env.example` para `.env` e ajuste as seguintes variáveis:

- `DB_USER` / `DB_PASS`: Credenciais do PostgreSQL
- `JWT_SECRET`: Chave secreta para JWT (use uma chave forte em produção)

### Portas

- **Aplicação**: http://localhost:8082
- **PostgreSQL**: localhost:5433 (para não conflitar com auth na 5432)
- **Swagger UI**: http://localhost:8082/swagger-ui.html
- **Health Check**: http://localhost:8082/actuator/health

## 📊 Monitoramento

### Health Checks

A aplicação inclui health checks automáticos:

```bash
# Verificar status da aplicação
curl http://localhost:8082/actuator/health

# Verificar informações da aplicação
curl http://localhost:8082/actuator/info
```

### Logs

```bash
# Ver logs em tempo real
docker-compose logs -f cbkt-api-cadastro

# Ver logs do PostgreSQL
docker-compose logs -f postgres

# Ver todos os logs
docker-compose logs -f
```

## 🛠️ Comandos Úteis

```bash
# Parar todos os serviços
docker-compose down

# Parar e remover volumes (CUIDADO: apaga dados do banco)
docker-compose down -v

# Reiniciar apenas a aplicação
docker-compose restart cbkt-api-cadastro

# Reconstruir apenas a aplicação
docker-compose up --build cbkt-api-cadastro

# Verificar status dos containers
docker-compose ps

# Entrar no container da aplicação
docker-compose exec cbkt-api-cadastro sh

# Entrar no PostgreSQL
docker-compose exec postgres psql -U postgres -d cbkt
```

## 🗄️ Banco Externo

Para usar PostgreSQL externo:

```bash
# Usar configuração para banco externo
docker-compose -f docker-compose.external-db.yml up --build -d
```

Configure as variáveis no `.env`:
```bash
DB_HOST=localhost  # ou IP do servidor PostgreSQL
DB_PORT=5432
DB_NAME=cbkt
DB_USER=postgres
DB_PASS=sua_senha
```

## 🔍 Solução de Problemas

### Problemas com Java 25

Se houver problemas com Java 25 Early Access:

1. **Use o Dockerfile alternativo com Java 21**:
   ```bash
   docker build -f Dockerfile.java21 -t cbkt-api-cadastro .
   docker-compose up -d
   ```

2. **Verificar compatibilidade**:
   ```bash
   # Verificar versão Java no container
   docker-compose exec cbkt-api-cadastro java -version
   ```

### Conflito de Portas

Se a porta 5433 do PostgreSQL estiver em uso:

1. **Alterar porta no docker-compose.yml**:
   ```yaml
   ports:
     - "5434:5432"  # ou outra porta disponível
   ```

### Aplicação não inicia

1. Verifique se o PostgreSQL está rodando:
   ```bash
   docker-compose logs postgres
   ```

2. Verifique se a porta 8082 não está em uso:
   ```bash
   netstat -tlnp | grep 8082
   ```

## 🏗️ Arquitetura

```
┌─────────────────────┐    ┌─────────────────┐
│   cbkt-api-cadastro │────│   PostgreSQL    │
│   (Spring Boot)     │    │     (port       │
│   (port 8082)       │    │      5433)      │
└─────────────────────┘    └─────────────────┘
        │
        ├── /actuator/health (Health Check)
        ├── /swagger-ui.html (API Docs)
        └── /api/** (API Endpoints)
```

## 🔒 Segurança

- Aplicação roda com usuário não-root
- Variáveis sensíveis via arquivo .env
- Health checks configurados
- Logs de produção otimizados
- JVM otimizada para containers

## 📚 Desenvolvimento

Para desenvolvimento local sem Docker:

1. Configure PostgreSQL local
2. Ajuste `application.properties`
3. Execute: `./mvnw spring-boot:run`

## 📁 Dados de Exemplo

O projeto inclui arquivos CSV na pasta `db/` com dados de exemplo:
- `atleta_*.csv`
- `clube_*.csv` 
- `graduacao_*.csv`
- `exame_*.csv`

Estes arquivos podem ser usados para popular o banco de dados inicial.