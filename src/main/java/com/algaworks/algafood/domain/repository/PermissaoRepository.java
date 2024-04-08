package com.algaworks.algafood.domain.repository;


import com.algaworks.algafood.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> todos();
    Permissao porId(Long id);
    Permissao adicionar(Permissao formaPagamento);
    void remover(Permissao formaPagamento);
}
