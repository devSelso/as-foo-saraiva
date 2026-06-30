package br.restaurante.observer;

import br.restaurante.model.Pedido;
import br.restaurante.status.StatusPedido;

public class ClienteObserver implements Observer {

    @Override
    public void atualizar(Pedido pedido, StatusPedido novoStatus) {
        String mensagem = switch (novoStatus) {
            case RECEBIDO       -> "Seu pedido #%s foi recebido! Em breve começamos o preparo.";
            case EM_PREPARO     -> "Seu pedido #%s está sendo preparado. Aguarde!";
            case SAIU_PARA_ENTREGA -> "Seu pedido #%s saiu para entrega. Fique de olho!";
            case ENTREGUE       -> "Seu pedido #%s foi entregue. Bom apetite!";
            case CANCELADO      -> "Seu pedido #%s foi cancelado. Entre em contato conosco.";
        };

        System.out.println("[NOTIF → CLIENTE %s | %s] %s"
                .formatted(
                        pedido.getCliente().getNome(),
                        pedido.getCliente().getEmail(),
                        mensagem.formatted(pedido.getId())
                ));
    }
}
