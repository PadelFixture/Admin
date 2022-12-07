var $vendedor;
function exec(){
	var input = {
			URL: "INSERTAR_FOTOS"
		}
		getData(input).then(function(res){
			console.log(res)
		})
}
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
	var option = "<option value=''></option>";
	$.each(res.data, function(k,v){
		option += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
	})
	$("#vendedor").html(option);
	$vendedor = res.data;
})
var tabla;
function cambioVendedor($v){
	var sp = {
		SP: "GET_RUTAS_VENDEDOR",
		FILTERS: [{value: $v}]
	}
	getSp(sp).then(function(res){
		console.log(res)
		var datos = [];
		$.each(res.data,function(k, v) {
			var tbl = ["<a title='Ver Comercios en esta Ruta' class='details-control'></a>",v.id, v.idRv,v.nombre, 
			           v.fecha, 
			           "<button title='Ver Mapa' onclick='verGeoreferencia("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-map-marker'></i></button>"];
			datos.push(tbl);
		})
		if (tabla) {
			tabla.destroy();
			$('#tbl_RendimientoVlidadr').empty();
		}
		var columnas = [ "#", "Id", "idRv","Nombre", "Fecha", "Ver Mapa"];
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
//			paging: false
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
		var geo = "";
		$.each(res.data,function(k, v) {
			if(v.GEOREFERENCIA){
				geo = v.GEOREFERENCIA;
				return false;
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
	var idRv = d[2];
	var tabla = "";
	var get = {
		SP: "GET_RUTA_VENDEDOR_DETALLE",
		FILTERS: [{value: id},{value: idRv}]
	};
	console.log(get)
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
			tabla +=					"<th>Estado</th>";	
			tabla +=					"<th>Foto</th>";					
			tabla +=					"<th>Ver Ubicacion Comercio</th>";	
			tabla +=					"<th>Ver Ubicacion Guardada</th>";			
			tabla +=				"</tr>";
			tabla +=			"</thead>";
			tabla +=			"<body id='bodyValores'>";
			$.each(res.data, function(k,v){
				var est = v.estado == 1? "Pendiente": "Terminado";
				var btn3 = v.estado == 1? "": "<button title='Ver Foto' onclick='verFoto("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-camera'></i></button>";
				var btn = v.estado == 1? "": "<button title='Ver Mapa' onclick='verUbicacion("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-map-marker'></i></button>";
				var btn2 = v.GEOREFERENCIA == ""? "": "<button title='Ver Mapa' onclick='verUbicacionComercio("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-map-marker'></i></button>";
				tabla +=			"<tr>";
				tabla +=				"<td>"+v.RUT+"</td>";
				tabla +=				"<td>"+v.RAZON_SOCIAL+"</td>";
				tabla +=				"<td>"+v.DIRECCION+"</td>";
				tabla +=				"<td>"+est+"</td>";
				tabla +=				"<td>"+btn3+"</td>";
				tabla +=				"<td>"+btn2+"</td>";
				tabla +=				"<td>"+btn+"</td>";
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
function verUbicacion($v){
	console.log($v)
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div id='mapa' style='width: 100%; height: 400px; float: left;'></div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cerrar</a>";
	pop += 		"</div>";
	popUp("Agregar Circuito", pop, true, "450px", true);
	map = new google.maps.Map(document.getElementById('mapa'),{
		center: JSON.parse($v.geoSave),
		zoom: 14
	});
	var marker = new google.maps.Marker({
        position: JSON.parse($v.geoSave),
        map: map,
        title: $v.RAZON_SOCIAL
    });
}
function verUbicacionComercio($v){
	console.log($v)
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div id='mapa' style='width: 100%; height: 400px; float: left;'></div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cerrar</a>";
	pop += 		"</div>";
	popUp("Agregar Circuito", pop, true, "450px", true);
	map = new google.maps.Map(document.getElementById('mapa'),{
		center: JSON.parse($v.GEOREFERENCIA),
		zoom: 14
	});
	var marker = new google.maps.Marker({
        position: JSON.parse($v.GEOREFERENCIA),
        map: map,
        title: $v.RAZON_SOCIAL
    });
}
var tablaFoto;
function verFoto($v){
	console.log($v)
	var input = {
		URL: "VER_FOTO_FILE",
		FILTERS: [{
			CODIGO: $v.idVisita
		}]
	}
	getData(input).then(function(res){
		console.log(res)
		if(res.error == 0){
    		var pop = "";
    		pop +=	'<div class="col-sm-12 col-md-12">';
    		pop +=		"<table class='table table-scrollable table-bordered table-hover table-striped table-condensed' id='tbl_Fotos'>";
    		pop +=			"<thead>";
    		pop +=				"<tr>";			
    		pop +=					"<th>Foto</th>";		
    		pop +=				"</tr>";
    		pop +=			"</thead>";
    		pop +=			"<body id='bodyValores'>";
			$.each(res.data, function(k,v){
				pop +=			"<tr>";
				pop +=				"<td><div height='500px' width='100%'><img id='imgFoto"+k+"' height='100%' width='100%'></img></div></td>";
				pop +=			"</tr>";
			})
			pop +=			"</body>";
			pop +=		"</table>";
			pop += 		"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
			pop +=	'</div>';
			popUp("Foto", pop, true, "700px", true);
			$.each(res.data, function(k,v){
		    	var src='data:image/png;base64,'+v.image;
		        $('#imgFoto'+k).attr('src',src);
			})
			$('#tbl_Fotos').DataTable({
				pagingType : "full_numbers",
				"lengthMenu": [[1], [1]],
				language : languageDT(),
			});
			$("#tbl_Fotos_filter").hide();
			$("#tbl_Fotos_length").hide();
		}else{
			alert(res.message)
		}
	})
}