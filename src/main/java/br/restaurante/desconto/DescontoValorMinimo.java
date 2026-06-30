package br.restaurante.desconto;

import br.restaurante.model.Pedido;

public class DescontoValorMinimo implements EstrategiaDesconto {

    private final double valorMinimo;
    private final EstrategiaDesconto estrategiaReal;

    public DescontoValorMinimo(double valorMinimo, EstrategiaDesconto estrategiaReal) {
        this.valorMinimo = valorMinimo;
        this.estrategiaReal = estrategiaReal;
    }

    @Override
    public double calcular(Pedido pedido) {
        if (pedido.calcularTotal() >= valorMinimo) {
            return estrategiaReal.calcular(pedido);
        }
        return 0.0;
    }

    @Override
    public String descricao() {
        return "%s (pedido mínimo R$%.2f)".formatted(estrategiaReal.descricao(), valorMinimo);
    }
}
