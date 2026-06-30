package br.restaurante.pagamento;

import java.util.Map;

public class PagamentoFactory {

    public static Pagamento criar(TipoPagamento tipo, Map<String, String> params) {
        return switch (tipo) {
            case CARTAO_CREDITO -> new PagamentoCartao(
                    TipoPagamento.CARTAO_CREDITO,
                    params.getOrDefault("numeroCartao", "0000000000000000"),
                    Integer.parseInt(params.getOrDefault("parcelas", "1"))
            );
            case CARTAO_DEBITO -> new PagamentoCartao(
                    TipoPagamento.CARTAO_DEBITO,
                    params.getOrDefault("numeroCartao", "0000000000000000"),
                    1
            );
            case PIX -> new PagamentoPix(
                    params.getOrDefault("chavePix", "restaurante@pix.com")
            );
            case DINHEIRO -> new PagamentoDinheiro(
                    Double.parseDouble(params.getOrDefault("valorPago", "0"))
            );
        };
    }
}
