package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public Estado buscar(@PathVariable Long estadoId) {
        return cadastroEstadoService.buscarOuFalhar(estadoId);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid Estado estado) {
        estado = cadastroEstadoService.salvar(estado);
        if (estado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstadoService.excluir(estadoId);
    }

    @PutMapping("/{idEstado}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long idEstado, @RequestBody @Valid Estado estado) {
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(idEstado);

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return ResponseEntity.ok(estadoAtual);
    }

}
