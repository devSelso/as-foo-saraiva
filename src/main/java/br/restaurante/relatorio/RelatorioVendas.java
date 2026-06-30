package br.restaurante.relatorio;

import br.restaurante.model.Pedido;
import br.restaurante.repositorio.PedidoRepo;
import br.restaurante.status.StatusPedido;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioVendas extends RelatorioTemplate {

    private final PedidoRepo pedidoRepo;

    public RelatorioVendas(PedidoRepo pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    protected String titulo() {
        return "RELATÓRIO DE VENDAS";
    }

    @Override
    protected void imprimirCorpo() {
        List<Pedido> todos = pedidoRepo.listarTodos();

        if (todos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }

        double totalGeral = todos.stream().mapToDouble(Pedido::calcularTotal).sum();
        long entregues = todos.stream().filter(p -> p.getStatus() == StatusPedido.ENTREGUE).count();

        System.out.println("Total de pedidos: " + todos.size());
        System.out.println("Pedidos entregues: " + entregues);
        System.out.printf("Receita total: R$%.2f%n", totalGeral);

        System.out.println("\nPor status:");
        Map<StatusPedido, Long> porStatus = todos.stream()
                .collect(Collectors.groupingBy(Pedido::getStatus, Collectors.counting()));
        porStatus.forEach((status, count) ->
                System.out.println("  " + status.getDescricao() + ": " + count));

        System.out.println("\nDetalhes por pedido:");
        todos.forEach(p -> System.out.printf("  #%s | %s | %s | R$%.2f%n",
                p.getId(), p.getCliente().getNome(),
                p.getStatus().getDescricao(), p.calcularTotal()));
    }
}
