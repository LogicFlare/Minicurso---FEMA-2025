package com.flare.minicurso_hibernate.controllers;

import com.flare.minicurso_hibernate.model.Aluno;
import com.flare.minicurso_hibernate.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> encontrar(UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(alunoService.encontrar(id));
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> encontrarPorMatricula(@PathVariable("matricula") String matricula) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(alunoService.encontrarPorMatricula(matricula));
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(alunoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(Aluno data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alunoService.criar(data));
    }

    @DeleteMapping
    public ResponseEntity excluir(UUID id) {
        alunoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
