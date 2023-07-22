package Modelo;

import lombok.Data;

@Data
public class Tipo {

    private int idTipo;
    private String nomTipo;
    private String estadoTipo;
    private int numSolcitudes;

    public Tipo() {
    }

    public Tipo(int idTipo, String nomTipo, String estadoTipo) {
        this.idTipo = idTipo;
        this.nomTipo = nomTipo;
        this.estadoTipo = estadoTipo;
    }

}
