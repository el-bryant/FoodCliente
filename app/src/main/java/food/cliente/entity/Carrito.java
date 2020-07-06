package food.cliente.entity;

/**
 * By: El Bryant
 */

public class Carrito {
    private String idDetallePedido;
    private String idProducto;
    private String nombre;
    private Double precio;
    private String proveedor;
    private String imagen;
    private String idProveedor;

    public Carrito(String idDetallePedido, String idProducto, String nombre, Double precio, String proveedor, String imagen, String idProveedor) {
        this.idDetallePedido = idDetallePedido;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.proveedor = proveedor;
        this.imagen = imagen;
        this.idProveedor = idProveedor;
    }

    public String getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(String idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }
}
