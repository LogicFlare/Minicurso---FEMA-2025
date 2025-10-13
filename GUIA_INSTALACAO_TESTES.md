# 🚀 Guia de Instalação e Teste - Spring Security + Actuator

## ⚠️ IMPORTANTE: Resolver Erros de Compilação

Após adicionar as novas dependências, você precisa recarregar o Maven:

### No IntelliJ IDEA:

1. **Abra a aba Maven** (lado direito da IDE)
2. Clique no ícone de **"Reload All Maven Projects"** (🔄)
3. Aguarde o download das dependências
4. **OU** pressione: `Ctrl + Shift + O` (Windows/Linux) ou `Cmd + Shift + I` (Mac)

### Via Terminal:

```bash
cd C:\Users\joao.pedro\Desktop\lg\minicursos\Minicurso---FEMA-2025

# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

---

## 📋 Checklist de Implementação

### ✅ Arquivos Criados

- [x] `Usuario.java` - Entidade de usuário
- [x] `UsuarioRepository.java` - Repository
- [x] `LoginRequestDTO.java` - DTO de login
- [x] `LoginResponseDTO.java` - DTO de resposta
- [x] `RegisterRequestDTO.java` - DTO de registro
- [x] `JwtService.java` - Serviço JWT
- [x] `AuthenticationService.java` - Serviço de autenticação
- [x] `ApplicationConfig.java` - Configuração de segurança
- [x] `JwtAuthenticationFilter.java` - Filtro JWT
- [x] `SecurityConfig.java` - Configuração Spring Security
- [x] `AuthController.java` - Controller de autenticação

### ✅ Arquivos Modificados

- [x] `pom.xml` - Dependências adicionadas
- [x] `application.properties` - Configurações JWT e Actuator
- [x] `AlunoService.java` - Métricas customizadas

### ✅ Documentação

- [x] `SEGURANCA_E_OBSERVABILIDADE.md` - Guia completo

---

## 🔧 Passo a Passo para Testar

### 1. Recarregar dependências Maven

```bash
# No diretório do projeto
mvnw.cmd clean install -DskipTests
```

### 2. Verificar se não há erros de compilação

No IntelliJ, verifique se não há mais linhas vermelhas nos arquivos criados.

### 3. Subir o banco de dados (Docker)

```bash
docker-compose up -d postgres
```

### 4. Rodar a aplicação

**Opção 1: No IntelliJ**
- Clique com botão direito em `MinicursoHibernateApplication.java`
- Selecione "Run"

**Opção 2: Via terminal**
```bash
mvnw.cmd spring-boot:run
```

**Opção 3: Docker completo**
```bash
docker-compose up -d --build
```

### 5. Verificar se a aplicação subiu

```bash
curl http://localhost:8080/api/actuator/health
```

**Resposta esperada:**
```json
{
  "status": "UP"
}
```

---

## 🧪 Testes Passo a Passo

### Teste 1: Registrar usuário ADMIN

```bash
curl -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"admin\",\"password\":\"admin123\",\"email\":\"admin@biblioteca.com\",\"role\":\"ADMIN\"}"
```

**✅ Sucesso:** Você receberá um JSON com `token`, `username` e `role`

**❌ Erro comum:** 
- "404 Not Found" → Aplicação não está rodando
- "500 Internal Server Error" → Banco de dados não está rodando

### Teste 2: Fazer login

```bash
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

**Copie o token retornado!** Você vai precisar dele.

### Teste 3: Tentar acessar endpoint protegido SEM token

```bash
curl -X GET http://localhost:8080/api/alunos
```

**✅ Sucesso:** Deve retornar erro 403 (Forbidden)

### Teste 4: Acessar endpoint COM token

**Substitua `SEU_TOKEN_AQUI` pelo token que você copiou:**

```bash
curl -X GET http://localhost:8080/api/alunos ^
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

**✅ Sucesso:** Retorna lista de alunos (pode estar vazia)

### Teste 5: Criar um aluno

```bash
curl -X POST http://localhost:8080/api/alunos ^
  -H "Authorization: Bearer SEU_TOKEN_AQUI" ^
  -H "Content-Type: application/json" ^
  -d "{\"nome\":\"João Silva\",\"matricula\":\"123456\"}"
```

### Teste 6: Ver métricas customizadas

```bash
# Ver métrica de logins
curl http://localhost:8080/api/actuator/metrics/auth.login.success

# Ver métrica de alunos criados
curl http://localhost:8080/api/actuator/metrics/alunos.criados
```

### Teste 7: Ver todas as métricas no formato Prometheus

```bash
curl http://localhost:8080/api/actuator/prometheus | findstr auth
```

### Teste 8: Testar permissão ADMIN

```bash
# Criar um usuário comum (não admin)
curl -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"user\",\"password\":\"user123\",\"email\":\"user@biblioteca.com\"}"

# Copie o token do USER
# Tente deletar um aluno (deve dar erro 403)
curl -X DELETE http://localhost:8080/api/alunos/{ID_DO_ALUNO} ^
  -H "Authorization: Bearer TOKEN_DO_USER"

# Agora com token ADMIN (deve funcionar)
curl -X DELETE http://localhost:8080/api/alunos/{ID_DO_ALUNO} ^
  -H "Authorization: Bearer TOKEN_DO_ADMIN"
