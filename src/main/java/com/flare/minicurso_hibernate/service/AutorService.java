package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.dto.autor.AutorRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.autor.AutorResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.repository.AutorRepository;
import com.flare.minicurso_hibernate.infra.repository.EmprestimoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Autor encontrar(UUID id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado. ID:" + id));
    }

    public Autor encontrarPorNome(String nome) {
        return autorRepository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado. Nome:" + nome));
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor criar(AutorRequestDTO data) {
        Autor autor = new Autor().builder()
                .nome(data.getNome())
                .build();
        return autorRepository.save(autor);
    }

    @Transactional
    public AutorResponseDTO atualizar(UUID id, AutorRequestDTO data) {

        Autor autor = encontrar(id);

        if (data.getNome() != null) autor.setNome(data.getNome());

        return AutorResponseDTO.fromEntity(autorRepository.save(autor));
    }

    public void excluir(UUID id) {

        Autor autor = encontrar(id);

        autor.getLivros().forEach(livro -> {
            livro.getEmprestimos().forEach(emprestimo -> {
                emprestimo.getLivros().remove(livro);
                emprestimoRepository.save(emprestimo);
            });
            livro.getEmprestimos().clear();
        });

        autorRepository.deleteById(id);
    }
}
