package Controlador;

import Modelo.Tipo;
import ModeloDAO.TipoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlTipo extends HttpServlet {

    private TipoDAO daoTipo = new TipoDAO();

    private String pagTipo = "Vista/pagTipo.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
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

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarTipo", daoTipo.ListarTipo());
        request.getRequestDispatcher(pagTipo).forward(request, response);

    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String nombre = request.getParameter("txtTipo");
        String estado = request.getParameter("txtEstado");

        Tipo obj = new Tipo();
        obj.setNomTipo(nombre);
        obj.setEstadoTipo(estado);

        int res = daoTipo.AgregarTipo(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Tipo de solicitud agregado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al agregar un nuevo tipo de solicitud");
        }

        response.sendRedirect("ControlTipo?accion=Listar");

    }

    protected void Actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("lblCodigo"));
        String nombre = request.getParameter("lblTipo");
        String estado = request.getParameter("lblEstado");

        Tipo obj = new Tipo(codigo, nombre, estado);

        int res = daoTipo.ActualizarTipo(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Tipo de solicitud actualizado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al actualizar el tipo de solicitud seleccionado");
        }

        response.sendRedirect("ControlTipo?accion=Listar");

    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("id"));

        int res = daoTipo.EliminarTipo(codigo);

        if (res > 0) {
            request.getSession().setAttribute("success", "Tipo de solicitud eliminado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al eliminar el tipo de solicitud seleccionado");
        }

        response.sendRedirect("ControlTipo?accion=Listar");

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
