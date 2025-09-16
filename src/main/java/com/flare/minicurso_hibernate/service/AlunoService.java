package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.model.Aluno;
import com.flare.minicurso_hibernate.model.Autor;
import com.flare.minicurso_hibernate.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno encontrar(UUID id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. ID:" + id));
    }

    public Aluno encontrarPorMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. Matricula:" + matricula));
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno criar(Aluno data) {
        if (alunoRepository.existsByMatricula(data.getMatricula())) {
            throw new IllegalArgumentException("Matrícula já existe!");
        }

        Aluno aluno = Aluno.builder()
                .nome(data.getNome())
                .matricula(data.getMatricula())
                .build();
        return alunoRepository.save(aluno);
    }

    public void atualizar(UUID uuid, Aluno data) {
    }

    public void excluir(UUID id) {
        alunoRepository.deleteById(id);
    }
}
