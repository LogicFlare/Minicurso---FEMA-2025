package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.dto.emprestimo.EmprestimoRequestDTO;
import com.flare.minicurso_hibernate.infra.enumerated.StatusEmprestimo;
import com.flare.minicurso_hibernate.infra.model.Aluno;
import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import com.flare.minicurso_hibernate.infra.model.Livro;
import com.flare.minicurso_hibernate.infra.repository.EmprestimoRepository;
import com.flare.minicurso_hibernate.service.metrics.MetricsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    @Lazy
    private MetricsService metricsService;

    @Autowired
    @Lazy
    private AlunoService alunoService;

    @Autowired
    @Lazy
    private LivroService livroService;

    public Emprestimo encontrar(UUID id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado. ID:" + id));
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Page<Emprestimo> listarTodos(Pageable pageable) {
        return emprestimoRepository.findAll(pageable);
    }

    public Emprestimo criar(EmprestimoRequestDTO data) {
        Long inicio = System.currentTimeMillis();
       try {
           Aluno aluno = alunoService.encontrarEntidade(data.getAlunoId());

           aluno.setDataUltimoLivroEmprestado(LocalDateTime.now());

           List<Livro> livros = data.getLivroIds().stream()
                   .map(livroService::encontrar)
                   .toList();

           Emprestimo emprestimo = new Emprestimo().builder()
                   .aluno(aluno)
                   .livros(livros)
                   .dataEmprestimo(null)
                   .dataDevolucao(null)
                   .status(StatusEmprestimo.PENDENTE)
                   .build();

           metricsService.registrarOperacao("criar_emprestimo");

           return emprestimoRepository.save(emprestimo);
       } catch (Exception e) {
           metricsService.registrarErro("erro_criar_emprestimo");
           throw e;
       } finally {
           metricsService.registrarTempoOperacao("tempo_criar_emprestimo", System.currentTimeMillis() - inicio);
       }
    }

    public void excluir(UUID id) {
        emprestimoRepository.deleteById(id);
    }

    public List<Emprestimo> emprestimosDoAluno(UUID alunoId) {
        return emprestimoRepository.findAllByAlunoId(alunoId);
    }

    @Transactional
    public void marcarDevolucao(UUID id) {
        Emprestimo emprestimo = encontrar(id);
        emprestimo.setDataDevolucao(LocalDateTime.now());
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
    }

    public List<Emprestimo> encontrarPorMes(int mes, int ano) {
        return emprestimoRepository.findByMesEAno(mes, ano);
    }
}
