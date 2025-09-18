package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.infra.model.Autor;
import com.flare.minicurso_hibernate.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

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

    public Autor criar(Autor data) {
        Autor autor = new Autor().builder()
                .nome(data.getNome())
                .build();
        return autorRepository.save(autor);
    }

    public void atualizar(UUID uuid, Autor data) {
    }

    public void excluir(UUID id) {
        autorRepository.deleteById(id);
    }
}
