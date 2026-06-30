package br.restaurante.produto;

public class ProdutoBase implements Produto {

    private final String id;
    private final String nome;
    private final String descricao;
    private final double preco;
    private final String categoria;

    public ProdutoBase(String id, String nome, String descricao, double preco, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    @Override public String getId() { return id; }
    @Override public String getNome() { return nome; }
    @Override public String getDescricao() { return descricao; }
    @Override public double getPreco() { return preco; }
    @Override public String getCategoria() { return categoria; }

    @Override
    public String toString() {
        return "Produto[nome=%s, preco=R$%.2f, categoria=%s]"
                .formatted(nome, preco, categoria);
    }
}
