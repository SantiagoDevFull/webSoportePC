<%-- 
    Document   : pagVerBdetalle
    Created on : 20 jul. 2023, 9:15:36
    Author     : usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<table id="tabla" class="mt-3 table table-bordered text-center bordered" >

    <thead class="bg-info">
        <tr>
            <th class="col-2 text-center">Cantidad</th>
            <th class="col-5 text-center">Producto</th>
            <th class="col-2 text-center">Precio</th>
            <th class="col-3 text-center">Importe</th>
        </tr>
    </thead>

    <tbody>
        <c:forEach var="x" items="${ListarDetalle}">
            <tr>
                <td>${x.getCantidad()}</td>
                <td>${x.getProducto().getNomPro()}</td>
                <td>${x.getPrecio()}</td>
                <td>${x.Importe()}</td>
            </tr>
        </c:forEach>
    </tbody>

    <tfoot>
        <tr>
            <td></td>
            <td></td>
            <td class="text-center text-primary fw-bold">Total</td>
            <td class="text-center text-primary fw-bold">${total}</td>
        </tr>
    </tfoot>
</table>
