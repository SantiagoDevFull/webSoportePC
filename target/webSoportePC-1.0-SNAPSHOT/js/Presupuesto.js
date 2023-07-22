function AumentarCantidad(id) {

    $.ajax({
        url: 'ControlPresupuesto',
        type: 'POST',
        data: {
            accion: 'ActualizarCantidad',
            txtCodigoPro: id,
            txtCantidad: 1
        },
        success: function (response) {

            location.reload();
        },
        error: function (xhr, status, error) {
            // Manejo de errores si la petición AJAX falla
            console.error('Error al aumentar cantidad: ' + error);
        }
    });
}

function DisminuirCantidad(id) {

    $.ajax({
        url: 'ControlPresupuesto',
        type: 'POST',
        data: {
            accion: 'ActualizarCantidad',
            txtCodigoPro: id,
            txtCantidad: -1
        },
        success: function (response) {

            location.reload();
        },
        error: function (xhr, status, error) {
            // Manejo de errores si la petición AJAX falla
            console.error('Error al disminuir cantidad: ' + error);
        }
    });
}

function CargarDetalle(id){
    
    $('#detalle').load('ControlCliente?accion=ListarDetalle&id='+id);
    
    $('#modalDetalle').modal('show');
}

