# CBKT API Cadastro - Docker Setup

## 📋 Visão Geral

Esta é a API de cadastro da CBKT, configurada para rodar com Docker **conectando a um banco de dados PostgreSQL externo**.
**Configurado para Java 25** conforme especificado no pom.xml.

## ☕ Versões Java Suportadas

- **Dockerfile principal**: Java 25 Early Access (conforme pom.xml)
- **Dockerfile.java21**: Java 21 LTS (fallback estável)

O script `start.sh` tenta automaticamente usar Java 25 e faz fallback para Java 21 se houver problemas.

## 🚀 Início Rápido

### Pré-requisitos

**IMPORTANTE**: Você precisa de um banco PostgreSQL rodando externamente. A aplicação Docker **NÃO** inclui mais o banco de dados.

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
# OBRIGATÓRIO: Edite o arquivo .env com os dados do seu banco PostgreSQL externo

# 3. Iniciar o serviço
docker-compose up --build -d
```

## 🔧 Configuração

### Variáveis de Ambiente Obrigatórias

Copie `.env.example` para `.env` e configure as seguintes variáveis **OBRIGATÓRIAS**:

```bash
# Banco PostgreSQL Externo (OBRIGATÓRIO)
DB_HOST=localhost           # IP/hostname do seu PostgreSQL
DB_PORT=5432               # Porta do PostgreSQL
DB_NAME=cbkt               # Nome da base de dados
DB_USER=postgres           # Usuário do banco
DB_PASS=sua_senha_aqui     # Senha do banco

# Segurança (OBRIGATÓRIO)
JWT_SECRET=SuaChaveSecretaMuitoForteAqui123!
```

### Portas

- **Aplicação**: http://localhost:8082
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
# Ver logs da aplicação em tempo real
docker-compose logs -f cbkt-api-cadastro

# Ver todos os logs
docker-compose logs -f
```

## 🛠️ Comandos Úteis

```bash
# Parar o serviço
docker-compose down

# Reiniciar a aplicação
docker-compose restart cbkt-api-cadastro

# Reconstruir a aplicação
docker-compose up --build cbkt-api-cadastro

# Verificar status do container
docker-compose ps

# Entrar no container da aplicação
docker-compose exec cbkt-api-cadastro sh
```

## 🗄️ Configuração do Banco Externo

### Opções de Banco PostgreSQL Externo

1. **PostgreSQL Local**: Instalar PostgreSQL na máquina local
2. **PostgreSQL em Container Separado**: Rodar PostgreSQL em container separado
3. **PostgreSQL na Nuvem**: Usar serviços como AWS RDS, Google Cloud SQL, etc.

### Exemplo: PostgreSQL Local

```bash
# Instalar PostgreSQL (Ubuntu/Debian)
sudo apt update
sudo apt install postgresql postgresql-contrib

# Criar base de dados
sudo -u postgres createdb cbkt

# Configurar no .env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=cbkt
DB_USER=postgres
DB_PASS=sua_senha
```

### Exemplo: PostgreSQL em Container Separado

```bash
# Rodar PostgreSQL em container separado
docker run -d \
  --name postgres-cbkt \
  -e POSTGRES_DB=cbkt \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15-alpine

# Configurar no .env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=cbkt
DB_USER=postgres
DB_PASS=postgres
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

Se a porta 8082 da aplicação estiver em uso:

1. **Alterar porta no docker-compose.yml**:
   ```yaml
   ports:
     - "8083:8080"  # ou outra porta disponível
   ```

### Problemas de Conexão com Banco

1. **Verificar se o PostgreSQL externo está rodando**:
   ```bash
   # Testar conectividade
   telnet localhost 5432
   # ou
   nc -zv localhost 5432
   ```

2. **Verificar credenciais no .env**:
   ```bash
   cat .env | grep DB_
   ```

3. **Testar conexão direta ao banco**:
   ```bash
   psql -h localhost -p 5432 -U postgres -d cbkt
   ```

### Aplicação não inicia

1. Verifique os logs da aplicação:
   ```bash
   docker-compose logs cbkt-api-cadastro
   ```

2. Verifique se as variáveis de ambiente estão configuradas:
   ```bash
   docker-compose exec cbkt-api-cadastro env | grep DB_
   ```

## 🏗️ Arquitetura

```
┌─────────────────────┐    ┌─────────────────┐
│   cbkt-api-cadastro │────│   PostgreSQL    │
│   (Spring Boot)     │    │   (Externo)     │
│   (port 8082)       │    │                 │
└─────────────────────┘    └─────────────────┘
        │
        ├── /actuator/health (Health Check)
        ├── /swagger-ui.html (API Docs)
        └── /api/** (API Endpoints)
```

**Nota**: O PostgreSQL deve ser configurado e executado externamente ao Docker da aplicação.

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