```

---

## 🎯 Testando no Swagger

1. Acesse: http://localhost:8080/api/swagger-ui.html

2. Vá até o endpoint `/auth/login` (seção "Autenticacao")

3. Clique em **"Try it out"**

4. Digite:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```

5. Clique em **"Execute"**

6. **Copie o token** da resposta

7. Role até o topo da página e clique no botão **"Authorize"** (cadeado verde)

8. Digite: `Bearer SEU_TOKEN_AQUI`

9. Clique em **"Authorize"** e depois **"Close"**

10. Agora você pode testar todos os outros endpoints! 🎉

---

## 🐳 Testando com Docker Completo

### 1. Subir tudo

```bash
docker-compose up -d --build
```

### 2. Aguardar containers iniciarem

```bash
docker-compose ps
```

**Aguarde até todos estarem "Up" (pode levar 1-2 minutos)**

### 3. Ver logs da aplicação

```bash
docker-compose logs -f api-biblioteca
```

**Procure por:** `Started MinicursoHibernateApplication`

### 4. Testar

```bash
curl http://localhost:8080/api/actuator/health
```

---

## 📊 Configurar Dashboard no Grafana

### 1. Acessar Grafana

http://localhost:3000

**Login:** admin / admin

### 2. Adicionar Data Source

1. Menu lateral → **Configuration** → **Data Sources**
2. Clique em **"Add data source"**
3. Selecione **"Prometheus"**
4. URL: `http://prometheus:9090`
5. Clique em **"Save & Test"**

**✅ Deve aparecer:** "Data source is working"

### 3. Criar Dashboard

1. Menu lateral → **Create** → **Dashboard**
2. Clique em **"Add new panel"**
3. No campo de query, digite:
   ```promql
   rate(auth_login_success_total[5m])
   ```
4. Em "Title", digite: "Taxa de Logins (por minuto)"
5. Clique em **"Apply"**

### 4. Adicionar mais painéis

**Painel 2: Total de operações em Alunos**
```promql
alunos_criados_total
alunos_atualizados_total
alunos_excluidos_total
```

**Painel 3: Uso de Memória**
```promql
jvm_memory_used_bytes{area="heap"}
```

**Painel 4: Requisições HTTP por segundo**
```promql
rate(http_server_requests_seconds_count[5m])
```

### 5. Salvar Dashboard

1. Clique no ícone de **disquete** no topo
2. Nome: "Biblioteca - Métricas"
3. Clique em **"Save"**

---

## 🔍 Verificar se está tudo funcionando

### Checklist Final

- [ ] Dependências Maven carregadas (sem erros vermelhos no código)
- [ ] Aplicação inicia sem erros
- [ ] `/actuator/health` retorna status UP
- [ ] Consigo registrar usuário
- [ ] Consigo fazer login e recebo token
- [ ] Token funciona para acessar endpoints protegidos
- [ ] Usuário USER não consegue deletar (403)
- [ ] Usuário ADMIN consegue deletar
- [ ] Métricas aparecem em `/actuator/metrics`
- [ ] Prometheus coleta métricas da aplicação
- [ ] Grafana mostra gráficos

---

## 🐛 Problemas Comuns

### Erro: "Cannot resolve symbol 'security'"

**Causa:** Maven não baixou as dependências

**Solução:**
```bash
mvnw.cmd clean install -U
```

No IntelliJ: `File` → `Invalidate Caches` → `Invalidate and Restart`

### Erro: "Table 'usuarios' doesn't exist"

**Causa:** Hibernate não criou a tabela

**Solução:** Verifique se `spring.jpa.hibernate.ddl-auto=update` está em `application.properties`

### Erro: "Full authentication is required"

**Causa:** Token não foi enviado ou está inválido

**Solução:** 
- Verifique se está enviando: `Authorization: Bearer TOKEN`
- Token expira em 24 horas, faça login novamente

### Erro ao subir Docker: "port is already allocated"

**Solução:**
```bash
# Parar tudo
docker-compose down

# Ver o que está usando a porta
netstat -ano | findstr :8080

# Matar o processo (substitua PID)
taskkill /PID <PID> /F

# Subir novamente
docker-compose up -d
```

### Prometheus não coleta métricas

**Verificar:**
1. Acesse http://localhost:9090/targets
2. Procure por `api-biblioteca`
3. Status deve estar **UP**

**Se estiver DOWN:**
- Verifique se a API está rodando
- Verifique se `/actuator/prometheus` responde

---

## 📞 Comandos Úteis

```bash
# Ver logs em tempo real
docker-compose logs -f

# Ver logs apenas da API
docker-compose logs -f api-biblioteca

# Reiniciar um serviço
docker-compose restart api-biblioteca

# Parar tudo
docker-compose down

# Rebuild completo
docker-compose down -v
docker-compose up -d --build

# Acessar bash do container
docker exec -it api-biblioteca sh

# Ver banco de dados
docker exec -it postgres-biblioteca psql -U postgres -d biblioteca_minicurso
```

---

## ✅ Próximos Passos

Depois que tudo estiver funcionando:

1. Explore a documentação em `SEGURANCA_E_OBSERVABILIDADE.md`
2. Crie dashboards no Grafana
3. Teste diferentes cenários de autenticação
4. Experimente criar métricas customizadas para outros services
5. Configure alertas no Grafana

---

**Qualquer dúvida, consulte `SEGURANCA_E_OBSERVABILIDADE.md` para documentação completa!** 📚

Desenvolvido para o Minicurso FEMA 2025 🚀

