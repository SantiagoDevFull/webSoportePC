package Controlador;

import Config.MySQLConexion;
import Modelo.Cliente;
import Modelo.Factura;
import Modelo.Fdetalle;
import Modelo.Producto;
import Modelo.Usuario;
import ModeloDAO.FacturaDAO;
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

public class ControlFactura extends HttpServlet {

    private FacturaDAO daoFact = new FacturaDAO();

    private String pagNuevaFactura = "Vista/pagNuevaFactura.jsp";
    private String pagFdetalle = "Vista/pagDetalleFactura.jsp";
    private String pagProductoFactura = "Vista/pagProductosFactura.jsp";
    private String pagVerFdetalle = "Vista/pagVerFdetalle.jsp";
    private String pagConsultarFactura = "Vista/pagConsultarFactura.jsp";

    private DecimalFormat formato = new DecimalFormat("0.00");

    private String rutaReporte = "src\\main\\java\\Reportes\\";

    public static ArrayList<Fdetalle> detalle = new ArrayList();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "NuevaFactura":
                NuevaFactura(request, response);
                break;
            case "AgregarProducto":
                AgregarProducto(request, response);
                break;
            case "RehacerFactura":
                RehacerFactura(request, response);
                break;
            case "ActualizarProducto":
                ActualizarProducto(request, response);
                break;
            case "DetalleSize":
                DetalleSize(request, response);
                break;
            case "ProcesarFactura":
                ProcesarFactura(request, response);
                break;
            case "ListarFactura":
                ListarFactura(request, response);
                break;
            case "ListarDetalle":
                ListarDetalle(request, response);
                break;
        }

    }

    protected void DescargarReporteFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String archivo = "ReporteFacturaCliente.jrxml";

            int idf = Integer.parseInt(request.getParameter("txtIDfactura"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_FACT", idf);

            ByteArrayOutputStream byteOut = generarPDFfactura(parametros, archivo, request, response);
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

    public ByteArrayOutputStream generarPDFfactura(Map parametros, String archivo,
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
        for (Fdetalle x : daoFact.ListarDetalle(id)) {
            suma = suma + x.Importe();
        }

        double igv = suma * 0.18;
        double neto = suma + igv;

        request.setAttribute("total", formato.format(suma));
        request.setAttribute("igv", formato.format(igv));
        request.setAttribute("neto", formato.format(neto));
        request.setAttribute("ListarDetalle", daoFact.ListarDetalle(id));
        request.getRequestDispatcher(pagVerFdetalle).forward(request, response);

    }

    protected void ListarFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarFactura", daoFact.ListarFactura());
        request.getRequestDispatcher(pagConsultarFactura).forward(request, response);

    }

    protected void ProcesarFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int idFact = Integer.parseInt(request.getParameter("txtIDfactura"));
        int idUsu = Integer.parseInt(request.getParameter("txtIDusuario"));
        int idCli = Integer.parseInt(request.getParameter("txtIDcli"));

        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        String fecharegistro = fecha + " " + hora;

        double total = Total();
        double igv = IGV();
        double neto = Neto();

        Cliente cliente = new Cliente();
        cliente.setIdCli(idCli);

        Usuario u = new Usuario();
        u.setIdUsu(idUsu);

        Factura obj = new Factura(idFact, u, cliente, fecharegistro, total, igv, neto);
        obj.setDetalle(detalle);
        int res = daoFact.ProcesarFactura(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Factura procesado exitosamente");
            detalle.removeAll(detalle);
            DescargarReporteFactura(request, response);
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al procesar la factura");
        }

        response.sendRedirect("ControlFactura?accion=NuevaFactura");
    }

    protected void ActualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarProducto", daoFact.ListarProductosActivos());
        request.getRequestDispatcher(pagProductoFactura).forward(request, response);
    }

    protected void RehacerFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        for (Fdetalle x : detalle) {
            daoFact.AumentarStock(x.getProducto().getIdPro(), x.getCantidad());
        }

        detalle.removeAll(detalle);

        request.getRequestDispatcher("ControlFactura?accion=NuevaFactura");
        request.getRequestDispatcher(pagFdetalle).forward(request, response);
    }

    protected void NuevaFactura(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("total", formato.format(Total()));
        request.setAttribute("igv", formato.format(IGV()));
        request.setAttribute("neto", formato.format(Neto()));
        request.setAttribute("ListarProducto", daoFact.ListarProductosActivos());
        request.setAttribute("ListarCliente", daoFact.ListarClientesActivos());
        request.setAttribute("ListarDetalle", detalle);
        request.setAttribute("txtCodigoFact", daoFact.RetornarCodigoFactura());
        request.getRequestDispatcher(pagNuevaFactura).forward(request, response);

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

        daoFact.DisminuirStock(id, cantidad);

        Producto p = new Producto();
        p.setIdPro(id);
        p.setNomPro(nombre);

        for (Fdetalle x : detalle) {
            if (x.getProducto().getIdPro() == id) {
                x.setCantidad(x.getCantidad() + cantidad);

                request.setAttribute("total", formato.format(Total()));
                request.setAttribute("igv", formato.format(IGV()));
                request.setAttribute("neto", formato.format(Neto()));
                request.setAttribute("ListarDetalle", detalle);
                request.setAttribute("ListarProducto", daoFact.ListarProductosActivos());
                request.getRequestDispatcher(pagFdetalle).forward(request, response);

                return;
            }
        }

        Fdetalle obj = new Fdetalle();
        obj.setProducto(p);
        obj.setPrecio(precio);
        obj.setCantidad(cantidad);

        detalle.add(obj);

        request.setAttribute("total", formato.format(Total()));
        request.setAttribute("igv", formato.format(IGV()));
        request.setAttribute("neto", formato.format(Neto()));
        request.setAttribute("ListarDetalle", detalle);
        request.setAttribute("ListarProducto", daoFact.ListarProductosActivos());
        request.getRequestDispatcher(pagFdetalle).forward(request, response);

    }

    public double Total() {
        double total = 0;
        for (Fdetalle x : detalle) {
            total = total + x.Importe();
        }
        return total;
    }

    public double IGV() {
        return Total() * 0.18;
    }

    public double Neto() {
        return Total() + IGV();
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
