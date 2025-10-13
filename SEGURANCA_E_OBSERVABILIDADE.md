# 🔐 Segurança e Observabilidade - Guia Completo

## 📚 Índice
1. [Visão Geral](#visão-geral)
2. [Spring Security + JWT](#spring-security--jwt)
3. [Spring Boot Actuator](#spring-boot-actuator)
4. [Métricas Customizadas](#métricas-customizadas)
5. [Como Usar](#como-usar)
6. [Configuração no Grafana](#configuração-no-grafana)
7. [Segurança em Produção](#segurança-em-produção)

---

## 🎯 Visão Geral

Este projeto agora possui:
- ✅ **Autenticação JWT** - Sistema de login seguro com tokens
- ✅ **Autorização baseada em roles** - ADMIN e USER
- ✅ **Spring Boot Actuator** - Endpoints de monitoramento
- ✅ **Métricas Customizadas** - Contadores de operações
- ✅ **Integração com Prometheus** - Exportação de métricas
- ✅ **OAuth2 Resource Server** - Suporte a OAuth2

---

## 🔐 Spring Security + JWT

### O que foi implementado?

#### 1. **Entidade Usuario**
- Implementa `UserDetails` do Spring Security
- Suporta dois tipos de roles: `ADMIN` e `USER`
- Armazenada no PostgreSQL com senha criptografada (BCrypt)

#### 2. **Sistema de Autenticação**
- **JWT (JSON Web Token)** - Token de acesso válido por 24 horas
- **BCrypt Password Encoder** - Senhas nunca são armazenadas em texto puro
- **Stateless Authentication** - Não usa sessões (ideal para APIs REST)

#### 3. **Filtro de Segurança**
- Intercepta todas as requisições
- Valida o token JWT no header `Authorization`
- Adiciona o usuário autenticado no contexto do Spring Security

### Como funciona?

```
1. Cliente faz POST /auth/register ou /auth/login
2. Backend valida credenciais
3. Backend gera token JWT assinado
4. Cliente recebe o token
5. Cliente envia token no header: Authorization: Bearer <token>
6. Backend valida token e autoriza acesso
```

### Endpoints Públicos (não requerem autenticação)
- `/auth/register` - Registrar novo usuário
- `/auth/login` - Fazer login
- `/swagger-ui/**` - Documentação Swagger
- `/actuator/**` - Endpoints de monitoramento

### Endpoints Protegidos
- **Qualquer usuário autenticado** pode:
  - Listar alunos, livros, autores, empréstimos
  - Criar novos registros
  - Atualizar seus próprios dados

- **Apenas ADMIN** pode:
  - Deletar alunos (`DELETE /alunos/{id}`)
  - Deletar autores (`DELETE /autores/{id}`)
  - Deletar livros (`DELETE /livros/{id}`)

---

## 🚀 Como Usar

### 1. Registrar um usuário ADMIN

**Request:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@biblioteca.com",
    "role": "ADMIN"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5...",
  "type": "Bearer",
  "username": "admin",
  "role": "ADMIN"
}
```

### 2. Registrar um usuário comum (USER)

**Request:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joao",
    "password": "senha123",
    "email": "joao@email.com"
  }'
```

> **Nota:** Se não especificar `role`, o padrão é `USER`

### 3. Fazer Login

**Request:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5...",
  "type": "Bearer",
  "username": "admin",
  "role": "ADMIN"
}
```

### 4. Usar o token nas requisições

Copie o token recebido e adicione no header `Authorization`:

**Exemplo: Listar alunos**
```bash
curl -X GET http://localhost:8080/api/alunos \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5..."
```

**Exemplo: Criar aluno**
```bash
curl -X POST http://localhost:8080/api/alunos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Silva",
    "matricula": "123456"
  }'
```

**Exemplo: Deletar aluno (apenas ADMIN)**
```bash
curl -X DELETE http://localhost:8080/api/alunos/{id} \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 5. Usando no Swagger

1. Acesse: http://localhost:8080/api/swagger-ui.html
2. Faça login via endpoint `/auth/login`
3. Copie o token retornado
4. Clique no botão **"Authorize"** (cadeado) no topo da página
5. Digite: `Bearer SEU_TOKEN_AQUI`
6. Clique em "Authorize"
7. Agora todos os endpoints protegidos funcionarão!

---

## 📊 Spring Boot Actuator

### O que é?

O Spring Boot Actuator expõe endpoints para **monitoramento e gerenciamento** da aplicação em tempo real.

### Endpoints Disponíveis

| Endpoint | Descrição | Exemplo |
|----------|-----------|---------|
| `/actuator` | Lista todos os endpoints disponíveis | http://localhost:8080/api/actuator |
| `/actuator/health` | Status da aplicação e dependências | http://localhost:8080/api/actuator/health |
| `/actuator/info` | Informações sobre a aplicação | http://localhost:8080/api/actuator/info |
| `/actuator/metrics` | Lista de métricas disponíveis | http://localhost:8080/api/actuator/metrics |
| `/actuator/metrics/{metricName}` | Detalhes de uma métrica específica | http://localhost:8080/api/actuator/metrics/jvm.memory.used |
| `/actuator/prometheus` | Métricas no formato Prometheus | http://localhost:8080/api/actuator/prometheus |
| `/actuator/env` | Variáveis de ambiente | http://localhost:8080/api/actuator/env |
| `/actuator/loggers` | Configuração de logs | http://localhost:8080/api/actuator/loggers |

### Exemplo de Response - Health

```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 499963174912,
        "free": 200000000000,
        "threshold": 10485760
      }
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

### Exemplo de Response - Info

```json
{
  "app": {
    "name": "Biblioteca API",
    "description": "API de Gerenciamento de Biblioteca com Spring Boot",
    "version": "1.0.0",
    "java": {
      "version": "17"
    }
  }
}
```

---

## 📈 Métricas Customizadas

### Métricas de Autenticação

Criadas no `AuthenticationService`:

1. **`auth_login_success_total`** - Contador de logins bem-sucedidos
2. **`auth_login_failure_total`** - Contador de logins falhados
3. **`auth_register_total`** - Contador de novos registros

### Métricas de Alunos

Criadas no `AlunoService`:

1. **`alunos_criados_total`** - Contador de alunos criados
2. **`alunos_atualizados_total`** - Contador de alunos atualizados
3. **`alunos_excluidos_total`** - Contador de alunos excluídos

### Métricas Automáticas do Spring Boot

- `http_server_requests_seconds_*` - Tempo de resposta das requisições HTTP
- `jvm_memory_used_bytes` - Memória JVM utilizada
- `jvm_threads_live` - Número de threads ativas
- `process_cpu_usage` - Uso de CPU
- `system_cpu_usage` - Uso de CPU do sistema
- `jdbc_connections_active` - Conexões JDBC ativas

### Como visualizar?

**Listar todas as métricas:**
```bash
curl http://localhost:8080/api/actuator/metrics
```

**Ver métrica específica:**
```bash
# Logins bem-sucedidos
curl http://localhost:8080/api/actuator/metrics/auth.login.success

# Alunos criados
curl http://localhost:8080/api/actuator/metrics/alunos.criados

# Uso de memória JVM
curl http://localhost:8080/api/actuator/metrics/jvm.memory.used
```

**Response exemplo:**
```json
{
  "name": "auth.login.success",
  "description": "Numero de logins bem-sucedidos",
  "baseUnit": null,
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 15.0
    }
  ]
}
```

---

## 📊 Configuração no Grafana

### 1. Configurar Data Source

1. Acesse http://localhost:3000
2. Login: `admin` / `admin`
3. Vá em: **Configuration** → **Data Sources** → **Add data source**
4. Selecione **Prometheus**
5. Configure:
   - **Name:** Prometheus
   - **URL:** `http://prometheus:9090`
6. Clique em **Save & Test**

### 2. Criar Dashboard

1. Vá em **Create** → **Dashboard** → **Add new panel**
2. Use as queries abaixo:

#### Painel 1: Taxa de Login
```promql
rate(auth_login_success_total[5m])
```
- Título: "Taxa de Logins Bem-Sucedidos (por minuto)"
- Tipo: Graph

#### Painel 2: Total de Logins vs Falhas
```promql
auth_login_success_total
auth_login_failure_total
```
- Título: "Logins: Sucesso vs Falha"
- Tipo: Stat

#### Painel 3: Operações CRUD de Alunos
```promql
alunos_criados_total
alunos_atualizados_total
alunos_excluidos_total
```
- Título: "Operações em Alunos"
- Tipo: Bar gauge

#### Painel 4: Tempo de Resposta por Endpoint
```promql
rate(http_server_requests_seconds_sum{uri=~"/api/alunos.*"}[5m])
/
rate(http_server_requests_seconds_count{uri=~"/api/alunos.*"}[5m])
```
- Título: "Tempo Médio de Resposta (segundos)"
- Tipo: Graph

#### Painel 5: Uso de Memória JVM
```promql
jvm_memory_used_bytes{area="heap"}
```
- Título: "Memória Heap Utilizada"
- Tipo: Graph

#### Painel 6: Requisições HTTP por Status
```promql
rate(http_server_requests_seconds_count[5m])
```
- Legenda: `{{status}} - {{method}} {{uri}}`
- Título: "Requisições HTTP por Status"
- Tipo: Graph

### 3. Salvar Dashboard

1. Clique no ícone de **disquete** no topo
2. Dê um nome: "Biblioteca API - Métricas"
3. Clique em **Save**

---

## 🔒 Segurança em Produção

### ⚠️ IMPORTANTE: Antes de ir para produção

#### 1. Altere a chave JWT

A chave atual é uma chave de exemplo. **Nunca use em produção!**

**Gerar nova chave:**
```bash
# No Linux/Mac:
openssl rand -base64 64

# No Windows (PowerShell):
[Convert]::ToBase64String((1..64 | ForEach-Object { Get-Random -Minimum 0 -Maximum 256 }))
```

**Adicione como variável de ambiente:**
```bash
export JWT_SECRET="sua_chave_segura_gerada_aqui"
```

#### 2. Use variáveis de ambiente para senhas

Nunca coloque senhas em `application.properties`!

**Exemplo no docker-compose.yml:**
```yaml
environment:
  - DB_PASSWORD=${DB_PASSWORD}
  - JWT_SECRET=${JWT_SECRET}
```

#### 3. Configure HTTPS

Em produção, **sempre** use HTTPS para proteger os tokens JWT em trânsito.

#### 4. Limite endpoints do Actuator

Não exponha todos os endpoints em produção:

```properties
# Produção - apenas health e metrics
management.endpoints.web.exposure.include=health,metrics,prometheus
```

#### 5. Configure CORS

Se sua API será consumida por frontend em domínio diferente:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://seu-frontend.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

#### 6. Adicione Rate Limiting

Proteja contra ataques de força bruta:

```xml
<dependency>
    <groupId>com.bucket4j</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>8.7.0</version>
</dependency>
```

#### 7. Configure expiração de tokens

Ajuste o tempo de expiração conforme necessidade:

```properties
# 1 hora = 3600000 ms
jwt.expiration=3600000

# 24 horas = 86400000 ms (padrão)
jwt.expiration=86400000
```

---

## 🧪 Testando a Implementação

### 1. Teste de Registro

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"teste","password":"123456","email":"teste@email.com"}'
```

**Esperado:** Status 200 + token JWT

### 2. Teste de Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"teste","password":"123456"}'
```

**Esperado:** Status 200 + token JWT

### 3. Teste de Acesso Negado (sem token)

```bash
curl -X GET http://localhost:8080/api/alunos
```

**Esperado:** Status 403 (Forbidden)

### 4. Teste de Acesso Autorizado (com token)

```bash
TOKEN="seu_token_aqui"

curl -X GET http://localhost:8080/api/alunos \
  -H "Authorization: Bearer $TOKEN"
```

**Esperado:** Status 200 + lista de alunos

### 5. Teste de Acesso ADMIN

```bash
# Como USER (deve falhar)
curl -X DELETE http://localhost:8080/api/alunos/{id} \
  -H "Authorization: Bearer $TOKEN_USER"

# Como ADMIN (deve funcionar)
curl -X DELETE http://localhost:8080/api/alunos/{id} \
  -H "Authorization: Bearer $TOKEN_ADMIN"
```

### 6. Teste do Actuator

```bash
# Health check
curl http://localhost:8080/api/actuator/health

# Métricas
curl http://localhost:8080/api/actuator/metrics

# Prometheus
curl http://localhost:8080/api/actuator/prometheus | grep auth_login
```

---

## 🐛 Troubleshooting

### Erro: "Full authentication is required"

**Causa:** Token não foi enviado ou é inválido

**Solução:**
1. Verifique se está enviando o header: `Authorization: Bearer <token>`
2. Verifique se o token não expirou (24 horas)
3. Faça login novamente para obter novo token

### Erro: "Access Denied"

**Causa:** Usuário não tem permissão (role insuficiente)

**Solução:**
- Endpoints de DELETE requerem role ADMIN
- Registre um usuário com role ADMIN

### Erro: "Usuario ja existe"

**Causa:** Username já está cadastrado

**Solução:**
- Use outro username
- Ou faça login com o username existente

### Métricas não aparecem no Grafana

**Solução:**
1. Verifique se Prometheus está coletando dados:
   - Acesse http://localhost:9090/targets
   - Procure por `api-biblioteca`
   - Status deve ser **UP**
2. No Grafana, teste a query diretamente:
   - Vá em Explore
   - Digite: `auth_login_success_total`
   - Execute

---

## 📚 Arquitetura da Solução

```
┌─────────────────┐
│   Cliente       │
│  (Frontend)     │
└────────┬────────┘
         │
         │ 1. POST /auth/login
         ▼
┌─────────────────────────────┐
│   AuthController            │
└────────┬────────────────────┘
         │
         │ 2. Valida credenciais
         ▼
┌─────────────────────────────┐
│  AuthenticationService      │
│  - BCrypt validation        │
│  - Gera JWT token           │
│  - Incrementa métricas      │
└────────┬────────────────────┘
         │
         │ 3. Retorna token
         ▼
┌─────────────────┐
│   Cliente       │
│  (guarda token) │
└────────┬────────┘
         │
         │ 4. GET /alunos
         │    Header: Authorization: Bearer <token>
         ▼
┌─────────────────────────────┐
│  JwtAuthenticationFilter    │
│  - Extrai token             │
│  - Valida assinatura        │
│  - Verifica expiração       │
└────────┬────────────────────┘
         │
         │ 5. Token válido
         ▼
┌─────────────────────────────┐
│  SecurityContext            │
│  (usuário autenticado)      │
└────────┬────────────────────┘
         │
         │ 6. Acesso autorizado
         ▼
┌─────────────────────────────┐
│  AlunoController            │
└────────┬────────────────────┘
         │
         ▼
    [Response]
```

---

## 🎓 Conceitos Explicados

### O que é JWT?

**JWT (JSON Web Token)** é um padrão de token que contém informações (claims) codificadas em Base64 e assinadas criptograficamente.

**Estrutura:**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV
│        Header       │     Payload     │        Signature         │
```

**Vantagens:**
- ✅ Stateless - Não precisa armazenar sessões no servidor
- ✅ Escalável - Funciona bem com múltiplas instâncias
- ✅ Seguro - Assinado criptograficamente
- ✅ Portátil - Pode ser usado entre diferentes domínios

### O que é BCrypt?

Algoritmo de hash de senha com **salt** automático que torna o hash lento propositalmente para dificultar ataques de força bruta.

**Exemplo:**
```
Senha: "admin123"
Hash:  "$2a$10$N9qo8uLOickgx2ZMRZoMye7FP6jHB2WVqk5O.7"
```

Mesmo senha = hashes diferentes (por causa do salt aleatório)

### O que é Spring Security?

Framework de segurança que fornece:
- Autenticação (quem você é?)
- Autorização (o que você pode fazer?)
- Proteção contra ataques (CSRF, Session Fixation, etc.)

### O que é Actuator?

Módulo do Spring Boot que expõe informações de **produção** sobre a aplicação:
- Saúde (health checks)
- Métricas (performance, uso de recursos)
- Informações (versão, configuração)
- Logs dinâmicos

---

## 📝 Resumo

✅ **Autenticação JWT** implementada
✅ **Autorização por roles** (ADMIN/USER)
✅ **Spring Boot Actuator** configurado
✅ **Métricas customizadas** criadas
✅ **Integração com Prometheus** funcionando
✅ **Documentação Swagger** atualizada
✅ **Segurança** configurada corretamente

**Próximos passos sugeridos:**
1. Implementar refresh tokens
2. Adicionar recuperação de senha
3. Implementar auditoria de ações
4. Adicionar rate limiting
5. Configurar alertas no Grafana

---

**Desenvolvido para o Minicurso FEMA 2025** 🚀

