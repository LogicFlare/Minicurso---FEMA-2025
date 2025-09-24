package com.flare.minicurso_hibernate.infra.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flare.minicurso_hibernate.infra.enumerated.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Emprestimo")
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "aluno_id", nullable = false)
    @JsonManagedReference
    private Aluno aluno;

    @ManyToMany(fetch = FetchType.LAZY) // → carrega os Livros somente quando acessar o atributo.
    @JoinTable(
            name = "emprestimo_livros",
            joinColumns = @JoinColumn(name = "emprestimo_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    @JsonManagedReference
    private List<Livro> livros = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime dataEmprestimo;

    private LocalDateTime dataDevolucao;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status = StatusEmprestimo.PENDENTE;

}
