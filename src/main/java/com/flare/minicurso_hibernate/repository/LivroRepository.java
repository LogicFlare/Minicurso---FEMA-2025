package com.flare.minicurso_hibernate.repository;

import com.flare.minicurso_hibernate.infra.dto.livro.LivroEmprestadoRecordDTO;
import com.flare.minicurso_hibernate.infra.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    @Query("""
            SELECT l FROM Livro l
            WHERE l.autor.nome = :nomeAutor
            """)
    List<Livro> findAllByAutorNome(String nomeAutor);

    Livro findByTitulo(String titulo);

    @Query(
            "SELECT new com.flare.minicurso_hibernate.infra.dto.livro.LivroEmprestadoRecordDTO(" +
                    "l.titulo, a.nome, aut.nome) " +
                    "FROM Livro l " +
                    "JOIN l.emprestimos e " +
                    "JOIN e.aluno a " +
                    "JOIN l.autor aut"
    )
    List<LivroEmprestadoRecordDTO> findLivrosEmprestados();
}
