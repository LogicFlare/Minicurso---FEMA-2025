package com.flare.minicurso_hibernate.controllers;

import com.flare.minicurso_hibernate.infra.dto.livro.LivroEmprestadoRecordDTO;
import com.flare.minicurso_hibernate.infra.dto.livro.LivroRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.livro.LivroResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Livro;
import com.flare.minicurso_hibernate.service.LivroService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Livro> encontrar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.encontrar(id));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<LivroResponseDTO> encontrarPorTitulo(@PathVariable("titulo") String titulo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.encontrarPorTitulo(titulo));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<LivroResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.listarTodos());
    }

    @GetMapping("/autor/{nomeAutor}")
    public ResponseEntity<List<Livro>> listarTodosPorAutor(@PathVariable("nomeAutor") String nomeAutor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(livroService.listarTodosPorAutor(nomeAutor));
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody LivroRequestDTO data, HttpServletRequest request) {
        Livro livro = livroService.criar(data, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable UUID id) {
        livroService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(@PathVariable("id") UUID id, @RequestBody LivroRequestDTO data){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(livroService.atualizar(id, data));
    }

    @GetMapping("/emprestados")
    public ResponseEntity<List<LivroEmprestadoRecordDTO>> listarLivrosEmprestados(){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.listarLivrosEmprestados());
    }

}
