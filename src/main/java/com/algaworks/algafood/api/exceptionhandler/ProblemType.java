package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRAD0("/recurso-nao-encontrado", "Recurso näo encontrado"),
    ERRO_NEGOCIO("/erro-negocio", "Violacao da regra de negocio"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    MENSAGEM_IMCOMPREENSIVEL("/mensagem-incompreensivel", "Mesagem Incompreensivel"),
    PROPRIEDADE_IGNORADA("/propriedade-ignorada", "Propriedade Ignorada"),
    PROPERIEDADE_DESCONHECIDA("/propriedade-desconhecida", "Propriedade Desconhecida"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos");


    private String title;
    private String path;

    ProblemType(String path, String title) {
        this.path = path;
        this.title = title;
    }
}
