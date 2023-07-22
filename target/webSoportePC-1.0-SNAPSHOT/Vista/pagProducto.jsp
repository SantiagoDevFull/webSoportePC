<%-- 
    Document   : pagProducto
    Created on : 19 jul. 2023, 1:39:08
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
            <script src="js/Producto.js"></script>
        <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

            <div class="container-fluid mt-3">

                <h4>::: Gestión de Productos :::</h4>
                <hr>

                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalAgregar">
                    <i class="fa-solid fa-plus fa-xl" style="color: #fcfcfc;"></i> Nuevo
                </button><br><br>

                <table id="tabla" class="text-center table table-striped" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">Código</th>
                            <th class="text-center">Descripción</th>
                            <th class="text-center">Stock</th>
                            <th class="text-center">Precio</th>
                            <th class="text-center">Estado</th>
                            <th class="text-center">Categoría</th>
                            <th class="text-center">Proveedor</th>
                            <th class="text-center">Imagen</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dato" items="${ListarProducto}">
                        <tr>
                            <td>${dato.getIdPro()}</td>
                            <td>${dato.getNomPro()}</td>
                            <td>${dato.getStockPro()}</td>
                            <td>${dato.getPrecioPro()}</td>
                            <td>${dato.getEstadoPro()}</td>
                            <td>${dato.getCategoria().getNomCat()}</td>
                            <td>${dato.getProveedor().getNomProv()}</td>
                            <td><img src="http://localhost/img/${dato.getFotoPro()}" class="img-fluid" width="150" height="120"></td>
                            <td>
                                <button class="btn btn-warning" onclick="PasarDatosActualizar(${dato.getIdPro()}, '${dato.getNomPro()}',
                                        ${dato.getStockPro()},${dato.getPrecioPro()}, '${dato.getEstadoPro()}',${dato.getCategoria().getIdCat()},
                                        ${dato.getProveedor().getIdProv()}), '${dato.getFotoPro()}'">
                                    <i class="fa-solid fa-pen-to-square fa-xl" style="color: #050505;"></i>
                                </button>
                                <button class="btn btn-danger" onclick="return PasarDatosEliminar(${dato.getIdPro()}, '${dato.getNomPro()}')">
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
                    <form action="ControlProducto" method="POST" onsubmit="return ValidarDatosAgregar()" enctype="multipart/form-data">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Nuevo Producto :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Producto<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="txtNom" id="txtNom">

                            <label class="form-label">Stock<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="txtStock" id="txtStock">

                            <label class="form-label">Precio<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" step="0.01" name="txtPrecio" id="txtPrecio">

                            <label class="form-label">::: Seleccionar estado :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtEstado" id="txtEstado">
                                <option value="">::: Seleccionar :::</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                            </select>

                            <label class="form-label">::: Seleccionar categoría :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtCategoria" id="txtCategoria">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarCategoria}">
                                    <option value="${dato.getIdCat()}">${dato.getNomCat()}</option>
                                </c:forEach>
                            </select>

                            <label class="form-label">::: Seleccionar proveedor :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="txtProveedor" id="txtProveedor">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarProveedor}">
                                    <option value="${dato.getIdProv()}">${dato.getNomProv()}</option>
                                </c:forEach>
                            </select>

                            <label class="form-label">Imagen<span style="color: red"> *</span></label>
                            <input class="form-control" type="file" name="txtImagen" id="txtImagen">

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
                    <form action="ControlProducto" method="POST" onsubmit="return ValidarDatosActualizar()" enctype="multipart/form-data">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Actualizar Producto :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <label class="form-label">Código<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="lblCodigo" id="lblCodigo" readonly="">

                            <label class="form-label">Producto<span style="color: red"> *</span></label>
                            <input class="form-control" type="text" name="lblNom" id="lblNom">

                            <label class="form-label">Stock<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" name="lblStock" id="lblStock">

                            <label class="form-label">Precio<span style="color: red"> *</span></label>
                            <input class="form-control" type="number" step="0.01" name="lblPrecio" id="lblPrecio">

                            <label class="form-label">::: Seleccionar estado :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblEstado" id="lblEstado">
                                <option value="">::: Seleccionar :::</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                            </select>

                            <label class="form-label">::: Seleccionar categoría :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblCategoria" id="lblCategoria">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarCategoria}">
                                    <option value="${dato.getIdCat()}">${dato.getNomCat()}</option>
                                </c:forEach>
                            </select>

                            <label class="form-label">::: Seleccionar proveedor :::<span style="color: red"> *</span></label>
                            <select class="form-select" name="lblProveedor" id="lblProveedor">
                                <option value="">::: Seleccionar :::</option>
                                <c:forEach var="dato" items="${ListarProveedor}">
                                    <option value="${dato.getIdProv()}">${dato.getNomProv()}</option>
                                </c:forEach>
                            </select>

                            <label class="form-label">Imagen<span style="color: red"> *</span></label>
                            <input class="form-control" type="file" name="lblImagen" id="lblImagen">

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
                    <form action="ControlProducto" method="POST">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Eliminar Producto :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>¿Estás seguro que deseas eliminar el producto <span id="lblTexto"></span>?</p>
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
