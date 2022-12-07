console.log(USER);
var new_receptor = 1;
var receptor = "";
if(USER.PERFIL == 1){
	getSubdistribuidor();
	new_receptor = 1;
	receptor = "subdistribuidor";
}
if(USER.PERFIL == 2){
	getVendedor();
	new_receptor = 2;
	receptor = "vendedor";
}
if(USER.PERFIL == 3){
	getComercios();
	new_receptor = 3;
	receptor = "comercio";
}
function getSubdistribuidor(){
	var vendedor = "";
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 1},
            {value: 0},
            {value: USER.COD_RECEPTOR}
        ]
    };
	console.log(input);
	console.log(USER)
	$.ajax({	
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		async : false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		$.each(response.data, function(k,v){
			if(USER.PERFIL == 1){
				vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
			}else{
				if(v.id*1 == USER.COD_RECEPTOR){
					vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
				}
			}
		})
		
	}).error(function (e) {
		console.log(e);
	})
	$("#subdistribuidor").html(vendedor);
	$("#divSubdistribuidor").show();
}
function getVendedor(){
	var vendedor = "";
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 2},
            {value: 0},
            {value: USER.COD_RECEPTOR}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		async:false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response.data);
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		
	}).error(function (e) {
		console.log(e);
	})
	console.log(vendedor);
	$("#vendedor").html(vendedor);
	$("#divVendedor").show();
}

function getComercios(){
	var vendedor = "";
	var input = {
	        SP: "GET_COMERCIO",
	        FILTERS: [
	            {value: 0},
	            {value: USER.COD_RECEPTOR},
	            {value: 0},
	            {value: 0}
	            
	        ]
	    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		async:false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response.data);
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		
	}).error(function (e) {
		console.log(e);
	})
	console.log(vendedor);
	$("#comercio").html(vendedor);
	$("#divComercio").show();
}
var tabla;
function cargar(){
	$.fn.dataTable.ext.errMode = 'none';
	if($("#desde").val() == "" ||  $("#hasta").val() == ""){
		alert("Debe completar desde y hasta");
		return false;
	}
	var input = {
        SP: "GET_RANGO_SIM",
        FILTERS: [
            {value: $("#desde").val()},
            {value: $("#hasta").val()},
            {value: USER.COD_RECEPTOR}
        ]
    };
	console.log(input);
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
			var tbl = [v.imei, v.iccid, v.material ,  v.imsi];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["Imei","Iccid", "Tipo","Imsi"];
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
	}).error(function (e) {
		console.log(e);
	})
	$("#vender").show();
}

function vender(){
	
	var input = {
        SP: "MOVER_RANGO",
        FILTERS: [
            {value: $("#desde").val()},
            {value: $("#hasta").val()},
            {value: $("#"+receptor).val()},
            {value: new_receptor},
            {value: USER.COD_RECEPTOR}
        ]
    };
	console.log(input);
	//return;
	loading.show();
	setTimeout(function(){ 
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		async:false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		if(response.data.length > 0){
			loading.hide();
			setTimeout(function(){ 
				 cargar();
			}, 50);
		}	
	}).error(function (e) {
		console.log(e);
	})
	}, 50);
}