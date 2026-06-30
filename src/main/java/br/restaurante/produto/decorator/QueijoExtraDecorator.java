package br.restaurante.produto.decorator;

import br.restaurante.produto.Produto;

public class QueijoExtraDecorator extends ProdutoDecorator {

    private static final double PRECO_ADICIONAL = 5.00;

    public QueijoExtraDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String getNome() {
        return produto.getNome() + " + Queijo Extra";
    }

    @Override
    public String getDescricao() {
        return produto.getDescricao() + ", queijo extra";
    }

    @Override
    public double getPreco() {
        return produto.getPreco() + PRECO_ADICIONAL;
    }
}
