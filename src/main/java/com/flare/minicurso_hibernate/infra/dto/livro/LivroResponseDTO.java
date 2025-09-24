package com.flare.minicurso_hibernate.infra.dto.livro;

import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.model.Emprestimo;
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
public class LivroResponseDTO {
    UUID id;
    String titulo;
    Autor autor;
    List<UUID> emprestimosId;

    public static LivroResponseDTO fromEntity(Livro livro) {
        return LivroResponseDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .autor(livro.getAutor())
                .emprestimosId(livro.getEmprestimos().stream().map(Emprestimo::getId).toList())
                .build();
    }

    public static List<LivroResponseDTO> fromEntities(List<Livro> livros) {
        return livros.stream()
                .map(LivroResponseDTO::fromEntity)
                .toList();
    }

}
