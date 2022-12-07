var datosExcel;
var tabla;
var datos;
var columnas;
$('#periodo').datepicker({
	dateFormat:'yy-mm',
	firstDay :1,
	changeMonth : true,
	changeYear: true
	});
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
function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var periodo = $("#periodo").val();
	var input = {
	        SP: "DETALLE_CAMADA",
	        FILTERS: [
	            {value: periodo.split("-").join("")},
	            {value: $("#tipo").val()}
	        ]
	    };
	console.log(input)
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
			var tbl = [v.factura, v.fecha_doc, v.ICCID, v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR, v.VENDEDOR, v.COMERCIO,v.FECHA_HABILITACION ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["Factura","Fecha doc", "ICCID" ,"Distribuidor", "Sub Distribuidor","Vendedor","Comercio","FECHA HABILITACION"];
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