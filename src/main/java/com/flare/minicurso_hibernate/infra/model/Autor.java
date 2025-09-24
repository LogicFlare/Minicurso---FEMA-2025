package com.flare.minicurso_hibernate.infra.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Autor")
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    // Tanto essa anotação quanto a do livro sao do jackson para resolver referências circulares de forma controlada
    // Lado "pai" usa essa anotação  permitindo a serialização normal da propriedade
    private List<Livro> livros = new ArrayList<>();
}

