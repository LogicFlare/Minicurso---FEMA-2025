# Instalação Completa com Docker Compose

## Pré-requisitos
- Docker instalado
- Docker Compose instalado

## 1. Subir toda a stack (PostgreSQL, pgAdmin, API, Prometheus e Grafana)

```bash
# Navegar até o diretório do projeto
cd Minicurso---FEMA-2025

# Subir todos os containers
docker-compose up -d

# Verificar se todos os containers estão rodando
docker-compose ps
```

## 2. Serviços disponíveis após o docker-compose up

| Serviço | URL | Credenciais |
|---------|-----|-------------|
| **API** | http://localhost:8080/api | - |
| **Swagger** | http://localhost:8080/api/swagger-ui.html | - |
| **PostgreSQL** | localhost:5432 | User: `postgres` / Pass: `root` |
| **pgAdmin** | http://localhost:5050 | Email: `admin@admin.com` / Pass: `admin` |
| **Prometheus** | http://localhost:9090 | - |
| **Grafana** | http://localhost:3000 | User: `admin` / Pass: `admin` |

## 3. Configurar pgAdmin

1. Acesse http://localhost:5050
2. Faça login com:
   - Email: `admin@admin.com`
   - Senha: `admin`
3. Clique em "Add New Server"
4. Na aba "General":
   - Name: `PostgreSQL Biblioteca`
5. Na aba "Connection":
   - Host: `postgres` (nome do container)
   - Port: `5432`
   - Maintenance database: `biblioteca_minicurso`
   - Username: `postgres`
   - Password: `root`
   - Save password: ✓
6. Clique em "Save"

## 4. Comandos úteis

```bash
# Ver logs de todos os containers
docker-compose logs -f

# Ver logs de um container específico
docker-compose logs -f api-biblioteca
docker-compose logs -f postgres
docker-compose logs -f pgadmin

# Parar todos os containers
docker-compose down

# Parar e remover volumes (CUIDADO: apaga dados do banco)
docker-compose down -v

# Rebuild da API após mudanças no código
docker-compose up -d --build api-biblioteca

# Reiniciar um serviço específico
docker-compose restart api-biblioteca

# Ver status dos containers
docker-compose ps

# Acessar o terminal de um container
docker exec -it postgres-biblioteca psql -U postgres -d biblioteca_minicurso
docker exec -it api-biblioteca sh
```

## 5. Backup e Restore do Banco de Dados

### Fazer backup:
```bash
# Backup do banco
docker exec -t postgres-biblioteca pg_dump -U postgres biblioteca_minicurso > backup_biblioteca_$(date +%Y%m%d_%H%M%S).sql
```

### Restaurar backup:
```bash
# Restaurar backup
docker exec -i postgres-biblioteca psql -U postgres biblioteca_minicurso < backup_biblioteca.sql
```

## 6. Verificar saúde dos containers

```bash
# Ver informações de saúde
docker inspect postgres-biblioteca | grep -A 10 Health

# Testar conexão com o banco
docker exec postgres-biblioteca pg_isready -U postgres
```

## 7. Configurar Prometheus no Grafana

1. Acesse http://localhost:3000
2. Login: `admin` / `admin`
3. Vá em: Configuration → Data Sources → Add data source
4. Selecione "Prometheus"
5. Configure:
   - URL: `http://prometheus:9090`
6. Clique em "Save & Test"

## 8. Acessar métricas da aplicação

- Métricas Prometheus: http://localhost:8080/api/actuator/prometheus
- Health check: http://localhost:8080/api/actuator/health
- Info: http://localhost:8080/api/actuator/info

## 9. Estrutura de volumes (persistência de dados)

Os dados são persistidos nos seguintes volumes Docker:
- `postgres-data`: Dados do PostgreSQL
- `pgadmin-data`: Configurações do pgAdmin
- `grafana-storage`: Dashboards e configurações do Grafana

Para visualizar os volumes:
```bash
docker volume ls
```

## 10. Solução de Problemas

### API não conecta ao banco:
```bash
# Verificar se o postgres está saudável
docker-compose ps

# Ver logs do postgres
docker-compose logs postgres

# Verificar conectividade
docker exec api-biblioteca ping postgres
```

### Porta já em uso:
```bash
# Windows
netstat -ano | findstr :8080
netstat -ano | findstr :5432
netstat -ano | findstr :5050

# Matar processo (substitua PID pelo número encontrado)
taskkill /PID <PID> /F
```

### Rebuild completo:
```bash
# Parar tudo e limpar
docker-compose down -v

# Remover imagens antigas
docker rmi minicurso---fema-2025-api-biblioteca

# Rebuild e subir
docker-compose up -d --build
```

### Reset do banco de dados:
```bash
# Parar containers
docker-compose down

# Remover apenas volume do postgres
docker volume rm minicurso---fema-2025_postgres-data

# Subir novamente (vai criar banco limpo)
docker-compose up -d
```

## 11. Ambiente de Desenvolvimento vs Produção

### Desenvolvimento (sem Docker):
Se quiser rodar a API localmente no IntelliJ e usar apenas o banco Docker:

```bash
# Subir apenas postgres e pgadmin
docker-compose up -d postgres pgadmin

# A API rodará no IntelliJ e se conectará ao banco em localhost:5432
```

### Produção (tudo no Docker):
```bash
# Subir tudo
docker-compose up -d
```

---

## 📌 Resumo Rápido

```bash
# 1. Subir tudo
docker-compose up -d

# 2. Acessar:
# - API: http://localhost:8080/api
# - pgAdmin: http://localhost:5050 (admin@admin.com / admin)
# - Grafana: http://localhost:3000 (admin / admin)
# - Prometheus: http://localhost:9090

# 3. Ver logs
docker-compose logs -f

# 4. Parar tudo
docker-compose down
```
