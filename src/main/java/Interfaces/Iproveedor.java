package Interfaces;

import Modelo.Proveedor;
import java.util.ArrayList;

public interface Iproveedor {

    public ArrayList<Proveedor> ListarProveedor();

    public int AgregarProveedor(Proveedor obj);

    public int ActualizarProveedor(Proveedor obj);

    public int EliminarProveedor(int id);

}
