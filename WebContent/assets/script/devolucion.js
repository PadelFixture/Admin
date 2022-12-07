console.log(USER);
var new_receptor = 1;
var receptor = "";
setTimeout(function(){
	$(".toggle-on").text("Vendedor");
	$(".toggle-off").text("Comercio");
	$(".toggle").css("width","164px");
},500)

console.log(USER);
if(USER.TIPO_RECEPTOR == 0){
	$("#divSubdistribuidor").show();
	var input = {
	    SP: "GET_RECEPTOR",
	    FILTERS: [
	        {value: 1},
	        {value: 0},
	        {value: 0}
	    ]
	};
	var vendedor = "";
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		data: JSON.stringify(input),
		async:false,
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

if(USER.TIPO_RECEPTOR == 1){
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 2},
            {value: 0},
            {value: USER.COD_RECEPTOR}
        ]
    };
	var x = 0;
	var pad = 0;
	var comercio = "";
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
			if(x== 0){
				pad = v.id;
			}
			comercio += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
			x++;
		})
		
	}).error(function (e) {
		console.log(e);
	})
	console.log(comercio);
	$("#vendedor").html(comercio);
	$(".divVendedor").show();
	
	
}


$("#check_ingreso").change(function(){
	var input = {
	        SP: "GET_COMERCIO",
	        FILTERS: [
	            {value: 0},
	            {value: $("#vendedor").val()},
	            {value: 0},
	            {value: 0}
	            
	        ] 
	    };
		
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
//			timeout: 10000,
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
		$("#comercio").html(vendedor);
	if($(this).is(':checked')){
		$("#divComercio").hide();
	}else {
		$("#divComercio").show();
	}
});

$("#vendedor").change(function(){
	var input = {
	        SP: "GET_COMERCIO",
	        FILTERS: [
	            {value: 0},
	            {value: $("#vendedor").val()},
	            {value: 0},
	            {value: 0}
	            
	        ] 
	    };
		
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
//			timeout: 10000,
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
		$("#comercio").html(vendedor);
});

var tabla;
function cargar(){
	$.fn.dataTable.ext.errMode = 'none';
	if($("#desde").val() == "" ||  $("#hasta").val() == ""){
		alert("Debe completar desde y hasta");
		return false;
	}
	var id = 0;
	if(USER.TIPO_RECEPTOR == 0){
		id = $("#subdistribuidor").val();
	}
	if(USER.TIPO_RECEPTOR == 1 ){
		if($("#check_ingreso").is(':checked')){
			id = $("#vendedor").val();
		} 
		else
		{
			id = $("#comercio").val();
		}
	
	}
	var input = {
        SP: "GET_RANGO_SIM",
        FILTERS: [
            {value: $("#desde").val()},
            {value: $("#hasta").val()},
            {value: id}
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
	var id = 0;
	var receptor = USER.COD_RECEPTOR;
	if(USER.TIPO_RECEPTOR == 0){
		id = $("#subdistribuidor").val();
	}
	if(USER.TIPO_RECEPTOR == 1 ){
		if($("#check_ingreso").is(':checked')){
			id = $("#vendedor").val();
		} 
		else
		{
			id = $("#comercio").val();
			receptor = $("#vendedor").val();
		}
	
	}
	var input = {
        SP: "Devolver_SIM",
        FILTERS: [
            {value: $("#desde").val()},
            {value: $("#hasta").val()},
            {value: id},
            {value: receptor}
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