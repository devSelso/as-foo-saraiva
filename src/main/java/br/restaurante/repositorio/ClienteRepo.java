package br.restaurante.repositorio;

import br.restaurante.model.Cliente;

import java.util.*;

public class ClienteRepo {

    private final Map<String, Cliente> clientes = new HashMap<>();
    private int contador = 1;

    public Cliente salvar(String nome, String email, String telefone, String endereco) {
        String id = "CLI-%03d".formatted(contador++);
        Cliente c = new Cliente(id, nome, email, telefone, endereco);
        clientes.put(id, c);
        return c;
    }

    public Optional<Cliente> buscarPorId(String id) {
        return Optional.ofNullable(clientes.get(id));
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    public boolean remover(String id) {
        return clientes.remove(id) != null;
    }
}
