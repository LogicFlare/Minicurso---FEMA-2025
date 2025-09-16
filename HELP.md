# 📚 Sistema de Biblioteca (Exemplo com Spring + Hibernate)

Este projeto é um **exemplo introdutório** de aplicação Java usando **Spring Boot**, **Spring Data JPA** e **Hibernate**.  
O objetivo é demonstrar como funciona o mapeamento de entidades, a persistência de dados e algumas das funcionalidades do **Hibernate** em um sistema simples de **biblioteca**.

---

## 🛠 Tecnologias utilizadas
- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate como implementação)
- Banco de dados H2

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
- **Um Empréstimo** sempre está ligado a **um Livro** e **um Aluno**.

---

## ⚡ Fluxo básico de uso
1. **Cadastrar autores** → exemplo: "Machado de Assis".
2. **Cadastrar livros** → exemplo: "Dom Casmurro", vinculado ao autor.
3. **Cadastrar alunos** → exemplo: "Maria Silva".
4. **Registrar empréstimo** → "Maria Silva" pegou "Dom Casmurro".

---

# URL do banco em memória
spring.datasource.url=jdbc:h2:mem:biblioteca
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Console web do H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

