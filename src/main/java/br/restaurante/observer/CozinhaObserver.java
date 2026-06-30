package br.restaurante.observer;

import br.restaurante.model.Pedido;
import br.restaurante.status.StatusPedido;

public class CozinhaObserver implements Observer {

    @Override
    public void atualizar(Pedido pedido, StatusPedido novoStatus) {
        if (novoStatus == StatusPedido.RECEBIDO || novoStatus == StatusPedido.EM_PREPARO) {
            System.out.println("[NOTIF → COZINHA] Pedido #%s | %s | Total: R$%.2f"
                    .formatted(pedido.getId(), novoStatus.getDescricao(), pedido.calcularTotal()));
        }
    }
}
