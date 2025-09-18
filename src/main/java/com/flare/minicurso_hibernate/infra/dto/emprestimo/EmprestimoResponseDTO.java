package com.flare.minicurso_hibernate.infra.dto.emprestimo;

import com.flare.minicurso_hibernate.infra.model.Aluno;
import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import com.flare.minicurso_hibernate.infra.model.Livro;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class EmprestimoResponseDTO {
    UUID id;
    Aluno aluno;
    List<Livro> livros;
    LocalDateTime dataEmprestimo;
    LocalDateTime dataDevolucao;

    EmprestimoResponseDTO(Emprestimo data){
        this.id = data.getId();
        this.aluno = data.getAluno();
        this.livros = data.getLivros();
        this.dataEmprestimo = data.getDataEmprestimo();
        this.dataDevolucao = data.getDataDevolucao();
    }

}
