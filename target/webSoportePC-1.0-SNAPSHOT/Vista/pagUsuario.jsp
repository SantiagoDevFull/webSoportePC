<%-- 
    Document   : pagUsuario
    Created on : 18 jul. 2023, 1:18:22
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
            <script src="js/Usuario.js"></script>
        <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4>::: Gestión de Empleados :::</h4>
                <hr>

                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalAgregar">
                    <i class="fa-solid fa-plus fa-xl" style="color: #fcfcfc;"></i> Nuevo
                </button><br><br>

                <table id="tabla" class="text-center table table-striped" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">Código</th>
                            <th class="text-center">Correo electrónico</th>
                            <th class="text-center">Contraseña</th>
                            <th class="text-center">Nombre completo</th>
                            <th class="text-center">Estado</th>
                            <th class="text-center">Rol</th>
                            <th class="text-center">#Boletas</th>
                            <th class="text-center">#Facturas</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarUsuario}">
                        <tr>
                            <td>${dato.getIdUsu()}</td>
                            <td>${dato.getCorreoUsu()}</td>
                            <td>${dato.getPassUsu()}</td>
                            <td>${dato.getNomUsu()}</td>
                            <td>${dato.getEstadoUsu()}</td>
                            <td>${dato.getRol().getNomRol()}</td>
                            <td>${dato.getNumBol()}</td>
                            <td>${dato.getNumFac()}</td>
                            <td>
                                <button class="btn btn-warning" onclick="PasarDatosActualizar(${dato.getIdUsu()}, '${dato.getCorreoUsu()}',
                                                '${dato.getPassUsu()}', '${dato.getNomUsu()}', '${dato.getEstadoUsu()}',${dato.getRol().getIdRol()})">
                                    <i class="fa-solid fa-pen-to-square fa-xl" style="color: #050505;"></i>
                                </button>

                                <c:if test="${sesionUsuario.getIdUsu()==dato.getIdUsu()}">   
                                    <button disabled="" class="btn btn-danger" onclick="return PasarDatosEliminar(${dato.getIdUsu()}, '${dato.getNomUsu()}',${dato.getNumBol()},${dato.getNumFac()})">
                                        <i class="fa-solid fa-trash-can fa-xl" style="color: #ffffff;"></i>
                                    </button>
                                </c:if>
                                <c:if test="${sesionUsuario.getIdUsu()!=dato.getIdUsu()}">   
                                    <button class="btn btn-danger" onclick="return PasarDatosEliminar(${dato.getIdUsu()}, '${dato.getNomUsu()}',${dato.getNumBol()},${dato.getNumFac()})">
                                        <i class="fa-solid fa-trash-can fa-xl" style="color: #ffffff;"></i>
                                    </button>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>

        <!-- Modal para agregar-->
        <div class="modal fade" id="modalAgregar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlUsuario" method="POST" onsubmit="return ValidarDatosAgregar();">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Nuevo Empleado :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Correo electrónico<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtCorreo" id="txtCorreo">

                            <label class="form-label">Contraseña<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtPass" id="txtPass">

                            <label class="form-label">Nombre completo<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtNombre" id="txtNombre">

                            <label class="form-label">::: Seleccionar estado :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtEstado" id="txtEstado">
                                <option value="">::: Seleccionar :::</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                            </select>

                            <label class="form-label">::: Seleccionar Rol :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtRol" id="txtRol">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarRol}">
                                    <option value="${dato.getIdRol()}">${dato.getNomRol()}</option>
                                </c:forEach>
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

        <!-- Modal para actualizar-->
        <div class="modal fade" id="modalActualizar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlUsuario" method="POST" onsubmit="return ValidarDatosActualizar();">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Actualizar Empleado :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Código<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="lblCodigo" id="lblCodigo" readonly="">

                            <label class="form-label">Correo electrónico<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblCorreo" id="lblCorreo">

                            <label class="form-label">Contraseña<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblPass" id="lblPass">

                            <label class="form-label">Nombre completo<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblNombre" id="lblNombre">

                            <label class="form-label">::: Seleccionar estado :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblEstado" id="lblEstado">
                                <option value="">::: Seleccionar :::</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                            </select>

                            <label class="form-label">::: Seleccionar Rol :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblRol" id="lblRol">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarRol}">
                                    <option value="${dato.getIdRol()}">${dato.getNomRol()}</option>
                                </c:forEach>
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
                    <form action="ControlUsuario" method="POST">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Eliminar Rol :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>¿Estás seguro que deseas eliminar al empleado <span id="lblTexto"></span>?</p>
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
