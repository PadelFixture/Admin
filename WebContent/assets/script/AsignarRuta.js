$(document).ready(function(){
	var cir = {
		TABLE: "circuito",
		WHERE:{estado: 1}
	}
	Select(cir).then(function(res){
		if(res.error == 0){
			console.log(res)
			var option = "<option value=''></option>";
			$.each(res.data, function(k,v){
				option += "<option value='"+JSON.stringify(v)+"'>"+v.nombre+"</option>";
			})
			$("#Circuito").html(option)
		}else{
			alerta(res.mensaje);
		}
	})
	var getS = {
		TABLE: "RutaVendedor"
	}
	Select(getS).then(function(res){
		console.log(res)
		$.each(res.data, function(k, v){
			$VenFechas.push(v);
		})
	})
})
var $VenFechas = [];
var $vendedor;
var ven = {
	SP: "GET_RECEPTOR",
	FILTERS: [
	    {value: 2},
	    {value: 0},
	    {value: 0},
	]
}
getSp(ven).then(function(res){
	console.log(res)
	$vendedor = res.data;
})
var tabla;
var $datosRutas = [];
function cambioCircuito($v){
	$v = JSON.parse($v);
	var rutas = {
		SP: "GET_CIRCUITO_RUTAS",
		FILTERS: {_ID_CIRCUITO: $v.id}
	}
	callSp(rutas).then(function(res){
		console.log(res)
		$datosRutas = res.data;
		var datos = [];
		$.each(res.data,function(k, v) {
			var tbl = ["<a title='Ver Comercios en esta Ruta' class='details-control'></a>", v.id,v.nombre, 
			           "<select class='form-control input-sm vendedor' disabled id='vendedor"+v.id+"' style='width: 250px;'>"+getVendedor($v.idVendedor)+"</select>", 
			           "<input class='form-control fechasInput' readonly style='width:200px;' onchange='cambioFecha("+$v.idVendedor+", this);' name='fecha' id='fecha"+v.id+"' value='"+formatFecha(v.fecha)+"'>", 
			           "<button title='Ver Mapa' onclick='verGeoreferencia("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-map-marker'></i></button>"];
			datos.push(tbl);
		})
		if (tabla) {
			tabla.destroy();
			$('#tbl_RendimientoVlidadr').empty();
		}
		var columnas = [ "#", "Id","Nombre", "Vendedor", "Fecha", "Ver Mapa"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			if(i == 0){
				finalColumn.push({
					title: columnas[i],
					"class":          "details-control",
	                "orderable":      false,
	                "data":           null,
	                "defaultContent": ""
				})
			}else{
				finalColumn.push({title: columnas[i]})
			}
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			language : languageDT(),
			paging: false
		});
		$("#tbl_RendimientoVlidadr_filter").hide();
		var detailRows = [];
		$('#tbl_RendimientoVlidadr tbody').on( 'click', 'tr td.details-control', function () {
	        var tr = $(this).closest('tr');
	        console.log(tr)
	        var row = tabla.row( tr );
	        var idx = $.inArray( tr.attr('id'), detailRows );
	 
	        if(row.child.isShown()){
	            tr.removeClass( 'details' );
	            row.child.hide();
	            detailRows.splice( idx, 1 );
		        $(tr).removeClass("success")
	        }else {
	            tr.addClass( 'details' );
	            row.child( addDetail( row.data() ) ).show();
	            console.log( addDetail( row.data() ) )
				var tabla2 = $('#tbl_Datos_Comunes').DataTable({
					autoWidth: true,
					ordering: false,
					fixedHeader: true
				});
		        $(tr).addClass("success")
				$("#tbl_Datos_Comunes_filter").hide();
				$("#tbl_Datos_Comunes_length").hide();
	            if ( idx === -1 ) {
	                detailRows.push( tr.attr('id') );
	            }
	        }
	    } );
		selectCss();
		fechas();
	})
}
function cambioFecha($id, $this){
	$.each($VenFechas, function(k,v){
		if(v.idVendedor == $id && formatFecha($this.value) == v.fecha){
			alerta("Este vendedor ya posee una ruta asignada para la fecha seleccionada");
			$($this).val("");
			return false;
		}
	})
}
function verGeoreferencia($v){
	console.log($v)
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div id='mapa' style='width: 100%; height: 400px; float: left;'></div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='guardar()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Circuito", pop, true, "450px", true);
	var getRut = {
		SP: "GET_RUTA_DETALLE",
		FILTERS:[{value: $v.id}]
	}
	getSp(getRut).then(function(res){
		console.log(res)
		var geo;
		$.each(res.data,function(k, v) {
			if(v.GEOREFERENCIA){
				geo = v.GEOREFERENCIA;
			}
		})
		map = new google.maps.Map(document.getElementById('mapa'),{
			center: JSON.parse(geo),
			zoom: 14
		});
		$.each(res.data,function(k, v) {
			if(v.GEOREFERENCIA){
				var marker = new google.maps.Marker({
		            position: JSON.parse(v.GEOREFERENCIA),
		            map: map,
		            title: v.RAZON_SOCIAL
		        });
			}
		})
	})
}
function addDetail ( d ) {
	loading.show()
	console.log(d)
	var id = d[1];
	var tabla = "";
	var get = {
		SP: "GET_RUTA_DETALLE",
		FILTERS: [{value: id}]
	};
	$.ajax({
		url: "/recotec/json/RECOTEC",
		type:	"PUT",
		dataType: 'json',
		data: JSON.stringify(get),
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(res){
			console.log(res)
			tabla +="<div class='col-xs-2 col-sm-2 col-md-2'>";
			tabla +="</div>";
			tabla +="<div class='col-xs-8 col-sm-8 col-md-8'>";
			tabla +=	"<div class='table-responsive'>";
			tabla +=		"<table class='table table-scrollable table-bordered table-hover table-striped table-condensed' id='tbl_Datos_Comunes'>";
			tabla +=			"<thead>";
			tabla +=				"<tr>";			
			tabla +=					"<th>Rut</th>";
			tabla +=					"<th>Nombre Comercio</th>";
			tabla +=					"<th>Direccion</th>";			
			tabla +=				"</tr>";
			tabla +=			"</thead>";
			tabla +=			"<body id='bodyValores'>";
			$.each(res.data, function(k,v){
				tabla +=			"<tr>";
				tabla +=				"<td>"+v.RUT+"</td>";
				tabla +=				"<td>"+v.RAZON_SOCIAL+"</td>";
				tabla +=				"<td>"+v.DIRECCION+"</td>";
				tabla +=			"</tr>";
			})
			tabla +=			"</body>";
			tabla +=		"</table>";
			tabla +=	'</div>';
			tabla +='</div>';
			tabla +="<div class='col-xs-2 col-sm-2 col-md-2 success'>";
			tabla +="</div>";
			loading.hide()
		},error: function(e){
			console.log(e)
		},complete: function(){
			loading.hide()
		}
	})
	return tabla;
}
function getVendedor(id){
	var option = "<option value=''></option>";
	$.each($vendedor, function(k,v){
		option += v.id == id?"<option selected value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>": "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
	})
	return option;
}
function agregar(){
	var ifValue = false;
	$(".fechasInput").each(function(){
		if($(this).val()){
			ifValue = true;
		}
	})
	if(ifValue){
		var values = [];
		$(".vendedor").each(function(i,e){
			if($(this).val() && $("#fecha"+k).val()){
				var k = $(this)[0].id.split("vendedor")[1];
				console.log($datosRutas[i])
				console.log(k)
				var json = {
					idRuta: k,
					idVendedor: $(this).val(),
					fecha: formatFecha($("#fecha"+k).val())
				}
				values.push(json);
			}
		})
		var inRV = {
			TABLE: "RutaVendedor",
			VALUES: values,
			ALERTA: false
		}
		console.log(inRV)
		Mantenedor(inRV).then(function(res){
			if(res.error == 0){
				alert("Informmacion guardada con exito");
			}else{
				alert(res.mensaje)
			}
		})
	}else{
		alert("No ha seleccionado ningun vendedor para una ruta");
	}
}