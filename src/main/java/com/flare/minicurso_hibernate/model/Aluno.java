package com.flare.minicurso_hibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "alunos")
@Entity(name = "Aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 20)
    private String matricula;

    @OneToMany(
            mappedBy = "aluno", // → indica que o lado proprietário do relacionamento é o campo aluno da classe Emprestimo.
            cascade = CascadeType.ALL // → ajudam a gerenciar persistência e remoção automática.
    )
    private List<Emprestimo> emprestimos = new ArrayList<>();
}
