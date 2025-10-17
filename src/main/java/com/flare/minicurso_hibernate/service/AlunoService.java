package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Aluno;
import com.flare.minicurso_hibernate.infra.repository.AlunoRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    private final Counter alunosCriadosCounter;
    private final Counter alunosAtualizadosCounter;
    private final Counter alunosExcluidosCounter;

    public AlunoService(AlunoRepository alunoRepository, MeterRegistry meterRegistry) {
        this.alunoRepository = alunoRepository;

        this.alunosCriadosCounter = Counter.builder("alunos.criados")
                .description("Numero de alunos criados")
                .register(meterRegistry);

        this.alunosAtualizadosCounter = Counter.builder("alunos.atualizados")
                .description("Numero de alunos atualizados")
                .register(meterRegistry);

        this.alunosExcluidosCounter = Counter.builder("alunos.excluidos")
                .description("Numero de alunos excluidos")
                .register(meterRegistry);
    }

    public AlunoResponseDTO encontrar(UUID id) {
        return AlunoResponseDTO.fromEntity(alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. ID:" + id)));
    }

    public Aluno encontrarEntidade(UUID id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. ID:" + id));
    }

    public AlunoResponseDTO encontrarPorMatricula(String matricula) {
        return AlunoResponseDTO.fromEntity(alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado. Matricula:" + matricula)));
    }

    @Cacheable(value = "alunos-all-search",
            key = "'all'",
            condition = "#result.size() < 5")
    public List<AlunoResponseDTO> listarTodos() {
        return AlunoResponseDTO.fromEntities(alunoRepository.findAll());
    }

    @Cacheable(value = "alunos-all-search-pageable",
            key = "#pageable.pageNumber + '_' + #pageable.pageSize", unless = "#result.size() == 0")
    public Page<AlunoResponseDTO> listarTodosPaginado(Pageable pageable) {
        return alunoRepository.findAll(pageable).map(AlunoResponseDTO::fromEntity);
    }

    public AlunoResponseDTO criar(AlunoRequestDTO data) {
        Aluno alunoSalvo = alunoRepository.save(data.toEntity());
        alunosCriadosCounter.increment();

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

        alunosAtualizadosCounter.increment();

        return AlunoResponseDTO.fromEntity(aluno);
    }

    public void excluir(UUID id) {
        alunoRepository.deleteById(id);
        alunosExcluidosCounter.increment();
    }
}
