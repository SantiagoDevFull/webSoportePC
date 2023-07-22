package Modelo;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Boleta {

    private int idBol;
    private Usuario usuario;
    private Cliente cliente;
    private String fechaBol;
    private double totalBol;

    ArrayList<Bdetalle> detalle = new ArrayList();

    public Boleta() {
    }

    public Boleta(int idBol, Usuario usuario, Cliente cliente, String fechaBol, double totalBol) {
        this.idBol = idBol;
        this.usuario = usuario;
        this.cliente = cliente;
        this.fechaBol = fechaBol;
        this.totalBol = totalBol;
    }

}
