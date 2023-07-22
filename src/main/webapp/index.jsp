<%-- 
    Document   : index
    Created on : 17 jul. 2023, 1:59:49
    Author     : usuario
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Modelo.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ModeloDAO.ProductoDAO"%>
<%
    ProductoDAO dao = new ProductoDAO();
    ArrayList<Producto> lista = dao.ListarProducto();%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>R y J Computer S.A.C.</title>

        <link rel="icon" href="http://localhost/img/iconoLogoEmpresa.ico">

        <jsp:include page="Include/RecursosCSS.jsp"></jsp:include>
            <style>
                .marquee-container {
                    width: 100%;
                    overflow: hidden;
                    white-space: nowrap;
                    position: relative;
                }

                .marquee-content {
                    display: inline-block;
                    padding-left: 100%;
                    animation: marquee 10s linear infinite;
                }

                @keyframes marquee {
                    0% { transform: translateX(0); }
                    100% { transform: translateX(-100%); }
                }
            </style>
        </head>
        <body>

        <jsp:include page="Include/RecursosJS.jsp"></jsp:include>
            <script src="js/Cliente.js"></script>
            <script src="js/Notificacion.js"></script>

        <jsp:include page="Include/NavegacionCliente.jsp"></jsp:include>    

            <br>
            <div class="marquee-container">
                <div class="marquee-content">
                    <h1 style="font-family: Bodoni MT Black">Estamos comprometidos a brindar una experiencia de usuario excepcional. 
                        Nuestro equipo de soporte técnico está listo para ayudarte en cada paso del camino.</h1>
                </div>
            </div>
            <br>



            <div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
                <div class="carousel-inner">
                <% int count = 0; %>
                <% for (Producto x : lista) {%>
                <div class="carousel-item <%= count == 0 ? "active" : ""%> container-fluid d-flex justify-content-center align-items-center">
                    <img src="http://localhost/img/<%= x.getFotoPro()%>" class="d-block w-50" width="30%" height="50%">
                </div>
                <% count++; %>
                <% }%>
            </div>
            <button class="carousel-control-prev rounded bg-primary" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next rounded bg-primary" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <br><br>
        <div class="spinner-grow text-primary" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="spinner-grow text-secondary" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="spinner-grow text-success" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="spinner-grow text-danger" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="spinner-grow text-warning" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="spinner-grow text-info" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="spinner-grow text-dark" role="status">
            <span class="visually-hidden">Loading...</span>
        </div><br><br>

        <jsp:include page="Include/FooterCliente.jsp"></jsp:include>

    </body>
</html>
