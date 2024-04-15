package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        Optional<Estado> estado = cadastroEstadoService.buscar(estadoId);
        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Estado estado) {
        estado = cadastroEstadoService.salvar(estado);
        if (estado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{idEstado}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long idEstado, @RequestBody Estado estado) {
        Optional<Estado> estadoAtual = estadoRepository.findById(idEstado);
        if (estadoAtual.isPresent()) {
            BeanUtils.copyProperties(estado, estadoAtual.get(), "id");

            Estado estadoOptional = estadoRepository.save(estadoAtual.get());
            return ResponseEntity.ok(estadoOptional);
        }
        return ResponseEntity.notFound().build();
    }

}
