package com.flare.minicurso_hibernate.controllers;


import com.flare.minicurso_hibernate.model.Aluno;
import com.flare.minicurso_hibernate.model.Autor;
import com.flare.minicurso_hibernate.service.AlunoService;
import com.flare.minicurso_hibernate.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/{id}")
    public ResponseEntity<Autor> encontrar(UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(autorService.encontrar(id));
    }

    @GetMapping("/nome/{id}")
    public ResponseEntity<Autor> encontrarPorNome(String nome) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(autorService.encontrarPorNome(nome));
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(autorService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Autor> criar(Autor data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autorService.criar(data));
    }

    @DeleteMapping
    public ResponseEntity excluir(UUID id) {
        autorService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
