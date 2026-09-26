package edu.usal.constantesenums;

public class PedidoV2 {

    private PedidoEstado pedidoEstado;

    public PedidoV2() {
    }

    public PedidoEstado getPedidoEstado() {
        return pedidoEstado;
    }

    public void setPedidoEstado(PedidoEstado pedidoEstado) {
        this.pedidoEstado = pedidoEstado;
    }

    public static void main(String[] args) {
        PedidoV2 pedido = new PedidoV2();
        pedido.setPedidoEstado(PedidoEstado.ENVIADO);
    }
}
