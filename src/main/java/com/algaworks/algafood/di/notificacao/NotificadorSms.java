package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@TipoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorSms implements Notificador {


    @Override
    public void notificar(Cliente cliente, String mensagem) {

        System.out.printf("Notificando %s através do SMS para o número %s: %s\n",
                cliente.getNome(), cliente.getTelefone(), mensagem);
    }

}
