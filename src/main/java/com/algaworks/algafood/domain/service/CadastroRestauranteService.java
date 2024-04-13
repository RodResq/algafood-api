package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ResutaranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante buscar(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.porId(restauranteId);
        if (restaurante == null) {
            throw new EntidadeNaoEncontradaException(String.format("Restaurante %d näo encontrado", restauranteId));
        }
        return restaurante;
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d näo existe", cozinhaId)));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.adicionar(restaurante);
    }

    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteRepository.porId(restauranteId);
        Long cozinhaId = restaurante.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

        if (cozinha.isEmpty()) {
            throw new ResutaranteNaoEncontradoException(String.format("Restaurante com o id %d näo encontrado", restauranteId));
        }

        if (cozinha.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d näo existe", cozinhaId));
        }
        restaurante.setCozinha(cozinha.get());
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

        return restauranteRepository.adicionar(restauranteAtual);
    }
}
