package br.restaurante.menu;

import br.restaurante.model.Cliente;
import br.restaurante.model.ItemPedido;
import br.restaurante.model.Pedido;
import br.restaurante.observer.ClienteObserver;
import br.restaurante.observer.CozinhaObserver;
import br.restaurante.observer.EntregadorObserver;
import br.restaurante.pagamento.PagamentoFactory;
import br.restaurante.pagamento.TipoPagamento;
import br.restaurante.produto.Produto;
import br.restaurante.repositorio.ClienteRepo;
import br.restaurante.repositorio.PedidoRepo;
import br.restaurante.repositorio.ProdutoRepo;
import br.restaurante.status.StatusPedido;

import java.util.Map;

public class DataSeeder {

    public static void popular(ClienteRepo clienteRepo, ProdutoRepo produtoRepo, PedidoRepo pedidoRepo) {
        System.out.println("\n[Carregando dados de exemplo...]\n");

        // Clientes
        Cliente joao  = clienteRepo.salvar("João Silva",    "joao@email.com",  "11999990001", "Rua das Flores, 100");
        Cliente maria = clienteRepo.salvar("Maria Souza",   "maria@email.com", "11999990002", "Av. Brasil, 250");
        Cliente pedro = clienteRepo.salvar("Pedro Almeida", "pedro@email.com", "11999990003", "Rua do Sol, 42");

        // Produtos — Pizzas
        Produto margherita = produtoRepo.salvar("Pizza Margherita",    "Tomate, mussarela, manjericão",          45.00, "Pizza");
        Produto portuguesa = produtoRepo.salvar("Pizza Portuguesa",    "Presunto, ovos, cebola, azeitona",       49.00, "Pizza");
        Produto calabresa  = produtoRepo.salvar("Pizza Calabresa",     "Calabresa, cebola, azeitona",            47.00, "Pizza");

        // Produtos — Lanches
        Produto hamburguer = produtoRepo.salvar("Hambúrguer Clássico", "Carne 180g, queijo, alface, tomate",     32.00, "Lanche");
        Produto xbacon     = produtoRepo.salvar("X-Bacon",             "Carne 180g, bacon, queijo cheddar",      36.00, "Lanche");

        // Produtos — Bebidas
        Produto suco       = produtoRepo.salvar("Suco de Laranja",     "Natural 500ml",                          12.00, "Bebida");
        Produto refri      = produtoRepo.salvar("Refrigerante 350ml",  "Lata gelada",                             7.00, "Bebida");
        Produto agua       = produtoRepo.salvar("Água Mineral",        "500ml com ou sem gás",                    5.00, "Bebida");

        // Pedido 1 — João, já entregue
        Pedido p1 = pedidoRepo.criar(joao);
        p1.adicionarObserver(new ClienteObserver());
        p1.adicionarObserver(new CozinhaObserver());
        p1.adicionarObserver(new EntregadorObserver());
        p1.adicionarItem(new ItemPedido(margherita, 1));
        p1.adicionarItem(new ItemPedido(suco, 2));
        p1.pagar(PagamentoFactory.criar(TipoPagamento.PIX, Map.of("chavePix", "joao@email.com")));
        p1.setStatus(StatusPedido.EM_PREPARO);
        p1.setStatus(StatusPedido.SAIU_PARA_ENTREGA);
        p1.setStatus(StatusPedido.ENTREGUE);

        // Pedido 2 — Maria, em preparo
        Pedido p2 = pedidoRepo.criar(maria);
        p2.adicionarObserver(new ClienteObserver());
        p2.adicionarObserver(new CozinhaObserver());
        p2.adicionarObserver(new EntregadorObserver());
        p2.adicionarItem(new ItemPedido(hamburguer, 2));
        p2.adicionarItem(new ItemPedido(refri, 2));
        p2.pagar(PagamentoFactory.criar(TipoPagamento.CARTAO_CREDITO,
                Map.of("numeroCartao", "4111111111111234", "parcelas", "2")));
        p2.setStatus(StatusPedido.EM_PREPARO);

        // Pedido 3 — Pedro, recém criado
        Pedido p3 = pedidoRepo.criar(pedro);
        p3.adicionarObserver(new ClienteObserver());
        p3.adicionarObserver(new CozinhaObserver());
        p3.adicionarObserver(new EntregadorObserver());
        p3.adicionarItem(new ItemPedido(calabresa, 1));
        p3.adicionarItem(new ItemPedido(agua, 1));

        System.out.println("✓ 3 clientes cadastrados");
        System.out.println("✓ 8 produtos no cardápio");
        System.out.println("✓ 3 pedidos criados (1 entregue, 1 em preparo, 1 recebido)");
        System.out.println("─".repeat(40));
    }
}
