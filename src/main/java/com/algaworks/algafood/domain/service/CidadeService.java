package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.buscar(cidadeId);
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);

        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("O estado com o código %d näo pode ser encontrado", estadoId));
        }
        cidade.setEstado(estado);
        return cidadeRepository.adicionar(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.remover(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade com o código %d näo encontrada", cidadeId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
        }
    }

    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estaudoAtual = estadoRepository.buscar(estadoId);
        Cidade cidadeAtual = buscar(cidadeId);

        if (estaudoAtual == null) {
            throw new EntidadeNaoEncontradaException(String.format("Estado com o código %d näo encontrado", estadoId));
        }

        if (cidadeAtual == null) {
            throw new CidadeNaoEncontradaException();
        }
        cidade.setEstado(estaudoAtual);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        return cidadeRepository.adicionar(cidadeAtual);
    }
}
