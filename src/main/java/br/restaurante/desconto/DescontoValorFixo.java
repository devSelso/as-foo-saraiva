package br.restaurante.desconto;

import br.restaurante.model.Pedido;

public class DescontoValorFixo implements EstrategiaDesconto {

    private final double valorFixo;
    private final String nome;

    public DescontoValorFixo(String nome, double valorFixo) {
        this.nome = nome;
        this.valorFixo = valorFixo;
    }

    @Override
    public double calcular(Pedido pedido) {
        return Math.min(valorFixo, pedido.calcularTotal());
    }

    @Override
    public String descricao() {
        return "%s (R$%.2f)".formatted(nome, valorFixo);
    }
}
