package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Aluno;
import com.flare.minicurso_hibernate.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    public Aluno encontrarEntidade(UUID id){
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. ID:" + id));
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

    // mesmo se o método principal falhar, esse é executado.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logarOperacao(String aluno, String operacao) {
        System.out.println("Aluno: " + aluno + " - Operação: " + operacao + ". Log salvo mesmo com falha no método principal.");

    }

    @Transactional(
            timeout = 10,
            rollbackFor = Exception.class
    )
    public AlunoResponseDTO atualizar(UUID id, AlunoRequestDTO data) {

        System.out.println("Executado antes de encontrar a entidade");

        Aluno aluno = encontrarEntidade(id);

        System.out.println("Se encontrou, vai executar! ");

        logarOperacao(data.getNome(), "Atualização de dados");

        if (data.getMatricula() != null) aluno.setMatricula(data.getMatricula());

        if (data.getNome() != null) aluno.setNome(data.getNome());

        return AlunoResponseDTO.fromEntity(aluno);
    }

    public void excluir(UUID id) {
        alunoRepository.deleteById(id);
    }
}
