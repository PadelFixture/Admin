var x = 0;
getReceptores();
var datosExcel;
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
		url: "/recotec/json/RECOTEC",
		method: 'PUT',
		dataType: 'json',
		data: JSON.stringify(input),
		async: false, 
	}).success(function (response) {
		console.log(response);
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#subdistribuidor").html(vendedor);
		
	}).error(function (e) {
		console.log(e);
	})
}
function buscar(){
	getStock($(this).val());
};

var tabla;
function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var receptor = $("#subdistribuidor").val();
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	if(!receptor || !fechahasta || !fechahasta){
		return;
	}
	console.log(receptor)
	receptor = receptor == undefined? USER.COD_RECEPTOR: receptor;
	if(receptor*1 == 0){
		receptor = USER.PERFIL == 1? receptor: USER.COD_RECEPTOR;
	}
	var input = {
        SP: "CONSULTA_STOCK_DIS",
        FILTERS: [
            {value: receptor},
            {value: 1},
            {value: USER.PERFIL == 1? 0: USER.PADRE},
            {value: fechadesde},
            {value: fechahasta}
        ]
    };
	console.log(input);
	loading.show();
	setTimeout(function(){
	$.ajax({
		url: "/recotec/json/RECOTEC",
		method: 'PUT',
		dataType: 'json',
		data: JSON.stringify(input),
	}).success(function (response) {
		console.log(response);
		var datos = [];
		$.each(response.data, function(k,v){
			var tbl = [v.RAZON_SOCIAL, v.factura, v.iccid, v.material, v.imei, v.imsi, v.fecha_doc, v.FECHA ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["Sub Distribuidor","Factura", "Iccid","Material","Imei","Imsi","Fecha Documento", "Fecha Recepción"];
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
			nombreArchivo: "Stock SubDistribuidores",
			nombreHoja: "Stock SubDistribuidores",
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