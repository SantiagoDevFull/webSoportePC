<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/Cliente.js"></script>
<nav class="navbar navbar-expand-lg bg-warning p-3">
    <div class="container-fluid">
        <div class="nav-item">
            <a class="nav-link" aria-current="page" href="ControlCliente?accion=NuevoIndex">
                <img src="http://localhost/img/logoEmpresa.jpg" class="rounded img-fluid" width="8%"></i> 
                <span style="font-size: 40px; font-family: Bodoni MT Black">R y J Computer S.A.C.</span> </a>
        </div>
        <div class="collapse navbar-collapse container-fluid" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 d-flex mx-auto"> 

                <c:if test="${sesionCliente!=null}">
                    <li class="nav-item">
                        <a href="ControlPresupuesto?accion=Nueva" class="nav-link btn btn-info">
                            <i class="fa-solid fa-cart-shopping fa-2xl" style="color: #00040a;"></i> Carrito
                        </a>
                    </li>
                </c:if>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle btn btn-info" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-bars fa-2xl" style="color: #000000;"></i> Categoría de Productos
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="ControlProducto?accion=ListarSoftware">Software</a></li>
                        <li><a class="dropdown-item" href="ControlProducto?accion=ListarHardware">Hardware</a></li>
                    </ul>
                </li>
                <c:if test="${sesionCliente==null}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle btn btn-info" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa-solid fa-user fa-2xl" style="color: #000000;"></i> Hola, Inicia sesión
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#modalIniciarsesion">Iniciar sesión</a></li>
                            <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#modalRegistrate">Regístrate</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sesionCliente!=null}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa-solid fa-user fa-2xl" style="color: #000000;"></i> Mi perfil
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="text-center">
                                    <br>
                                    <i class="fa-solid fa-user fa-2xl" style="color: #000000;"></i><br><br>
                                    ${sesionCliente.getNomCli()}

                                </div>
                            </li>
                            <hr>
                            <li><a class="dropdown-item bg-danger btn text-light" href="ControlCliente?accion=CerrarSesion">Cerrar sesión</a></li>
                            <li><a class="dropdown-item" href="ControlCliente?accion=ListarSolicitud&idCliente=${sesionCliente.getIdCli()}">Mis solicitudes</a></li>
                            <li><a class="dropdown-item" href="ControlCliente?accion=ListarPresupuesto&idCli=${sesionCliente.getIdCli()}">Mis presupuestos</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<!-- Modal iniciar sesión-->
<div class="modal fade" id="modalIniciarsesion" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="ControlCliente" method="POST" class="form" onsubmit="return ValidarIniciarSesion();">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Iniciar sesión :::</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <div class="text-center">
                        <i class="fa-solid fa-user" style="color: #0a0a0a;  font-size:  8em"></i>
                    </div>
                    <br>

                    <label class="form-label">Correo electrónico<span style="color: red"> *</span></label>
                    <input class="form-control" type="text" name="txtCorreo" placeholder="Correo electrónico" id="txtCorreo">

                    <label class="form-label">Contraseña<span style="color: red"> *</span></label>
                    <input class="form-control" type="password" name="txtPass" placeholder="Contraseña" id="txtPass">

                    <label class="form-label">::: Por favor, seleccione una opción :::<span style="color: red"> *</span></label>
                    <select class="form-select" name="tipoPer" id="tipoPer">
                        <option value="0">::: Seleccionar :::</option>
                        <option value="1">Cliente</option>
                        <option value="2">Empleado</option>
                    </select>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary" name="accion" value="IniciarSesion">Acceder</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal registrarme-->
<div class="modal fade" id="modalRegistrate" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="ControlCliente" method="GET" onsubmit="return ValidarRegistro()" class="form">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">::: Regístrate :::</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <label class="form-label">::: Por favor, seleccione tipo de documento :::<span style="color: red"> *</span></label>
                    <select class="form-select"  name="lblDoc" id="lblDoc">
                        <option value="">::: Seleccionar :::</option>
                        <option value="DNI">DNI</option>
                        <option value="RUC">RUC</option>
                    </select>

                    <label class="form-label">Número de documento<span style="color: red"> *</span></label>
                    <input class="form-control" type="text" name="lblNum" id="lblNum">

                    <label class="form-label">Nombre completo / Razón social<span style="color: red"> *</span></label>
                    <input class="form-control" type="text" name="lblNom" id="lblNom">

                    <label class="form-label">Correo electrónico<span style="color: red"> *</span></label>
                    <input class="form-control" type="text" name="lblCorreo" id="lblCorreo">

                    <label class="form-label">Contraseña<span style="color: red"> *</span></label>
                    <input class="form-control" type="password" name="lblPass" id="lblPass">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" name="accion" value="Registrarme" class="btn btn-primary">Registrarme</button>
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

