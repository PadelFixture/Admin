var x = 0;
getReceptores();
$('#periodo').datepicker({
	dateFormat:'yy-mm',
	firstDay :1,
	changeMonth : true,
	changeYear: true
	});
$("#simpleAgro").addClass("page-sidebar-closed");
$(".page-sidebar-menu").addClass("page-sidebar-menu-closed")
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
	$.fn.dataTable.ext.errMode = 'none';
	var periodo = $("#periodo").val();
	var input = "";
	if(USER.TIPO_RECEPTOR == 0){
		input = {
		        SP: "GET_HABILITACIONES_PERMANENCIA",
		        FILTERS: [
		            {value: periodo.split("-").join("")},
		            {value: $("#tipo").val()}
		        ]
		    };
	}
	if(USER.TIPO_RECEPTOR == 1){
		input = {
		        SP: "GET_HABILITACIONES_PERMANENCIA_SUB",
		        FILTERS: [
		            {value: periodo.split("-").join("")},
		            {value: $("#tipo").val()},
		            {value: USER.COD_RECEPTOR}
		            
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
			var tbl = [];
			if(USER.PERFIL != 4){
				tbl = [v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR,v.VENDEDOR, v.COMERCIO,v.STOCK, v.VENDIDOS,v.HABILITADOS*1, v.RECARGAS_0_30*1, v.CANTIDAD_RACARGAS_0_30*1,
				           v.MONTO_0_30*1, v.RECARGAS_30_60*1, v.CANTIDAD_RACARGAS_30_60*1, v.MONTO_30_60*1, v.RECARGAS_60_90*1, v.CANTIDAD_RACARGAS_60_90*1, v.MONTO_60_90*1];
			} else {
				tbl = [v.DISTRIBUIDOR, v.COMERCIO,v.STOCK, v.VENDIDOS,v.HABILITADOS*1, v.RECARGAS_0_30*1, v.CANTIDAD_RACARGAS_0_30*1,
			           v.MONTO_0_30*1, v.RECARGAS_30_60*1, v.CANTIDAD_RACARGAS_30_60*1, v.MONTO_30_60*1, v.RECARGAS_60_90*1, v.CANTIDAD_RACARGAS_60_90*1, v.MONTO_60_90*1];
			}
			
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		if(USER.PERFIL != 4){
			columnas = ["Distribuidor", "Sub Distribuidor","Vendedor","Comercio","Stock","Ventas","Hab", "Rec 0-30","Q Rec 0-30","$ Rec 0-30"
		            , "Rec 30-60","Q Rec 30-60","$ Rec 30-60"
		            , "Rec 60-90","Q de Rec 60-90","$ Rec 60-90"];
		} else {
			columnas = ["Distribuidor", "Comercio","Stock","Ventas","Hab", "Rec 0-30","Q Rec 0-30","$ Rec 0-30"
			            , "Rec 30-60","Q Rec 30-60","$ Rec 30-60"
			            , "Rec 60-90","Q de Rec 60-90","$ Rec 60-90"];
		}
		if(USER.PERFIL != 4){
			$('#tbl_RendimientoVlidadr').html('<tfoot><tr><th></th><th></th><th></th><th style="text-align:right">Total:</th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>')
		} else {
			$('#tbl_RendimientoVlidadr').html('<tfoot><tr><th></th><th style="text-align:right">Total:</th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>')
		}
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			//autoWidth: false,
			ordering: true,
			fixedHeader: true,
			orderCellsTop: true,
			//"scrollX": true,
//			responsive: true,
	        paging: false,
			dom: 'Bfrtip',
			"pageLength": 100,
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
	            
	            var x = 4;
	            if(USER.PERFIL == 4){
	            	x = 2;
	            }
	            
	            tColumn2 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn2);
	            x++;
	            
	            tColumn3 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn3);
	            x++;
	            
	            tColumn4 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn4);
	            x++;
	            
	            tColumn5 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn5);
	            x++;
	            
	            tColumn6 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn6);
	            x++;
	            
	            tColumn7 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn7);
	            x++;
	            
	            tColumn8 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn8);
	            x++;
	            
	            tColumn9 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn9);
	            x++;
	            
	            tColumn10 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn10);
	            x++;
	            
	            tColumn11 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn11);
	            x++;
	            
	            tColumn12 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn12);
	            x++;
	            
	            tColumn13 = api.column(x).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(x).footer() ).html(tColumn13);
	            x++;

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