package Interfaces;

import Modelo.Solicitud;
import java.util.ArrayList;

public interface Isolicitud {

    public ArrayList<Solicitud> ListarSolicitud();

    public int ResponderSolicitud(int codigo, String mensaje);

    public ArrayList<Solicitud> ListarSolicitudes(int id);
    
    public int EnviarSolicitud(Solicitud obj);

}
