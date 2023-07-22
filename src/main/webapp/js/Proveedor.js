function ValidarDatosAgregar() {

    var ruc = $('#txtRuc').val();
    var nombre = $('#txtNombre').val();
    var dire = $('#txtDire').val();
    var tel = $('#txtTel').val();
    var estado = $('#txtEstado').val();

    if (ruc.trim().length != 11) {
        fnToast("warning", "El RUC debe contener 11 dígitos");
        return false;
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre vacío");
        return false;
    }

    if (dire.trim().length <= 0) {
        fnToast("warning", "Dirección vacío");
        return false;
    }

    if (tel.trim().length <= 0) {
        fnToast("warning", "Teléfono vacío");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosActualizar(id, ruc, nombre, direccion, telefono, estado) {

    $('#lblCodigo').val(id);
    $('#lblRuc').val(ruc);
    $('#lblNombre').val(nombre);
    $('#lblDire').val(direccion);
    $('#lblTel').val(telefono);
    $('#lblEstado').val(estado);

    $('#modalActualizar').modal('show');

}

function ValidarDatosActualizar() {

    var ruc = $('#lblRuc').val();
    var nombre = $('#lblNombre').val();
    var dire = $('#lblDire').val();
    var tel = $('#lblTel').val();
    var estado = $('#lblEstado').val();

    if (ruc.trim().length != 11) {
        fnToast("warning", "El RUC debe contener 11 dígitos");
        return false;
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre vacío");
        return false;
    }

    if (dire.trim().length <= 0) {
        fnToast("warning", "Dirección vacío");
        return false;
    }

    if (tel.trim().length <= 0) {
        fnToast("warning", "Teléfono vacío");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosEliminar(id,prov,productos){
    
    if(productos>0){
        fnToast("error","El proveedor a eliminar tiene productos registrados");
        return false;
    }
    
    $('#lblTexto').html(prov);
    $('#id').val(id);
    
    $('#modalEliminar').modal('show');
    
}

