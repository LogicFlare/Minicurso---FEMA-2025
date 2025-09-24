package com.flare.minicurso_hibernate.controllers;


import com.flare.minicurso_hibernate.infra.dto.autor.AutorRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.autor.AutorResponseDTO;
import com.flare.minicurso_hibernate.infra.model.Autor;
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
    public ResponseEntity<Autor> encontrar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(autorService.encontrar(id));
    }

    @GetMapping("/nome/{id}")
    public ResponseEntity<Autor> encontrarPorNome(@PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(autorService.encontrarPorNome(nome));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(autorService.listarTodos());
    }

    @PostMapping("/criar")
    public ResponseEntity<Autor> criar(@RequestBody AutorRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autorService.criar(data));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity excluir(@PathVariable UUID id) {
        autorService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AutorResponseDTO> atualizar(@PathVariable("id") UUID id, @RequestBody AutorRequestDTO data){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(autorService.atualizar(id, data));
    }

}
