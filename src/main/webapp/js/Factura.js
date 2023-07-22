function PasarDatosCliente(codigo, numero, nombre) {

    $('#txtIDcli').val(codigo);
    $('#txtCliente').val(numero + ' - ' + nombre);

    $('#modalBuscarCliente').modal('hide');

}

function PasarDatosProducto(codigo, nombre, stock, precio) {

    $('#txtIDpro').val(codigo);
    $('#txtNomPro').val(nombre);
    $('#txtStockPro').val(stock);
    $('#txtPrecioPro').val(precio);

    $('#modalBuscarProducto').modal('hide');

}

function ValidarCarrito() {

    var nombre = $('#txtNomPro').val();
    var stock = $('#txtStockPro').val();
    var cantidad = $('#txtCantidadPro').val();

    if (nombre.trim().length <= 0) {
        fnToast("warning", "::: Seleccionar producto :::");
        return false;
    }

    if (cantidad === "") {
        fnToast("warning", "Cantidad vacía");
        return false;
    }

    if (parseInt(cantidad) <= 0) {
        fnToast("error", "Cantidad inválida");
        return false;
    }

    if (parseInt(cantidad) > parseInt(stock)) {
        fnToast("error", "Stock insuficiente");
        return false;
    }

    return true;

}

function CargarCarrito() {

    if (!ValidarCarrito()) {
        return;
    }

    var idPro = $('#txtIDpro').val();
    var nombre = $('#txtNomPro').val();
    var precio = $('#txtPrecioPro').val();
    var cantidad = $('#txtCantidadPro').val();

    $('#carrito').load("ControlFactura?accion=AgregarProducto&txtIDpro=" + idPro + "&txtNomPro=" + nombre + "&txtPrecioPro=" + precio + "&txtCantidadPro=" + cantidad,
            function () {
                $('#tabla3').DataTable(); // Actualizar tabla de detalles de la boleta
                $('#producto').load("ControlFactura?accion=ActualizarProducto"); // Recargar lista de productos
            });

    $('#txtIDpro').val("");
    $('#txtNomPro').val("");
    $('#txtStockPro').val("");
    $('#txtPrecioPro').val("");
    $('#txtCantidadPro').val("");

}

function Limpiar() {

    $('#txtIDcli').val("");
    $('#txtCliente').val("");
    
    $('#txtIDpro').val("");
    $('#txtNomPro').val("");
    $('#txtStockPro').val("");
    $('#txtPrecioPro').val("");
    $('#txtCantidadPro').val("");

    $('#carrito').load('ControlFactura?accion=RehacerFactura', function () {
        $('#tabla3').DataTable();
    });

    $('#producto').load('ControlFactura?accion=RehacerFactura', function () {
        $('#tabla2').DataTable();
        $('#tabla3').DataTable(); // Actualizar tabla de detalles de la boleta
        $('#producto').load("ControlFactura?accion=ActualizarProducto"); // Recargar lista de productos
    });

}

function ValidarProceso() {
    var cliente = $('#txtCliente').val();

    if (cliente.trim().length <= 0) {
        fnToast("warning", "::: Seleccionar cliente :::");
        return false;
    }

    var detalleSize;
    $.ajax({
        type: 'GET',
        url: 'ControlFactura?accion=DetalleSize',
        async: false,
        success: function (data) {
            detalleSize = parseInt(data);
        }
    });

    if (detalleSize === 0) {
        fnToast("warning", "::: La lista de detalles está vacía :::");
        return false;
    }

    return true;
}

function CargarDetalle(id){
    
    $('#detalle').load('ControlFactura?accion=ListarDetalle&id='+id);
    
    $('#modalDetalle').modal('show');
}


