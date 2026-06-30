package br.restaurante.produto.decorator;

import br.restaurante.produto.Produto;

public class MolhoEspecialDecorator extends ProdutoDecorator {

    private static final double PRECO_ADICIONAL = 3.00;

    public MolhoEspecialDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String getNome() {
        return produto.getNome() + " + Molho Especial";
    }

    @Override
    public String getDescricao() {
        return produto.getDescricao() + ", molho especial da casa";
    }

    @Override
    public double getPreco() {
        return produto.getPreco() + PRECO_ADICIONAL;
    }
}
