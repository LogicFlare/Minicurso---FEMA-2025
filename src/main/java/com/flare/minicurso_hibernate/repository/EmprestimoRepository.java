package com.flare.minicurso_hibernate.repository;

import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

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

    @Query(value = """
            SELECT DISTINCT e.* FROM emprestimo e
            JOIN emprestimo_livros el 
                ON e.id = el.emprestimo_id
            JOIN livro l 
                ON el.livros_id = l.id
            WHERE e.data_devolucao  < CURRENT_DATE 
                AND e.status <> 'DEVOLVIDO'
            """, nativeQuery = true)
    List<Emprestimo> findAtrasados();
}
