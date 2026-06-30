package br.restaurante.produto.decorator;

import br.restaurante.produto.Produto;

public abstract class ProdutoDecorator implements Produto {

    protected final Produto produto;

    protected ProdutoDecorator(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String getId() { return produto.getId(); }

    @Override
    public String getNome() { return produto.getNome(); }

    @Override
    public String getCategoria() { return produto.getCategoria(); }
}
