package Modelo;

import lombok.Data;

@Data
public class Pdetalle {

    private Producto producto;
    private int cantidad;
    private double precio;

    public Pdetalle() {
    }

    public Pdetalle(Producto producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public double Importe() {
        return cantidad * precio;
    }

}
