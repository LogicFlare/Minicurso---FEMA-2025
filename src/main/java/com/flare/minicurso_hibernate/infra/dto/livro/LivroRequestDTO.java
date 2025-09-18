package com.flare.minicurso_hibernate.infra.dto.livro;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LivroRequestDTO {
    String titulo;
    UUID autor;
}
