function ValidarDatosAgregar() {

    var nombre = $('#txtNom').val();
    var stock = $('#txtStock').val();
    var precio = $('#txtPrecio').val();
    var estado = $('#txtEstado').val();
    var categoria = $('#txtCategoria').val();
    var proveedor = $('#txtProveedor').val();
    var imagen = $('#txtImagen').val();

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre del producto vacío");
        return false;
    }

    if (stock === "") {
        fnToast("warning", "Stock vacío");
        return false;
    }

    if (parseInt(stock) < 0) {
        fnToast("warning", "Stock negativo");
        return false;
    }

    if (precio === "") {
        fnToast("warning", "Precio vacío");
        return false;
    }

    if (parseFloat(precio) < 0) {
        fnToast("warning", "Precio negativo");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    if (categoria === "") {
        fnToast("warning", "::: Seleccionar categoría :::");
        return false;
    }

    if (proveedor === "") {
        fnToast("warning", "::: Seleccionar proveedor :::");
        return false;
    }

    if (imagen === "") {
        fnToast("warning", "::: Seleccionar imagen :::");
        return false;
    }

    return true;
}

function PasarDatosActualizar(codigo, nombre, stock, precio, estado, idcat, idprov, imagen) {

    $('#lblCodigo').val(codigo);
    $('#lblNom').val(nombre);
    $('#lblStock').val(stock);
    $('#lblPrecio').val(precio);
    $('#lblEstado').val(estado);
    $('#lblCategoria').val(idcat);
    $('#lblProveedor').val(idprov);
    $('#lblImagen').html(imagen);

    $('#modalActualizar').modal('show');

}

function ValidarDatosActualizar() {

    var nombre = $('#lblNom').val();
    var stock = $('#lblStock').val();
    var precio = $('#lblPrecio').val();
    var estado = $('#lblEstado').val();
    var categoria = $('#lblCategoria').val();
    var proveedor = $('#lblProveedor').val();
    var imagen = $('#lblImagen').val();

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre del producto vacío");
        return false;
    }

    if (stock === "") {
        fnToast("warning", "Stock vacío");
        return false;
    }

    if (parseInt(stock) < 0) {
        fnToast("warning", "Stock negativo");
        return false;
    }

    if (precio === "") {
        fnToast("warning", "Precio vacío");
        return false;
    }

    if (parseFloat(precio) < 0) {
        fnToast("warning", "Precio negativo");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    if (categoria === "") {
        fnToast("warning", "::: Seleccionar categoría :::");
        return false;
    }

    if (proveedor === "") {
        fnToast("warning", "::: Seleccionar proveedor :::");
        return false;
    }

    if (imagen === "") {
        fnToast("warning", "::: Seleccionar imagen :::");
        return false;
    }

    return true;

}

function PasarDatosEliminar(codigo,nombre){
    
    $('#id').val(codigo);
    $('#lblTexto').html(nombre);
    
    $('#modalEliminar').modal('show');
}

