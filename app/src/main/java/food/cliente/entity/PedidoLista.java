package food.cliente.entity;

/**
 * By: El Bryant
 */

public class PedidoLista {
    private String idPedidoLista;
    private String iconoTienda;
    private String fecha;
    private String hora;
    private String nombreProducto;
    private String monto;

    public PedidoLista(String idPedidoLista, String iconoTienda, String fecha, String hora, String nombreProducto, String monto) {
        this.idPedidoLista = idPedidoLista;
        this.iconoTienda = iconoTienda;
        this.fecha = fecha;
        this.hora = hora;
        this.nombreProducto = nombreProducto;
        this.monto = monto;
    }

    public String getIdPedidoLista() {
        return idPedidoLista;
    }

    public void setIdPedidoLista(String idPedidoLista) {
        this.idPedidoLista = idPedidoLista;
    }

    public String getIconoTienda() {
        return iconoTienda;
    }

    public void setIconoTienda(String iconoTienda) {
        this.iconoTienda = iconoTienda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
