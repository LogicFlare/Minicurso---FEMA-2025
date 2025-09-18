package com.flare.minicurso_hibernate.infra.dto.aluno;

import com.flare.minicurso_hibernate.infra.model.Aluno;
import lombok.*;

@Data
public class AlunoRequestDTO {
    String nome;
    String matricula;

    public Aluno toEntity() {
        return Aluno.builder()
                .nome(this.nome)
                .matricula(this.matricula)
                .build();
    }

}
