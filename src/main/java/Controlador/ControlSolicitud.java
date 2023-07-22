package Controlador;

import ModeloDAO.SolicitudDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlSolicitud extends HttpServlet {
    
    private SolicitudDAO daoSol = new SolicitudDAO();
    
    private String pagSolicitud = "Vista/pagSolicitud.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "Listar":
                Listar(request, response);
                break;
            case "Responder":
                Responder(request, response);
                break;
        }
        
    }
    
    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        request.setAttribute("ListarSolicitud", daoSol.ListarSolicitud());
        request.getRequestDispatcher(pagSolicitud).forward(request, response);
        
    }
    
    protected void Responder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int codigo = Integer.parseInt(request.getParameter("lblCodigo"));
        String mensaje = request.getParameter("lblRes");
        
        int res = daoSol.ResponderSolicitud(codigo, mensaje);
        
        if (res > 0) {
            request.getSession().setAttribute("success", "Respuesta enviada exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al enviar la respuesta");
        }
        
        response.sendRedirect("ControlSolicitud?accion=Listar");
        
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
