package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@TipoNotificador(NivelUrgencia.URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

    @Autowired
    private NotificacaoPropertie propertie;

    public NotificadorEmail() {
        System.out.println("Notificacao REAL");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("Host: " + propertie.getHostServidor());
        System.out.println("Porta: " + propertie.getPortaServidor());

        System.out.printf("Notificando %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }

}
