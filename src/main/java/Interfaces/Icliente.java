package Interfaces;

import Modelo.Cliente;
import java.util.ArrayList;

public interface Icliente {

    public Cliente LoginCliente(String correo, String pass);

    public ArrayList<Cliente> ListarCliente();

    public int AgregarCliente(Cliente obj);

    public int ActualizarCliente(Cliente obj);

    public int EliminarCliente(int id);

    public String ExisteNumeroDocumento(String num);

    public String ExisteCorreo(String correo);

}
