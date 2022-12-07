var x = 0;
getReceptores();
var datosExcel;
var vendedor ;
var datosVendedor;
function getReceptores(){
	vendedor = "<option value='0'>TODO</option>"
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
	console.log(input);
	console.log(USER)
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		async : false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		datosVendedor = response.data;
		$.each(response.data, function(k,v){
			if(USER.PERFIL == 1){
				vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
			}else{
				if(v.id*1 == USER.COD_RECEPTOR){
					vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
				}
			}
		})
		
	}).error(function (e) {
		console.log(e);
	})
	console.log(vendedor)
	$("#subdistribuidor").html(vendedor);
	getDistribuidor(0);
	return vendedor;
}
$("#subdistribuidor").change(function(){
	getDistribuidor($(this).val());
});

var tabla;
function getDistribuidor(padre){
	$.fn.dataTable.ext.errMode = 'none';
	if(USER.PERFIL == 1){
		var input = {
	        SP: "GET_RECEPTOR",
	        FILTERS: [
	            {value: 2},
	            {value: 0},
	            {value: padre}
	        ]
	    };
	} else {
		var input = {
		        SP: "GET_RECEPTOR",
		        FILTERS: [
		            {value: 2},
		            {value: 0},
		            {value: USER.COD_RECEPTOR}
		        ]
		    };
	}
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
			var tbl = [v.id, v.padre, v.rut, v.RAZON_SOCIAL ,  v.CORREO, v.TELEFONO,
			           "<button title='Editar' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
			           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["ID", "Subdistribuidor","Rut", "Nombre","Correo", "Telefono","Opciones"];
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
	console.log($v)
	console.log(datosVendedor)
	var pop = "";
//	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
//	pop += 		"<div class='col-xs-6 portlet'>";
//	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Sub Distribuidor</h5>";
//	pop += 			"<select class='form-control input-sm required-modal' id='subdispop'>"+vendedor+"</select>";
//	pop += 		"</div>";
//	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' id='rut' onblur='Rut(this.value)' value='"+$v.rut+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value='"+$v.RAZON_SOCIAL+"'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-6 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Correo</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' value='"+$v.CORREO+"' id='correo'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Telefono</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' value='"+$v.TELEFONO+"' id='fono'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Vendedor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='padreVendedor'>";
	pop += 				getReceptores();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='modificar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Modificar Datos de "+$v.RAZON_SOCIAL, pop, true, "650px", true);
	selectCss();
	console.log($v);
	$("#padreVendedor").val($v.padreid).trigger("change");
//	var RAZON_SOCIAL;
//	$.each(datosVendedor, function(k,v){
//		if($v.PADRE == v.RAZON_SOCIAL){
//			RAZON_SOCIAL = v.id;
//		}
//	})
//	console.log(RAZON_SOCIAL)
//	$("#subdispop").val(RAZON_SOCIAL).trigger("change");
}
function eliminar($v){
	var c = confirmar.confirm("¿Seguro quiere eliminar los datos de "+$v.RAZON_SOCIAL+"?");
	$(c.aceptar).click(function(){
		setTimeout(function(){ 
			var input = {
				TABLE: "RECEPTOR",
				SET:{ESTADO: 2},
				WHERE: {id: $v.id}
			};
			console.log(input)
			Update(input).then(function(res){
				console.log(res)
				setTimeout(function(){ 
					getDistribuidor($("#subdistribuidor").val());
				}, 50);
			})
		}, 50);
	})
}
function modificar($v){
	var input = {
        SP: "UPDATE_RECEPTOR_WEB",
        FILTERS: [
            {value: $v.id},
            {value: $("#rut").val()},
            {value: $("#razon_social").val()},
            {value: 0},//propio
            {value: 0},//comuna
            {value: 0},//direccion
            {value: $("#correo").val()},
            {value: $("#fono").val()},
            {value: $("#padreVendedor").val()}
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
	if($("#subdistribuidor").val()*1 == 0){
		alert("Debe seleccionar un Sub Distribuidor");
		return false;
	}
	var pop = "";
	// TODO
//	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
//	pop += 		"<div class='col-xs-6 portlet'>";
//	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Sub Distribuidor</h5>";
//	pop += 			"<select class='form-control input-sm required-modal' id='subdispop'>"+vendedor+"</select>";
//	pop += 		"</div>";
//	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Rut</h5>";
	pop += 			"<input class='form-control required-modal' onblur='Rut(this.value)' id='rut' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input class='form-control required-modal' id='razon_social' value=''>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-6 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Correo</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal'  id='correo'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Telefono</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal'  id='fono'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarDis()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Distribuidor", pop, true, "650px", true);
	selectCss();
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
		            {value: 0},//propio
		            {value: 0},//comuna
		            {value: 0},//direccion
		            {value: 2},
		            {value: $("#subdistribuidor").val()},
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
						getDistribuidor($("#subdistribuidor").val());
					}, 50);
				}
				
			}).error(function (e) {
				console.log(e);
			})
	}, 50);
}
function generarExcelPulento(){
	if(datosExcel){
		var datos = {
			nombreArchivo: "Stock Vendedor",
			nombreHoja: "Stock Vendedor",
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