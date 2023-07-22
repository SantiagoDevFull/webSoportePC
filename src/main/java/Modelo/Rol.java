package Modelo;

import lombok.Data;

@Data
public class Rol {

    private int idRol;
    private String nomRol;
    private String estadoRol;
    private int numUsuarios;

    public Rol() {
    }

    public Rol(int idRol, String nomRol, String estadoRol) {
        this.idRol = idRol;
        this.nomRol = nomRol;
        this.estadoRol = estadoRol;
    }

}
