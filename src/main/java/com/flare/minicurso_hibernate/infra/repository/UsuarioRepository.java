package com.flare.minicurso_hibernate.infra.repository;

import com.flare.minicurso_hibernate.infra.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
}

