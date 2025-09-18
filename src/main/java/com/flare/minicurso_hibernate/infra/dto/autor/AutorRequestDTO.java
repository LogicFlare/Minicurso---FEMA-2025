package com.flare.minicurso_hibernate.infra.dto.autor;

import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.model.Livro;
import lombok.Data;

import java.util.List;

@Data
public class AutorRequestDTO {
    String nome;
    List<Livro> livros;

    public Autor toEntity() {
        return Autor.builder()
                .nome(this.nome)
                .livros(this.livros)
                .build();
    }

}
