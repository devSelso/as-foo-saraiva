package br.restaurante.observer;

import br.restaurante.model.Pedido;
import br.restaurante.status.StatusPedido;

public class EntregadorObserver implements Observer {

    @Override
    public void atualizar(Pedido pedido, StatusPedido novoStatus) {
        if (novoStatus == StatusPedido.SAIU_PARA_ENTREGA) {
            System.out.println("[NOTIF → ENTREGADOR] Novo pedido para entrega: #%s | Destino: %s"
                    .formatted(pedido.getId(), pedido.getCliente().getEndereco()));
        }
    }
}
