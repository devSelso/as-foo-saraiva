package br.restaurante.observer;

import br.restaurante.model.Pedido;
import br.restaurante.status.StatusPedido;

public interface Observer {
    void atualizar(Pedido pedido, StatusPedido novoStatus);
}
