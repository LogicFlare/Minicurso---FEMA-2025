package com.flare.minicurso_hibernate.infra.dto.livro;

import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import com.flare.minicurso_hibernate.infra.model.Livro;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LivroResponseDTO {
    UUID id;
    String titulo;
    Autor autor;
    List<Emprestimo> emprestimos;

    LivroResponseDTO(Livro data){
        this.id = data.getId();
        this.titulo = data.getTitulo();
        this.autor = data.getAutor();
        this.emprestimos = data.getEmprestimos();
    }

}
