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
function buscar(){
	getStock($(this).val());
};

var tabla;
var datosExcel;
var datos;
var columnas;
function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	var sub = $("#subdistribuidor").val();
	var dis = USER.COD_RECEPTOR;
	console.log(USER);
	if(USER.TIPO_RECEPTOR == 1){
		sub = USER.COD_RECEPTOR;
		dis = USER.PADRE;
	}
	var input = {
        SP: "CONSULTA_FACTURAS_SUB",
        FILTERS: [
            {value: sub},
            {value: $("#tipo").val()},
            {value: dis},
            {value: fechadesde},
            {value: fechahasta},
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
			var tbl = [v.PROVEEDOR, v.DISTRIBUIDOR, v.SUBDISTRIBUIDOR,v.FACTURA, v.fecha_doc, v.FECHA, v.Q_RECEPCION, v.HABILITADOS,v.RECARGA_30_60, v.PENDIENTES_HABILITAR, v.Q ];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["Proveedor", "Distribuidor", "Sub Distribuidor","Factura","Fecha Documento", "Fecha","Cantidad Recepcion","Habilitados","Recargas al 30-60" ,"Pendientes X Habilitar","QPDV"];
		$('#tbl_RendimientoVlidadr').html('<tfoot><tr><th colspan="6" style="text-align:right">Total:</th><th></th><th></th><th></th><th></th></tr></tfoot>')
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
	            tQRecepcon = api.column(6).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            yHabilitados = api.column(7).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            tpHabilitados = api.column(8).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
//	            tCantidad = api.column(8).data().reduce( function (a, b) {
//                    return intVal(a) + intVal(b);
//                }, 0 );
	            $( api.column(6).footer() ).html(tQRecepcon);
	            $( api.column(7).footer() ).html(yHabilitados);
	            $( api.column(8).footer() ).html(tpHabilitados);
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
				SHEET: "Habilitados Sub Distribuidor",
				FILE: "Habilitados Sub Distribuidor"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}