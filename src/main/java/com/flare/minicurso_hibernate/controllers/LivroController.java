package com.flare.minicurso_hibernate.controllers;

import com.flare.minicurso_hibernate.model.Livro;
import com.flare.minicurso_hibernate.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/{id}")
    public ResponseEntity<Livro> encontrar(UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.encontrar(id));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Livro> encontrarPorTitulo(@PathVariable("titulo") String titulo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.encontrarPorTitulo(titulo));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.listarTodos());
    }

    @GetMapping("/autor/{nomeAutor}")
    public ResponseEntity<List<Livro>> listarTodosPorAutor(@PathVariable("nomeAutor") String nomeAutor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.listarTodosPorAutor(nomeAutor));
    }

    @PostMapping
    public ResponseEntity<Livro> criar(Livro data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livroService.criar(data));
    }

    @DeleteMapping
    public ResponseEntity excluir(UUID id) {
        livroService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
