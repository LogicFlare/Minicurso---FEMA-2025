package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.dto.livro.LivroEmprestadoRecordDTO;
import com.flare.minicurso_hibernate.infra.dto.livro.LivroRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.livro.LivroResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.infra.model.Livro;
import com.flare.minicurso_hibernate.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorService autorService;

    public Livro encontrar(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado. ID:" + id));
    }

    public LivroResponseDTO encontrarPorTitulo(String titulo) {
        return LivroResponseDTO.fromEntity(livroRepository.findByTitulo(titulo));
    }

    // precisa para o Lazy Loading funcionar, para que as propriedades de aluno.nome e
    // autor.nome sejam carregadas sob demanda e otimizar a query
    @Transactional(readOnly = true)
    public List<LivroEmprestadoRecordDTO> listarLivrosEmprestados(){
        return livroRepository.findLivrosEmprestados();
    }

    public List<LivroResponseDTO> listarTodos() {
        return LivroResponseDTO.fromEntities(livroRepository.findAll());
    }

    public List<Livro> listarTodosPorAutor(String nomeAutor) {
        return livroRepository.findAllByAutorNome(nomeAutor);
    }

    public Livro criar(LivroRequestDTO data) {

        Autor autor = autorService.encontrar(data.getAutor());

        Livro livro = new Livro().builder()
                .titulo(data.getTitulo())
                .autor(autor)
                .build();
        return livroRepository.save(livro);
    }

    @Transactional
    public LivroResponseDTO atualizar(UUID id, LivroRequestDTO data) {

        Livro livro = encontrar(id);

        if (data.getTitulo() != null) livro.setTitulo(data.getTitulo());
        if (data.getAutor() != null) {
            Autor autor = autorService.encontrar(data.getAutor());
            livro.setAutor(autor);
        }
        return LivroResponseDTO.fromEntity(livro);
    }

    public void excluir(UUID id) {
        Livro livro = encontrar(id);
        if (!livro.getEmprestimos().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir: O livro está em empréstimos.");
        }
        livroRepository.delete(livro);
    }
}
