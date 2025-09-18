package com.flare.minicurso_hibernate.repository;

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

    Optional<Livro> findByTitulo(String titulo);
}
