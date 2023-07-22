package Interfaces;

import Modelo.Pdetalle;
import Modelo.Presupuesto;
import java.util.ArrayList;

public interface Ipresupuesto {

    public int ProcesarPresupuesto(Presupuesto obj);

    public ArrayList<Presupuesto> ListarPresupuesto(int id);
    public int RetornarCodigoPresupuesto();
    public ArrayList<Pdetalle>ListarDetallePresupuesto(int idPre);

}
