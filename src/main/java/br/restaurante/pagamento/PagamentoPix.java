package br.restaurante.pagamento;

import java.util.UUID;

public class PagamentoPix implements Pagamento {

    private final String chavePix;
    private final String txid;

    public PagamentoPix(String chavePix) {
        this.chavePix = chavePix;
        this.txid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public TipoPagamento getTipo() { return TipoPagamento.PIX; }

    @Override
    public boolean processar(double valor) {
        System.out.println("[PIX] Cobrança de R$%.2f gerada. Chave: %s | TXID: %s"
                .formatted(valor, chavePix, txid));
        return true;
    }

    @Override
    public String getDetalhes() {
        return "PIX - chave: %s | txid: %s".formatted(chavePix, txid);
    }
}
