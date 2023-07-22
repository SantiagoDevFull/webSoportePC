package Modelo;

import java.io.InputStream;
import lombok.Data;

@Data
public class Producto {

    private int idPro;
    private String nomPro;
    private int stockPro;
    private double precioPro;
    private String estadoPro;
    private Categoria categoria;
    private Proveedor proveedor;
    private String fotoPro;

    public Producto() {
    }

    public Producto(int idPro, String nomPro, int stockPro, double precioPro, String estadoPro, Categoria categoria, Proveedor proveedor, String fotoPro) {
        this.idPro = idPro;
        this.nomPro = nomPro;
        this.stockPro = stockPro;
        this.precioPro = precioPro;
        this.estadoPro = estadoPro;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.fotoPro = fotoPro;
    }

}
