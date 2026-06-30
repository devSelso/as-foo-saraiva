package br.restaurante.pagamento;

public interface Pagamento {
    TipoPagamento getTipo();
    boolean processar(double valor);
    String getDetalhes();
}
