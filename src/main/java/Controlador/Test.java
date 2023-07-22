package Controlador;

import Modelo.Bdetalle;
import Modelo.Categoria;
import Modelo.Producto;
import Modelo.Proveedor;
import ModeloDAO.BoletaDAO;
import ModeloDAO.ProductoDAO;

public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BoletaDAO dao = new BoletaDAO();
 
        for(Bdetalle x:dao.ListarDetalle(1)){
            System.out.println(x.getCantidad());
        }
        
    }

}
