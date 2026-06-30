package br.restaurante.desconto;

import br.restaurante.model.Pedido;

public class DescontoPorcentagem implements EstrategiaDesconto {

    private final double porcentagem;
    private final String nome;

    public DescontoPorcentagem(String nome, double porcentagem) {
        if (porcentagem < 0 || porcentagem > 100)
            throw new IllegalArgumentException("Porcentagem inválida: " + porcentagem);
        this.nome = nome;
        this.porcentagem = porcentagem;
    }

    @Override
    public double calcular(Pedido pedido) {
        return pedido.calcularTotal() * (porcentagem / 100.0);
    }

    @Override
    public String descricao() {
        return "%s (%.0f%%)".formatted(nome, porcentagem);
    }
}
