package PracticaCRUD.EjercicioOpcional3.models;
import java.time.LocalDate;

public class Venta {
    private int id;
    private String producto;
    private int cantidad;
    private double precioUnitario;
    private String fecha;

    public Venta() {}

    public Venta(int id, String producto, int cantidad, double precio) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precio;
        this.fecha = LocalDate.now().toString();
    }

    // Getters y Setters
    public String getFecha() { return fecha; }
    public double getTotal() { return cantidad * precioUnitario; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
}