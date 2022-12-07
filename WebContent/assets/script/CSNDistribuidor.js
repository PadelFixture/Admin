var x = 0;
getReceptores();
function getReceptores(){
	var vendedor = "<option value='0'>TODO</option>";
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 1},
            {value: 0},
            {value: USER.PERFIL == 1?0:USER.COD_RECEPTOR}
        ]
    };
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
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		
	}).error(function (e) {
		console.log(e);
	})
	$("#subdistribuidor").html(vendedor);
	//getStock(0);
}
function buscar(){
	getStock($(this).val());
};

var tabla;
var datosExcel;
function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	var input = {
        SP: "CONSULTA_STOCK_NEW_DIS",
        FILTERS: [
            {value: USER.COD_RECEPTOR},
            {value: 0},
            {value: 0},
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
			var tbl = [v.DISTRIBUIDOR, v.CANTIDAD];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["Dsitribuidor", "Cantidad"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false,
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
			nombreArchivo: "Stock Distribuidores",
			nombreHoja: "Stock Distribuidores",
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