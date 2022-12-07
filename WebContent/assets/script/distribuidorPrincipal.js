getDistribuidor();
var tabla;
getRegion();
getProvincia();
getComuna();
var datosExcel;
function getDistribuidor(){
	$.fn.dataTable.ext.errMode = 'none';
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 0},
            {value: 0},
            {value: 0}
        ]
    };
	
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
			var tbl = [v.id, v.rut, v.RAZON_SOCIAL,v.PROPIO,v.region, v.provincia, v.NCOMUNA, v.DIRECCION,  v.CORREO, v.TELEFONO,
			           "<button title='Editar' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
			           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["ID","Rut", "Razon Social","Propio","Region","Ciudad","Comuna","Dirección", "Correo", "Telefono", "Opciones"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false,
			fixedHeader: true
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
	}).error(function (e) {
		console.log(e);
	})
}
function editar($v){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' id='rut' onblur='Rut(this.value)' value='"+$v.rut+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Razon Social</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value='"+$v.RAZON_SOCIAL+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Propio</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='propio'>";
	pop += 				getPropios($v.PROPIO);
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
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Dirección</h5>";
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
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='modificar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Modificar Datos de "+$v.RAZON_SOCIAL, pop, true, "950px", true);
	selectCss()
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
					getDistribuidor();
				}, 50);
			}).error(function (e) {
				console.log(e);
			})
		}, 50);
	})
}
function getPropios(id){
	if(id == undefined){
		id = 2;
	}
	var option = "<option value=''></option>";
	var propiosArr = [{
		id:1,
		nombre: "Si",
	},{
		id:0,
		nombre: "No"
	}]
	$.each(propiosArr, function(k,v){
		if(v.id == id){
			option += "<option value='"+v.id+"' selected>"+v.nombre+"</option>";
		}else{
			option += "<option value='"+v.id+"'>"+v.nombre+"</option>";
		}
	})
	return option;
}
function modificar($v){
	var input = {
        SP: "UPDATE_RECEPTOR",
        FILTERS: [
            {value: $v.id},
            {value: $("#rut").val()},
            {value: $("#razon_social").val()},
            {value: $("#propio").val()},
            {value: $("#comuna").val()},
            {value: $("#direccion").val()},
            {value: $("#correo").val()},
            {value: $("#fono").val()}
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
				getDistribuidor();
			}, 50);
		}
		
	}).error(function (e) {
		console.log(e);
	})
}
function agregar(){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' onblur='Rut(this.value)' id='rut' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Razon Social</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Propio</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='propio'>";
	pop += 				getPropios();
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
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Dirección</h5>";
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
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarDis()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Distribuidor", pop, true, "950px", true);
	selectCss()
}
function getRegionSelect(region){
	var option = "<option value=''></option>";
	console.log($REGIONES)
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
	console.log($PROVINCIAS)
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
		            {value: $("#propio").val()},
		            {value: $("#comuna").val()},
		            {value: $("#direccion").val()},
		            {value: 0},
		            {value: -1},
		            {value: $("#correo").val()},
		            {value: $("#fono").val()}
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
						getDistribuidor();
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
	if(datosExcel){
		var datos = {
			nombreArchivo: "Sub Distribuidores",
			nombreHoja: "Sub Distribuidores",
			data: datosExcel
		}
		loading.show();
		setTimeout(function(){ 
			$.ajax({
				url : "/recotec/json/AGRO/EXCEL_RECOTEC",
				type : "PUT",
				data : JSON.stringify(datos),
				processData: false,
				contentType: false,
				dataType: "text",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success : function(data, textStatus, jqXHR) {
					window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_LISTADO/"+data,"_blank");
					loading.hide();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					swal({
						  title: "Error!",
						  text: "No se ha podido generar el documento, consulte con el administrador del sistema",
						  type: "error",
						  confirmButtonText: "Aceptar"
					});
					loading.hide();
				}
			});
		}, 10);
	}
}