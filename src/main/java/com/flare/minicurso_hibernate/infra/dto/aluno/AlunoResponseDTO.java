package com.flare.minicurso_hibernate.infra.dto.aluno;

import com.flare.minicurso_hibernate.infra.model.Aluno;
import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoResponseDTO {
    private UUID id;
    private String nome;
    private String matricula;
    private List<Emprestimo> emprestimos;
    private LocalDateTime dataUltimoLivroEmprestado;

    public static AlunoResponseDTO fromEntity(Aluno aluno) {
        return AlunoResponseDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .matricula(aluno.getMatricula())
                .emprestimos(aluno.getEmprestimos())
                .dataUltimoLivroEmprestado(aluno.getDataUltimoLivroEmprestado())
                .build();
    }

    public static List<AlunoResponseDTO> fromEntities(List<Aluno> alunos) {
        return alunos.stream()
                .map(AlunoResponseDTO::fromEntity)
                .toList();
    }

}
