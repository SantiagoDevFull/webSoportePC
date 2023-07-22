<%-- 
    Document   : pagCliente
    Created on : 18 jul. 2023, 22:50:58
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
            <script src="js/Cliente.js"></script>
        <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4>::: Gestión de Clientes :::</h4>
                <hr>

                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalAgregar">
                    <i class="fa-solid fa-plus fa-xl" style="color: #fcfcfc;"></i> Nuevo
                </button><br><br>

                <table id="tabla" class="text-center table table-striped" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">Código</th>
                            <th class="text-center">Documento</th>
                            <th class="text-center">Número</th>
                            <th class="text-center">Nombre / Razón social</th>
                            <th class="text-center">Correo electrónico</th>
                            <th class="text-center">Contraseña</th>
                            <th class="text-center">#Solicitudes</th>
                            <th class="text-center">#Boletas</th>
                            <th class="text-center">#Facturas</th>
                            <th class="text-center">Estado</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarCliente}">
                        <tr>
                            <td>${dato.getIdCli()}</td>
                            <td>${dato.getDocCli()}</td>
                            <td>${dato.getNumCli()}</td>
                            <td>${dato.getNomCli()}</td>
                            <td>${dato.getCorreoCli()}</td>
                            <td>${dato.getPassCli()}</td>
                            <td>${dato.getNumSoli()}</td>
                            <td>${dato.getNumBol()}</td>
                            <td>${dato.getNumFac()}</td>
                            <td>${dato.getEstadoCli()}</td>
                            <td>
                                <button class="btn btn-warning" onclick="PasarDatosActualizar(${dato.getIdCli()}, '${dato.getDocCli()}', '${dato.getNumCli()}',
                                                '${dato.getNomCli()}', '${dato.getCorreoCli()}', '${dato.getPassCli()}', '${dato.getEstadoCli()}')">
                                    <i class="fa-solid fa-pen-to-square fa-xl" style="color: #050505;"></i>
                                </button>
                                <button class="btn btn-danger" onclick="return PasarDatosEliminar(${dato.getIdCli()}, '${dato.getNomCli()}',
                                        ${dato.getNumSoli()},${dato.getNumBol()},${dato.getNumFac()})">
                                    <i class="fa-solid fa-trash-can fa-xl" style="color: #ffffff;"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
        </div>    

        <!-- Modal para agregar -->
        <div class="modal fade" id="modalAgregar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlCliente" method="POST" onsubmit="return ValidarDatosAgregar()">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Nuevo Cliente :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">::: Seleccionar documento :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtDoc" id="txtDoc">
                                <option value="">::: Seleccionar :::</option>
                                <option value="DNI">DNI</option>
                                <option value="RUC">RUC</option>
                            </select>

                            <label class="form-label">Número de documento<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtNum" id="txtNum">

                            <label class="form-label">Nombre / Razón social<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtNom" id="txtNom">

                            <label class="form-label">Correo electrónico<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtCorreo" id="txtCorreo">

                            <label class="form-label">Contraseña<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtPass" id="txtPass">

                            <label class="form-label">::: Seleccionar estado :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtEstado" id="txtEstado">
                                <option value="">::: Seleccionar :::</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                            </select>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" name="accion" value="Agregar" class="btn btn-success">Agregar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal para actualizar -->
        <div class="modal fade" id="modalActualizar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlCliente" method="POST" onsubmit="return ValidarDatosActualizar()">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Actualizar Cliente :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Código<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="lblCodigo" id="lblCodigo" readonly="">

                            <label class="form-label">::: Seleccionar documento :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblDoc" id="lblDoc">
                                <option value="">::: Seleccionar :::</option>
                                <option value="DNI">DNI</option>
                                <option value="RUC">RUC</option>
                            </select>

                            <label class="form-label">Número de documento<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblNum" id="lblNum">

                            <label class="form-label">Nombre / Razón social<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblNom" id="lblNom">

                            <label class="form-label">Correo electrónico<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblCorreo" id="lblCorreo">

                            <label class="form-label">Contraseña<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblPass" id="lblPass">

                            <label class="form-label">::: Seleccionar estado :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblEstado" id="lblEstado">
                                <option value="">::: Seleccionar :::</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                            </select>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" name="accion" value="Actualizar" class="btn btn-warning">Actualizar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal para eliminar -->
        <div class="modal fade" id="modalEliminar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlCliente" method="POST">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Eliminar Cliente :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>¿Estás seguro que deseas eliminar al cliente <span id="lblTexto"></span>?</p>
                            <input type="hidden" name="id" id="id">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-danger" name="accion" value="Eliminar">Eliminar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
