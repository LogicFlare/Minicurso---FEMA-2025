package com.flare.minicurso_hibernate.infra.dto.emprestimo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class EmprestimoRequestDTO {
    UUID alunoId;
    List<UUID> livroIds;
}
