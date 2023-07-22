package Controlador;

import Config.MySQLConexion;
import Modelo.Bdetalle;
import Modelo.Boleta;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Usuario;
import ModeloDAO.BoletaDAO;
import Utileria.Ruta;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ControlBoleta extends HttpServlet {

    private BoletaDAO daoBol = new BoletaDAO();

    private String pagNuevaBoleta = "Vista/pagNuevaBoleta.jsp";
    private String pagBdetalle = "Vista/pagDetalleBoleta.jsp";
    private String pagProductoBoleta = "Vista/pagProductosBoleta.jsp";
    private String pagConsultarBoleta = "Vista/pagConsultarBoleta.jsp";
    private String pagVerBdetalle = "Vista/pagVerBdetalle.jsp";

    private DecimalFormat formato = new DecimalFormat("0.00");

    public static ArrayList<Bdetalle> detalle = new ArrayList();
    private String rutaReporte = "src\\main\\java\\Reportes\\";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "NuevaBoleta":
                NuevaBoleta(request, response);
                break;
            case "AgregarProducto":
                AgregarProducto(request, response);
                break;
            case "RehacerBoleta":
                RehacerBoleta(request, response);
                break;
            case "ActualizarProducto":
                ActualizarProducto(request, response);
                break;
            case "DetalleSize":
                DetalleSize(request, response);
                break;
            case "ProcesarBoleta":
                ProcesarBoleta(request, response);
                break;
            case "ListarBoleta":
                ListarBoleta(request, response);
                break;
            case "ListarDetalle":
                ListarDetalle(request, response);
                break;
        }

    }

    protected void DescargarReporteBoleta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String archivo = "ReporteBoletaCliente.jrxml";

            int idBol = Integer.parseInt(request.getParameter("txtIDboleta"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_BOL", idBol);

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

    protected void ListarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        double suma = 0;
        for (Bdetalle x : daoBol.ListarDetalle(id)) {
            suma = suma + x.Importe();
        }

        request.setAttribute("total", suma);
        request.setAttribute("ListarDetalle", daoBol.ListarDetalle(id));
        request.getRequestDispatcher(pagVerBdetalle).forward(request, response);

    }

    protected void ListarBoleta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarBoleta", daoBol.ListarBoleta());
        request.getRequestDispatcher(pagConsultarBoleta).forward(request, response);

    }

    protected void ProcesarBoleta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int idBol = Integer.parseInt(request.getParameter("txtIDboleta"));
        int idUsu = Integer.parseInt(request.getParameter("txtIDusuario"));
        int idCli = Integer.parseInt(request.getParameter("txtIDcli"));

        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        String fecharegistro = fecha + " " + hora;

        double total = Total();

        Cliente cliente = new Cliente();
        cliente.setIdCli(idCli);

        Usuario u = new Usuario();
        u.setIdUsu(idUsu);

        Boleta obj = new Boleta(idBol, u, cliente, fecharegistro, total);
        obj.setDetalle(detalle);
        int res = daoBol.ProcesarBoleta(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Boleta procesado exitosamente");
            detalle.removeAll(detalle);
            DescargarReporteBoleta(request, response);
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al procesar la boleta");
        }

        response.sendRedirect("ControlBoleta?accion=NuevaBoleta");
    }

    protected void ActualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarProducto", daoBol.ListarProductosActivos());
        request.getRequestDispatcher(pagProductoBoleta).forward(request, response);
    }

    protected void RehacerBoleta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        for (Bdetalle x : detalle) {
            daoBol.AumentarStock(x.getProducto().getIdPro(), x.getCantidad());
        }

        detalle.removeAll(detalle);

        request.getRequestDispatcher("ControlBoleta?accion=NuevaBoleta");
        request.getRequestDispatcher(pagBdetalle).forward(request, response);
    }

    protected void NuevaBoleta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("total", formato.format(Total()));
        request.setAttribute("ListarProducto", daoBol.ListarProductosActivos());
        request.setAttribute("ListarCliente", daoBol.ListarClientesActivos());
        request.setAttribute("ListarDetalle", detalle);
        request.setAttribute("txtCodigoBol", daoBol.RetornarCodigoBoleta());
        request.getRequestDispatcher(pagNuevaBoleta).forward(request, response);

    }

    protected void DetalleSize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int detalleSize = detalle.size();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(detalleSize));
    }

    protected void AgregarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("txtIDpro"));
        String nombre = request.getParameter("txtNomPro");
        double precio = Double.parseDouble(request.getParameter("txtPrecioPro"));
        int cantidad = Integer.parseInt(request.getParameter("txtCantidadPro"));

        daoBol.DisminuirStock(id, cantidad);

        Producto p = new Producto();
        p.setIdPro(id);
        p.setNomPro(nombre);

        for (Bdetalle x : detalle) {
            if (x.getProducto().getIdPro() == id) {
                x.setCantidad(x.getCantidad() + cantidad);

                request.setAttribute("total", formato.format(Total()));
                request.setAttribute("ListarDetalle", detalle);
                request.setAttribute("ListarProducto", daoBol.ListarProductosActivos());
                request.getRequestDispatcher(pagBdetalle).forward(request, response);

                return;
            }
        }

        Bdetalle obj = new Bdetalle();
        obj.setProducto(p);
        obj.setPrecio(precio);
        obj.setCantidad(cantidad);

        detalle.add(obj);

        request.setAttribute("total", formato.format(Total()));
        request.setAttribute("ListarDetalle", detalle);
        request.setAttribute("ListarProducto", daoBol.ListarProductosActivos());
        request.getRequestDispatcher(pagBdetalle).forward(request, response);

    }

    public double Total() {
        double total = 0;
        for (Bdetalle x : detalle) {
            total = total + x.Importe();
        }
        return total;
    }

    protected int getDetalleSize() {
        return detalle.size();
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
