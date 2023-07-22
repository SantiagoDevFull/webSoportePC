<%-- 
    Document   : pagPresupuesto
    Created on : 21 jul. 2023, 10:45:33
    Author     : usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:if test="${sesionCliente==null}">
            <c:redirect url="index.jsp"></c:redirect>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>R y J Computer S.A.C.</title>
        <link rel="icon" href="http://localhost/img/iconoLogoEmpresa.ico">
        <jsp:include page="../Include/RecursosCSS.jsp"></jsp:include>

        </head>
        <body>

        <jsp:include page="../Include/RecursosJS.jsp"></jsp:include>
            <script src="js/Notificacion.js"></script>
            <script src="js/Presupuesto.js"></script>
        <jsp:include page="../Include/NavegacionCliente.jsp"></jsp:include>
            <br><br>

            <div class="container mt-3">

                <h4>::: Nuevo Presupuesto N° ${txtId} :::</h4>
            <hr><br>

            <table id="tabla2" class="mt-3 table table-bordered text-center bordered" >

                <thead class="bg-info">
                    <tr>
                        <th class="col-2 text-center">Acciones</th>
                        <th class="col-2 text-center">Cantidad</th>
                        <th class="col-5 text-center">Producto</th>
                        <th class="col-2 text-center">Precio</th>
                        <th class="col-2 text-center">Importe</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="x" items="${ListarDetalle}">
                        <tr>
                            <td>
                                <button class="btn" onclick="DisminuirCantidad(${x.getProducto().getIdPro()})">
                                    <i class="fa-solid fa-circle-minus fa-2xl" style="color: #f20202;"></i>
                                </button>
                                <button class="btn" onclick="AumentarCantidad(${x.getProducto().getIdPro()})">
                                    <i class="fa-solid fa-circle-plus fa-2xl" style="color: #043ce7;"></i>
                                </button>
                            </td>
                            <td>${x.getCantidad() }</td>
                            <td>${x.getProducto().getNomPro()}</td>
                            <td>${x.getPrecio()}</td>
                            <td>${(x.Importe())}</td>
                        </tr>
                    </c:forEach>
                </tbody>

                <tfoot>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td class="text-center">Total</td>
                        <td class="text-center">${total}</td>
                    </tr>
                </tfoot>
            </table>

        </div>

        <div class="container">
            <a href="ControlPresupuesto?accion=Procesar&id=${sesionCliente.getIdCli()}&txtId=${txtId}&txtCorreo=${sesionCliente.getCorreoCli()}" clasS="btn btn-success">Procesar</a>
        </div>

        <br><br>
        <jsp:include page="../Include/FooterCliente.jsp"></jsp:include>
    </body>

</html>
