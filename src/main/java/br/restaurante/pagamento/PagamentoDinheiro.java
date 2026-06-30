package br.restaurante.pagamento;

public class PagamentoDinheiro implements Pagamento {

    private final double valorPago;
    private double troco;

    public PagamentoDinheiro(double valorPago) {
        this.valorPago = valorPago;
    }

    @Override
    public TipoPagamento getTipo() { return TipoPagamento.DINHEIRO; }

    @Override
    public boolean processar(double valor) {
        if (valorPago < valor) {
            System.out.println("[DINHEIRO] Valor insuficiente. Necessário: R$%.2f | Recebido: R$%.2f"
                    .formatted(valor, valorPago));
            return false;
        }
        troco = valorPago - valor;
        System.out.println("[DINHEIRO] Pago R$%.2f | Troco: R$%.2f".formatted(valorPago, troco));
        return true;
    }

    @Override
    public String getDetalhes() {
        return "Dinheiro - pago: R$%.2f | troco: R$%.2f".formatted(valorPago, troco);
    }

    public double getTroco() { return troco; }
}
