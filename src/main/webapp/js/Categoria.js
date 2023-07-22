function ValidarDatosAgregar() {

    var categoria = $('#txtCategoria').val();
    var estado = $('#txtEstado').val();

    if (categoria.trim().length <= 0) {
        fnToast("warning", "Categoría vacía");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosActualizar(codigo, categoria, estado) {

    $('#lblCodigo').val(codigo);
    $('#lblCategoria').val(categoria);
    $('#lblEstado').val(estado);
    $('#modalActualizar').modal('show');

}

function ValidarDatosActualizar() {

    var categoria = $('#lblCategoria').val();
    var estado = $('#lblEstado').val();

    if (categoria.trim().length <= 0) {
        fnToast("warning", "Categoría vacía");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosEliminar(codigo, nombre, productos) {

    if (productos > 0) {
        fnToast("error", "La categoría a eliminar tiene productos registrados");
        return false;
    }

    $('#lblTexto').html(nombre);
    $('#id').val(codigo);

    $('#modalEliminar').modal('show');

}

