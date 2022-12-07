getReceptores();
var datosExcel;
function getReceptores(){
	var inputs = [];
	for(var i = 1; i <= 2; i++){
		var input = {
	        SP: "GET_RECEPTOR",
	        FILTERS: [
	            {value: i},
	            {value: 0},
	            {value: USER.PERFIL == 1?0:USER.COD_RECEPTOR}
	        ]
	    };
		inputs.push(input);
	}
	$.each(inputs, function(k,v){
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			data: JSON.stringify(v),
			async: false,
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response);
			var vendedor = "<option value='0'>Todos</option>";
			$.each(response.data, function(k,v){
				vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
			})
			k+1 == 1?$("#subdistribuidor").html(vendedor):$("#vendedor").html(vendedor);
		}).error(function (e) {
			console.log(e);
		})
	})
	var input = {
        SP: "GET_COMERCIO",
        FILTERS: [
            {value: 0},
            {value: USER.PERFIL == 1?0:USER.COD_RECEPTOR},
            {value: 0},
            {value: USER.PERFIL == 1?0:USER.PADRE}
        ]
    };
	console.log(input)
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		data: JSON.stringify(input),
		async: false,
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		var vendedor = "<option value='0'>Todos</option>";
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#comercio").html(vendedor);
	}).error(function (e) {
		console.log(e);
	})
	var getTipo = {
		SP: "GET_CONCEPTOS",
        FILTERS: [
            {value: 'MATERIAL'},
        ]
	}
	var tipo = "<option value=''></option>";
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		data: JSON.stringify(getTipo),
		async: false, 
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		$.each(response.data, function(k,v){
			tipo += "<option value='"+v.DESCRIPCION+"'>"+v.DESCRIPCION+"</option>";
		})
		
	}).error(function (e) {
		console.log(e);
	})
	$("#tipo").html(tipo);
}
function cambioSub($v){
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 2},
            {value: 0},
            {value: $v == 0? USER.COD_RECEPTOR: $v}
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
		console.log(response);
		var vendedor = "<option value='0'>Todos</option>";
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#vendedor").html(vendedor);
	}).error(function (e) {
		console.log(e);
	})
}
function cambioVendedor($v){
	console.log($v)
	var input = {
        SP: "GET_COMERCIO",
        FILTERS: [
            {value: 0},
            {value: $v},
            {value: 0},
            {value: USER.PERFIL == 1?0:USER.COD_RECEPTOR}
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
		console.log(response);
		var vendedor = "<option value='0'>Todos</option>";
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#comercio").html(vendedor);
	}).error(function (e) {
		console.log(e);
	})
}
var tabla;
var datos;
var columnas;
function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var subdistribuidor = $("#subdistribuidor").val();
	var comercio = $("#comercio").val();
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	var vendedor = $("#vendedor").val();
	if(!comercio || !fechahasta || !fechahasta){
		return;
	}
	var value3 = 0;
	comercio = comercio == undefined? USER.COD_RECEPTOR: comercio;
	vendedor = vendedor == undefined? USER.COD_RECEPTOR: vendedor;
	subdistribuidor = subdistribuidor == undefined? USER.COD_RECEPTOR: subdistribuidor;
//	if(vendedor*1 != 0){
//		subdistribuidor = vendedor;
//	}
//	if(comercio*1 == 0){
//		comercio = USER.PERFIL == 1? 0: USER.COD_RECEPTOR;
//	}
	var dis = USER.PERFIL != 1? 0: USER.COD_RECEPTOR;
	var input = {
        SP: "CONSULTA_FACTURAS_COM",
        FILTERS: [
            {value: $("#comercio").val()},
            {value: $("#tipo").val()},
            {value: vendedor},
            {value: subdistribuidor},
            {value: dis},
            {value: fechadesde},
            {value: fechahasta}
        ]
    };
	console.log(input);
	loading.show();
	setTimeout(function(){
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		datos = [];
		$.each(response.data, function(k,v){
			var tbl = [v.PROVEEDOR, v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR, v.VENDEDOR, v.COMERCIO,v.FACTURA, v.fecha_doc, v.FECHA, v.Q_RECEPCION, v.HABILITADOS,v.RECARGA_30_60, v.PENDIENTES_HABILITAR];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["Proveedor", "Distribuidor", "SubDistribuidor", "Vendedor", "Comercio","Factura","Fecha Documento", "Fecha","Cantidad Recepcion","Habilitados","Recargas al 30-60" ,"Pendientes X Habilitar"];
		$('#tbl_RendimientoVlidadr').html('<tfoot><tr><th colspan="8" style="text-align:right">Total:</th><th></th><th></th><th></th></tr></tfoot>')
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false,
			dom: 'Bfrtip',
		    buttons: [
		        {  extend: 'excel',
		        	footer: true ,
		            text: 'Excel',
		            className: 'btn btn-success',
		        }
		    ],
			footerCallback: function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	            var intVal = function ( i ) {
	                return typeof i === 'string' ?i.replace(/[\$,]/g, '')*1 :
	                typeof i === 'number' ?i : 0;
	            };
	            tQRecepcon = api.column(8).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            yHabilitados = api.column(9).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            tpHabilitados = api.column(10).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
//	            tCantidad = api.column(8).data().reduce( function (a, b) {
//                    return intVal(a) + intVal(b);
//                }, 0 );
	            $( api.column(8).footer() ).html(tQRecepcon);
	            $( api.column(9).footer() ).html(yHabilitados);
	            $( api.column(10).footer() ).html(tpHabilitados);
//	            $( api.column(8).footer() ).html(tCantidad);
	        }
		});
		datosExcel = getDataExcel(columnas, datos);
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
		loading.hide();
	}).error(function (e) {
		console.log(e);
	})
	}, 50);
}
function generarExcelPulento(){
	if(datos){
		var input = {
			HEADER: columnas,
			DATA: datos,
			NAMES:{
				SHEET: "Habilitados Comercio",
				FILE: "Habilitados Comercio"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}