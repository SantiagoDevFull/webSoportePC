package Controlador;

import Modelo.Cliente;
import Modelo.Pdetalle;
import Modelo.Solicitud;
import Modelo.Tipo;
import Modelo.Usuario;
import ModeloDAO.ClienteDAO;
import ModeloDAO.PresupuestoDAO;
import ModeloDAO.SolicitudDAO;
import ModeloDAO.TipoDAO;
import ModeloDAO.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlCliente extends HttpServlet {

    private TipoDAO daoTipo = new TipoDAO();
    private UsuarioDAO daoUsu = new UsuarioDAO();
    private ClienteDAO daoCli = new ClienteDAO();
    private SolicitudDAO daoSol = new SolicitudDAO();
    private PresupuestoDAO daoPre = new PresupuestoDAO();

    private String index = "index.jsp";
    private String soporte = "Vista/pagSoporte.jsp";
    private String pagCliente = "Vista/pagCliente.jsp";
    private String pagSolicitud = "Vista/pagSolicitudesCliente.jsp";
    private String pagListarPresupuesto = "Vista/pagPresupuestoCliente.jsp";
    private String pagDetallePresupuesto = "Vista/pagDetallePresupuesto.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "IniciarSesion":
                IniciarSesion(request, response);
                break;
            case "CerrarSesion":
                CerrarSesion(request, response);
                break;
            case "Listar":
                Listar(request, response);
                break;
            case "Agregar":
                Agregar(request, response);
                break;
            case "Actualizar":
                Actualizar(request, response);
                break;
            case "Eliminar":
                Eliminar(request, response);
                break;
            case "NuevoIndex":
                NuevoIndex(request, response);
                break;
            case "Registrarme":
                Registrarme(request, response);
                break;
            case "ListarSolicitud":
                ListarSolicitud(request, response);
                break;
            case "Enviar":
                Enviar(request, response);
                break;
            case "CerrarSesionUsuario":
                CerrarSesionUsuario(request, response);
                break;
            case "ListarPresupuesto":
                ListarPresupuesto(request, response);
                break;
            case "ListarDetalle":
                ListarDetalle(request, response);
                break;
        }

    }

    protected void ListarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        double suma = 0;
        for (Pdetalle x : daoPre.ListarDetallePresupuesto(id)) {
            suma += x.Importe();
        }

        request.setAttribute("total", suma);
        request.setAttribute("ListarDetalle", daoPre.ListarDetallePresupuesto(id));
        request.getRequestDispatcher(pagDetallePresupuesto).forward(request, response);

    }

    protected void ListarPresupuesto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("idCli"));
        request.setAttribute("ListarPresupuesto", daoPre.ListarPresupuesto(id));
        request.getRequestDispatcher(pagListarPresupuesto).forward(request, response);

    }

    protected void CerrarSesionUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ControlPresupuesto.det.removeAll(ControlPresupuesto.det);
        ControlBoleta.detalle.removeAll(ControlBoleta.detalle);
        ControlBoleta.detalle.removeAll(ControlFactura.detalle);

        request.getSession().removeAttribute("sesionUsuario");
        response.sendRedirect("ControlCliente?accion=NuevoIndex");

    }

    protected void Enviar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int idCli = Integer.parseInt(request.getParameter("txtIDcli"));
        int idTipo = Integer.parseInt(request.getParameter("txtTipo"));
        String des = request.getParameter("txtDescripcion");
        String estado = "Sin responder";

        Cliente cli = new Cliente();
        cli.setIdCli(idCli);

        Tipo tipo = new Tipo();
        tipo.setIdTipo(idTipo);

        Solicitud obj = new Solicitud();
        obj.setDescripcionSol(des);
        obj.setEstadoSol(estado);

        obj.setCliente(cli);
        obj.setTipo(tipo);

        int res = daoSol.EnviarSolicitud(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Solicitud enviado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al enviar su solicitud");
        }

        response.sendRedirect("ControlCliente?accion=ListarSolicitud&idCliente=" + idCli + "");

    }

    protected void ListarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("idCliente"));
        request.setAttribute("ListarTipo", daoTipo.ListarTipo());
        request.setAttribute("ListarSolicitud", daoSol.ListarSolicitudes(id));
        request.getRequestDispatcher(pagSolicitud).forward(request, response);

    }

    protected void Registrarme(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String doc = request.getParameter("lblDoc");
        String num = request.getParameter("lblNum");
        String nom = request.getParameter("lblNom");
        String correo = request.getParameter("lblCorreo");
        String pass = request.getParameter("lblPass");
        String estado = "Activo";

        String numeroDoc = daoCli.ExisteNumeroDocumento(num);
        String correoDoc = daoCli.ExisteCorreo(correo);

        if (numeroDoc.length() > 0) {
            request.getSession().setAttribute("error", "Número de documento ya existe, intente con otro");
            response.sendRedirect("ControlCliente?accion=NuevoIndex");
        } else {
            if (correoDoc.length() > 0) {
                request.getSession().setAttribute("error", "Correo ya existe, intente con otro");
                response.sendRedirect("ControlCliente?accion=NuevoIndex");
            } else {

                Cliente c = new Cliente();
                c.setDocCli(doc);
                c.setNumCli(num);
                c.setNomCli(nom);
                c.setCorreoCli(correo);
                c.setPassCli(pass);
                c.setEstadoCli(estado);

                int res = daoCli.AgregarCliente(c);

                if (res > 0) {
                    request.getSession().setAttribute("success", "Cliente registrado exitosamente");
                } else {
                    request.getSession().setAttribute("success", "Hubo un error interno al registrar sus datos");
                }
                response.sendRedirect("ControlCliente?accion=NuevoIndex");
            }
        }

    }

    protected void NuevoIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher(index).forward(request, response);

    }

    protected void IniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String correo = request.getParameter("txtCorreo");
        String pass = request.getParameter("txtPass");
        int tipo = Integer.parseInt(request.getParameter("tipoPer"));

        if (tipo == 1) {

            Cliente cliente = daoCli.LoginCliente(correo, pass);

            if (cliente != null) {
                request.getSession().setAttribute("sesionCliente", cliente);

            } else {
                request.getSession().setAttribute("error", "Correo electrónico y/o contraseña incorrectos");
            }
            response.sendRedirect("ControlCliente?accion=NuevoIndex");
        } else {
            Usuario usuario = daoUsu.LoginUsuario(correo, pass);

            if (usuario != null) {
                request.getSession().setAttribute("sesionUsuario", usuario);
                request.getRequestDispatcher(soporte).forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Correo electrónico y/o contraseña incorrectos");

                response.sendRedirect("ControlCliente?accion=NuevoIndex");
            }

        }

    }

    protected void CerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ControlPresupuesto.det.removeAll(ControlPresupuesto.det);
        ControlBoleta.detalle.removeAll(ControlBoleta.detalle);
        ControlBoleta.detalle.removeAll(ControlFactura.detalle);
        request.getSession().removeAttribute("sesionCliente");
        request.getSession().removeAttribute("sesionUsuario");
        response.sendRedirect("ControlCliente?accion=NuevoIndex");

    }

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarCliente", daoCli.ListarCliente());
        request.getRequestDispatcher(pagCliente).forward(request, response);

    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String documento = request.getParameter("txtDoc");
        String numero = request.getParameter("txtNum");
        String nombre = request.getParameter("txtNom");
        String correo = request.getParameter("txtCorreo");
        String pass = request.getParameter("txtPass");
        String estado = request.getParameter("txtEstado");

        Cliente obj = new Cliente();
        obj.setDocCli(documento);
        obj.setNumCli(numero);
        obj.setNomCli(nombre);
        obj.setCorreoCli(correo);
        obj.setPassCli(pass);
        obj.setEstadoCli(estado);

        int res = daoCli.AgregarCliente(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Cliente agregado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al agregar un nuevo cliente");
        }

        response.sendRedirect("ControlCliente?accion=Listar");

    }

    protected void Actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("lblCodigo"));
        String documento = request.getParameter("lblDoc");
        String numero = request.getParameter("lblNum");
        String nombre = request.getParameter("lblNom");
        String correo = request.getParameter("lblCorreo");
        String pass = request.getParameter("lblPass");
        String estado = request.getParameter("lblEstado");

        Cliente obj = new Cliente(codigo, documento, numero, nombre, correo, pass, estado);

        int res = daoCli.ActualizarCliente(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Cliente actualizado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al actualizar al cliente seleccionado");
        }

        response.sendRedirect("ControlCliente?accion=Listar");

    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("id"));

        int res = daoCli.EliminarCliente(codigo);

        if (res > 0) {
            request.getSession().setAttribute("success", "Cliente eliminado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al eliminar al cliente seleccionado");
        }

        response.sendRedirect("ControlCliente?accion=Listar");

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
