package com.flare.minicurso_hibernate.infra.repository;

import com.flare.minicurso_hibernate.infra.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    Optional<Aluno> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}
