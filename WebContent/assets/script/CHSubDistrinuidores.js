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
var datos;
var columnas;
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
        SP: "CONSULTA_HABILITADOS_SUB",
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
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			data: JSON.stringify(input),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
		console.log(response);
		datos = [];
		$.each(response.data, function(k,v){
			tbl = [v.PROVEEDOR,v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR, v.FECHA,v.REQUESTDATE, v.factura, v.iccid, v.material, v.imei, v.imsi, v.fecha_doc ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["Proveedor", "Distribuidor", "Sub Distribuidor","FECHA MOVIMIENTO","FECHA HABILITADO","Factura", "Iccid","Material","Imei","Imsi","Fecha Documento"];
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
	if(datos){
		var input = {
			HEADER: columnas,
			DATA: datos,
			NAMES:{
				SHEET: "Habilitados Sub Distribuidor",
				FILE: "Habilitados Sub Distribuidor"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}