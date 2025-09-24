package com.flare.minicurso_hibernate.infra.dto.autor;

import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutorResponseDTO {
    UUID id;
    String nome;
    List<Livro> livros;

    AutorResponseDTO(Autor data){
        this.id = data.getId();
        this.nome = data.getNome();
        this.livros = data.getLivros();
    }

    public static AutorResponseDTO fromEntity(Autor autor) {
        return AutorResponseDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .livros(autor.getLivros())
                .build();
    }

}
