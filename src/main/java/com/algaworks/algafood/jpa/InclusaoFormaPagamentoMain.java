package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoFormaPagamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
        FormaPagamento formaPagamento1 = new FormaPagamento();
        formaPagamento1.setDescricao("Boleto");

        FormaPagamento formaPagamento2 = new FormaPagamento();
        formaPagamento2.setDescricao("Pix");

        formaPagamento1 = formaPagamentoRepository.adicionar(formaPagamento1);
        formaPagamento2 = formaPagamentoRepository.adicionar(formaPagamento2);

        System.out.printf("%d - %s\n", formaPagamento1.getId(), formaPagamento1.getDescricao());
        System.out.printf("%d - %s\n", formaPagamento2.getId(), formaPagamento2.getDescricao());

    }
}
