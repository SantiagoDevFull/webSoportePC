function ValidarIniciarSesion() {

    var validarCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var correo = $('#txtCorreo').val();
    var pass = $('#txtPass').val();
    var tipo = $('#tipoPer').val();

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

    if (parseInt(tipo) == 0) {
        fnToast("warning", "::: Seleccionar una opción :::");
        return false;
    }

    return true;

}

function ValidarDatosAgregar() {

    var validarCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var documento = $('#txtDoc').val();
    var numero = $('#txtNum').val();
    var nombre = $('#txtNom').val();
    var correo = $('#txtCorreo').val();
    var pass = $('#txtPass').val();
    var estado = $('#txtEstado').val();

    if (documento === "") {
        fnToast("warning", "::: Seleccionar documento :::");
        return false;
    }

    if (documento === "DNI") {
        if (numero.trim().length != 8) {
            fnToast("warning", "El DNI debe contener 8 dígitos");
            return false;
        }
    } else {
        if (numero.trim().length != 11) {
            fnToast("warning", "El RUC debe contener 11 dígitos");
            return false;
        }
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre / Razón social : vacío");
        return false;
    }

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

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosActualizar(codigo, documento, numero, nombre, correo, pass, estado) {

    $('#lblCodigo').val(codigo);
    $('#lblDoc').val(documento);
    $('#lblNum').val(numero);
    $('#lblNom').val(nombre);
    $('#lblCorreo').val(correo);
    $('#lblPass').val(pass);
    $('#lblEstado').val(estado);

    $('#modalActualizar').modal('show');

}

function ValidarDatosActualizar() {

    var validarCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var documento = $('#lblDoc').val();
    var numero = $('#lblNum').val();
    var nombre = $('#lblNom').val();
    var correo = $('#lblCorreo').val();
    var pass = $('#lblPass').val();
    var estado = $('#lblEstado').val();

    if (documento === "") {
        fnToast("warning", "::: Seleccionar documento :::");
        return false;
    }

    if (documento === "DNI") {
        if (numero.trim().length != 8) {
            fnToast("warning", "El DNI debe contener 8 dígitos");
            return false;
        }
    } else {
        if (numero.trim().length != 11) {
            fnToast("warning", "El RUC debe contener 11 dígitos");
            return false;
        }
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre / Razón social : vacío");
        return false;
    }

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

    if (estado === "") {
        fnToast("warning", "::: Seleccionar estado :::");
        return false;
    }

    return true;

}

function PasarDatosEliminar(codigo, nombre, soli, boletas, facturas) {

    if (soli > 0) {
        fnToast("error", "El cliente a eliminar tiene solicitudes registradas");
        return false;
    }
    if (boletas > 0) {
        fnToast("error", "El cliente a eliminar tiene boletas registradas");
        return false;
    }
    if (facturas > 0) {
        fnToast("error", "El cliente a eliminar tiene facturas registradas");
        return false;
    }

    $('#id').val(codigo);
    $('#lblTexto').html(nombre);

    $('#modalEliminar').modal('show');

}


function ValidarRegistro() {

    var validarCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var documento = $('#lblDoc').val();
    var numero = $('#lblNum').val();
    var nombre = $('#lblNom').val();
    var correo = $('#lblCorreo').val();
    var pass = $('#lblPass').val();

    if (documento === "") {
        fnToast("warning", "::: Seleccionar documento :::");
        return false;
    }

    if (documento === "DNI") {
        if (numero.trim().length != 8) {
            fnToast("warning", "El DNI debe contener 8 dígitos");
            return false;
        }
    } else {
        if (numero.trim().length != 11) {
            fnToast("warning", "El RUC debe contener 11 dígitos");
            return false;
        }
    }

    if (nombre.trim().length <= 0) {
        fnToast("warning", "Nombre / Razón social : vacío");
        return false;
    }

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

}

function ValidarSolicitud() {

    var tipo = $('#txtTipo').val();
    var desc = $('#txtDescripcion').val();

    if (tipo === "") {
        fnToast("warning", "::: Seleccionar tipo de solicitud :::");
        return false;
    }

    if (desc.trim().length <= 0) {
        fnToast("warning", "LLenar descripción");
        return false;
    }

    return true;
}
