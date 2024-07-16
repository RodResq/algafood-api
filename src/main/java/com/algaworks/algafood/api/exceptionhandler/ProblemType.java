package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade näo encontrada"),
    ERRO_NEGOCIO("/erro-negocio", "Violacao da regra de negocio"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    MENSAGEM_IMCOMPREENSIVEL("/mensagem-incompreensivel", "Mesagem Incompreensivel");

    private String title;
    private String path;

    ProblemType(String path, String title) {
        this.path = path;
        this.title = title;
    }
}
