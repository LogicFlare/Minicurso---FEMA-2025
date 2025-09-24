package com.flare.minicurso_hibernate.infra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    // Falar sobre o cascade = CascadeType.PERSIST
    // Se fosse anotado e no DTO do request tivesse um tipo Autor, ao salvar o livro poderiamos escrever os dados do autor ali e ele salvaria o autor caso n existisse.
    // testar isso
    @JsonBackReference
    // Lado filho da relação, ignorando a serialização.
    // Caso precisar que apareça esse lado, deve usar um DTO para mostrar os dados.
    // Em relacionamento Unidirecional esse problema nao acontece. Só em Bidirecional.
    // O cascade não resolve o problema pois ele atua na persistência dos dados, e não na serialização.
    // O fetchtype EAGER ou LAZY também não resolve, pois eles atuam na forma de carregar os dados, e não na serialização.
    private Autor autor;

    @ManyToMany(mappedBy = "livros", fetch = FetchType.LAZY) // Para saber todos os empréstimos de um livro.
    @JsonBackReference
    private List<Emprestimo> emprestimos = new ArrayList<>();
}
