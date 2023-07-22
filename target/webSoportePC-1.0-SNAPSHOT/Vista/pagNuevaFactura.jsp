<%-- 
    Document   : pagNuevaFactura
    Created on : 20 jul. 2023, 0:38:32
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
            <script src="js/Factura.js"></script>
        <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

            <br>
            <div class="container mt-3">

                <h4>::: Nueva Factura N° ${txtCodigoFact} :::</h4>
            <hr>

            <form action="ControlFactura" method="GET" onsubmit="return ValidarProceso()">
                <input type="hidden" name="txtIDfactura" value="${txtCodigoFact}">
                <div class="col-12 d-flex row mt-4">

                    <div class="col-6">
                        <label class="form-label">Cliente<span style="color: red"> *</span></label>

                        <div class="input-group">
                            <input type="hidden" name="txtIDcli" id="txtIDcli">
                            <input type="text" class="form-control" readonly="" id="txtCliente">
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalBuscarCliente">
                                Buscar
                            </button>
                        </div>
                    </div>

                    <div class="col-6">
                        <label class="form-label">Responsable de la Atención<span style="color: red"> *</span></label>
                        <input type="hidden" name="txtIDusuario" id="txtIDusuario" value="${sesionUsuario.getIdUsu()}">
                        <input type="text" class="form-control" readonly="" id="txtUsuario" 
                               value="${sesionUsuario.getNomUsu()} | ${sesionUsuario.getRol().getNomRol()}">
                    </div>

                </div>

                <div class="d-flex col-12 row mt-4">

                    <div class="col-3">
                        <label class="form-label">Producto<span style="color: red"> *</span></label>
                        <div class="input-group">
                            <input type="hidden" name="txtIDpro" id="txtIDpro">
                            <input type="text" class="form-control" readonly="" name="txtNomPro" id="txtNomPro">
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalBuscarProducto">
                                Buscar
                            </button>
                        </div>
                    </div> 

                    <div class="col-3">
                        <label class="form-label">Stock<span style="color: red"> *</span></label>
                        <input type="number" class="form-control" readonly="" name="txtStockPro" id="txtStockPro">
                    </div> 

                    <div class="col-3">
                        <label class="form-label">Precio<span style="color: red"> *</span></label>
                        <input type="number" step="0.01" readonly="" class="form-control" name="txtPrecioPro"  id="txtPrecioPro">
                    </div> 

                    <div class="col-3">
                        <label class="form-label">Cantidad<span style="color: red"> *</span></label>
                        <input type="number" class="form-control" name="txtCantidadPro" id="txtCantidadPro">
                    </div> 

                </div>

                <div class="mt-3">
                    <div class="d-flex justify-content-end gap-2">
                        <button type="button" onclick="CargarCarrito()" class="btn btn-success" data-bs-dismiss="modal">
                            <i class="fa-solid fa-check"></i>&nbsp;Agregar
                        </button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="Limpiar()">
                            <i class="fa-sharp fa-solid fa-spinner"></i>&nbsp;Rehacer Compra
                        </button>
                        <button type="submit" name="accion" value="ProcesarFactura" class="btn btn-primary" data-bs-dismiss="modal">
                            <i class="fa-solid fa-cart-plus"></i>&nbsp;Realizar Compra
                        </button>
                    </div>
                </div>

                <div id="carrito" class="mt-3">
                    <jsp:include page="../Vista/pagDetalleFactura.jsp"></jsp:include>
                    </div>

                </form>

            </div>

            <!-- Modal para buscar cliente-->
            <div class="modal fade" id="modalBuscarCliente" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Buscar Cliente :::</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <table id="tabla" class="table table-striped table-bordered text-center" >
                                <thead class="bg-warning">
                                    <tr>
                                        <th class="text-center">Seleccionar</th>
                                        <th class="text-center">Código</th>
                                        <th class="text-center">RUC</th>
                                        <th class="text-center">Nombre</th>
                                        <th class="text-center">Correo</th>
                                        <th class="text-center">Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="dato" items="${ListarCliente}">
                                    <tr>
                                        <td>
                                            <button class="btn" onclick="PasarDatosCliente(${dato.getIdCli()}, '${dato.getNumCli()}', '${dato.getNomCli()}')">
                                                <i class="fa-sharp fa-solid fa-square-check fa-2xl" style="color: #da02de;"></i>
                                            </button>
                                        </td>
                                        <td>${dato.getIdCli()}</td>
                                        <td>${dato.getNumCli()}</td>
                                        <td>${dato.getNomCli()}</td>
                                        <td>${dato.getCorreoCli()}</td>
                                        <td>${dato.getEstadoCli()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>     

        <!-- Modal para buscar producto-->
        <div class="modal fade" id="modalBuscarProducto" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Buscar Producto :::</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="producto">
                        <jsp:include page="../Vista/pagProductosFactura.jsp"></jsp:include>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div> 

    </body>
</html>
