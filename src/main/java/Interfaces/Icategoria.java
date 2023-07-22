package Interfaces;

import Modelo.Categoria;
import java.util.ArrayList;

public interface Icategoria {

    public ArrayList<Categoria> ListarCategoria();

    public int AgregarCategoria(Categoria c);

    public int ActualizarCategoria(Categoria c);

    public int EliminarCategoria(int id);

}
