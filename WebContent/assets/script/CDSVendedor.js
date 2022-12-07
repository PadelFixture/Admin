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
var tabla;
function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var subdistribuidor = $("#subdistribuidor").val();
	var vendedor = $("#vendedor").val();
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	if(!fechahasta || !fechahasta){
		return;
	}
	vendedor = vendedor == undefined? 0: vendedor;
	if(USER.PERFIL == 3){
		subdistribuidor = USER.PADRE;
		vendedor = USER.COD_RECEPTOR;
	}
	var input = {
        SP: "CONSULTA_STOCK_NEW_VENDEDOR",
        FILTERS: [
            {value: vendedor},
            {value: 2},
            {value: subdistribuidor == undefined? USER.COD_RECEPTOR: subdistribuidor},
            {value: ''},
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
		var datos = [];
		$.each(response.data, function(k,v){
			var tbl = [ v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR, v.VENDEDOR, v.CANTIDAD];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		$("#tbl_RendimientoVlidadr").html('<tfoot><tr><th colspan="2" style="text-align:right">Total:</th><th></th><th></th></tfoot>')
		var columnas = ["Distribuidor", "Sub Distribuidor","Vendedor","Cantidad"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
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
	            var cantidadTotal = api.column(3).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(3).footer() ).html(cantidadTotal);
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