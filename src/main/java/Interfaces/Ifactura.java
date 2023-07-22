package Interfaces;

import Modelo.Cliente;
import Modelo.Factura;
import Modelo.Fdetalle;
import Modelo.Producto;
import java.util.ArrayList;

public interface Ifactura {

    public int RetornarCodigoFactura();

    public ArrayList<Cliente> ListarClientesActivos();

    public ArrayList<Producto> ListarProductosActivos();

    public void DisminuirStock(int codigo, int cantidad);

    public void AumentarStock(int codigo, int cantidad);

    public int ProcesarFactura(Factura obj);

    public ArrayList<Factura> ListarFactura();

    public ArrayList<Fdetalle> ListarDetalle(int id);
}
