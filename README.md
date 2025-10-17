# 📚 Biblioteca API - Sistema de Gerenciamento de Biblioteca

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)

API RESTful para gerenciamento de biblioteca desenvolvida com Spring Boot, Hibernate, JWT Authentication, e integração completa de observabilidade com Prometheus e Grafana.

## 📋 Índice

- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Endpoints da API](#-endpoints-da-api)
- [Banco de Dados](#-banco-de-dados)
- [Observabilidade](#-observabilidade)
- [Autenticação e Segurança](#-autenticação-e-segurança)
- [Métricas Personalizadas](#-métricas-personalizadas)
- [URLs de Acesso](#-urls-de-acesso)
- [Documentação Interativa (Swagger)](#-documentação-interativa-swagger)
- [Autor](#-autor)

---

## 🚀 Funcionalidades

- ✅ **CRUD completo** de Alunos, Autores, Livros e Empréstimos
- 🔐 **Autenticação JWT** com Spring Security
- 📊 **Métricas personalizadas** com Micrometer
- 💾 **Cache** com Spring Cache e Caffeine
- 📈 **Observabilidade** com Prometheus e Grafana
- 🐳 **Containerização** completa com Docker
- 📝 **Documentação interativa** com Swagger/OpenAPI
- 🔍 **Health checks** e monitoramento com Actuator
- 🌐 **Reverse proxy** com Nginx

---

## 🛠 Tecnologias

### Backend
- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA / Hibernate**
- **Spring Security + JWT**
- **PostgreSQL 15**
- **Lombok**
- **Maven**

### Observabilidade
- **Spring Boot Actuator**
- **Micrometer**
- **Prometheus**
- **Grafana**

### Infraestrutura
- **Docker & Docker Compose**
- **Nginx (Reverse Proxy)**
- **pgAdmin 4**

### Documentação
- **Swagger/OpenAPI 3**

---

## 🏗 Arquitetura

```
┌─────────────┐
│   Cliente   │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Nginx    │ (Reverse Proxy - Porta 80)
└──────┬──────┘
       │
       ├──────────────────┬─────────────────┬──────────────────┐
       ▼                  ▼                 ▼                  ▼
┌──────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│ API (8080)   │  │ Grafana     │  │ Prometheus  │  │  pgAdmin    │
│              │  │ (3000)      │  │ (9090)      │  │  (5050)     │
└──────┬───────┘  └─────────────┘  └──────┬──────┘  └─────────────┘
       │                                   │
       ▼                                   ▼
┌──────────────┐                    ┌─────────────┐
│  PostgreSQL  │◄───────────────────┤  Métricas   │
│   (5432)     │                    └─────────────┘
└──────────────┘
```

---

## 📦 Pré-requisitos

- **Docker** (versão 20.10+)
- **Docker Compose** (versão 2.0+)
- **Git**
- Portas disponíveis: `80`, `3000`, `5432`, `5050`, `8080`, `9090`

---

## 🚀 Instalação e Execução

### 1️⃣ Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd Minicurso---FEMA-2025
```

### 2️⃣ Configure as variáveis de ambiente (opcional)

Crie um arquivo `.env` na raiz do projeto:

```env
# Banco de Dados
DB_HOST=postgres
DB_PORT=5432
DB_NAME=biblioteca_minicurso
DB_USER=postgres
DB_PASSWORD=root

# JWT
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION=86400000

# Grafana
GF_SECURITY_ADMIN_PASSWORD=admin

# pgAdmin
PGADMIN_DEFAULT_EMAIL=admin@admin.com
PGADMIN_DEFAULT_PASSWORD=admin
```

### 3️⃣ Build da aplicação

```bash
docker-compose build
```

Este comando irá:
- Baixar todas as imagens Docker necessárias
- Compilar a aplicação Java com Maven
- Criar a imagem Docker da API
- Configurar a rede e volumes

⏱️ **Tempo estimado:** 5-10 minutos (primeira execução)

### 4️⃣ Inicie os serviços

```bash
docker-compose up -d
```

Parâmetros:
- `-d`: Executa em modo detached (background)

Para ver os logs em tempo real:
```bash
docker-compose logs -f api-biblioteca
```

### 5️⃣ Verifique os serviços

```bash
docker-compose ps
```

Todos os serviços devem estar com status **Up**:

```
NAME                IMAGE                       STATUS
api-biblioteca      minicurso-fema-2025:latest  Up
grafana             grafana/grafana:latest      Up
nginx               nginx:alpine                Up
pgadmin             dpage/pgadmin4:latest       Up
postgres            postgres:15-alpine          Up
prometheus          prom/prometheus:latest      Up
```

### 6️⃣ Parar os serviços

```bash
docker-compose down
```

Para remover volumes (dados do banco):
```bash
docker-compose down -v
```

---

## 🔧 Variáveis de Ambiente

### Aplicação (Spring Boot)

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `DB_HOST` | Host do PostgreSQL | `localhost` |
| `DB_PORT` | Porta do PostgreSQL | `5432` |
| `DB_NAME` | Nome do banco de dados | `biblioteca_minicurso` |
| `DB_USER` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `root` |
| `JWT_SECRET` | Chave secreta JWT (256 bits) | *(gerada automaticamente)* |
| `JWT_EXPIRATION` | Tempo de expiração do token (ms) | `86400000` (24h) |

### Grafana

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `GF_SECURITY_ADMIN_PASSWORD` | Senha do admin | `admin` |
| `GF_SERVER_ROOT_URL` | URL raiz do Grafana | `http://localhost/grafana/` |

### pgAdmin

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `PGADMIN_DEFAULT_EMAIL` | Email de login | `admin@admin.com` |
| `PGADMIN_DEFAULT_PASSWORD` | Senha de login | `admin` |

---

## 📡 Endpoints da API

**Base URL:** `http://localhost/api` (via Nginx) ou `http://localhost:8080/api` (direto)

### 🔓 Autenticação (Público)

#### Registrar Usuário
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "admin",
  "email": "admin@exemplo.com",
  "password": "senha123",
  "role": "ADMIN"
}
```

**Roles disponíveis:** `USER`, `ADMIN`

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

---

### 🔐 Endpoints Protegidos (Requerem Token JWT)

**Header obrigatório:**
```
Authorization: Bearer {seu_token_jwt}
```

### 👨‍🎓 Alunos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/aluno` | Lista todos os alunos |
| `GET` | `/api/aluno/{id}` | Busca aluno por ID |
| `POST` | `/api/aluno` | Cria novo aluno |
| `PUT` | `/api/aluno/{id}` | Atualiza aluno |
| `DELETE` | `/api/aluno/{id}` | Deleta aluno |

**Exemplo - Criar Aluno:**
```json
POST /api/aluno
{
  "nome": "João Silva",
  "matricula": "2023001"
}
```

### ✍️ Autores

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/autor` | Lista todos os autores |
| `GET` | `/api/autor/{id}` | Busca autor por ID |
| `POST` | `/api/autor` | Cria novo autor |
| `PUT` | `/api/autor/{id}` | Atualiza autor |
| `DELETE` | `/api/autor/{id}` | Deleta autor |

**Exemplo - Criar Autor:**
```json
POST /api/autor
{
  "nome": "Machado de Assis"
}
```

### 📖 Livros

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/livro` | Lista todos os livros |
| `GET` | `/api/livro/{id}` | Busca livro por ID |
| `GET` | `/api/livro/titulo/{titulo}` | Busca por título |
| `GET` | `/api/livro/autor/{nomeAutor}` | Lista livros por autor |
| `GET` | `/api/livro/emprestados` | Lista livros emprestados |
| `POST` | `/api/livro` | Cria novo livro (com geolocalização) |
| `PUT` | `/api/livro/{id}` | Atualiza livro |
| `DELETE` | `/api/livro/{id}` | Deleta livro |

**Exemplo - Criar Livro:**
```json
POST /api/livro
{
  "titulo": "Dom Casmurro",
  "autor": "uuid-do-autor"
}
```

### 📚 Empréstimos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/emprestimo` | Lista todos os empréstimos |
| `GET` | `/api/emprestimo/{id}` | Busca empréstimo por ID |
| `POST` | `/api/emprestimo` | Cria novo empréstimo |
| `POST` | `/api/emprestimo/devolucao/{id}` | Registra devolução |
| `DELETE` | `/api/emprestimo/{id}` | Deleta empréstimo |

**Exemplo - Criar Empréstimo:**
```json
POST /api/emprestimo
{
  "alunoId": "uuid-do-aluno",
  "livrosIds": ["uuid-livro-1", "uuid-livro-2"]
}
```

---

## 🗄️ Banco de Dados

### Configuração PostgreSQL

- **Host:** `localhost` (fora do Docker) ou `postgres` (dentro do Docker)
- **Porta:** `5432`
- **Database:** `biblioteca_minicurso`
- **Usuário:** `postgres`
- **Senha:** `root`

### Estrutura de Tabelas

#### `usuarios`
```sql
- id (UUID, PK)
- username (VARCHAR, UNIQUE)
- email (VARCHAR)
- password (VARCHAR, encrypted)
- role (VARCHAR: USER/ADMIN)
- enabled (BOOLEAN)
```

#### `alunos`
```sql
- id (UUID, PK)
- nome (VARCHAR)
- matricula (VARCHAR, UNIQUE)
- data_ultimo_livro_emprestado (TIMESTAMP)
```

#### `autores`
```sql
- id (UUID, PK)
- nome (VARCHAR)
```

#### `livros`
```sql
- id (UUID, PK)
- titulo (VARCHAR)
- autor_id (UUID, FK -> autores)
```

#### `emprestimos`
```sql
- id (UUID, PK)
- aluno_id (UUID, FK -> alunos)
- data_emprestimo (TIMESTAMP)
- data_devolucao (TIMESTAMP)
- ativo (BOOLEAN)
```

#### `emprestimo_livros` (Tabela de junção)
```sql
- emprestimo_id (UUID, FK -> emprestimos)
- livro_id (UUID, FK -> livros)
```

### Acesso via pgAdmin

1. Acesse: `http://localhost/pgadmin`
2. Login: `admin@admin.com`
3. Senha: `admin`
4. Adicione novo servidor:
   - **Name:** Biblioteca Local
   - **Host:** `postgres`
   - **Port:** `5432`
   - **Database:** `biblioteca_minicurso`
   - **Username:** `postgres`
   - **Password:** `root`

---

## 📊 Observabilidade

### Prometheus

**URL:** `http://localhost/prometheus`

Coleta métricas da aplicação a cada **15 segundos**.

**Métricas principais:**
- `jvm_memory_used_bytes`: Uso de memória JVM
- `http_server_requests_seconds`: Latência de requisições HTTP
- `operacoes_total`: Contador de operações por tipo
- `operacoes_geolocalizadas_total`: Operações com geolocalização
- `erros_total`: Contador de erros
- `auth_login_success_total`: Logins bem-sucedidos
- `auth_login_failure_total`: Logins falhados

**Queries úteis:**

```promql
# Taxa de requisições HTTP
rate(http_server_requests_seconds_count[5m])

# Operações de criação de livros
operacoes_total{tipo="criar_livro"}

# Taxa de erros
rate(erros_total[5m])

# Latência p95 das requisições
histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m]))
```

### Grafana

**URL:** `http://localhost/grafana`

**Credenciais:**
- **Usuário:** `admin`
- **Senha:** `admin`

#### Configurar Data Source (Primeira vez)

1. Acesse **Configuration** → **Data Sources**
2. Clique em **Add data source**
3. Selecione **Prometheus**
4. Configure:
   - **URL:** `http://prometheus:9090`
   - Clique em **Save & Test**

#### Dashboards Sugeridos

##### 1. Dashboard de Aplicação
- Requisições HTTP (total e por endpoint)
- Latência (média, p95, p99)
- Taxa de erros
- Memória JVM
- Threads ativas

##### 2. Dashboard de Autenticação
- Total de registros
- Logins bem-sucedidos vs falhados
- Taxa de falhas de autenticação

##### 3. Geomap - Criação de Livros
- **Visualization:** Geomap
- **Query:** `operacoes_geolocalizadas_total{tipo="criar_livro"}`
- **Map View:**
  - Latitude field: `latitude`
  - Longitude field: `longitude`

### Spring Boot Actuator

**Base URL:** `http://localhost:8080/api/actuator`

#### Endpoints Disponíveis

| Endpoint | Descrição |
|----------|-----------|
| `/health` | Status da aplicação e dependências |
| `/info` | Informações da aplicação |
| `/prometheus` | Métricas no formato Prometheus |
| `/metrics` | Métricas disponíveis |
| `/metrics/{metricName}` | Detalhes de métrica específica |

**Exemplo:**
```bash
curl http://localhost:8080/api/actuator/health
```

---

## 🔐 Autenticação e Segurança

### Fluxo de Autenticação

1. **Registrar** usuário via `/api/auth/register`
2. **Login** via `/api/auth/login` → recebe token JWT
3. **Incluir token** em todas as requisições protegidas:
   ```
   Authorization: Bearer {token}
   ```

### Segurança Implementada

- ✅ **JWT Stateless Authentication**
- ✅ **Password Encryption** com BCrypt
- ✅ **CORS Configuration** para múltiplas origens
- ✅ **CSRF Protection** desabilitado (API REST stateless)
- ✅ **Role-Based Access Control** (RBAC)
  - `USER`: Acesso limitado
  - `ADMIN`: Acesso total aos endpoints

### Endpoints Públicos

- `/api/auth/**` (registro e login)
- `/api/actuator/**` (health checks e métricas)
- `/swagger-ui/**` (documentação)
- `/v3/api-docs/**` (OpenAPI spec)

---

## 📈 Métricas Personalizadas

### Métricas de Autenticação

- `auth_login_success_total`: Total de logins bem-sucedidos
- `auth_login_failure_total`: Total de tentativas de login falhadas
- `auth_register_total`: Total de novos registros

### Métricas de Operações

- `operacoes_total{tipo="criar_livro"}`: Livros criados
- `operacoes_total{tipo="atualizar_livro"}`: Livros atualizados
- `operacoes_total{tipo="excluir_livro"}`: Livros excluídos
- `operacoes_total{tipo="criar_aluno"}`: Alunos criados
- `operacoes_total{tipo="atualizar_aluno"}`: Alunos atualizados

### Métricas de Desempenho

- `operacao_duracao_seconds`: Tempo de execução das operações

---

## 🌐 URLs de Acesso

### Produção (via Nginx)

| Serviço | URL | Credenciais |
|---------|-----|-------------|
| **API** | `http://localhost/api` | Token JWT |
| **Swagger UI** | `http://localhost/api/swagger-ui/index.html` | - |
| **Grafana** | `http://localhost/grafana` | admin / admin |
| **Prometheus** | `http://localhost/prometheus` | - |
| **pgAdmin** | `http://localhost/pgadmin` | admin@admin.com / admin |

### Desenvolvimento (Direto)

| Serviço | URL |
|---------|-----|
| **API** | `http://localhost:8080/api` |
| **Swagger UI** | `http://localhost:8080/api/swagger-ui/index.html` |
| **Actuator** | `http://localhost:8080/api/actuator` |
| **Grafana** | `http://localhost:3000` |
| **Prometheus** | `http://localhost:9090` |
| **pgAdmin** | `http://localhost:5050` |

---

## 📝 Documentação Interativa (Swagger)

Acesse a documentação interativa da API:

**URL:** `http://localhost/api/swagger-ui/index.html`

Recursos:
- 📖 Documentação completa de todos os endpoints
- 🧪 Teste interativo de requisições
- 📋 Schemas de request/response
- 🔐 Autenticação JWT integrada

### Como usar o Swagger:

1. Acesse a URL do Swagger
2. Faça login via `/auth/login` para obter o token
3. Clique em **Authorize** (cadeado no topo)
4. Cole o token: `Bearer {seu_token}`
5. Teste qualquer endpoint protegido

---

## 👤 Autor

**João Pedro Prestupa**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/joao-pedro-prestupa/)

Desenvolvido com ❤️ para o Minicurso de Spring Boot e Hibernate - FEMA 2025

---

**⭐ Se este projeto foi útil, considere dar uma estrela no GitHub!**
