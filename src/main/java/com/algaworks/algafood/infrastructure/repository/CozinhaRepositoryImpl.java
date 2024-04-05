package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @Override
    public List<Cozinha> todas() {
        return null;
    }

    @Override
    public Cozinha porId(Long id) {
        return null;
    }

    @Override
    public Cozinha adicionar(Cozinha cozinha) {
        return null;
    }

    @Override
    public void remover(Cozinha cozinha) {

    }
}
