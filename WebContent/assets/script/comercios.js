var x = 0;
var $datosComercio;
var count = 0;
getSubDis();
function getSubDis(){
	var vendedor = "<option value='0'>Todos</option>";
	var id = 0;
	var padre = 0;
	if(USER.TIPO_RECEPTOR == 2){
		id = USER.PADRE;
	}
	if(USER.TIPO_RECEPTOR == 1){
		id = USER.COD_RECEPTOR;
	}
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 1},
            {value: id},
            {value: padre}
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		data: JSON.stringify(input),
		async:false,
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		
	}).error(function (e) {
		console.log(e);
	})
	$("#subdistribuidor").html(vendedor);
	getReceptores(0);
}
$("#subdistribuidor").change(function(){
	getReceptores($(this).val());
	getDistribuidor(0,$(this).val());
});
getRegion();
getProvincia();
getComuna();
function getReceptores(pad, opt){
	var vendedor = "<option value='0'>Todos</option>";
	var input;
	
	var id = 0;
	var padre = 0;
	if(USER.TIPO_RECEPTOR == 2){
		id = USER.COD_RECEPTOR;
	}
	if(USER.TIPO_RECEPTOR == 1){
		padre = USER.COD_RECEPTOR;
	}
	if(USER.TIPO_RECEPTOR == 0){
		padre = pad;
	}
	
	input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 2},
            {value: id},
            {value: padre}
        ]
    };
	
	
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		async:false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		
	}).error(function (e) {
		console.log(e);
	})
	$("#vendedores").html(vendedor);
	if(opt != 0){
		getDistribuidor(0,0);
	}
	
	return vendedor;
}
$("#vendedores").change(function(){
	getDistribuidor($(this).val(),$("#subdistribuidor").val());
});

