package br.restaurante.produto.decorator;

import br.restaurante.produto.Produto;

public class BaconDecorator extends ProdutoDecorator {

    private static final double PRECO_ADICIONAL = 7.00;

    public BaconDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String getNome() {
        return produto.getNome() + " + Bacon";
    }

    @Override
    public String getDescricao() {
        return produto.getDescricao() + ", bacon crocante";
    }

    @Override
    public double getPreco() {
        return produto.getPreco() + PRECO_ADICIONAL;
    }
}
