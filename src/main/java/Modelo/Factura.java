package Modelo;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Factura {

    private int idFact;
    private Usuario usuario;
    private Cliente cliente;
    private String fechaFact;
    private double totalFact;
    private double igvFact;
    private double netoFact;

    ArrayList<Fdetalle> detalle = new ArrayList();

    public Factura() {
    }

    public Factura(int idFact, Usuario usuario, Cliente cliente, String fechaFact, double totalFact, double igvFact, double netoFact) {
        this.idFact = idFact;
        this.usuario = usuario;
        this.cliente = cliente;
        this.fechaFact = fechaFact;
        this.totalFact = totalFact;
        this.igvFact = igvFact;
        this.netoFact = netoFact;
    }

}
