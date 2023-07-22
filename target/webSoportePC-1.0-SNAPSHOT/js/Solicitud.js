function PasarDatosResponder(id, documento, nombre, tipo, descripcion) {

    $('#lblCodigo').val(id);
    $('#lblDoc').val(documento);
    $('#lblNombre').val(nombre);
    $('#lblTipo').val(tipo);
    $('#lblDes').val(descripcion);

    $('#modalResponder').modal('show');
}

function ValidarDatosResponder() {

    var res = $('#lblRes').val();

    if (res.trim().length <= 0) {
        fnToast("warning", "Respuesta vacía");
        return false;
    }

    return true;

}


