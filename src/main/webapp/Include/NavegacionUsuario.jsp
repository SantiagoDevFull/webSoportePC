<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">

        <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="#">R y J Computer S.A.C.</a>
        <div class="offcanvas offcanvas-start text-bg-dark" tabindex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">Bienvenido (a)</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>

            <div class="offcanvas-body">
                <hr>
                <div class="text-center">

                    <i class="fa-solid fa-user" style="font-size: 6em"></i><br>
                    <br>
                    ${sesionUsuario.getNomUsu()}<br>
                    ${sesionUsuario.getRol().getNomRol()}
                    <br><br>
                    <form action="ControlCliente" method="POST">
                        <button class="btn btn-danger" type="submit" name="accion" value="CerrarSesionUsuario">Cerrar sesión</button>
                    </form>

                </div>
                <hr>
                <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                    <li class="nav-item">
                        <a class="nav-link" href="ControlUsuario?accion=Inicio"><i class="fa-solid fa-house fa-2xl" style="color: #26e600;"></i> Inicio</a>
                    </li>
                    <br>

                    <c:if test="${sesionUsuario.getRol().getNomRol()=='Administrador'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fa-solid fa-gear fa-2xl" style="color: #0259ed"></i> Gestión
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark">
                                <li><a class="dropdown-item" href="ControlRol?accion=Listar">Rol de Empleados</a></li>
                                <li><a class="dropdown-item" href="ControlUsuario?accion=Listar">Empleados</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="ControlTipo?accion=Listar">Tipo de Solicitudes</a></li>
                                <li><a class="dropdown-item" href="ControlSolicitud?accion=Listar">Solicitudes</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="ControlCliente?accion=Listar">Clientes</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="ControlCategoria?accion=Listar">Categoría de Productos</a></li>
                                <li><a class="dropdown-item" href="ControlProveedor?accion=Listar">Proveedor de Productos</a></li>
                                <li><a class="dropdown-item" href="ControlProducto?accion=Listar">Productos</a></li>
                            </ul>
                        </li>
                        <br>
                    </c:if>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa-solid fa-cart-shopping fa-2xl" style="color: #f410ec;"></i> Venta
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark">
                            <li><a class="dropdown-item" href="ControlBoleta?accion=NuevaBoleta">Nueva Boleta</a></li>
                            <li><a class="dropdown-item" href="ControlFactura?accion=NuevaFactura">Nueva Factura</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="ControlBoleta?accion=ListarBoleta">Consultar Boleta</a></li>
                            <li><a class="dropdown-item" href="ControlFactura?accion=ListarFactura">Consultar Factura</a></li>
                        </ul>
                    </li>
                    <br>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa-solid fa-file-pdf fa-2xl" style="color: #ff0000;"></i> Reportes
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark">
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=1">Rol de Empleados</a></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=2">Empleados</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=3">Tipo de Solicitudes</a></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=4">Solicitudes</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=5">Clientes</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=6">Categoría de Productos</a></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=7">Proveedor de Productos</a></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=8">Productos</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=9">Boletas</a></li>
                            <li><a class="dropdown-item" href="ControlReporte?accion=Descargar&tipo=10">Facturas</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <button type="button" class="dropdown-item btn text-light" data-bs-toggle="modal" data-bs-target="#modalBoleta">
                                    Boleta por Código
                                </button>
                            </li>
                            <li>
                                <button type="button" class="dropdown-item btn text-light" data-bs-toggle="modal" data-bs-target="#modalFactura">
                                    Factura por Código
                                </button>
                            </li>
                        </ul>
                    </li>

                </ul>
            </div>
        </div>
    </div>
</nav>

<div class="modal fade" id="modalBoleta" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="ControlReporte" method="GET">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Boleta por Código :::</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <label class="form-label">Código de boleta</label>
                    <input class="form-control" type="numer" name="txtIDbol" id="txtIDbol">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" name="accion" value="DescargarBoleta" class="btn btn-primary">Descargar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="modalFactura" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="ControlReporte" method="GET">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Factura por Código :::</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <label class="form-label">Código de factura</label>
                    <input class="form-control" type="numer" name="txtIDfact" id="txtIDfact">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" name="accion" value="DescargarFactura" class="btn btn-primary">Descargar</button>
                </div>
            </form>
        </div>
    </div>
</div>


<%

    String mensaje = "";

    if (request.getSession().getAttribute("success") != null) {
        mensaje = (String) request.getSession().getAttribute("success");
        out.print("<script>fnToast('success','" + mensaje + "')</script>");
    }

    if (request.getSession().getAttribute("error") != null) {
        mensaje = (String) request.getSession().getAttribute("error");
        out.print("<script>fnToast('error','" + mensaje + "')</script>");
    }

    if (request.getSession().getAttribute("warning") != null) {
        mensaje = (String) request.getSession().getAttribute("warning");
        out.print("<script>fnToast('warning','" + mensaje + "')</script>");
    }

    if (request.getSession().getAttribute("info") != null) {
        mensaje = (String) request.getSession().getAttribute("info");
        out.print("<script>fnToast('info','" + mensaje + "')</script>");
    }

    request.getSession().removeAttribute("success");
    request.getSession().removeAttribute("error");
    request.getSession().removeAttribute("info");
    request.getSession().removeAttribute("warning");

%>