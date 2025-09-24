# 📚 Sistema de Biblioteca (Exemplo com Spring + Hibernate)

Este projeto é um **exemplo introdutório** de aplicação Java usando **Spring Boot**, **Spring Data JPA** e **Hibernate**.  
O objetivo é demonstrar como funciona o mapeamento de entidades, a persistência de dados e algumas das funcionalidades do **Hibernate** em um sistema simples de **biblioteca**.

---

## 🛠 Tecnologias utilizadas
- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate como implementação)
- Banco de dados PostgreSQL

---

## 🎯 Objetivo do sistema
O sistema permite gerenciar:
- **Alunos** que podem pegar livros emprestados.
- **Autores** que escrevem livros.
- **Livros** cadastrados na biblioteca.
- **Empréstimos** que registram quando um aluno pega um livro.

---

## 🗂 Estrutura básica das entidades

### 👨‍🎓 Aluno
- `id`
- `nome`
- `matricula`
- `emprestimos` (relacionamento `@OneToMany`)

### ✍️ Autor
- `id`
- `nome`
- `livros` (relacionamento `@OneToMany`)

### 📖 Livro
- `id`
- `titulo`
- `autor` (relacionamento `@ManyToOne`)
- `emprestimos` (relacionamento `@ManyToMany`)

### 🔄 Empréstimo
- `id`
- `aluno` (relacionamento `@ManyToOne`)
- `livro` (relacionamento `@ManyToMany`)
- `dataEmprestimo`
- `dataDevolucao`

---

## 🔗 Relacionamentos principais
- **Um Autor** pode ter **muitos Livros** (`@OneToMany`).
- **Um Livro** pertence a **um Autor** (`@ManyToOne`).
- **Um Aluno** pode ter **muitos Empréstimos**.
- **Um Empréstimo** sempre está ligado a **um Aluno** e **um** ou **vários Livros**.

---

## ⚡ Fluxo básico de uso
1. **Cadastrar autores** → exemplo: "Machado de Assis".
2. **Cadastrar livros** → exemplo: "Dom Casmurro", vinculado ao autor.
3. **Cadastrar alunos** → exemplo: "Maria Silva".
4. **Registrar empréstimo** → "Maria Silva" pegou ["Pequeno Príncipe", "Dom Casmurro"].

---

# URL da documentação (SWAGGER)
http://localhost:8080/api/swagger-ui/index.html

# JPA / Hibernate
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca_minicurso
spring.datasource.username=postgres
spring.datasource.password=fema
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
server.servlet.context-path=/api
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

