package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Aluno;
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

    public AlunoResponseDTO encontrar(UUID id) {
        return AlunoResponseDTO.fromEntity(alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. ID:" + id)));
    }

    public AlunoResponseDTO encontrarPorMatricula(String matricula) {
        return AlunoResponseDTO.fromEntity(alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. Matricula:" + matricula)));
    }
    public List<AlunoResponseDTO> listarTodos() {
        return AlunoResponseDTO.fromEntities(alunoRepository.findAll());
    }

    public AlunoResponseDTO criar(AlunoRequestDTO data) {
        Aluno alunoSalvo = alunoRepository.save(data.toEntity());

        return AlunoResponseDTO.builder()
                .id(alunoSalvo.getId())
                .nome(alunoSalvo.getNome())
                .matricula(alunoSalvo.getMatricula())
                .emprestimos(alunoSalvo.getEmprestimos())
                .build();
    }

    public void atualizar(UUID uuid, Aluno data) {
    }

    public void excluir(UUID id) {
        alunoRepository.deleteById(id);
    }
}
