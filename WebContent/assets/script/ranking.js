getRegion();
getProvincia();
getComuna();
var x = 0;

var tabla;
function getRanking(){
	$.fn.dataTable.ext.errMode = 'none';
	var input = {
        SP: "GET_RANKING_COMERCIO",
        FILTERS: [
            {value: $("#region").val()},
            {value: $("#provincia").val()},
            {value: $("#comuna").val()}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		var datos = [];
		$.each(response.data, function(k,v){
			var hab = v.HABILITACIONES / v.RECEPCION *100;
			var rec = v.RECARGADOS / v.RECEPCION * 100;
			var tbl = [ v.SUBDISTRIBUIDOR, v.VENDEDOR, v.RAZON_SOCIAL, v.RECEPCION, v.HABILITACIONES,hab+"%", v.RECARGADOS,rec+"%", v.CANTIDAD_RECARGAS ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["Sub Distribuidor", "Vendedor(a)","Nombre","Recepcionados","Habilitados","% Hab","Recargados","% Rec","Cantidad Recargas"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: true,
			fixedHeader: true
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
	}).error(function (e) {
		console.log(e);
	})
}
function editar($v){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' id='rut' value='"+$v.rut+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value='"+$v.RAZON_SOCIAL+"'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='modificar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Modificar Datos de "+$v.RAZON_SOCIAL, pop, true, "650px", true);
}
function eliminar($v){
	var c = confirmar.confirm("¿Seguro quiere eliminar los datos de "+$v.RAZON_SOCIAL+"?");
	$(c.aceptar).click(function(){
		setTimeout(function(){ 
			var input = {
		        _ID: $v.id,
		    };
			console.log(input)
		    $.ajax({
				url: '/recotec/json/REC/DELETE_RECEPTOR',
				method: 'PUT',
				dataType: 'json',
				data:JSON.stringify(input),
			}).success(function (response) {
				console.log(response);
				closeModal();
				loading.hide();
				setTimeout(function(){ 
					getDistribuidor($("#subdistribuidor").val());
				}, 50);
			}).error(function (e) {
				console.log(e);
			})
		}, 50);
	})
}
function modificar($v){
	
	/*loading.show();
	setTimeout(function(){ 
	var input = {
        SP: "UPDATE_RECEPTOR",
        _ID: $v.id,
        _RUT: $("#rut").val(),
        _RAZON_SOCIAL: $("#razon_social").val()
    };
	console.log(input)
    $.ajax({
		url: '/recotec/json/REC/UPDATE_RECEPTOR',
		method: 'PUT',
		dataType: 'json',
		data:JSON.stringify(input),
	}).success(function (response) {
		console.log(response);
		closeModal();
//		getDistribuidor();
		loading.hide();
	}).error(function (e) {
		console.log(e);
	})
	}, 50);

	return;*/
	var input = {
        SP: "UPDATE_RECEPTOR",
        FILTERS: [
            {value: $v.id},
            {value: $("#rut").val()},
            {value: $("#razon_social").val()},
            {value: 0},
            {value: 0},
            {value: 0},
            {value: 0},
            {value: 0},
            {value: 0}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//			timeout: 10000,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		if(response.data.length > 0){
			closeModal();
			loading.hide();
			setTimeout(function(){ 
				getDistribuidor($("#subdistribuidor").val());
			}, 50);
		}
		
	}).error(function (e) {
		console.log(e);
	})
}
function agregar(){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' id='rut' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value=''>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarDis()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Distribuidor", pop, true, "650px", true);
}
function agregarDis(){
	if(!$("#rut").val()){
		return;
	}else if(!$("#razon_social").val()){
		return;
	}
	loading.show();
	setTimeout(function(){ 
		var input = {
		        SP: "INSERT_RECEPTOR",
		        FILTERS: [
		            {value: $("#rut").val()},
		            {value: $("#razon_social").val()},
		            {value: 0},
		            {value: 0},
		            {value: 0},
		            {value: 0},
		            {value: 0},
		            {value: 0},
		            {value: 2},
		            {value: $("#subdistribuidor").val()}
		        ]
		    };
			console.log(input);
			$.ajax({
				url: IPSERVER + "post",
				method: 'POST',
				dataType: 'json',
//					timeout: 10000,
				data: JSON.stringify(input),
				headers: { 'Content-Type': "application/json", 'connection-properties': configService }
			}).success(function (response) {
				console.log(response.data.length);
				if(response.data.length > 0){
					closeModal();
					loading.hide();
					setTimeout(function(){ 
						getDistribuidor($("#subdistribuidor").val());
					}, 50);
				}
				
			}).error(function (e) {
				console.log(e);
			})
	}, 50);
}

var $REGIONES;
var $PROVINCIAS;
var $COMUNAS;
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