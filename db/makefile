# CONTÊINERES
POSTGRES_CONTAINER=postgres
DB_USER=cbkt
DB_NAME=cbkt

# Arquivo SQL de seed
SEED_SQL=script.sql

### ===========================
### Comandos principais
### ===========================

# Popula sem apagar tabelas (útil pra testes rápidos)
seed:
	@echo "===> Populando tabelas via script.sql"
	docker exec -i $(POSTGRES_CONTAINER) psql -U $(DB_USER) -d $(DB_NAME) -f /db/script.sql

# Limpa tudo, reinicia sequences e repopula
reset-seed:
	@echo "===> Resetando tabelas + importando CSVs"
	docker exec -it $(POSTGRES_CONTAINER) psql -U $(DB_USER) -d $(DB_NAME) <<EOF
	TRUNCATE TABLE \
	   cadastro.atleta, \
	   cadastro.atleta_clube, \
	   cadastro.atleta_graduacao, \
	   cadastro.clube, \
	   cadastro.exame, \
	   cadastro.graduacao \
	   RESTART IDENTITY CASCADE;
	EOF
	docker exec -i $(POSTGRES_CONTAINER) psql -U $(DB_USER) -d $(DB_NAME) -f /db/script.sql
	@echo "===> Reset concluído."

# Importa apenas tabelas alteradas (mais rápido)
seed-fast:
	@echo "===> Seed rápido"
	docker exec -i $(POSTGRES_CONTAINER) psql -U $(DB_USER) -d $(DB_NAME) -f /db/script.sql

# Mostra contagens e próxima chave das sequences
verify-seed:
	@echo "===> Verificando dados importados"
	docker exec -it $(POSTGRES_CONTAINER) psql -U $(DB_USER) -d $(DB_NAME) <<EOF
	SELECT 'ATLETA:' , COUNT(*) FROM cadastro.atleta;
	SELECT 'CLUBE:' , COUNT(*) FROM cadastro.clube;
	SELECT 'GRADUACAO:' , COUNT(*) FROM cadastro.graduacao;
	SELECT 'EXAME:' , COUNT(*) FROM cadastro.exame;
	SELECT 'ATLETA_CLUBE:' , COUNT(*) FROM cadastro.atleta_clube;
	SELECT 'ATLETA_GRADUACAO:' , COUNT(*) FROM cadastro.atleta_graduacao;

	SELECT 'NEXTVAL ATLETA:' , nextval(pg_get_serial_sequence('cadastro.atleta','codigoatleta'));
	SELECT 'NEXTVAL CLUBE:' , nextval(pg_get_serial_sequence('cadastro.clube','codigoclube'));
	SELECT 'NEXTVAL GRADUACAO:' , nextval(pg_get_serial_sequence('cadastro.graduacao','codigograduacao'));
	SELECT 'NEXTVAL EXAME:' , nextval(pg_get_serial_sequence('cadastro.exame','codigoexame'));
	EOF
