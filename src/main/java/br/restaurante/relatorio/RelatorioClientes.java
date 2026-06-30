package br.restaurante.relatorio;

import br.restaurante.model.Cliente;
import br.restaurante.model.Pedido;
import br.restaurante.repositorio.ClienteRepo;
import br.restaurante.repositorio.PedidoRepo;

import java.util.List;

public class RelatorioClientes extends RelatorioTemplate {

    private final ClienteRepo clienteRepo;
    private final PedidoRepo pedidoRepo;

    public RelatorioClientes(ClienteRepo clienteRepo, PedidoRepo pedidoRepo) {
        this.clienteRepo = clienteRepo;
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    protected String titulo() {
        return "RELATÓRIO DE CLIENTES";
    }

    @Override
    protected void imprimirCorpo() {
        List<Cliente> clientes = clienteRepo.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("Total de clientes: " + clientes.size());
        System.out.println();

        for (Cliente c : clientes) {
            List<Pedido> pedidos = pedidoRepo.listarPorCliente(c.getId());
            double totalGasto = pedidos.stream().mapToDouble(Pedido::calcularTotal).sum();

            System.out.printf("Cliente: %s (%s)%n", c.getNome(), c.getId());
            System.out.printf("  Email: %s | Tel: %s%n", c.getEmail(), c.getTelefone());
            System.out.printf("  Pedidos: %d | Total gasto: R$%.2f%n", pedidos.size(), totalGasto);
            System.out.println();
        }
    }
}
