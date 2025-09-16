package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.model.Livro;
import com.flare.minicurso_hibernate.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro encontrar(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado. ID:" + id));
    }

    public Livro encontrarPorTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado. Titulo:" + titulo));
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarTodosPorAutor(String nomeAutor) {
        return livroRepository.findAllByAutorNome(nomeAutor);
    }

    public Livro criar(Livro data) {
        Livro livro = new Livro().builder()
                .titulo(data.getTitulo())
                .autor(data.getAutor())
                .build();
        return livroRepository.save(livro);
    }

    public void atualizar(UUID uuid, Livro data) {
    }

    public void excluir(UUID id) {
        Livro livro = encontrar(id);
        if (!livro.getEmprestimos().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir: O livro está em empréstimos.");
        }
        livroRepository.delete(livro);
    }
}
