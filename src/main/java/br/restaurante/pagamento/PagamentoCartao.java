package br.restaurante.pagamento;

public class PagamentoCartao implements Pagamento {

    private final TipoPagamento tipo;
    private final String numeroCartao;
    private final int parcelas;

    public PagamentoCartao(TipoPagamento tipo, String numeroCartao, int parcelas) {
        if (tipo != TipoPagamento.CARTAO_CREDITO && tipo != TipoPagamento.CARTAO_DEBITO) {
            throw new IllegalArgumentException("Tipo inválido para cartão.");
        }
        if (parcelas > 1 && tipo == TipoPagamento.CARTAO_DEBITO) {
            throw new IllegalArgumentException("Débito não permite parcelamento.");
        }
        this.tipo = tipo;
        this.numeroCartao = numeroCartao;
        this.parcelas = parcelas;
    }

    @Override
    public TipoPagamento getTipo() { return tipo; }

    @Override
    public boolean processar(double valor) {
        System.out.println("[CARTÃO] Processando R$%.2f em %dx no cartão final %s"
                .formatted(valor, parcelas, numeroCartao.substring(numeroCartao.length() - 4)));
        return true;
    }

    @Override
    public String getDetalhes() {
        return "%s - final %s - %dx"
                .formatted(tipo, numeroCartao.substring(numeroCartao.length() - 4), parcelas);
    }
}
