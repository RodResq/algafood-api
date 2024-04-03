package com.algaworks.algafood.di.listener;


import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoNotificador;
import com.algaworks.algafood.di.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class NoficacaoService {

    @TipoNotificador(NivelUrgencia.URGENCIA)
    @Autowired
    private Notificador notificador;

    @EventListener
    @Order(1)
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "O cliente está ativo no sistema!");

    }
}
