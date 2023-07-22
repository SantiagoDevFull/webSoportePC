package Utileria;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class Ruta {

    public static String RutaAbsoluta(HttpServletRequest request) {
        String ruta = request.getServletContext().getRealPath("");
        return ruta.substring(0, ruta.indexOf("target"));
    }

    public static String FechaActual() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String fecha = dateFormatter.format(new Date());
        return fecha;
    }

}
