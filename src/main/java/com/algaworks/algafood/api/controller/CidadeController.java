package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
        Optional<Cidade> cidade = cidadeService.buscar(cidadeId);
        if (cidade.isPresent()) {
            return ResponseEntity.ok(cidade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            cidade = cidadeService.salvar(cidade);
            return ResponseEntity.ok(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeId) {
        try {
            cidadeService.excluir(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        try {
            cidade = cidadeService.atualizar(cidadeId, cidade);
            return ResponseEntity.ok(cidade);
        } catch (CidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }


    }
}
