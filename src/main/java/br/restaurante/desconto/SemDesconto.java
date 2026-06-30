package br.restaurante.desconto;

import br.restaurante.model.Pedido;

public class SemDesconto implements EstrategiaDesconto {

    @Override
    public double calcular(Pedido pedido) { return 0.0; }

    @Override
    public String descricao() { return "Sem desconto"; }
}
