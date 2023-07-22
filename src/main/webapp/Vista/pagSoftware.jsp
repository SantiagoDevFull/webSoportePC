<%-- 
    Document   : pagSoftware
    Created on : 20 jul. 2023, 22:13:42
    Author     : usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>R y J Computer S.A.C.</title>
        <link rel="icon" href="http://localhost/img/iconoLogoEmpresa.ico">
        <jsp:include page="../Include/RecursosCSS.jsp"></jsp:include>

        </head>
        <body>

        <jsp:include page="../Include/RecursosJS.jsp"></jsp:include>
            <script src="js/Notificacion.js"></script>
        <jsp:include page="../Include/NavegacionCliente.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4 class="text-center">::: Productos de la categoría Software :::</h4>

                <div class="container mt-4">
                    <hr>

                    <div class="row text-center">
                    <c:forEach var="dato" items="${ListarProducto}">
                        <div class="col-md-4 mb-4">
                            <img src="http://localhost/img/${dato.getFotoPro()}" class="img-fluid" width="200" height="170">
                            <br><br>

                            <h6 class="text-center">${dato.getNomPro()}</h6>
                            <h6 class="text-center">S/. ${dato.getPrecioPro()}</h6>

                            <c:if test="${sesionCliente!=null}">
                                <form action="ControlPresupuesto" method="POST" >
                                    <input type="hidden" name="txtCodigoPro" id="" value="${dato.getIdPro()}">
                                    <input type="hidden" name="txtNombrePro" id="" value="${dato.getNomPro()}">
                                    <input type="hidden" name="txtPrecioPro" id="" value="${dato.getPrecioPro()}">
                                    <button class="btn btn-primary" type="submit" name="accion" value="Agregar">
                                        Agregar
                                    </button>
                                </form>
                            </c:if>

                        </div>

                    </c:forEach>
                </div>

            </div>

        </div>

        <jsp:include page="../Include/FooterCliente.jsp"></jsp:include>
    </body>
</html>

