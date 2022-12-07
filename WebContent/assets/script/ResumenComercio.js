var x = 0;
getReceptores();
/*$('#periodo').datepicker({
	dateFormat:'yy-mm',
	firstDay :1,
	changeMonth : true,
	changeYear: true
	});
$("#simpleAgro").addClass("page-sidebar-closed");
$(".page-sidebar-menu").addClass("page-sidebar-menu-closed")*/
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
}
function buscar(){
	getStock($(this).val());
};

var tabla;
var datosExcel;
var datos;
var columnas;
function buscar(){
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	$.fn.dataTable.ext.errMode = 'none';
	var periodo = $("#periodo").val();
	var input = "";
	if(USER.TIPO_RECEPTOR == 0){
		input = {
		        SP: "GET_HABILITACIONES_PERMANENCIA_2",
		        FILTERS: [
		            {value: fechadesde},
		            {value: fechahasta},
		            {value: $("#tipo").val()},
		            {value: 0},
		            {value: 0},
		            {value: 0},
		            {value: 0}
		        ]
		    };
	}
	if(USER.TIPO_RECEPTOR == 1){
		input = {
		        SP: "GET_HABILITACIONES_PERMANENCIA_SUB_2",
		        FILTERS: [
					{value: fechadesde},
					{value: fechahasta},
		            {value: $("#tipo").val()},
		            {value: USER.COD_RECEPTOR},
		           
		            
		        ]
		    };
	}
	
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
			var tbl = [v.id, v.COMERCIO, v.VENDIDOS,v.HABILITADOS*1]; 
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["ID","Comercio","Ventas","Hab"];
		$('#tbl_RendimientoVlidadr').html('<tfoot><tr><th style="text-align:right">Total:</th><th></th><th></th><th></th></tr></tfoot>')
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			//autoWidth: false,
			ordering: true,
			//fixedHeader: true,
			orderCellsTop: true,
			//"scrollX": true,
//			responsive: true,
	        paging: true,
			dom: 'Bfrtip',
			//"pageLength": 100,
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
	            tColumn2 = api.column(2).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(2).footer() ).html(tColumn2);
	            
	            tColumn3 = api.column(3).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            console.log(tColumn3);
	            $( api.column(3).footer() ).html(tColumn3);
	        }
		});
		datosExcel = getDataExcel(columnas, datos);
		$("#tbl_RendimientoVlidadr_filter").hide();
		$("#tbl_RendimientoVlidadr_length").hide();
		
//		$('#tbl_RendimientoVlidadr thead tr').clone(true).appendTo( '#tbl_RendimientoVlidadr thead' );
//	    $('#tbl_RendimientoVlidadr thead tr:eq(1) th').each( function (i) {
//	    	if($(this).text() != "" && $(this).text() != "Detalle"){
//	    		var title = $(this).text();
//	            $(this).html( '<input type="text" class="form-control input-sm" placeholder="'+title+'" />' );
//	     
//	            $( 'input', this ).on( 'keyup change', function () {
//	                if ( tabla.column(i).search() !== this.value ) {
//	                	tabla.column(i).search( this.value ).draw();
//	                }
//	            } );
//	    	}else{
//	    		$(this).html("");
//	    	}
//	    } );
	    //$('tbody').css('height', '500px');
	    //new $.fn.dataTable.FixedHeader( tabla );
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