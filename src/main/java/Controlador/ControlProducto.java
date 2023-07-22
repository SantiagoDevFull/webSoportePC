package Controlador;

import Modelo.Categoria;
import Modelo.Producto;
import Modelo.Proveedor;
import ModeloDAO.CategoriaDAO;
import ModeloDAO.ProductoDAO;
import ModeloDAO.ProveedorDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ControlProducto extends HttpServlet {

    private CategoriaDAO daoCat = new CategoriaDAO();
    private ProveedorDAO daoProv = new ProveedorDAO();
    private ProductoDAO daoPro = new ProductoDAO();

    private String pagProducto = "Vista/pagProducto.jsp";
    private String pagHardware = "Vista/pagHardware.jsp";
    private String pagSoftware = "Vista/pagSoftware.jsp";

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
            case "ListarHardware":
                ListarHardware(request, response);
                break;
            case "ListarSoftware":
                ListarSoftware(request, response);
                break;
        }

    }

    protected void ListarSoftware(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarProducto", daoPro.ListarSoftware());
        request.getRequestDispatcher(pagSoftware).forward(request, response);
    }

    protected void ListarHardware(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarProducto", daoPro.ListarHardware());
        request.getRequestDispatcher(pagHardware).forward(request, response);
    }

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("ListarProveedor", daoProv.ListarProveedor());
        request.setAttribute("ListarCategoria", daoCat.ListarCategoria());
        request.setAttribute("ListarProducto", daoPro.ListarProducto());
        request.getRequestDispatcher(pagProducto).forward(request, response);
    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String nombre = request.getParameter("txtNom");
        int stock = Integer.parseInt(request.getParameter("txtStock"));
        double precio = Double.parseDouble(request.getParameter("txtPrecio"));
        String estado = request.getParameter("txtEstado");

        Part part = request.getPart("txtImagen");
        String foto = getFileName(part);

        String uploadPath = "C:\\xampp\\htdocs\\img";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fileName = foto;
        Path imagePath = Paths.get(uploadPath, fileName);
        Files.copy(part.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        int idCat = Integer.parseInt(request.getParameter("txtCategoria"));
        int idProv = Integer.parseInt(request.getParameter("txtProveedor"));

        Categoria c = new Categoria();
        c.setIdCat(idCat);

        Proveedor prov = new Proveedor();
        prov.setIdProv(idProv);

        Producto p = new Producto();
        p.setNomPro(nombre);
        p.setStockPro(stock);
        p.setPrecioPro(precio);
        p.setEstadoPro(estado);
        p.setCategoria(c);
        p.setProveedor(prov);
        p.setFotoPro(foto);

        int res = daoPro.AgregarProducto(p);

        if (res > 0) {
            request.getSession().setAttribute("success", "Producto agregado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al agregar el nuevo producto");
        }
        response.sendRedirect("ControlProducto?accion=Listar");

    }

    protected void Actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("lblCodigo"));
        String nombre = request.getParameter("lblNom");
        int stock = Integer.parseInt(request.getParameter("lblStock"));
        double precio = Double.parseDouble(request.getParameter("lblPrecio"));
        String estado = request.getParameter("lblEstado");

        Part part = request.getPart("lblImagen");
        String foto = getFileName(part);

        String uploadPath = "C:\\xampp\\htdocs\\img";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fileName = foto;
        Path imagePath = Paths.get(uploadPath, fileName);
        Files.copy(part.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        int idCat = Integer.parseInt(request.getParameter("lblCategoria"));
        int idProv = Integer.parseInt(request.getParameter("lblProveedor"));

        Categoria c = new Categoria();
        c.setIdCat(idCat);

        Proveedor prov = new Proveedor();
        prov.setIdProv(idProv);

        Producto p = new Producto(codigo, nombre, stock, precio, estado, c, prov, foto);

        int res = daoPro.ActualizarProducto(p);

        if (res > 0) {
            request.getSession().setAttribute("success", "Producto actualizado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al actualizar el producto seleccionado");
        }
        response.sendRedirect("ControlProducto?accion=Listar");

    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int codigo = Integer.parseInt(request.getParameter("id"));

        int res = daoPro.EliminarProducto(codigo);

        if (res > 0) {
            request.getSession().setAttribute("success", "Producto eliminado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Hubo un error interno al eliminar el producto seleccionado");
        }
        response.sendRedirect("ControlProducto?accion=Listar");

    }

    private String getFileName(Part part) {
        String contentDispositionHeader = part.getHeader("content-disposition");
        String[] elements = contentDispositionHeader.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
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
