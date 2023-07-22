<%-- 
    Document   : pagSoporte
    Created on : 17 jul. 2023, 14:08:42
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="http://localhost/img/iconoLogoEmpresa.ico">
    <c:if test="${sesionUsuario==null}">
        <c:redirect url="index.jsp"></c:redirect>
    </c:if>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>R y J Computer S.A.C.</title>

    <jsp:include page="../Include/RecursosCSS.jsp"></jsp:include>

    </head>

    <body>

    <jsp:include page="../Include/RecursosJS.jsp"></jsp:include>

    <jsp:include page="../Include/NavegacionUsuario.jsp"></jsp:include>

    <div class="container-fluid">
        <div class="row">
            <div class="col-12 p-0">
                <img src="http://localhost/img/soporte.jpg" class="img-fluid " height="100%">
            </div>
        </div>
    </div>

</body>
</html>
