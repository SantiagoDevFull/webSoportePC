<%-- 
    Document   : pagProveedor
    Created on : 19 jul. 2023, 0:39:52
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
            <script src="js/Proveedor.js"></script>
        <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4>::: Gestión de Proveedores de Productos :::</h4>
                <hr>

                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalAgregar">
                    <i class="fa-solid fa-plus fa-xl" style="color: #fcfcfc;"></i> Nuevo
                </button><br><br>

                <table id="tabla" class="text-center table table-striped" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">Código</th>
                            <th class="text-center">RUC</th>
                            <th class="text-center">Proveedor</th>
                            <th class="text-center">Dirección</th>
                            <th class="text-center">Teléfono</th>
                            <th class="text-center">Estado</th>
                            <th class="text-center">#Productos</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarProveedor}">
                        <tr>
                            <td>${dato.getIdProv()}</td>
                            <td>${dato.getRucProv()}</td>
                            <td>${dato.getNomProv()}</td>
                            <td>${dato.getDireProv()}</td>
                            <td>${dato.getTelProv()}</td>
                            <td>${dato.getEstadoProv()}</td>
                            <td>${dato.getNumProductos()}</td>
                            <td>
                                <button class="btn btn-warning" onclick="PasarDatosActualizar(${dato.getIdProv()}, '${dato.getRucProv()}',
                                                '${dato.getNomProv()}', '${dato.getDireProv()}', '${dato.getTelProv()}', '${dato.getEstadoProv()}')">
                                    <i class="fa-solid fa-pen-to-square fa-xl" style="color: #050505;"></i>
                                </button>
                                <button class="btn btn-danger" onclick="return PasarDatosEliminar(${dato.getIdProv()}, '${dato.getNomProv()}',${dato.getNumProductos()})">
                                    <i class="fa-solid fa-trash-can fa-xl" style="color: #ffffff;"></i>
                                </button>
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
                    <form action="ControlProveedor" method="POST" onsubmit="return ValidarDatosAgregar()">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Nuevo Proveedor :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">RUC<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtRuc" id="txtRuc">

                            <label class="form-label">Proveedor<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtNombre" id="txtNombre">

                            <label class="form-label">Dirección<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtDire" id="txtDire">

                            <label class="form-label">Teléfono<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtTel" id="txtTel">

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

        <!-- Modal para actualizar-->
        <div class="modal fade" id="modalActualizar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlProveedor" method="POST" onsubmit="return ValidarDatosActualizar()">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Actualizar Proveedor :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Código<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="lblCodigo" id="lblCodigo" readonly="">

                            <label class="form-label">RUC<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblRuc" id="lblRuc">

                            <label class="form-label">Proveedor<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblNombre" id="lblNombre">

                            <label class="form-label">Dirección<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblDire" id="lblDire">

                            <label class="form-label">Teléfono<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblTel" id="lblTel">

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

        <div class="modal fade" id="modalEliminar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="ControlProveedor" method="POST">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Eliminar Proveedor :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>¿Estás seguro que deseas eliminar al proveedor <span id="lblTexto"></span>?</p>
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
