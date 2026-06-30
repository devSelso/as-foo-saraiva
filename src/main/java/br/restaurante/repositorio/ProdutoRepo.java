package br.restaurante.repositorio;

import br.restaurante.produto.Produto;
import br.restaurante.produto.ProdutoBase;

import java.util.*;

public class ProdutoRepo {

    private final Map<String, Produto> produtos = new HashMap<>();
    private int contador = 1;

    public Produto salvar(String nome, String descricao, double preco, String categoria) {
        String id = "PRD-%03d".formatted(contador++);
        Produto p = new ProdutoBase(id, nome, descricao, preco, categoria);
        produtos.put(id, p);
        return p;
    }

    public Optional<Produto> buscarPorId(String id) {
        return Optional.ofNullable(produtos.get(id));
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    public List<Produto> listarPorCategoria(String categoria) {
        return produtos.values().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }

    public boolean remover(String id) {
        return produtos.remove(id) != null;
    }
}
