package br.restaurante.desconto;

import br.restaurante.model.Pedido;

public interface EstrategiaDesconto {
    double calcular(Pedido pedido);
    String descricao();
}
