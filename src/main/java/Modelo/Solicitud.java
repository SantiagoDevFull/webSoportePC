package Modelo;

import lombok.Data;

@Data
public class Solicitud {

    private int idSol;
    private Cliente cliente;
    private Tipo tipo;
    private String descripcionSol;
    private String respuestaSol;
    private String estadoSol;

    public Solicitud() {
    }

    public Solicitud(int idSol, Cliente cliente, Tipo tipo, String descripcionSol, String respuestaSol, String estadoSol) {
        this.idSol = idSol;
        this.cliente = cliente;
        this.tipo = tipo;
        this.descripcionSol = descripcionSol;
        this.respuestaSol = respuestaSol;
        this.estadoSol = estadoSol;
    }

}
