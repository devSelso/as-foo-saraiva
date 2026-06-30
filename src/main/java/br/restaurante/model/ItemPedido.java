package br.restaurante.model;

import br.restaurante.produto.Produto;

public class ItemPedido {

    private final Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return "  %s x%d = R$%.2f".formatted(produto.getNome(), quantidade, getSubtotal());
    }
}
