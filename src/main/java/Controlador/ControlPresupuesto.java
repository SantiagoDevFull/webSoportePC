package Controlador;

import Config.MySQLConexion;
import Modelo.Cliente;
import Modelo.Pdetalle;
import Modelo.Presupuesto;
import Modelo.Producto;
import ModeloDAO.PresupuestoDAO;
import Utileria.Ruta;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class ControlPresupuesto extends HttpServlet {

    private PresupuestoDAO daoPre = new PresupuestoDAO();

    public static ArrayList<Pdetalle> det = new ArrayList();

    private String pagPresupuesto = "Vista/pagPresupuesto.jsp";

    private String rutaReporte = "src\\main\\java\\Reportes\\";
    private String correo = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "Nueva":
                Nueva(request, response);
                break;
            case "Agregar":
                Agregar(request, response);
                break;
            case "ActualizarCantidad":
                ActualizarCantidad(request, response);
                break;
            case "Procesar":
                Procesar(request, response);
                break;
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

    protected void DescargarReporteBoleta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();

        try {
            int tipo = Integer.parseInt(request.getParameter("tipo") == null ? "0" : request.getParameter("tipo"));
            String archivo = "ReportePresupuesto.jrxml";

            int id = Integer.parseInt(request.getParameter("txtId"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_PRE", id);

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

        //-------------------------------------------------
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        final String username = "danfeer.ingeniero.de.sistema@gmail.com";
        final String password = "sxhkgtfmjcmyjspd";

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String to = correo;
        String from = username;
        String subject = "::: R y J Computer S.A.C. :::";
        String messageText = "Constancia de presupuesto enviado";

        try {
            // Crea el mensaje y configura los destinatarios, asunto y contenido del mensaje
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

            // Crea la parte de texto del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageText);

            // Crea la parte del archivo adjunto
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            String filePath = "C:\\Users\\usuario\\Downloads\\" + "rpt" + Ruta.FechaActual() + ".pdf"; // Reemplaza esto con la ruta al archivo que deseas adjuntar
            DataSource source = new FileDataSource(filePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(source.getName());

            // Combina las partes del mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Establece el contenido del mensaje como el conjunto de partes
            msg.setContent(multipart);

            // Envía el mensaje
            Transport.send(msg);

            System.out.println("Correo enviado exitosamente.");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        //-------------------------------------------------------------

    }

    protected void Procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (det.size() <= 0) {
            request.getSession().setAttribute("error", "Carrito vacío");
            response.sendRedirect("ControlPresupuesto?accion=Nueva");
            return;
        }

        int idPre = Integer.parseInt(request.getParameter("txtId"));
        int idCli = Integer.parseInt(request.getParameter("id"));
        correo = request.getParameter("txtCorreo");

        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        String fecharegistro = fecha + " " + hora;

        double total = Total();

        Cliente c = new Cliente();
        c.setIdCli(idCli);

        Presupuesto obj = new Presupuesto(idPre, c, fecharegistro, total);
        obj.setDetalle(det);

        int res = daoPre.ProcesarPresupuesto(obj);

        if (res > 0) {

            det.removeAll(det);
            DescargarReporteBoleta(request, response);
            request.getSession().setAttribute("success", "Presupuesto generado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error al generar el presupuesto");
        }

        response.sendRedirect("ControlPresupuesto?accion=Nueva");

    }

    protected void ActualizarCantidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int idPro = Integer.parseInt(request.getParameter("txtCodigoPro"));
        int cantidad = Integer.parseInt(request.getParameter("txtCantidad"));

        for (Pdetalle x : det) {
            if (x.getProducto().getIdPro() == idPro) {

                if ((x.getCantidad() + cantidad) <= 0) {
                    request.getSession().setAttribute("error", "Cantidad nula");
                    response.sendRedirect("ControlPresupuesto?accion=Nueva");
                    return;
                }

                x.setCantidad(x.getCantidad() + cantidad);
                request.getSession().setAttribute("info", "El producto ya ha sido agregado al carrito");
                response.sendRedirect("ControlPresupuesto?accion=Nueva");
                return;
            }
        }

    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int idPro = Integer.parseInt(request.getParameter("txtCodigoPro"));
        String nomPro = request.getParameter("txtNombrePro");
        double prePro = Double.parseDouble(request.getParameter("txtPrecioPro"));

        for (Pdetalle x : det) {
            if (x.getProducto().getIdPro() == idPro) {
                request.getSession().setAttribute("info", "El producto ya ha sido agregado al carrito");
                response.sendRedirect("ControlPresupuesto?accion=Nueva");
                return;
            }
        }

        Producto p = new Producto();
        p.setIdPro(idPro);
        p.setNomPro(nomPro);

        Pdetalle obj = new Pdetalle();
        obj.setProducto(p);
        obj.setCantidad(1);
        obj.setPrecio(prePro);

        det.add(obj);

        response.sendRedirect("ControlPresupuesto?accion=Nueva");

    }

    protected void Nueva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("txtId", daoPre.RetornarCodigoPresupuesto());
        request.setAttribute("total", Total());
        request.setAttribute("ListarDetalle", det);
        request.getRequestDispatcher(pagPresupuesto).forward(request, response);

    }

    public double Total() {
        double total = 0;
        for (Pdetalle x : det) {
            total += x.Importe();
        }
        return total;
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
