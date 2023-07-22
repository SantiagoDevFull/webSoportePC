package Modelo;

import lombok.Data;

@Data
public class Usuario {
   
    private int idUsu;
    private String correoUsu;
    private String passUsu;
    private String nomUsu;
    private String estadoUsu;
    private Rol rol;
    private int numBol;
    private int numFac;

    public Usuario() {
    }

    public Usuario(int idUsu, String correoUsu, String passUsu, String nomUsu, String estadoUsu, Rol rol) {
        this.idUsu = idUsu;
        this.correoUsu = correoUsu;
        this.passUsu = passUsu;
        this.nomUsu = nomUsu;
        this.estadoUsu = estadoUsu;
        this.rol = rol;
    }
    
    
    
}
