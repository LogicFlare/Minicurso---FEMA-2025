package com.flare.minicurso_hibernate.controllers;

import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.aluno.AlunoResponseDTO;
import com.flare.minicurso_hibernate.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<AlunoResponseDTO> encontrar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(alunoService.encontrar(id));
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<AlunoResponseDTO> encontrarPorMatricula(@PathVariable("matricula") String matricula) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(alunoService.encontrarPorMatricula(matricula));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(alunoService.listarTodos());
    }

    @PostMapping("/criar")
    public ResponseEntity<AlunoResponseDTO> criar(@RequestBody AlunoRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alunoService.criar(data));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity excluir(@PathVariable UUID id) {
        alunoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable("id") UUID id, @RequestBody AlunoRequestDTO data){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alunoService.atualizar(id, data));
    }

    @GetMapping("/all/pageable")
    public ResponseEntity<Page<AlunoResponseDTO>> getAllPageable(@PageableDefault(size = 10, sort = "nome")Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(alunoService.listarTodosPaginado(pageable));
    }

}
