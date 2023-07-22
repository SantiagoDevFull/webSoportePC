<%-- 
    Document   : pagProductosFactura
    Created on : 20 jul. 2023, 0:40:48
    Author     : usuario
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="tabla2" class="table table-striped table-bordered text-center" >
    <thead class="bg-warning">
        <tr>
            <th class="text-center">Seleccionar</th>
            <th class="text-center">Código</th>
            <th class="text-center">Producto</th>
            <th class="text-center">Stock</th>
            <th class="text-center">Precio</th>
            <th class="text-center">Estado</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="dato" items="${ListarProducto}">
            <tr>
                <td>
                    <button class="btn" onclick="PasarDatosProducto(${dato.getIdPro()}, '${dato.getNomPro()}',${dato.getStockPro()},
                            ${dato.getPrecioPro()})">
                        <i class="fa-sharp fa-solid fa-square-check fa-2xl" style="color: #da02de;"></i>
                    </button>
                </td>
                <td>${dato.getIdPro()}</td>
                <td>${dato.getNomPro()}</td>
                <td>${dato.getStockPro()}</td>
                <td>${dato.getPrecioPro()}</td>
                <td>${dato.getEstadoPro()}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
