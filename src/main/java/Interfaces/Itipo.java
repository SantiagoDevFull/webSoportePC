package Interfaces;

import Modelo.Tipo;
import java.util.ArrayList;

public interface Itipo {

    public ArrayList<Tipo> ListarTipo();

    public int AgregarTipo(Tipo obj);

    public int ActualizarTipo(Tipo obj);

    public int EliminarTipo(int id);

}
