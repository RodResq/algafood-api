package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permissao> todos() {
        return entityManager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Override
    public Permissao buscar(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Transactional
    @Override
    public Permissao adicionar(Permissao permissao) {
        return entityManager.merge(permissao);
    }

    @Transactional
    @Override
    public void remover(Permissao permissao) {
        permissao = buscar(permissao.getId());
        entityManager.remove(permissao);
    }
}
