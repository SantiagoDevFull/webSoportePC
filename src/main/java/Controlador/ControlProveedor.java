package Controlador;

import Modelo.Proveedor;
import ModeloDAO.ProveedorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlProveedor extends HttpServlet {

    private ProveedorDAO daoProv = new ProveedorDAO();

    private String pagProveedor = "Vista/pagProveedor.jsp";

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

        request.setAttribute("ListarProveedor", daoProv.ListarProveedor());
        request.getRequestDispatcher(pagProveedor).forward(request, response);

    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String ruc = request.getParameter("txtRuc");
        String nombre = request.getParameter("txtNombre");
        String dire = request.getParameter("txtDire");
        String tel = request.getParameter("txtTel");
        String estado = request.getParameter("txtEstado");

        Proveedor obj = new Proveedor();
        obj.setRucProv(ruc);
        obj.setNomProv(nombre);
        obj.setDireProv(dire);
        obj.setTelProv(tel);
        obj.setEstadoProv(estado);

        int res = daoProv.AgregarProveedor(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Proveedor agregado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al agregar al nuevo proveedor");
        }

        response.sendRedirect("ControlProveedor?accion=Listar");

    }

    protected void Actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("lblCodigo"));
        String ruc = request.getParameter("lblRuc");
        String nombre = request.getParameter("lblNombre");
        String dire = request.getParameter("lblDire");
        String tel = request.getParameter("lblTel");
        String estado = request.getParameter("lblEstado");

        Proveedor obj = new Proveedor(id, ruc, nombre, dire, tel, estado);

        int res = daoProv.ActualizarProveedor(obj);

        if (res > 0) {
            request.getSession().setAttribute("success", "Proveedor actualizado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al actualizar al proveedor seleccionado");
        }

        response.sendRedirect("ControlProveedor?accion=Listar");

    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        int res = daoProv.EliminarProveedor(id);

        if (res > 0) {
            request.getSession().setAttribute("success", "Proveedor eliminado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al eliminar al proveedor seleccionado");
        }

        response.sendRedirect("ControlProveedor?accion=Listar");

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
