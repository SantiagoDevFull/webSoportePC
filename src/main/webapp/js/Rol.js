function PasarDatosActualizar(id, rol, estado) {

    $('#lblCodigo').val(id);
    $('#lblRol').val(rol);
    $('#lblEstado').val(estado);

    $('#modalActualizar').modal('show');
}

function ValidarDatosActualizar() {

    var rol = $('#lblRol').val();
    var estado = $('#lblEstado').val();

    if (rol.trim().length <= 0) {
        fnToast("warning", "Rol vacío");
        return false;
    }
    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }
    return true;
}

function ValidarDatosAgregar(){
    
    var rol = $('#txtRol').val();
    var estado = $('#txtEstado').val();

    if (rol.trim().length <= 0) {
        fnToast("warning", "Rol vacío");
        return false;
    }
    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }
    return true;
    
}

function PasarDatosEliminar(codigo,rol,empleados){
    
    if(empleados>0){
        fnToast("error","El rol a eliminar tiene empleados registrados");
        return false;
    }
    
    $('#id').val(codigo);
    $('#lblTexto').html(rol);
    $('#modalEliminar').modal('show');
    
}

