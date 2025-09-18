package com.flare.minicurso_hibernate.infra.dto.autor;

import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.model.Livro;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AutorResponseDTO {
    UUID id;
    String nome;
    List<Livro> livros;

    AutorResponseDTO(Autor data){
        this.id = data.getId();
        this.nome = data.getNome();
        this.livros = data.getLivros();
    }

}
