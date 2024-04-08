package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> todas();
    FormaPagamento buscar(Long id);
    FormaPagamento adicionar(FormaPagamento formaPagamento);
    void remover(FormaPagamento formaPagamento);
}
