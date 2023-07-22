package Interfaces;

import Modelo.Producto;
import java.util.ArrayList;

public interface Iproducto {

    public ArrayList<Producto> ListarProducto();

    public int AgregarProducto(Producto obj);

    public int ActualizarProducto(Producto obj);

    public int EliminarProducto(int id);

    public ArrayList<Producto> ListarHardware();

    public ArrayList<Producto> ListarSoftware();
}
