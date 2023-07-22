package Interfaces;

import Modelo.Rol;
import java.util.ArrayList;

public interface Irol {

    public ArrayList<Rol> ListarRol();

    public int AgregarRol(Rol obj);

    public int ActualizarRol(Rol obj);

    public int EliminarRol(int id);

}
