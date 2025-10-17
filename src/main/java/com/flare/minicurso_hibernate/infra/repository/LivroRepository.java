package com.flare.minicurso_hibernate.infra.repository;

import com.flare.minicurso_hibernate.infra.dto.livro.LivroEmprestadoRecordDTO;
import com.flare.minicurso_hibernate.infra.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    @Query("""
            SELECT l FROM Livro l
            WHERE l.autor.nome = :nomeAutor
            """)
    List<Livro> findAllByAutorNome(String nomeAutor);

    Livro findByTitulo(String titulo);
    // precisa do caminho para referenciar
    @Query(
            "SELECT new com.flare.minicurso_hibernate.infra.dto.livro.LivroEmprestadoRecordDTO(" +
                    "l.titulo, a.nome, aut.nome) " +
                    "FROM Livro l " +
                    "JOIN l.emprestimos e " +
                    "JOIN e.aluno a " +
                    "JOIN l.autor aut"
    )
    List<LivroEmprestadoRecordDTO> findLivrosEmprestados();

    // Diferença entre Query e NativeQuery:
    // Query: Usa JPQL (Java Persistence Query Language), que é uma linguagem orientada a objetos.
    // Ela trabalha com entidades e suas propriedades, permitindo consultas mais abstratas e independentes do banco de dados.

    // NativeQuery: Usa SQL nativo do banco de dados.
    // Ela permite consultas específicas para o banco de dados em uso, oferecendo mais controle e potencialmente melhor desempenho, mas é menos portátil.

}
