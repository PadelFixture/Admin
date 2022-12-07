getReceptores();
var datosExcel;
var input = {
    SP: "GET_RECEPTOR",
    FILTERS: [
        {value: 0},
        {value: 0},
        {value: 0}
    ]
};
console.log(input);
var vendedor = "<option value='0'>Todos</option>";
$.ajax({
	url: IPSERVER + "post",
	method: 'POST',
	dataType: 'json',
	data: JSON.stringify(input),
	async: false,
	headers: { 'Content-Type': "application/json", 'connection-properties': configService }
}).success(function (response) {
	$.each(response.data, function(k,v){
		vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
	})
	
}).error(function (e) {
	console.log(e);
});

$("#distribuidor").html(vendedor);
$("#fechahasta").val(formatFecha(dateHoy()))
var dia = formatFecha(dateHoy());
dia = dia.split("-");
dia = "01-"+dia[1]+"-"+dia[2];
$("#fechadesde").val(dia);



function getReceptores(){
	var d = new Date();
	var h = addZero(d.getHours());
	var m = addZero(d.getMinutes());
	//$("#horahasta").val(h+":"+m);
	var inputs = [];
	for(var i = 1; i <= 2; i++){
		var input = {
	        SP: "GET_RECEPTOR",
	        FILTERS: [
	            {value: i},
	            {value: 0},
	            {value: 35}
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
var columnas;
var datos;
function buscar(){
	var desde = formatFecha($("#fechadesde").val())+" "+$("#horadesde").val();
	var hasta = formatFecha($("#fechahasta").val())+" "+$("#horahasta").val();
	var dis = $("#distribuidor :selected").text();
	var sub = $("#subdistribuidor").val();
	var ven = $("#vendedor").val();
	var com = $("#comercio").val();
	var input = {
		URL: "VENTAS_ONLINE",
        FILTERS: [{
        	_DESDE: desde,
        	_HASTA: hasta,
        	_DISTRIBUIDOR: dis,
        	_SUBDISTRIBUIDOR: sub,
        	_VENDEDOR: ven,
        	_COMERCIO: com,
        }]
	}
	console.log(input);
	getData(input).then(function(response){
		console.log(response);
		datos = [];
		var t1 = 0;
		var t2 = 0;
		$.each(response.data, function(k,v){
			var total = v["Q SS 9.990"]+v["Q SS 14.990"]+v["Q PORTAS"];
			var toActi = total + v["Q PP"]+v["OTRO"];
			t1 += total;
			t2 += toActi;
			var tbl = [v["COMERCIO"],v["GIRO"],v["DIRECCION"],v["COMUNA"], v["TELEFONO"], v["VENDEDOR"], v["SUBDISTRIBUIDOR"], total, toActi];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		$("#tbl_RendimientoVlidadr").html('<tfoot><tr><th colspan="7" style="text-align:right">Total:</th><th></th><th></th></tr></tfoot>')
		columnas = ["Comercio/Vendedores","Giro","Direccion","Comuna","Contacto","Vendedor","Sub Distribuidor", "Q SS Total", "Total actividad"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			"order": [[ 8, "desc" ]],
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
	            t1 = api.column(7).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            t2 = api.column(8).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
	            $( api.column(7).footer() ).html(t1);
	            $( api.column(8).footer() ).html(t2);
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
	})
}
function generarExcelPulento(){
	if(datos){
		var input = {
			HEADER: columnas,
			DATA: datos,
			NAMES:{
				SHEET: "Ventas Online",
				FILE: "Ventas Online"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}
function addZero(i) {
	  if (i < 10) {
	    i = "0" + i;
	  }
	  return i;
	}