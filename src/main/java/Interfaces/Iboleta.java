package Interfaces;

import Modelo.Bdetalle;
import Modelo.Boleta;
import Modelo.Cliente;
import Modelo.Producto;
import java.util.ArrayList;

public interface Iboleta {
    
    public int RetornarCodigoBoleta();
    public ArrayList<Cliente>ListarClientesActivos();
    public ArrayList<Producto>ListarProductosActivos();
    public void DisminuirStock(int codigo,int cantidad);
    public void AumentarStock(int codigo,int cantidad);
    public int ProcesarBoleta(Boleta obj);
    public ArrayList<Boleta>ListarBoleta();
    public ArrayList<Bdetalle>ListarDetalle(int id);
    
}
