package Modelo;

import lombok.Data;

@Data
public class Categoria {

    private int idCat;
    private String nomCat;
    private String estadoCat;
    private int numProductos;

    public Categoria() {
    }

    public Categoria(int idCat, String nomCat, String estadoCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
        this.estadoCat = estadoCat;
    }

}
