package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurante = applicationContext.getBean(RestauranteRepository.class);
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Restaurante 1");
        restaurante1.setTaxaFrete(BigDecimal.valueOf(55.66));

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Restaurante 2");
        restaurante2.setTaxaFrete(BigDecimal.valueOf(456.54));

        restaurante1 = restaurante.adicionar(restaurante1);
        restaurante2 = restaurante.adicionar(restaurante2);

        System.out.printf("%d - %s - %s\n", restaurante1.getId(), restaurante1.getNome(), restaurante1.getTaxaFrete());
        System.out.printf("%d - %s - %s\n", restaurante2.getId(), restaurante2.getNome(), restaurante2.getTaxaFrete());

    }
}
