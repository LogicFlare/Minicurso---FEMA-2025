package com.flare.minicurso_hibernate.repository;

import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    // DISTINCT → Evita duplicatas de empréstimos no resultado, já que um empréstimo com vários livros geraria múltiplas linhas.
    // JOIN FETCH → Garante que os dados dos livros venham junto com os empréstimos;
    @Query("""
            SELECT DISTINCT e FROM Emprestimo e
            JOIN FETCH e.livros
            WHERE MONTH(e.dataEmprestimo) =: mes AND YEAR(e.dataEmprestimo) = :ano
            """)
    List<Emprestimo> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

    @Query("""
            SELECT DISTINCT e FROM Emprestimo e
            JOIN FETCH e.livros
            WHERE e.aluno.id = :alunoId
            """)
    List<Emprestimo> findAllByAlunoId(@Param("alunoId") UUID alunoId);
}
