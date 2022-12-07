$(document).ready(function() {
	onLoadContratacion();
	onLoadPreseleccion();


})


function onLoadPreseleccion() {

	$.getJSON("/simpleWeb/json/work/loadNotiPreseleccion/", function(data) {
		
		$("#notificacionWork").html(data.length);
		$("#pendingWork").html(data.length);
		datos = data;
		$.each(data, function(k, v) {
			var noti_preseleccion = "";
			noti_preseleccion += "<li>";
			noti_preseleccion += "<a href='preseleccion?id_pet=" + v.id_orden
					+ "'>";
			noti_preseleccion += "<span class='details'>Notificación :"
					+ v.id_orden + " / Cantidad: " + v.cantidad_total
					+ " / Operacion: " + v.obra + " / Fecha Inicio: <strong>"
					+ v.fecha_inicio + "</strong> / Empresa: <strong>"
					+ v.empresa + "</strong></span>";
			noti_preseleccion += "</a>";
			noti_preseleccion += "</li>";

			$("#listWorkReclutamiento").append(noti_preseleccion);

		})
	});

}

function onLoadContratacion() {
	
	var listContratacion = "";
	$.getJSON("/simpleWeb/json/work/loadContratacion/", function(data) {

		$("#notificacionContratacion").html(data.length);
		$("#notificacionContratacion").html(data.length);
		datos = data;
		$.each(data, function(k, v) {
			var listContratacion = "";
			listContratacion += "<li>";
			listContratacion += "<a href='Contratacion?cod_pet="
					+ v.codigo_peticion + "&id_pet=" + v.id_peticion + "'>";
			listContratacion += "<span class='details'>Orden Reclutamiento:"
					+ v.codigo_peticion + " / Requeridos: " + v.requeridos
					+ " / Encargado: " + v.usuario
					+ " / Proceso: <strong>Contratación</strong></span>";
			listContratacion += "</a>";
			listContratacion += "</li>";

			$("#listContratacion").append(listContratacion);

		})
	});
}