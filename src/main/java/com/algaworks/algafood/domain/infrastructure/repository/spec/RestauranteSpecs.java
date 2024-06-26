package com.algaworks.algafood.domain.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class RestauranteSpecs {

    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) ->
                builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) -> {
            if (StringUtils.hasText(nome)) {
                return builder.like(root.get("nome"), "%" + nome + "%");
            }
            return builder.isTrue(root.isNotNull());
        };
    }
}
