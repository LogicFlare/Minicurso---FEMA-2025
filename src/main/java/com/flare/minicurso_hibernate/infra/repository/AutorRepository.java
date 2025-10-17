package com.flare.minicurso_hibernate.infra.repository;

import com.flare.minicurso_hibernate.infra.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
    Optional<Autor> findByNome(String nome);
}
