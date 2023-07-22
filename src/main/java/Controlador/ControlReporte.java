package Controlador;

import Config.MySQLConexion;
import Utileria.Ruta;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ControlReporte extends HttpServlet {

    private String rutaReporte = "src\\main\\java\\Reportes\\";

    int id = 0;
    int idF = 0;
    int idP = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "Descargar":
                DescargarReporte(request, response);
                break;
            case "DescargarBoleta":
                DescargarReporteBoleta(request, response);
            case "DescargarFactura":
                DescargarReporteFactura(request, response);
            case "DescargarPresupuesto":
                DescargarPresupuesto(request, response);
                break;

        }

    }

    protected void DescargarPresupuesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String archivo = "ReportePresupuesto.jrxml";

            idP = Integer.parseInt(request.getParameter("id"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_PRE", idP);

            ByteArrayOutputStream byteOut = generarPresupuesto(parametros, archivo, request, response);
            out.write(byteOut.toByteArray());

            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Disposition", "attachment; filename=" + "rpt" + Ruta.FechaActual() + ".pdf");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ByteArrayOutputStream generarPresupuesto(Map parametros, String archivo,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            String ruta = Ruta.RutaAbsoluta(request) + rutaReporte;
            JasperReport reporte = JasperCompileManager.compileReport(ruta + archivo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, MySQLConexion.getConexion());
            ServletOutputStream out = response.getOutputStream();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteOut);
            return byteOut;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //----------------------
    public ByteArrayOutputStream generarPDFboleta(Map parametros, String archivo,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            String ruta = Ruta.RutaAbsoluta(request) + rutaReporte;
            JasperReport reporte = JasperCompileManager.compileReport(ruta + archivo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, MySQLConexion.getConexion());
            ServletOutputStream out = response.getOutputStream();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteOut);
            return byteOut;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected void DescargarReporteFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String archivo = "ReporteFacturaCliente.jrxml";

            idF = Integer.parseInt(request.getParameter("txtIDfact"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_FACT", idF);

            ByteArrayOutputStream byteOut = generarPDFboleta(parametros, archivo, request, response);
            out.write(byteOut.toByteArray());

            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Disposition", "attachment; filename=" + "rpt" + Ruta.FechaActual() + ".pdf");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    protected void DescargarReporteBoleta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String archivo = "ReporteBoletaCliente.jrxml";

            id = Integer.parseInt(request.getParameter("txtIDbol"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_BOL", id);

            ByteArrayOutputStream byteOut = generarPDFboleta(parametros, archivo, request, response);
            out.write(byteOut.toByteArray());

            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Disposition", "attachment; filename=" + "rpt" + Ruta.FechaActual() + ".pdf");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ByteArrayOutputStream generarPDF(Map parametros, String archivo,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            String ruta = Ruta.RutaAbsoluta(request) + rutaReporte;
            JasperReport reporte = JasperCompileManager.compileReport(ruta + archivo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, MySQLConexion.getConexion());
            ServletOutputStream out = response.getOutputStream();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteOut);
            return byteOut;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected void DescargarReporte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String reportes[] = {"", "ReporteRol", "ReporteEmpleado", "ReporteTipoSolicitud",
                "ReporteSolicitud", "ReporteCliente", "ReporteCategoria",
                "ReporteProveedor", "ReporteProducto", "ReporteBoleta", "ReporteFactura"};
            String archivo = reportes[tipo] + ".jrxml";

            ByteArrayOutputStream byteOut = generarPDF(null, archivo, request, response);
            out.write(byteOut.toByteArray());

            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Disposition", "attachment; filename=" + "rpt" + Ruta.FechaActual() + ".pdf");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
