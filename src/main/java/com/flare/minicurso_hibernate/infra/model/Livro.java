package com.flare.minicurso_hibernate.infra.model;

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
@Entity(name = "Livro")
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToMany // Para saber todos os empréstimos de um livro.
    private List<Emprestimo> emprestimos = new ArrayList<>();
}
