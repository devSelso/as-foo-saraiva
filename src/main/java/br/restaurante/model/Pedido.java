package br.restaurante.model;

import br.restaurante.desconto.EstrategiaDesconto;
import br.restaurante.desconto.SemDesconto;
import br.restaurante.observer.Observable;
import br.restaurante.observer.Observer;
import br.restaurante.pagamento.Pagamento;
import br.restaurante.status.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido implements Observable {

    private final String id;
    private final Cliente cliente;
    private final List<ItemPedido> itens;
    private final List<Observer> observers;
    private StatusPedido status;
    private final LocalDateTime criadoEm;
    private double desconto;
    private Pagamento pagamento;
    private EstrategiaDesconto estrategiaDesconto;

    public Pedido(String id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.status = StatusPedido.RECEBIDO;
        this.criadoEm = LocalDateTime.now();
        this.desconto = 0.0;
        this.estrategiaDesconto = new SemDesconto();
    }

    public void aplicarDesconto(EstrategiaDesconto estrategia) {
        this.estrategiaDesconto = estrategia;
        this.desconto = estrategia.calcular(this);
        System.out.println("[DESCONTO] %s aplicado: -R$%.2f"
                .formatted(estrategia.descricao(), this.desconto));
    }

    public EstrategiaDesconto getEstrategiaDesconto() { return estrategiaDesconto; }

    @Override
    public void adicionarObserver(Observer observer) { observers.add(observer); }

    @Override
    public void removerObserver(Observer observer) { observers.remove(observer); }

    @Override
    public void notificarObservers() {
        for (Observer o : observers) o.atualizar(this, status);
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
    }

    public double calcularTotal() {
        double subtotal = itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
        return subtotal - desconto;
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return Collections.unmodifiableList(itens); }
    public StatusPedido getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public double getDesconto() { return desconto; }

    public Pagamento getPagamento() { return pagamento; }

    public boolean pagar(Pagamento pagamento) {
        boolean sucesso = pagamento.processar(calcularTotal());
        if (sucesso) this.pagamento = pagamento;
        return sucesso;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
        notificarObservers();
    }
    public void setDesconto(double desconto) { this.desconto = desconto; }

    @Override
    public String toString() {
        return """
                Pedido #%s
                Cliente: %s
                Status: %s
                Itens:
                %s
                Desconto: R$%.2f
                Total: R$%.2f
                """.formatted(
                id,
                cliente.getNome(),
                status.getDescricao(),
                itens.stream().map(ItemPedido::toString).reduce("", (a, b) -> a + b + "\n"),
                desconto,
                calcularTotal()
        );
    }
}
