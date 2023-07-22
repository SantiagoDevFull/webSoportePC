package Controlador;

import Modelo.Rol;
import Modelo.Usuario;
import ModeloDAO.RolDAO;
import ModeloDAO.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlUsuario extends HttpServlet {

    private UsuarioDAO daoUsu = new UsuarioDAO();
    private RolDAO daoRol = new RolDAO();

    private String Inicio = "Vista/pagSoporte.jsp";
    private String pagUsuario = "Vista/pagUsuario.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "Inicio":
                Inicio(request, response);
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
        }

    }

    protected void Inicio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher(Inicio).forward(request, response);

    }

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarRol", daoRol.ListarRol());
        request.setAttribute("ListarUsuario", daoUsu.ListarUsuario());
        request.getRequestDispatcher(pagUsuario).forward(request, response);

    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String correo = request.getParameter("txtCorreo");
        String pass = request.getParameter("txtPass");
        String nombre = request.getParameter("txtNombre");
        String estado = request.getParameter("txtEstado");

        int idRol = Integer.parseInt(request.getParameter("txtRol"));

        Rol rol = new Rol();
        rol.setIdRol(idRol);

        Usuario u = new Usuario();
        u.setCorreoUsu(correo);
        u.setPassUsu(pass);
        u.setNomUsu(nombre);
        u.setEstadoUsu(estado);
        u.setRol(rol);

        int res = daoUsu.AgregarUsuario(u);

        if (res > 0) {
            request.getSession().setAttribute("success", "Usuario agregado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error al ingresar un nuevo usuario");
        }

        response.sendRedirect("ControlUsuario?accion=Listar");

    }

    protected void Actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("lblCodigo"));
        String correo = request.getParameter("lblCorreo");
        String pass = request.getParameter("lblPass");
        String nombre = request.getParameter("lblNombre");
        String estado = request.getParameter("lblEstado");

        int idRol = Integer.parseInt(request.getParameter("lblRol"));

        Rol rol = new Rol();
        rol.setIdRol(idRol);

        Usuario u = new Usuario(id, correo, pass, nombre, estado, rol);

        int res = daoUsu.ActualizarUsuario(u);

        if (res > 0) {
            request.getSession().setAttribute("success", "Usuario actualizado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error al actualizar al usuario seleccionado");
        }

        response.sendRedirect("ControlUsuario?accion=Listar");

    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        int res = daoUsu.EliminarUsuario(id);

        if (res > 0) {
            request.getSession().setAttribute("success", "Usuario eliminado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error al eliminar al usuario seleccionado");
        }

        response.sendRedirect("ControlUsuario?accion=Listar");

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
