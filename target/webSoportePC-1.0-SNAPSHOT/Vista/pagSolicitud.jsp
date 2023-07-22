<%-- 
    Document   : pagSolicitud
    Created on : 18 jul. 2023, 16:09:08
    Author     : usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:if test="${sesionUsuario==null}">
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
            <script src="js/Solicitud.js"></script>
        <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4>::: Gestión de Solicitudes :::</h4>
                <hr>

                <table id="tabla" class="table table-striped text-center" style="width:100%">
                    <thead>
                        <tr>
                            <th class="col-1 text-center">Código</th>
                            <th class="col-1 text-center">#Documento</th>
                            <th class="col-1 text-center">Nombre / Razón</th>
                            <th class="col-1 text-center">Tipo de Solicitud</th>
                            <th class="col-3 text-center">Descripción</th>
                            <th class="col-3 text-center">Respuesta</th>
                            <th class="col-1 text-center">Estado</th>
                            <th class="col-1 text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarSolicitud}">
                        <tr>
                            <td>${dato.getIdSol()}</td>
                            <td>${dato.getCliente().getNumCli()}</td>
                            <td>${dato.getCliente().getNomCli()}</td>
                            <td>${dato.getTipo().getNomTipo()}</td>
                            <td>${dato.getDescripcionSol()}</td>
                            <td>${dato.getRespuestaSol()}</td>
                            <td>${dato.getEstadoSol()}</td>
                            <td>
                                <c:if test="${dato.getEstadoSol()=='Sin responder'}">
                                    <button class="btn btn-warning" onclick="PasarDatosResponder(${dato.getIdSol()}, '${dato.getCliente().getNumCli()}',
                                                    '${dato.getCliente().getNomCli()}', '${dato.getTipo().getNomTipo()}',
                                                    '${dato.getDescripcionSol()}');">Responder</button>
                                </c:if>
                                <c:if test="${dato.getEstadoSol()=='Respondido'}">
                                    <button class="btn btn-warning" disabled="">Responder</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>


        <!-- Modal responder-->
        <div class="modal fade" id="modalResponder" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlSolicitud" method="POST" onsubmit="return ValidarDatosResponder()">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Responder Solicitud :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Código<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="lblCodigo" id="lblCodigo" readonly="">

                            <label class="form-label">#Documento<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblDoc" id="lblDoc" readonly="">

                            <label class="form-label">Nombre / Razón<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblNombre" id="lblNombre" readonly="">

                            <label class="form-label">Tipo de Solicitud<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblTipo" id="lblTipo" readonly="">

                            <label class="form-label">Descripción<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblDes" id="lblDes" readonly="">

                            <label class="form-label">Respuesta<span style="color: red"> *</span></label>
                            <textarea class="form-control" rows="8" name="lblRes" id="lblRes"></textarea>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" name="accion" value="Responder" class="btn btn-warning">Responder</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
