function PasarDatosActualizar(codigo, correo, pass, nombre, estado, idRol) {

    $('#lblCodigo').val(codigo);
    $('#lblCorreo').val(correo);
    $('#lblPass').val(pass);
    $('#lblNombre').val(nombre);
    $('#lblEstado').val(estado);
    $('#lblRol').val(idRol);

    $('#modalActualizar').modal('show');

}

function ValidarDatosActualizar() {

    var validarCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var correo = $('#lblCorreo').val();
    var pass = $('#lblPass').val();
    var nombre = $('#lblNombre').val();
    var estado = $('#lblEstado').val();
    var rol = $('#lblRol').val();

    if (correo.trim().length <= 0) {
        fnToast("warning", "Correo vacío");
        return false;
    }

    if (!validarCorreo.test(correo)) {
        fnToast("warning", "Correo inválido");
        return false;
    }

    if (pass.length <= 0) {
        fnToast("warning", "Contraseña vacía");
        return false;
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre completo vacío");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    if (rol === "") {
        fnToast("warning", "::: Seleccionar rol :::");
        return false;
    }

    return true;

}

function ValidarDatosAgregar() {

    var validarCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var correo = $('#txtCorreo').val();
    var pass = $('#txtPass').val();
    var nombre = $('#txtNombre').val();
    var estado = $('#txtEstado').val();
    var rol = $('#txtRol').val();

    if (correo.trim().length <= 0) {
        fnToast("warning", "Correo vacío");
        return false;
    }

    if (!validarCorreo.test(correo)) {
        fnToast("warning", "Correo inválido");
        return false;
    }

    if (pass.length <= 0) {
        fnToast("warning", "Contraseña vacía");
        return false;
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre completo vacío");
        return false;
    }

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    if (rol === "") {
        fnToast("warning", "::: Seleccionar rol :::");
        return false;
    }
    return true;
}

function PasarDatosEliminar(id, nombre, boletas, facturas) {

    if (boletas > 0) {
        fnToast("error", "El usuario a eliminar tiene boletas registradas");
        return false;
    }
    if (facturas > 0) {
        fnToast("error", "El usuario a eliminar tiene facturas registradas");
        return false;
    }

    $('#lblTexto').html(nombre);
    $('#id').val(id);

    $('#modalEliminar').modal('show');

}

