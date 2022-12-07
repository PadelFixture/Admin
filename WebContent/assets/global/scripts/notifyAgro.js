$(document).ready(function(){
	loadIncidencias();
//	setInterval(function(){ loadIncidencias() }, 15000);
})
//var SESION = getVars();
function loadIncidencias(){
	$("#IncidenciasNotify").html(SESION.incidencia.length);
	$("#inciPending").html(SESION.incidencia.length);

	var noti = "";
	$.each(SESION.incidencia, function(k, v) {
		noti += "<li>";
		noti += 	"<a onclick='detalleIncidencia("+v.codigo+");'>";
		noti += 		"<span class='details'>";
		noti += 			"<span class='label label-sm label-icon label-success'></span> Fecha Registro: "+v.fecha_ingreso;
		noti += 			"<br><span class='label label-sm label-icon label-success'></span> Tipo Incidencia: "+v.tipo_incidencia;
		noti += 		"</span>";
		noti += 	"</a>";
		noti += "</li>"
	})
	$("#InciNotificacion").html(noti);
}
function detalleIncidencia(codigo){
	console.log(codigo)
	$.ajax({
		url : "/simpleWeb/json/AGRO/DETALLE_INCIDENCIA_CODIGO/"+codigo,
		type : "GET",
		dataType : 'json',
		async : false,
		success : function(v) {
			console.log(v)
			var detalleNotificacion = "";
			detalleNotificacion +='<div class="table-responsive">';
			detalleNotificacion +=	'<table class="table table-striped table-condensed" id="tbl_colaboradores">';
			detalleNotificacion +=		'<thead style="text-align: center;">';
			detalleNotificacion +=			'<tr>';
			detalleNotificacion +=				'<th style="text-align: center;">Fecha Ingreso</th>';
			detalleNotificacion +=				'<th style="text-align: center;">Tipo Incidencia</th>';
			detalleNotificacion +=				'<th style="text-align: center;">Ingresado Por:</th>';
			detalleNotificacion +=				'<th style="text-align: center;">Cuartel</th>';
			detalleNotificacion +=				'<th style="text-align: center;">Onbservacion</th>';
			detalleNotificacion +=			'</tr>';
			detalleNotificacion +=		'</thead>';
			detalleNotificacion +=		'<tbody id="tblMaterial">';
			detalleNotificacion +=			"<td>"+formatFecha(v.fecha_ingreso)+"</td>";
			detalleNotificacion +=			"<td>"+v.tipo_incidencia+"</td>";
			detalleNotificacion +=			"<td>"+v.trabajador+"</td>";
			detalleNotificacion +=			"<td>"+v.ncuartel+"</td>";
			detalleNotificacion +=			"<td>"+v.observacion+"</td>";
			detalleNotificacion +=		'</tbody>';
			detalleNotificacion +=	'</table>';
			detalleNotificacion +='</div>';

			detalleNotificacion +=	'<h4>Cerrar Incidencia</h4<';
			detalleNotificacion +='<div class="table-responsive">';
			detalleNotificacion +=	'<table class="table table-striped table-condensed table-bordered" id="tbl_colaboradores">';
			detalleNotificacion +=		'<thead style="text-align: center;">';
			detalleNotificacion +=			'<tr>';
			detalleNotificacion +=				'<th style="text-align: center;width:30%;">Accion</th>';
			detalleNotificacion +=				'<th style="text-align: center;width:70%">Onservacion</th>';
			detalleNotificacion +=			'</tr>';
			detalleNotificacion +=		'</thead>';
			detalleNotificacion +=		'<tbody id="tblMaterial">';
			detalleNotificacion +=		"<tr>";
			detalleNotificacion +=			"<td><select class='form-control input-sm noty' id='accion"+v.codigo+"'><option value=''>Seleccione</option><option value='1'>Aplicaiones</option><option value='2'>Rendimiento Diario</option><option value='3'>Riego</option></td>";
			detalleNotificacion +=			"<td><textarea class='form-control noty' id='obscierre"+v.codigo+"'></textarea></td>";
			detalleNotificacion +=		"</tr>";
			detalleNotificacion +=		"</tbody>";
			detalleNotificacion +=	"</table>";
			detalleNotificacion +="</div>";
			detalleNotificacion +='<div class="col-sm-12 col-md-12">';
			detalleNotificacion +=		"<div class='btn btn-circle blue btn-outline' id='reprogramar' onclick='cerrarIncidencia("+v.codigo+");'><i class='fa fa-clock-o'></i> Cerrar Incidencia</div>";
//			detalleNotificacion +=		"<div class='btn btn-circle red btn-outline' onclick='confirmRechazar("+v.id_programa+");'>Rechazar</div>";
//			detalleNotificacion +=		'<div class="btn btn-circle green-dark btn-outline" onclick="javascript: hrefOrden('+v.id_programa+');"><i class="fa fa-clock-o"></i> Emitir Orden</div>';
			detalleNotificacion +='</div>';
			popUp("Detalle Incidencia", detalleNotificacion, true, "700px", true);
		}
	})
}
function cerrarIncidencia(codigo){
	var validate = true;
	$(".noty").each(function(){
		if(!$(this).val()){
			var a = alerta("Todos los campos son requeridos");
			$(this).focus();
			validate = false;
			return false;
		}
	})
	if(validate){
		var row = {
			usuario_cierre: SESION.idUser,
			accion: $("#accion"+codigo).val(),
			observacion_cierre: $("#obscierre"+codigo).val(),
			fecha_cierre: dateHoy(),
			codigo: codigo
		}
		$.ajax({
			url : "/simpleWeb/json/AGRO/CERRAR_INCIDENCIA/",
			type : "PUT",
			data : JSON.stringify(row),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success : function(data, textStatus, jqXHR) {
				closeModal();
				$.each(SESION.incidencia, function(ka,va){
					if(va.codigo == codigo){
						SESION.incidencia.splice(ka, 1);
						return false;
					}
				})
				loadIncidencias();
				alerta("Incidencia cerrada Correctamente");
			},
			error : function(jqXHR, textStatus, errorThrown) {
				swal({
					  title: "Error!",
					  text: "No se ha podido registrar la infomacion, consulte con el administrador del sistema",
					  type: "error",
					  confirmButtonText: "Aceptar"
				});
			}
		});
	}
}