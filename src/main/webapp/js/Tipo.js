function ValidarDatosAgregar() {

    var tipo = $('#txtTipo').val();
    var estado = $('#txtEstado').val();

    if (tipo.trim().length <= 0) {
        fnToast("warning", "Tipo de solicitud vacío");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosActualizar(codigo, nombre, estado) {

    $('#lblCodigo').val(codigo);
    $('#lblTipo').val(nombre);
    $('#lblEstado').val(estado);

    $('#modalActualizar').modal('show');

}

function ValidarDatosActualizar(){
    
    var tipo = $('#lblTipo').val();
    var estado = $('#lblEstado').val();

    if (tipo.trim().length <= 0) {
        fnToast("warning", "Tipo de solicitud vacío");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;
    
}

function PasarDatosEliminar(codigo,tipo,solicitudes){
    
    if(solicitudes>0){
        fnToast("error","EL tipo de solicitud a eliminar tiene solicitudes registrados");
        return false;
    }
    
    $('#lblTexto').html(tipo);
    $('#id').val(codigo);
    
    $('#modalEliminar').modal('show');
}


