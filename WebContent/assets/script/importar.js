var rowObj;
var tabla;
$("#fileImport").change(function(){
	$.fn.dataTable.ext.errMode = 'none';
	var name = $("#fileImport").prop("files")[0].name;
	$("#fImport").html(name);
	$("#desFiles").show();
	$("#bodyExcel").html("");
	var input = event.target;
    var reader = new FileReader();
	loading.show();
	setTimeout(function(){ 
	    reader.onload = function(){
	        var fileData = reader.result;
	        var wb = XLSX.read(fileData, {type : 'binary'});
	        wb.SheetNames.forEach(function(sheetName){
		        rowObj =XLSX.utils.sheet_to_row_object_array(wb.Sheets[sheetName]);
		        var jsonObj = JSON.stringify(rowObj);
		        jsonObj = jsonObj.split(".").join("");
		        rowObj = JSON.parse(jsonObj);
		        //console.log(rowObj)
		        loading.hide();
	        })
	        var jsonDatos = [];
	        $.each(rowObj, function(k,v){
	        	var json = [
	    			 v.Factura,
		    		v.Material,
		    		v.Imei,
		    		v.Iccid,
		    		v.Imsi,
		    		getDateFormat(v.Fdoc)
		    	]
		    	jsonDatos.push(json)
		    })
	        if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			var columnas = ["Factura", "Material","Imei","iccid","Imsi","fecha doc"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			console.log(jsonDatos);
			console.log(finalColumn);
			tabla = $('#tbl_RendimientoVlidadr').DataTable({
				data: jsonDatos,
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
	        
	    };
	    reader.readAsBinaryString(input.files[0]);
	    
	    
	    
	}, 50);
});
var jsonDatos = [];
function desFiles(){
	$("#desFiles").hide();
	$("#fImport").html("Ningun archivo seleccionado");
	$("#fileImport").val("");
}
function importar(){
	jsonDatos = [];
	console.log()
	if(!$("#fileImport").val()){
		return;
	}else if(!$("#subdistribuidor").val()){
		return;
	}
	var vr = JSON.parse($("#subdistribuidor").val());
	$.each(rowObj, function(k,v){
    	var json = {
    		_FACTURA: v.Factura,
    		_MATERIAL: v.Material,
    		_IMEI: v.Imei,
    		_ICCID: v.Iccid,
    		_IMSI: v.Imsi,
    		_FECHA: getDateFormat(v.Fdoc),
    		_RECEPTOR: vr.id,
    		_TIPO_RECEPTOR: vr.TIPO_RECEPTOR
    	}
    	if(!v.Factura){
    		json._FACTURA = 0;
    	}
    	jsonDatos.push(json)
    })
    console.log(jsonDatos);
	loading.show();
	setTimeout(function(){
		console.log(jsonDatos)
    	$.ajax({
			url : "/recotec/json/REC/INSERT_STOCK_SIM",
			type : "PUT",
			data : JSON.stringify(jsonDatos),
			processData: false,
			contentType: false,
			dataType: "text",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success : function(data, textStatus, jqXHR) {
				console.log(data)
				loading.hide();
				alert("Carga exitosa");
			},
			error : function(jqXHR, textStatus, errorThrown) {
				swal({
					  title: "Error!",
					  text: "No se ha podido registrar la infomacion, consulte con el administrador del sistema",
					  type: "error",
					  confirmButtonText: "Aceptar"
				});
				loading.hide();
			}
		});
    
    
    
    
    return;
    var input = {
        SP: "INSERT_STOCK_SIM",
        FILTERS: jsonDatos
    };
    $.ajax({
		url: 'http://200.55.206.139:8081/expled-mvn-api-custom-rest/call-sp-array',
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		data:JSON.stringify(input),
		headers: { 'Content-Type': 'application/json', 'config-properties': 'recotec/sim' }
	}).success(function (response) {
		console.log(response);
		loading.hide();
	}).error(function (e) {
		console.log(e);
	})
	}, 50);
}
getReceptores();
function getReceptores(){
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 1},
            {value: 0},
            {value: 0}
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//		timeout: 10000,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		var vendedor = "<option value=''></option>";
		$.each(response.data, function(k,v){
			vendedor += "<option value='"+JSON.stringify(v)+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#subdistribuidor").html(vendedor);
	}).error(function (e) {
		console.log(e);
	})
}
function getDateFormat(fecha){
	console.log(fecha);
	//var auxFecha = fecha.split("-");
	var dia = fecha.substring(0, 2);
	var mes = fecha.substring(2, 4);
	var anno = fecha.substring(4, 8);;
	/*if(auxFecha[0] < 10){
		mes = "0"+auxFecha[0];
	}
	if(auxFecha[1] < 10){
		dia = "0"+auxFecha[1];
	}*/
	console.log(anno+"-"+mes+"-"+dia);
	return anno+"-"+mes+"-"+dia;
}