package br.restaurante.relatorio;

import br.restaurante.model.ItemPedido;
import br.restaurante.model.Pedido;
import br.restaurante.repositorio.PedidoRepo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioProdutos extends RelatorioTemplate {

    private final PedidoRepo pedidoRepo;

    public RelatorioProdutos(PedidoRepo pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    protected String titulo() {
        return "RELATÓRIO DE PRODUTOS MAIS VENDIDOS";
    }

    @Override
    protected void imprimirCorpo() {
        List<Pedido> todos = pedidoRepo.listarTodos();

        Map<String, Integer> contagemPorProduto = new HashMap<>();
        Map<String, Double> receitaPorProduto = new HashMap<>();

        for (Pedido pedido : todos) {
            for (ItemPedido item : pedido.getItens()) {
                String nome = item.getProduto().getNome();
                contagemPorProduto.merge(nome, item.getQuantidade(), Integer::sum);
                receitaPorProduto.merge(nome, item.getSubtotal(), Double::sum);
            }
        }

        if (contagemPorProduto.isEmpty()) {
            System.out.println("Nenhum produto vendido ainda.");
            return;
        }

        System.out.println("Ranking de produtos:\n");
        contagemPorProduto.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.printf("  %-40s | Qtd: %3d | Receita: R$%.2f%n",
                        e.getKey(), e.getValue(), receitaPorProduto.get(e.getKey())));
    }
}
