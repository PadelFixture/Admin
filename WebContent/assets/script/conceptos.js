$.ajax({
	url: "/recotec/json/REC/GET_TIPO_CONCEPTO",
	type:	"GET",
	dataType: 'json',
	async: false,
	beforeSend : function(xhr) {
		xhr.setRequestHeader("Accept","application/json");
		xhr.setRequestHeader("Content-Type","application/json");
	},
	success: function(data){
		console.log(data)
		var tipo_concepto = "<option value=''></option>";
		$.each(data, function(k,v){
			tipo_concepto += "<option value='"+v.TIPO+"'>"+v.TIPO+"</option>";
		})
		$("#tipo_concepto").html(tipo_concepto)
	}
})
var tabla;
var datos2;
var columnas;
function cambioTipo($v){
	var input = {
        SP: "GET_CONCEPTOS",
        FILTERS: [
            {value: $v}
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		data: JSON.stringify(input),
		async: false,
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response)
		var datos = [];
		datos2 = [];
		$.each(response.data, function(k,v){
			var tbl2 = [v.id, v.TIPO, v.DESCRIPCION];
			datos2.push(tbl2);
			var tbl = [v.id, v.TIPO, v.DESCRIPCION, 
			           "<button title='Editar' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
			           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["Codigo","Tipo", "Descripcion", "Opciones"];
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
		$("#tbl_RendimientoVlidadr_filter").hide();
		$("#tbl_RendimientoVlidadr_length").hide();

		$('#tbl_RendimientoVlidadr thead tr').clone(true).appendTo( '#tbl_RendimientoVlidadr thead' );
	    $('#tbl_RendimientoVlidadr thead tr:eq(1) th').each( function (i) {
	    	if($(this).text() != "" && $(this).text() != "Opciones"){
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
function eliminar($v){
	var c = confirmar.confirm("¿Seguro quiere eliminar los datos de "+$v.DESCRIPCION+"?");
	$(c.aceptar).click(function(){
		setTimeout(function(){ 
			var input = {
		        _ID: $v.id,
		    };
			console.log(input)
		    $.ajax({
				url: '/recotec/json/REC/DELETE_CONCEPTO',
				method: 'PUT',
				dataType: 'json',
				data:JSON.stringify(input),
			}).success(function (response) {
				console.log(response);
				closeModal();
				loading.hide();
				setTimeout(function(){ 
					cambioTipo($("#tipo_concepto").val());
				}, 50);
			}).error(function (e) {
				console.log(e);
			})
		}, 50);
	})
}
function editar($v){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-12 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Descripcion</h5>";
	pop += 			"<input class='form-control required-modal' id='descripcion' value='"+$v.DESCRIPCION+"'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='modificar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Modificar Datos de "+$v.DESCRIPCION, pop, true, "450px", true);
	selectCss();
}
function agregar(){
	if(!$("#tipo_concepto").val()){
		return;
	}
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-12 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Descripcion</h5>";
	pop += 			"<input class='form-control required-modal' id='descripcion'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='addConcepto()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Concepto a "+$("#tipo_concepto").val(), pop, true, "450px", true);
	selectCss();
}
function modificar($v){
	if(!$("#descripcion").val()){
		return;
	}
	loading.show();
	setTimeout(function(){ 
	var input = {
        _ID: $v.id,
        DESCRIPCION: $("#descripcion").val(),
    };
	console.log(input)
    $.ajax({
		url: '/recotec/json/REC/UPDATE_CONCEPTOS',
		method: 'PUT',
		dataType: 'json',
		data:JSON.stringify(input),
	}).success(function (response) {
		console.log(response);
		closeModal();
		setTimeout(function(){ 
			cambioTipo($("#tipo_concepto").val());
		}, 50);
		loading.hide();
	}).error(function (e) {
		console.log(e);
	})
	}, 50);
}
function addConcepto(){
	if(!$("#descripcion").val()){
		return;
	}
	loading.show();
	setTimeout(function(){ 
	var input = {
        TIPO: $("#tipo_concepto").val(),
        DESCRIPCION: $("#descripcion").val(),
    };
	console.log(input)
    $.ajax({
		url: '/recotec/json/REC/ADD_CONCEPTO',
		method: 'PUT',
		dataType: 'json',
		data:JSON.stringify(input),
	}).success(function (response) {
		console.log(response);
		closeModal();
		setTimeout(function(){ 
			cambioTipo($("#tipo_concepto").val());
		}, 50);
		loading.hide();
	}).error(function (e) {
		console.log(e);
	})
	}, 50);
}
function generarExcelPulento(){
	if(datos2){
		var col = columnas.splice(columnas.length-1, 1)
		var input = {
			HEADER: columnas,
			DATA: datos2,
			NAMES:{
				SHEET: $("#tipo_concepto").val(),
				FILE: "Usuarios"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}