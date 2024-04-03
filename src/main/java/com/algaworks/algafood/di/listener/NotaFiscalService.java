package com.algaworks.algafood.di.listener;

import com.algaworks.algafood.di.service.ClienteAtivadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalService {

   @EventListener
   @Order(2)
   public void ClienteAtivadoListener(ClienteAtivadoEvent event) {
       System.out.println("Emitindo nota fiscal para o cliente: " + event.getCliente().getNome());
   }
}
