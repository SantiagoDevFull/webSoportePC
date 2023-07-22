<%-- 
    Document   : pagPresupuestoCliente
    Created on : 21 jul. 2023, 16:26:47
    Author     : usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <script src="js/Presupuesto.js"></script>
            <script src="js/Notificacion.js"></script>
        <jsp:include page="../Include/NavegacionCliente.jsp"></jsp:include>

            <div class="container mt-3">

                <h4>::: MIS PRESUPUESTOS :::</h4>

                <table id="tabla" class="text-center table table-striped" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">Cliente</th>
                            <th class="text-center">Fecha</th>
                            <th class="text-center">Total</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarPresupuesto}">
                        <tr>

                            <td>${dato.getCliente().getNumCli()} - ${dato.getCliente().getNomCli()}</td>
                            <td>${dato.getFecha()}</td>
                            <td>${dato.getTotal()}</td>
                            <td>
                                <button class="btn" onclick="CargarDetalle(${dato.getIdPre()})">
                                    <i class="fa-solid fa-magnifying-glass-plus fa-2xl" style="color: #0ae7eb;"></i>
                                </button>
                                <a href="ControlReporte?accion=DescargarPresupuesto&id=${dato.getIdPre()}">
                                    <i class="fa-solid fa-file-pdf fa-2xl" style="color: #ff0026;"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="modal fade" id="modalDetalle" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Detalle :::</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <div id="detalle">

                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../Include/FooterCliente.jsp"></jsp:include>
    </body>
</html>
