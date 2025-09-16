package com.flare.minicurso_hibernate.controllers;

import com.flare.minicurso_hibernate.model.Emprestimo;
import com.flare.minicurso_hibernate.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> encontrar(UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.encontrar(id));
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<Emprestimo>> listaPaginadaTodos(
            @PageableDefault(size = 10, sort = "dataEmprestimo", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.listarTodos(pageable));
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Emprestimo>> listarEmprestimosAluno(@PathVariable("alunoId") UUID alunoId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.emprestimosDoAluno(alunoId));
    }

    @GetMapping("/{mes}/{ano}")
    public ResponseEntity<List<Emprestimo>> listarMes(@PathVariable("mes") int mes, @PathVariable("ano") int ano) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.encontrarPorMes(mes, ano));
    }

    @PostMapping
    public ResponseEntity<Emprestimo> criar(Emprestimo data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emprestimoService.criar(data));
    }

    @PostMapping
    public ResponseEntity devolucao(UUID id) {
        emprestimoService.marcarDevolucao(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }


    @DeleteMapping
    public ResponseEntity excluir(UUID id) {
        emprestimoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
