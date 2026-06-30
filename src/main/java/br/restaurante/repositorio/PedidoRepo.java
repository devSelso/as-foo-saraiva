package br.restaurante.repositorio;

import br.restaurante.model.Cliente;
import br.restaurante.model.Pedido;
import br.restaurante.status.StatusPedido;

import java.util.*;

public class PedidoRepo {

    private final Map<String, Pedido> pedidos = new HashMap<>();
    private int contador = 1;

    public Pedido criar(Cliente cliente) {
        String id = "PED-%03d".formatted(contador++);
        Pedido p = new Pedido(id, cliente);
        pedidos.put(id, p);
        return p;
    }

    public Optional<Pedido> buscarPorId(String id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidos.values().stream()
                .filter(p -> p.getStatus() == status)
                .toList();
    }

    public List<Pedido> listarPorCliente(String clienteId) {
        return pedidos.values().stream()
                .filter(p -> p.getCliente().getId().equals(clienteId))
                .toList();
    }
}
