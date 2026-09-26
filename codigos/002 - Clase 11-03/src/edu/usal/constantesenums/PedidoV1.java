package edu.usal.constantesenums;

public class PedidoV1 {

    private static final int ESTADO_PENDIENTE = 1;
    private static final int ESTADO_ENVIADO = 2;
    private static final int ESTADO_ENTREGADO = 3;

    private int estadoPedido;

    public PedidoV1() {
    }

    public void setEstadoPedido(int estadoPedido){
        this.estadoPedido = estadoPedido;
    }

    public static void main(String[] args) {
        PedidoV1 pedido = new PedidoV1();
        pedido.setEstadoPedido(PedidoV1.ESTADO_ENTREGADO);
        pedido.setEstadoPedido(99);
    }
}
