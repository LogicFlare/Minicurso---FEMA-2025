package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.model.Emprestimo;
import com.flare.minicurso_hibernate.repository.EmprestimoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

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

    public Emprestimo criar(Emprestimo data) {

        Emprestimo emprestimo = new Emprestimo().builder()
                .aluno(data.getAluno())
                .livros(data.getLivros())
                .dataEmprestimo(data.getDataEmprestimo())
                .dataDevolucao(data.getDataDevolucao())
                .build();
        return emprestimoRepository.save(emprestimo);
    }

    public void atualizar(UUID uuid, Emprestimo data) {
    }

    public void excluir(UUID id) {
        emprestimoRepository.deleteById(id);
    }

    public List<Emprestimo> emprestimosDoAluno(UUID alunoId) {
        return emprestimoRepository.findAllByAlunoId(alunoId);
    }

    public void marcarDevolucao(UUID id) {
        Emprestimo emprestimo = encontrar(id);
        emprestimo.setDataDevolucao(LocalDateTime.now());
        emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> encontrarPorMes(int mes, int ano) {
        return emprestimoRepository.findByMesEAno(mes, ano);
    }
}