var tabla;
var datosExcel;
var datos2;
var columnas;
function getDistribuidor(padre, subdis){
	$.fn.dataTable.ext.errMode = 'none';
	var id = 0;
	var pad = 0;
	var tipo = 0;
	var sub = 0; 
	
	if(USER.TIPO_RECEPTOR == 2){
		pad = USER.COD_RECEPTOR;
		sub = subdis;
	}
	
	if(USER.TIPO_RECEPTOR == 1){
		sub = USER.COD_RECEPTOR;
		pad = padre;
		//sub = subdis;
	}
	
	if(USER.TIPO_RECEPTOR == 0){
		pad = padre;
		sub = subdis;
	}
	var input = {
		SP: "GET_COMERCIO",
		FILTERS: {
			_ID: id,
			_PADRE: pad,
			_TIPO_PADRE: tipo,
			_SUB_DISTRIBUIDOR: sub
		}
	}
	callSp(input).then(function(response){
		datos = [];
		console.log(response)
		datos2 = [];
		if(count == 0){
			$datosComercio = response.data;
			count++;
		}
		$.each(response.data, function(k,v){
			var tbl2 = [v.id,v.DISTRIBUIDOR,v.SUBDISTRIBUIDOR,v.VENDEDOR, v.RUT, v.RAZON_SOCIAL, v.DES_GIRO, v.DIRECCION, v.NCOMUNA, v.CORREO, v.TELEFONO, v.FECHA_CREACION, v.circuito, v.ruta, v.COMENTARIO];
			datos2.push(tbl2);
			var tbl = [v.id,v.DISTRIBUIDOR,v.SUBDISTRIBUIDOR,v.VENDEDOR, v.RUT, v.RAZON_SOCIAL, v.DES_GIRO, v.DIRECCION, v.NCOMUNA, v.CORREO, v.TELEFONO, v.FECHA_CREACION,
			           v.circuito, v.ruta, v.COMENTARIO,
			           "<button title='Editar' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
			           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["ID","Distribuidor","Sub Distrbuidor","Vendedor","Rut", "Razón Social","Giro","Dirección",
		            "Comuna","Correo", "Telefono", "Fecha Creación", "Circuito", "Ruta", "Comentario","Opciones"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false
		});
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
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
function editar($v){
	var comentario = $v.COMENTARIO?$v.COMENTARIO:"";
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' id='rut' onblur='Rut(this.value)' value='"+$v.RUT+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Razón Social</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value='"+$v.RAZON_SOCIAL+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Giro</h5>";
	pop += 			"<select class='form-control required-modal' id='giro'>";
	pop += getGiro($v.GIRO);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Region</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='region' onchange='getProvincia(this.value);'>";
	pop += 				getRegionSelect($v.idregion);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Provincia</h5>";
	pop += 			"<select class='form-control input-sm required-modal' onchange='getComuna(this.value)' id='provincia'>";
	pop += 				getProvinciaSelect($v.idprovincia);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Comuna</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='comuna'>";
	pop += 				getComunaSelect($v.COMUNA);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Direccion</h5>";
	pop += 			"<input class='form-control required-modal' id='direccion' value='"+$v.DIRECCION+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Correo</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' value='"+$v.CORREO+"' id='correo'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Telefono</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' value='"+$v.TELEFONO+"' id='fono'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Vendedor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='vendedorComercio'>";
	pop += 				getReceptores($("#subdistribuidor").val(), 0);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Capacitado</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='capacitado'>";
	pop += 				"<option "+$v.CAPACITADO*1 == 1?"selected":""+" value='1'>Si</option>";
	pop += 				"<option "+$v.CAPACITADO*1 == 0?"":"selected"+" value='0'>No</option>";
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Comentario</h5>";
	pop += 			"<input type='text' class='form-control  required-modal' value='"+comentario+"' id='comentario'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='modificar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Modificar Datos de "+$v.RAZON_SOCIAL, pop, true, "650px", true);
	selectCss();
	$("#vendedorComercio").val($v.PADRE).trigger("change");
}
function getRegionSelect(region){
	var option = "<option value=''></option>";
	$.each($REGIONES, function(k,v){
		if(v.id == region){
			option += "<option value='"+v.id+"' selected>"+v.region+"</option>";
		}else{
			option += "<option value='"+v.id+"'>"+v.region+"</option>";
		}
	})
	return option;
}
function getProvinciaSelect(p){
	var option = "<option value=''></option>";
	$.each($PROVINCIAS, function(k,v){
		if(v.id == p){
			option += "<option value='"+v.id+"' selected>"+v.provincia+"</option>";
		}else{
			option += "<option value='"+v.id+"'>"+v.provincia+"</option>";
		}
	})
	return option;
}
function getComunaSelect(c){
	var option = "<option value=''></option>";
	$.each($COMUNAS, function(k,v){
		if(v.id == c){
			option += "<option value='"+v.id+"' selected>"+v.comuna+"</option>";
		}else{
			option += "<option value='"+v.id+"'>"+v.comuna+"</option>";
		}
	})
	return option;
}
function getGiro(select){
	var giros = "";
	var input = {
        SP: "GET_CONCEPTOS",
        FILTERS: [
            {value: 'GIRO_COMERCIO'}
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
		$.each(response.data, function(k,v){
			if(v.id == select){
				giros += "<option value='"+v.id+"' selected>"+v.DESCRIPCION+"</option>";
			} else {
				giros += "<option value='"+v.id+"'>"+v.DESCRIPCION+"</option>";
			}
			
		})
		
	}).error(function (e) {
		console.log(e);
	})
	return giros;
		
}

function eliminar($v){
	var c = confirmar.confirm("¿Seguro quiere eliminar los datos de "+$v.RAZON_SOCIAL+"?");
	$(c.aceptar).click(function(){
		setTimeout(function(){ 
			var input = {
		        SP: "DELETE_COMERCIO",
		        FILTERS: [
		            {value: $v.id}
		        ]
		    };
			$.ajax({
				url: IPSERVER + "post",
				method: 'POST',
				dataType: 'json',
//						timeout: 10000,
				data: JSON.stringify(input),
				headers: { 'Content-Type': "application/json", 'connection-properties': configService }
			}).success(function (response) {
				if(response.data.length > 0){
					closeModal();
					loading.hide();
					setTimeout(function(){ 
						getDistribuidor($("#vendedores").val());
					}, 50);
				}
				
			}).error(function (e) {
				console.log(e);
			})
		}, 50);
	})
}
function modificar($v){
	if($("#vendedorComercio").val()== "0"){
		alert("Debe seleccionar vendedor para continuar.");
		return false;
		
	}
	var input = {
        SP: "UPDATE_COMERCIO_WEB",
        FILTERS: [
            {value: $v.id},
            {value: $("#razon_social").val()},
            {value: $("#rut").val()},
            {value: $("#giro").val()},
            {value: $("#direccion").val()},
            {value: $("#comuna").val()},
            {value: ""},
            {value: $("#correo").val()},
            {value: $("#fono").val()},
            {value: $("#capacitado").val()},
            {value: $("#vendedorComercio").val()},
            {value: $("#comentario").val()}
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//			timeout: 10000,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		if(response.data.length > 0){
			closeModal();
			loading.hide();
			setTimeout(function(){ 
				alert("Comercio actualizado con exito")
				getDistribuidor($("#vendedores").val(),$("#subdistribuidor").val());
			}, 50);
		}
		
	}).error(function (e) {
		console.log(e);
	})
}
function agregar(){
	if($("#vendedores").val()*1 == 0){
		alerta("Debe seleccionar un vendedor");
		return false;
	}
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' id='rut' onblur='Rut(this.value), getComerciosRut(this.value)' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Razón Social</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Giro</h5>";
	pop += 			"<select class='form-control required-modal' id='giro'>";
	pop += getGiro(0);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Region</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='region' onchange='getProvincia(this.value);'>";
	pop += 				getRegionSelect(0);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Provincia</h5>";
	pop += 			"<select class='form-control input-sm required-modal' onchange='getComuna(this.value)' id='provincia'>";
	pop += 				getProvinciaSelect(0);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Comuna</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='comuna'>";
	pop += 				getComunaSelect(0);
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Direccion</h5>";
	pop += 			"<input class='form-control required-modal' id='direccion' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Correo</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' id='correo'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Telefono</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' id='fono'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Capacitado</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='capacitado'>";
	pop += 				"<option value='1'>Si</option>";
	pop += 				"<option value='0'>No</option>";
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Comentario</h5>";
	pop += 			"<input type='text' class='form-control required-modal' id='comentario'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarDis()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Comercio", pop, true, "850px", true);
	selectCss();
}
function getComerciosRut($value){
	if($value){
		var input = {
			URL: "GET_COMERCIO_RUT",
			FILTERS: [{
				RUT: $value
			}]
		}
		getData(input).then(function(res){
			if(res.data.length != 0){
				if(res.data[0].ESTADO == 0){
					alerta("El comercio con el rut "+$value+" ya existe");
					$("#rut").val("");
					return;
				}else{
					var c = confirmar.confirm("El comercio con el rut "+$value+" ya existe.<br>¿Desea cargar los Datos ingresados?");
					$(c.aceptar).click(function(){
						closeModal()
						var comercio;
						$.each($datosComercio, function(k,v){
							if(v.id == res.data[0].ID){
								comercio = v;
								return false;
							}
						})
						editar(comercio)
					})
				}
				
				
			}
		})
	}
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
	        SP: "INSERT_COMERCIO_WEB",
	        FILTERS: [
	            {value: $("#razon_social").val()},
	            {value: $("#rut").val()},
	            {value: $("#giro").val()},
	            {value: $("#direccion").val()},
	            {value: $("#comuna").val()},
	            {value: ""},
	            {value:$("#vendedores").val() },
	            {value: 2},
	            {value: $("#correo").val()},
	            {value: $("#fono").val()},
	            {value: $("#capacitado").val()},
	            {value: $("#comentario").val()}
	        ]
	    };
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
//					timeout: 10000,
			data: JSON.stringify(input),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			if(response.data.length > 0){
				closeModal();
				loading.hide();
				setTimeout(function(){ 
					getDistribuidor($("#vendedores").val(),$("#subdistribuidor").val());
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
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		$REGIONES = response.data;
		
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
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		if($PROVINCIAS == undefined){
			$PROVINCIAS = response.data;
		}
		var option = "<option value=''></option>";
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
		var option = "<option value=''></option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.comuna+"</option>";
		})
		$("#comuna").html(option);
		
	}).error(function (e) {
		console.log(e);
	})
}
function generarExcelPulento(){
	if(datos){
		var col = columnas.splice(columnas.length-1, 1)
		var input = {
			HEADER: columnas,
			DATA: datos2,
			NAMES:{
				SHEET: "Comercios",
				FILE: "Comercios"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}