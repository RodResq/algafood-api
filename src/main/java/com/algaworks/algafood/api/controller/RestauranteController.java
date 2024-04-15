package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ResutaranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        try {
            Restaurante restarante = cadastroRestauranteService.buscar(restauranteId);
            return ResponseEntity.ok(restarante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.atualizar(restauranteId, restaurante);
            return ResponseEntity.ok(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }  catch (ResutaranteNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarRestaurante(@PathVariable Long restauranteId,
                                                  @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        merge(campos, restauranteAtual.get());

        return atualizar(restauranteId, restauranteAtual.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante retauranteNovo = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((propriedadeNome, propriedadeValor) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, propriedadeNome);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, retauranteNovo);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
            System.out.println(restauranteDestino);

        });
    }
}
