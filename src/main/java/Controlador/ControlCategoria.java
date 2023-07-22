package Controlador;

import Modelo.Categoria;
import ModeloDAO.CategoriaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlCategoria extends HttpServlet {

    private CategoriaDAO daoCat = new CategoriaDAO();

    private String pagCategoria = "Vista/pagCategoria.jsp";

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

        request.setAttribute("ListarCategoria", daoCat.ListarCategoria());
        request.getRequestDispatcher(pagCategoria).forward(request, response);

    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String categoria = request.getParameter("txtCategoria");
        String estado = request.getParameter("txtEstado");

        Categoria obj = new Categoria();
        obj.setNomCat(categoria);
        obj.setEstadoCat(estado);

        int res = daoCat.AgregarCategoria(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Categoría agregado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al agregar una nueva categoría de producto");
        }

        response.sendRedirect("ControlCategoria?accion=Listar");

    }

    protected void Actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("lblCodigo"));
        String categoria = request.getParameter("lblCategoria");
        String estado = request.getParameter("lblEstado");

        Categoria obj = new Categoria(codigo, categoria, estado);

        int res = daoCat.ActualizarCategoria(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Categoría actualizado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al actualizar la categoría seleccionada");
        }

        response.sendRedirect("ControlCategoria?accion=Listar");

    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("id"));

        int res = daoCat.EliminarCategoria(codigo);

        if (res > 0) {
            request.getSession().setAttribute("success", "Categoría eliminada exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al eliminar la categoría seleccionada");
        }

        response.sendRedirect("ControlCategoria?accion=Listar");

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
