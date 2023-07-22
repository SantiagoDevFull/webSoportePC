package Modelo;

import lombok.Data;

@Data
public class Cliente {

    private int idCli;
    private String docCli;
    private String numCli;
    private String nomCli;
    private String correoCli;
    private String passCli;
    private String estadoCli;
    private int numSoli;
    private int numBol;
    private int numFac;

    public Cliente() {
    }

    public Cliente(int idCli, String docCli, String numCli, String nomCli, String correoCli, String passCli, String estadoCli) {
        this.idCli = idCli;
        this.docCli = docCli;
        this.numCli = numCli;
        this.nomCli = nomCli;
        this.correoCli = correoCli;
        this.passCli = passCli;
        this.estadoCli = estadoCli;
    }

}
