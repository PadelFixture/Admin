getTorneos();
var tabla;


function getTorneos(){
	var torneos = {
			SP: "get_torneo",
			FILTERS: {  
				_COMUNA : "0",
		        _ESTADO : "0",
		        _USUARIO : "0",
		        _ID_TORNEO : "0"}
		}
	callSp(torneos).then(function(res){
		console.log(res)
		var datos = [];
		$.each(res.data,function(k, v) {
			var tbl = [' <img src="data:image/png;base64, '+v.image+'" width="40" height="40"/>'
			           //"<img src='"+v.imageBanner+"'  width='80' height='80'>"
			           , v.club
			           ,v.nombreTorneo
			           ,viewFecha(v.fecha_desde)
			           ,viewFecha(v.fecha_hasta)
			           ,v.estado
			           ,"<button title='Ver Torneo' onclick='verTorneo("+v.id+")' class='btn blue btn-outline btn-sm' ><i class='fa fa-search'></i></button>"];
					datos.push(tbl);
		})
		if (tabla) {
			tabla.destroy();
			$('#tbl_RendimientoVlidadr').empty();
		}
		var columnas = [ "#", "Club","Torneo", "Fecha Desde", "Fecha Hasta" ,"Estado", "Ver Torneo"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({title: columnas[i]});
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			paging: false
		});
		$("#tbl_RendimientoVlidadr_filter").hide();
	})
}

function agregar(){
	window.location.href = "detalleTorneo";
}

function verTorneo(id){
	window.location.href = "detalleTorneo?idTorneo="+id;
}