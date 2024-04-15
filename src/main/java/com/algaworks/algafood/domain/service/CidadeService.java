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

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Optional<Cidade> buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId);
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if (estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("O estado com o código %d näo pode ser encontrado", estadoId));
        }
        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade com o código %d näo encontrada", cidadeId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
        }
    }

    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estaudoAtual = estadoRepository.findById(estadoId);
        Optional<Cidade> cidadeAtual = buscar(cidadeId);

        if (estaudoAtual.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Estado com o código %d näo encontrado", estadoId));
        }

        if (cidadeAtual.isEmpty()) {
            throw new CidadeNaoEncontradaException();
        }
        cidade.setEstado(estaudoAtual.get());
        BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");

        return cidadeRepository.save(cidadeAtual.get());
    }
}
