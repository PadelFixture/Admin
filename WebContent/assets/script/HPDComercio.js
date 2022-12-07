var $REGIONES;
var $PROVINCIAS;
var $COMUNAS;
getRegion();
getProvincia();
getComuna();
var tabla;
$(document).ready(function(){
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: {
        	_TIPO: 2,
        	_ID: 0,
        	_PADRE: USER.PERFIL == 1?0:USER.COD_RECEPTOR
        }
    };
	callSp(input).then(function(res){
		console.log(res)
		var vendedor = "<option value='0'>Todos</option>";
		$.each(res.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#vendedor").html(vendedor);
	})
})
function getRegion(){
	var input = {
        SP: "GET_CHILE",
        FILTERS: [
            {value: 'REGIONES'},
            {value: 0}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		$REGIONES = response.data;
		var option = "<option value='0'>Todas</option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.region+"</option>";
		})
		$("#region").html(option);
		
	}).error(function (e) {
		console.log(e);
	})
}
function getProvincia(p){
	var input = {
        SP: "GET_CHILE",
        FILTERS: [
            {value: 'PROVINCIAS'},
            {value: p == undefined? 0:p}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		console.log($PROVINCIAS)
		if($PROVINCIAS == undefined){
			$PROVINCIAS = response.data;
		}
		var option = "<option value='0'>Todas</option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.provincia+"</option>";
		})
		$("#provincia").html(option);
	}).error(function (e) {
		console.log(e);
	})
}
function getComuna(comuna){
	var input = {
        SP: "GET_CHILE",
        FILTERS: [
            {value: 'COMUNAS'},
            {value: comuna == undefined? 0:comuna}
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		if(!$COMUNAS){
			$COMUNAS = response.data;
		}
		var option = "<option value='0'>Todas</option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.comuna+"</option>";
		})
		$("#comuna").html(option);
		
	}).error(function (e) {
		console.log(e);
	})
}
var $datos = [];
var $columnas = [];
function buscar(){
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	var id = 0;
	if(USER.TIPO_RECEPTOR == 1){
		id = USER.COD_RECEPTOR;
	}
	console.log(USER);
	var input = {
        SP: "GET_DETALLE_COMERCIO",
        FILTERS: {
        	_REGION: $("#region").val(),
        	_PROVUNCIA: $("#provincia").val(),
        	_COMUNA: $("#comuna").val(),
        	_DESDE: fechadesde,
        	_HASTA: fechahasta,
        	_PP : $("#pp").val(),
        	_SUB: id,
        	_VENDEDOR: $("#vendedor").val()
        },
        LOADING: true
    };
	console.log(input)
	callSp(input).then(function(res){
		console.log(res)
		var datos = [];
		var arr = [];
		$.each(res.data, function(k,v){
			if(arr.indexOf(v.id) == -1){
				var ubicacion = v.GEOREFERENCIA == 0? "": "<button title='Ver Foto' onclick='verGeoreferencia("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-map-marker'></i></button>";
				var foto = v.FOTO == 0? "": "<button title='Ver Foto' onclick='verDetalleVisistas("+JSON.stringify(v)+")' class='btn blue btn-outline btn-sm' ><i class='fa fa-info fa-2x'></i></button>";
				var tbl = [];
				if(USER.PERFIL != 4){
					tbl = [v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR, v.VENDEDOR, v.COMERCIO, v.FECHA_CREACION, v.REGION, v.COMUNA, v.DIRECCION, v.DESCRIPCION, v.GEOREFERENCIA, v.GEOLOCATION, v.Q_HABILITACION, v.Q_RECARGAS, ubicacion, foto];
				} else {
					tbl = [v.DISTRIBUIDOR, v.COMERCIO, v.FECHA_CREACION, v.REGION, v.COMUNA, v.DIRECCION, v.DESCRIPCION, v.GEOREFERENCIA, v.GEOLOCATION, v.Q_HABILITACION, v.Q_RECARGAS, ubicacion, foto];
				}
				arr.push(v.id);
				datos.push(tbl)
			}
			var tbl2 = [];
			if(USER.PERFIL != 4){
				tbl2 = [v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR, v.VENDEDOR, v.COMERCIO, v.FECHA_CREACION, v.REGION, v.COMUNA, v.DIRECCION, v.DESCRIPCION, v.GEOREFERENCIA, v.GEOLOCATION, v.FECHA, v.Q_HABILITACION, v.Q_RECARGAS];
			} else {
				tbl2 = [v.DISTRIBUIDOR, v.COMERCIO, v.FECHA_CREACION, v.REGION, v.COMUNA, v.DIRECCION, v.DESCRIPCION, v.GEOREFERENCIA, v.GEOLOCATION, v.FECHA, v.Q_HABILITACION, v.Q_RECARGAS];
			}
			$datos.push(tbl2)
		})
		var columnas = [];
		if(USER.PERFIL != 4){
			columnas = ["Distribuidor", "Sub distribuidor", "Vendedor", "Comercio", "Fecha Creacion", "Region", "Comuna", "Direccion", "Giro", "Georeferencia", "Georeferencia Visita", "Cantidad Habilitados", "Cantidad Recargas", "Ubicacion", "Visita"];
			$columnas = ["Distribuidor", "Sub distribuidor", "Vendedor", "Comercio", "Fecha Creacion", "Region", "Comuna", "Direccion", "Giro", "Georeferencia", "Georeferencia Visita", "Fecha Visita", "Cantidad Habilitados", "Cantidad Recargas"];
		} else{
			columnas = ["Distribuidor", "Comercio", "Fecha Creacion", "Region", "Comuna", "Direccion", "Giro", "Georeferencia", "Georeferencia Visita", "Cantidad Habilitados", "Cantidad Recargas", "Ubicacion", "Visita"];
			$columnas = ["Distribuidor", "Comercio", "Fecha Creacion", "Region", "Comuna", "Direccion", "Giro", "Georeferencia", "Georeferencia Visita", "Fecha Visita", "Cantidad Habilitados", "Cantidad Recargas"];
		}
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		if(USER.PERFIL != 4){
			$("#tbl_RendimientoVlidadr").html('<tfoot><tr><th colspan="9" style="text-align:right">Total:</th><th></th><th></th><th></th><th></th></tr></tfoot>');
		} else {
			$("#tbl_RendimientoVlidadr").html('<tfoot><tr><th colspan="7" style="text-align:right">Total:</th><th></th><th></th><th></th><th></th></tr></tfoot>');
		}
		var columns = [ 0, 1,2,3,4,5,6,7,8,9 ];
		if(USER.PERFIL == 4){
			columns = [ 0, 1,2,3,4,5,6,7];
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false,
			footerCallback: function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	            var intVal = function ( i ) {
	                return typeof i === 'string' ?i.replace(/[\$,]/g, '')*1 :
	                typeof i === 'number' ?i : 0;
	            };
	            var x = 10;
	            if(USER.PERFIL == 4){
	            	x = 8;
	            }
	            tQRecepcon = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tQRecepcon);
	            x++;
	            yHabilitados = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );	            
	            $( api.column(x).footer() ).html(yHabilitados);
	            x++;
	            $( api.column(x).footer() ).html("");
	            x++;
	            $( api.column(x).footer() ).html("");
	        }
		});
		$("#tbl_RendimientoVlidadr_filter").hide();
		$("#tbl_RendimientoVlidadr_length").hide();
		
		$('#tbl_RendimientoVlidadr thead tr').clone(true).appendTo( '#tbl_RendimientoVlidadr thead' );
	    $('#tbl_RendimientoVlidadr thead tr:eq(1) th').each( function (i) {
	    	if($(this).text() != "" && $(this).text() != "Detalle"){
	    		var title = $(this).text();
	            $(this).html( '<input type="text" class="form-control input-sm" placeholder="'+title+'" />' );
	     
	            $( 'input', this ).on( 'keyup change', function () {
	                if ( tabla.column(i).search() !== this.value ) {
	                	tabla.column(i).search( this.value ).draw();
	                }
	            } );
	    	}else{
	    		$(this).html("");
	    	}
	    } );
	})
}
function verDetalleVisistas($json){
	console.log($json)
	var input = {
        URL: "GET_DETALLE_VISITA",
        FILTERS: [{
        	COMERCIO: $json.id,
        }]
    };
	getData(input).then(function(res){
		console.log(res)
		var pop = "";
		pop +="<div class='col-xs-12 col-sm-12 col-md-12'>";
		pop +=	"<div class='table-responsive'>";
		pop +=		"<table class='table table-scrollable table-bordered tbl-row-rap table-hover table-condensed' id='tbl_Datos_Comunes'>";
		pop +=			"<thead>";
		pop +=				"<tr>";	
		pop +=					"<th>Fecha</th>";	
		pop +=					"<th>Publicidad</th>";
		pop +=					"<th>Foto</th>";			
		pop +=				"</tr>";
		pop +=			"</thead>";
		pop +=			"<tbody id='bodyDias'>";
		$.each(res.data, function(k,v){
			var pub = v.FOTO == 0?"No": "Si";
			var foto = v.IDVISITA == null? "": "<a title='Ver Foto' onclick='verFoto("+JSON.stringify(v)+")' class='btn red btn-outline btn-xs' ><i class='fa fa-camera-retro fa-2x'></i></a>";
			pop +=				"<tr>";	
			pop +=					"<td>"+v.FECHA+"</td>";	
			pop +=					"<td>"+pub+"</td>";
			pop +=					"<td>"+foto+"</td>";			
			pop +=				"</tr>";
		})
		pop +=			"</tbody>";
		pop +=		"</table>";
		pop +=	'</div>';
		pop +='</div>';
		pop += "<div style='text-align: center;'>";
		pop += 	"<a class='btn red' onclick='closeModal()'>Cerrar</a>";
		pop += "</div>";
		popUp("Detalle Visita: "+$json.COMERCIO, pop, true, "800px", true);
	})
}
function verFoto($v){
	console.log($v)
	var input = {
		URL: "VER_FOTO_FILE",
		FILTERS: [{
			CODIGO: $v.IDVISITA
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
function initMap(){
	
}
function verGeoreferencia($v){
	var geo = "";
	geo +=	'<div class="col-xs-12 col-sm-12 col-md-12 portlet light">';
	geo +=  	'<div class="col-xs-12 col-sm-12 col-md-12"><br></div>';
	geo +=		'<div class="col-xs-12 col-sm-12 col-md-12">';
	geo +=			'<div id="map" style="width: 100%; height: 500px; float: left;">';
	geo +=				'';
	geo +=			'</div>';
	geo +=		'</div>';
	geo +=  	'<div class="col-xs-12 col-sm-12 col-md-12"><br></div>';
	geo +=  	'<div class="col-xs-12 col-sm-12 col-md-12">';
	geo +=			'<div class="col-md-2">';
	geo +=				'<label style="color: #C70039;font-weight: bold" class="col-form-label" style="float: right;">LAT/LGN:</label>';
	geo +=			'</div>';
	geo +=			'<div class="col-sm-6 col-md-6">';
	geo +=				'<label style="color: #337ab7;font-weight: bold" id="lnglat"></label>';
	geo +=			'</div>';
	geo +=		'</div>';
	geo +=  	'<div class="col-xs-12 col-sm-12 col-md-12"><br></div>';
	geo +=		'<div class="col-xs-12 col-sm-12 col-md-12">'+
					'<div class="btn btn-circle red btn-outline" onclick="closeModal()" id="cancelarFormaAp">Cancelar</div>'+ 
				'</div>'
	geo +=	'</div>';
	popUp("Ver Ubicacion", geo, true, "700px", true);
	map = new google.maps.Map(document.getElementById('map'),{
		center: JSON.parse($v.GEOREFERENCIA),
		zoom: 15
	});
	
	var marker = new google.maps.Marker({
          position: JSON.parse($v.GEOREFERENCIA),
          map: map,
          draggable: true,
    });
	$("#lnglat").html(marker.position.lat()+","+marker.position.lng());
	location2 = marker.getPosition();
	google.maps.event.addListener(marker, "dragend", function() {
		posicion = marker.getPosition();
		location2 = posicion;
		deleteCircles();
		createRadio(posicion);
		$("#lnglat").html(posicion.lat()+","+posicion.lng());
	  });
}
function generarExcel(){
	if($datos){
		var input = {
			HEADER: $columnas,
			DATA: $datos,
			NAMES:{
				SHEET: "Habilitaciones y Recargas",
				FILE: "Habilitaciones_Recargas"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}