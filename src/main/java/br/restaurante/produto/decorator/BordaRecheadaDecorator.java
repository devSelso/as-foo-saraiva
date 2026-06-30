package br.restaurante.produto.decorator;

import br.restaurante.produto.Produto;

public class BordaRecheadaDecorator extends ProdutoDecorator {

    private static final double PRECO_ADICIONAL = 8.00;

    public BordaRecheadaDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String getNome() {
        return produto.getNome() + " + Borda Recheada";
    }

    @Override
    public String getDescricao() {
        return produto.getDescricao() + ", borda recheada com catupiry";
    }

    @Override
    public double getPreco() {
        return produto.getPreco() + PRECO_ADICIONAL;
    }
}
