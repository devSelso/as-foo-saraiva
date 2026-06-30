package br.restaurante.menu;

import br.restaurante.desconto.DescontoPorcentagem;
import br.restaurante.desconto.DescontoValorFixo;
import br.restaurante.model.Cliente;
import br.restaurante.model.ItemPedido;
import br.restaurante.model.Pedido;
import br.restaurante.observer.ClienteObserver;
import br.restaurante.observer.CozinhaObserver;
import br.restaurante.observer.EntregadorObserver;
import br.restaurante.pagamento.PagamentoFactory;
import br.restaurante.pagamento.TipoPagamento;
import br.restaurante.produto.Produto;
import br.restaurante.produto.decorator.BaconDecorator;
import br.restaurante.produto.decorator.BordaRecheadaDecorator;
import br.restaurante.produto.decorator.MolhoEspecialDecorator;
import br.restaurante.produto.decorator.QueijoExtraDecorator;
import br.restaurante.relatorio.RelatorioClientes;
import br.restaurante.relatorio.RelatorioProdutos;
import br.restaurante.relatorio.RelatorioVendas;
import br.restaurante.repositorio.ClienteRepo;
import br.restaurante.repositorio.PedidoRepo;
import br.restaurante.repositorio.ProdutoRepo;
import br.restaurante.status.StatusPedido;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final ClienteRepo clienteRepo = new ClienteRepo();
    private final ProdutoRepo produtoRepo = new ProdutoRepo();
    private final PedidoRepo pedidoRepo = new PedidoRepo();

    public void iniciar() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   RESTAURANTE AS-SARAIVA         ║");
        System.out.println("╚══════════════════════════════════╝");

        System.out.print("\nCarregar dados de exemplo? (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        if (resp.equals("s")) {
            DataSeeder.popular(clienteRepo, produtoRepo, pedidoRepo);
        }

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Clientes");
            System.out.println("2. Produtos");
            System.out.println("3. Pedidos");
            System.out.println("4. Relatórios");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            switch (lerInt()) {
                case 1 -> menuClientes();
                case 2 -> menuProdutos();
                case 3 -> menuPedidos();
                case 4 -> menuRelatorios();
                case 0 -> rodando = false;
                default -> System.out.println("Opção inválida.");
            }
        }
        System.out.println("\nAté logo!");
    }

    // ─── CLIENTES ───────────────────────────────────────────────────────────

    private void menuClientes() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            switch (lerInt()) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 0 -> loop = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        Cliente c = clienteRepo.salvar(nome, email, telefone, endereco);
        System.out.println("✓ Cliente cadastrado: " + c.getId() + " - " + c.getNome());
    }

    private void listarClientes() {
        List<Cliente> lista = clienteRepo.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum cliente cadastrado."); return; }
        System.out.println("\n--- Lista de clientes ---");
        lista.forEach(c -> System.out.printf("[%s] %s | %s | %s%n",
                c.getId(), c.getNome(), c.getEmail(), c.getTelefone()));
    }

    // ─── PRODUTOS ───────────────────────────────────────────────────────────

    private void menuProdutos() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Listar produtos");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            switch (lerInt()) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 0 -> loop = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarProduto() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();
        System.out.print("Preço (ex: 29.90): ");
        double preco = lerDouble();
        System.out.print("Categoria (ex: Pizza, Lanche, Bebida): ");
        String categoria = sc.nextLine();

        Produto p = produtoRepo.salvar(nome, descricao, preco, categoria);
        System.out.printf("✓ Produto cadastrado: %s - %s (R$%.2f)%n", p.getId(), p.getNome(), p.getPreco());
    }

    private void listarProdutos() {
        List<Produto> lista = produtoRepo.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum produto cadastrado."); return; }
        System.out.println("\n--- Cardápio ---");
        lista.forEach(p -> System.out.printf("[%s] %-30s R$%.2f  (%s)%n",
                p.getId(), p.getNome(), p.getPreco(), p.getCategoria()));
    }

    // ─── PEDIDOS ────────────────────────────────────────────────────────────

    private void menuPedidos() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Novo pedido");
            System.out.println("2. Adicionar item ao pedido");
            System.out.println("3. Remover item do pedido");
            System.out.println("4. Aplicar desconto");
            System.out.println("5. Escolher forma de pagamento");
            System.out.println("6. Atualizar status do pedido");
            System.out.println("7. Ver pedido");
            System.out.println("8. Listar todos os pedidos");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            switch (lerInt()) {
                case 1 -> novoPedido();
                case 2 -> adicionarItem();
                case 3 -> removerItem();
                case 4 -> aplicarDesconto();
                case 5 -> escolherPagamento();
                case 6 -> atualizarStatus();
                case 7 -> verPedido();
                case 8 -> listarPedidos();
                case 0 -> loop = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void novoPedido() {
        listarClientes();
        if (clienteRepo.listarTodos().isEmpty()) return;

        System.out.print("\nID do cliente: ");
        String idCliente = sc.nextLine().trim().toUpperCase();

        clienteRepo.buscarPorId(idCliente).ifPresentOrElse(cliente -> {
            Pedido pedido = pedidoRepo.criar(cliente);
            pedido.adicionarObserver(new ClienteObserver());
            pedido.adicionarObserver(new CozinhaObserver());
            pedido.adicionarObserver(new EntregadorObserver());
            System.out.println("✓ Pedido criado: " + pedido.getId() + " para " + cliente.getNome());
        }, () -> System.out.println("Cliente não encontrado."));
    }

    private void adicionarItem() {
        Pedido pedido = selecionarPedido();
        if (pedido == null) return;

        listarProdutos();
        if (produtoRepo.listarTodos().isEmpty()) return;

        System.out.print("\nID do produto: ");
        String idProduto = sc.nextLine().trim().toUpperCase();

        produtoRepo.buscarPorId(idProduto).ifPresentOrElse(produto -> {
            Produto produtoFinal = aplicarAdicionais(produto);

            System.out.print("Quantidade: ");
            int qtd = lerInt();

            pedido.adicionarItem(new ItemPedido(produtoFinal, qtd));
            System.out.printf("✓ Adicionado: %s x%d = R$%.2f%n",
                    produtoFinal.getNome(), qtd, produtoFinal.getPreco() * qtd);
            System.out.printf("Total do pedido: R$%.2f%n", pedido.calcularTotal());
        }, () -> System.out.println("Produto não encontrado."));
    }

    private Produto aplicarAdicionais(Produto produto) {
        System.out.println("\nAdicionais disponíveis:");
        System.out.println("1. Borda Recheada (+R$8,00)");
        System.out.println("2. Queijo Extra (+R$5,00)");
        System.out.println("3. Bacon (+R$7,00)");
        System.out.println("4. Molho Especial (+R$3,00)");
        System.out.println("0. Sem adicionais");

        Produto resultado = produto;
        boolean adicionando = true;
        while (adicionando) {
            System.out.print("Adicional (0 para finalizar): ");
            switch (lerInt()) {
                case 1 -> { resultado = new BordaRecheadaDecorator(resultado); System.out.println("+ Borda Recheada"); }
                case 2 -> { resultado = new QueijoExtraDecorator(resultado); System.out.println("+ Queijo Extra"); }
                case 3 -> { resultado = new BaconDecorator(resultado); System.out.println("+ Bacon"); }
                case 4 -> { resultado = new MolhoEspecialDecorator(resultado); System.out.println("+ Molho Especial"); }
                case 0 -> adicionando = false;
                default -> System.out.println("Opção inválida.");
            }
        }
        System.out.printf("Produto final: %s → R$%.2f%n", resultado.getNome(), resultado.getPreco());
        return resultado;
    }

    private void removerItem() {
        Pedido pedido = selecionarPedido();
        if (pedido == null) return;

        if (pedido.getItens().isEmpty()) { System.out.println("Pedido sem itens."); return; }

        System.out.println("\nItens do pedido:");
        for (int i = 0; i < pedido.getItens().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, pedido.getItens().get(i));
        }
        System.out.print("Número do item a remover (0 para cancelar): ");
        int num = lerInt();
        if (num < 1 || num > pedido.getItens().size()) { System.out.println("Cancelado."); return; }

        ItemPedido item = pedido.getItens().get(num - 1);
        pedido.removerItem(item);
        System.out.println("✓ Item removido: " + item.getProduto().getNome());
        System.out.printf("Total atualizado: R$%.2f%n", pedido.calcularTotal());
    }

    private void aplicarDesconto() {
        Pedido pedido = selecionarPedido();
        if (pedido == null) return;

        System.out.printf("Total atual: R$%.2f%n", pedido.calcularTotal());
        System.out.println("\nTipo de desconto:");
        System.out.println("1. Porcentagem (%)");
        System.out.println("2. Valor fixo (R$)");
        System.out.println("0. Cancelar");
        System.out.print("Opção: ");

        switch (lerInt()) {
            case 1 -> {
                System.out.print("Nome do desconto: ");
                String nome = sc.nextLine();
                System.out.print("Porcentagem (ex: 10): ");
                double pct = lerDouble();
                pedido.aplicarDesconto(new DescontoPorcentagem(nome, pct));
                System.out.printf("✓ Total com desconto: R$%.2f%n", pedido.calcularTotal());
            }
            case 2 -> {
                System.out.print("Nome do desconto: ");
                String nome = sc.nextLine();
                System.out.print("Valor (ex: 15.00): ");
                double valor = lerDouble();
                pedido.aplicarDesconto(new DescontoValorFixo(nome, valor));
                System.out.printf("✓ Total com desconto: R$%.2f%n", pedido.calcularTotal());
            }
            case 0 -> System.out.println("Cancelado.");
        }
    }

    private void escolherPagamento() {
        Pedido pedido = selecionarPedido();
        if (pedido == null) return;

        System.out.printf("Total do pedido: R$%.2f%n", pedido.calcularTotal());
        System.out.println("\nForma de pagamento:");
        System.out.println("1. Cartão de Crédito");
        System.out.println("2. Cartão de Débito");
        System.out.println("3. PIX");
        System.out.println("4. Dinheiro");
        System.out.println("0. Cancelar");
        System.out.print("Opção: ");

        switch (lerInt()) {
            case 1 -> {
                System.out.print("Número do cartão: ");
                String num = sc.nextLine();
                System.out.print("Parcelas: ");
                int parcelas = lerInt();
                pedido.pagar(PagamentoFactory.criar(TipoPagamento.CARTAO_CREDITO,
                        Map.of("numeroCartao", num, "parcelas", String.valueOf(parcelas))));
            }
            case 2 -> {
                System.out.print("Número do cartão: ");
                String num = sc.nextLine();
                pedido.pagar(PagamentoFactory.criar(TipoPagamento.CARTAO_DEBITO,
                        Map.of("numeroCartao", num)));
            }
            case 3 -> {
                System.out.print("Chave PIX: ");
                String chave = sc.nextLine();
                pedido.pagar(PagamentoFactory.criar(TipoPagamento.PIX, Map.of("chavePix", chave)));
            }
            case 4 -> {
                System.out.print("Valor pago: R$");
                double valorPago = lerDouble();
                pedido.pagar(PagamentoFactory.criar(TipoPagamento.DINHEIRO,
                        Map.of("valorPago", String.valueOf(valorPago))));
            }
            case 0 -> System.out.println("Cancelado.");
        }
    }

    private void atualizarStatus() {
        Pedido pedido = selecionarPedido();
        if (pedido == null) return;

        System.out.println("Status atual: " + pedido.getStatus().getDescricao());
        System.out.println("\nNovo status:");
        System.out.println("1. Recebido");
        System.out.println("2. Em preparo");
        System.out.println("3. Saiu para entrega");
        System.out.println("4. Entregue");
        System.out.println("5. Cancelado");
        System.out.println("0. Cancelar");
        System.out.print("Opção: ");

        StatusPedido novo = switch (lerInt()) {
            case 1 -> StatusPedido.RECEBIDO;
            case 2 -> StatusPedido.EM_PREPARO;
            case 3 -> StatusPedido.SAIU_PARA_ENTREGA;
            case 4 -> StatusPedido.ENTREGUE;
            case 5 -> StatusPedido.CANCELADO;
            default -> null;
        };

        if (novo != null) {
            pedido.setStatus(novo);
            System.out.println("✓ Status atualizado para: " + novo.getDescricao());
        }
    }

    private void verPedido() {
        Pedido pedido = selecionarPedido();
        if (pedido == null) return;
        System.out.println(pedido);
    }

    private void listarPedidos() {
        List<Pedido> lista = pedidoRepo.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum pedido registrado."); return; }
        System.out.println("\n--- Pedidos ---");
        lista.forEach(p -> System.out.printf("[%s] %s | %s | R$%.2f%n",
                p.getId(), p.getCliente().getNome(),
                p.getStatus().getDescricao(), p.calcularTotal()));
    }

    private Pedido selecionarPedido() {
        listarPedidos();
        if (pedidoRepo.listarTodos().isEmpty()) return null;

        System.out.print("\nID do pedido: ");
        String id = sc.nextLine().trim().toUpperCase();

        return pedidoRepo.buscarPorId(id).orElseGet(() -> {
            System.out.println("Pedido não encontrado.");
            return null;
        });
    }

    // ─── RELATÓRIOS ─────────────────────────────────────────────────────────

    private void menuRelatorios() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("1. Relatório de Vendas");
            System.out.println("2. Relatório de Clientes");
            System.out.println("3. Produtos mais vendidos");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            switch (lerInt()) {
                case 1 -> new RelatorioVendas(pedidoRepo).gerar();
                case 2 -> new RelatorioClientes(clienteRepo, pedidoRepo).gerar();
                case 3 -> new RelatorioProdutos(pedidoRepo).gerar();
                case 0 -> loop = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ─── UTILITÁRIOS ────────────────────────────────────────────────────────

    private int lerInt() {
        try {
            String linha = sc.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double lerDouble() {
        try {
            String linha = sc.nextLine().trim().replace(",", ".");
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, usando 0.");
            return 0.0;
        }
    }
}
