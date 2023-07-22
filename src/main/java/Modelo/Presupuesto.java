package Modelo;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Presupuesto {

    private int idPre;
    private Cliente cliente;
    private String fecha;
    private double total;

    private ArrayList<Pdetalle> detalle = new ArrayList();

    public Presupuesto() {
    }

    public Presupuesto(int idPre, Cliente cliente, String fecha, double total) {
        this.idPre = idPre;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
    }

}
