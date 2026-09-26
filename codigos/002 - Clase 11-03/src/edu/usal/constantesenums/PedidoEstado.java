package edu.usal.constantesenums;

public enum PedidoEstado {

    PENDIENTE("Pedido pendiente", 1),
    ENVIADO("Pedido enviado", 2),
    ENTREGADO("Pendido entregado", 3);

    private String pedido;
    private int pcodigoPedido;

    PedidoEstado(String pedido, int pcodigoPedido) {
        this.pedido = pedido;
        this.pcodigoPedido = pcodigoPedido;
    }

    public String getPedido() {
        return pedido;
    }

    public int getPcodigoPedido() {
        return pcodigoPedido;
    }
}
