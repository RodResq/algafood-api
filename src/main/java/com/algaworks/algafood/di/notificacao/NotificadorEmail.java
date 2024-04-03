package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@TipoNotificador(NivelUrgencia.URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

//    @Value("${notificador.email.host-servidor}")
//    private String host;

    @Autowired
    Environment environment;

//    @Value("${notificador.email.porta-servidor}")
//    private Integer port;

    public NotificadorEmail() {
        System.out.println("Notificacao REAL");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("Host: " + environment.getProperty("notificador.email.host-servidor"));
        System.out.println("Porta" + environment.getProperty("notificador.email.porta-servidor"));

        System.out.printf("Notificando %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }

}
