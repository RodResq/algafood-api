package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante buscar(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.porId(restauranteId);
        if (restaurante == null) {
            throw new EntidadeNaoEncontradaException(String.format("Restaurante %d näo encontrado", restauranteId));
        }
        return restaurante;
    }

}
