package Modelo;

import lombok.Data;

@Data
public class Proveedor {

    private int idProv;
    private String rucProv;
    private String nomProv;
    private String direProv;
    private String telProv;
    private String estadoProv;
    private int numProductos;

    public Proveedor() {
    }

    public Proveedor(int idProv, String rucProv, String nomProv, String direProv, String telProv, String estadoProv) {
        this.idProv = idProv;
        this.rucProv = rucProv;
        this.nomProv = nomProv;
        this.direProv = direProv;
        this.telProv = telProv;
        this.estadoProv = estadoProv;
    }

}
