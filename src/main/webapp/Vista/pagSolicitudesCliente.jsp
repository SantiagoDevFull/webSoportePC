<%-- 
    Document   : pagSolicitudesCliente
    Created on : 20 jul. 2023, 23:00:25
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

        <jsp:include page="../Include/RecursosCSS.jsp"></jsp:include>
            <script src="js/Cliente.js"></script>
            <script src="js/Notificacion.js"></script>
        <jsp:include page="../Include/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../Include/NavegacionCliente.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4>::: Mis solicitudes :::</h4>
                <hr>

                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                    Nueva Solicitud
                </button><br><br>

                <table id="tabla" class="text-center table table-striped" style="width:100%">
                    <thead>
                        <tr>
                            <th class="col-1 text-center">#Documento</th>
                            <th class="col-2 text-center">Autor</th>
                            <th class="col-2 text-center">Tipo de Solicitud</th>
                            <th class="col-3 text-center">#Descripción</th>
                            <th class="col-3 text-center">Respuesta</th>
                            <th class="col-1 text-center">Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarSolicitud}">
                        <tr>
                            <td>${dato.getCliente().getNumCli()}</td>
                            <td>${dato.getCliente().getNomCli()}</td>
                            <td>${dato.getTipo().getNomTipo()}</td>
                            <td>${dato.getDescripcionSol()}</td>
                            <td>${dato.getRespuestaSol()}</td>
                            <td>${dato.getEstadoSol()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlCliente" method="POST" onsubmit="return ValidarSolicitud()">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Nueva Solicitud :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <input type="hidden" name="txtIDcli" id="txtIDcli" value="${sesionCliente.getIdCli()}">

                            <label class="form-label">Tipo de Solicitud</label>
                            <select class="form-select" name="txtTipo" id="txtTipo">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarTipo}"> 
                                    <option value="${dato.getIdTipo()}">${dato.getNomTipo()}</option>
                                </c:forEach>
                            </select>

                            <label class="form-label">Descripción</label>
                            <textarea class="form-control" name="txtDescripcion" id="txtDescripcion" rows="8"></textarea>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" name="accion" value="Enviar" class="btn btn-primary">Enviar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <br>

        <jsp:include page="../Include/FooterCliente.jsp"></jsp:include>
    </body>
</html>
