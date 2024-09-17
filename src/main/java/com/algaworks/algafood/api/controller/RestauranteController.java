package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.Groups;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.algaworks.algafood.domain.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.domain.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId, HttpServletRequest request) {
        if (true) {
            throw new IllegalArgumentException("Teste");
        }
        return cadastroRestauranteService.buscarOuFalhar(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return cadastroRestauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Long cozinhaId = restaurante.getCozinha().getId();

        try {
            Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
            restaurante.setCozinha(cozinha);
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
                    "dataCadastro", "produtos");
            return restauranteRepository.save(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarRestaurante(@PathVariable Long restauranteId,
                                            @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        merge(campos, restauranteAtual, request);

        return atualizar(restauranteId, restauranteAtual);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante retauranteNovo = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((propriedadeNome, propriedadeValor) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, propriedadeNome);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, retauranteNovo);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
                System.out.println(restauranteDestino);

            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @GetMapping("/por-taxa-frete")
    public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/por-nome-cozinha")
    public List<Restaurante> restaurantePorNomeCozinha(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/por-nome")
    public Optional<Restaurante> restaurantePorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/top2-nome")
    public List<Restaurante> restauranteTop2Nome(String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/count-por-cozinha")
    public int restauranteCountCozinha(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/por-nome-taxa-frete")
    public List<Restaurante> restaurantePorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/por-frete-gratis")
    public List<Restaurante> restaurantePorNomeTaxaFrete(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

    @GetMapping("/primeiro")
    public Optional<Restaurante> buscarPrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }
}
