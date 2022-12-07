var input = {
    SP: "GET_CONCEPTOS",
    FILTERS: [
        {value: "TIPO_CARGA"}
    ]
};

$.ajax({
	url: IPSERVER + "post",
	method: 'POST',
	dataType: 'json',
	data: JSON.stringify(input),
	async: false,
	headers: { 'Content-Type': "application/json", 'connection-properties': configService },
	success: function(data){
		console.log(data)
		var tipo_concepto = "<option value=''></option>";
		$.each(data.data, function(k,v){
			tipo_concepto += "<option value='"+v.id+"'>"+v.DESCRIPCION+"</option>";
		})
		$("#tipo_carga").html(tipo_concepto)
	}
})
var rowObj;
var tabla;
console.log(Number.parseFloat(895601E+19).toFixed(2))
$("#fileImport").change(function(){
	if($("#tipo_carga").val() == ""){
		alert("Debe seleccionar tipo carga");
		$("#fileImport").empty();
		//return false;
		location.reload();
	}
//	$.fn.dataTable.ext.errMode = 'none';
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
		        console.log(rowObj);
		        console.log($("#tipo_carga").val());
		        var jsonObj = JSON.stringify(rowObj);
		        jsonObj = jsonObj.split(".").join("");
		        rowObj = JSON.parse(jsonObj);
		        console.log(rowObj)
		        loading.hide();
	        })
	        var jsonDatos = [];
	        var i = 0;
	        $.each(rowObj, function(k,v){
	        	console.log(v);
	        	v.ID_MES = v.ID_MES != undefined? v.ID_MES:" ";
	        	v.ID_DIA_CORTE = v.ID_DIA_CORTE != undefined? getDateFormat(v.ID_DIA_CORTE):" ";
	        	v.TIPO_LINEA = v.TIPO_LINEA != undefined? v.TIPO_LINEA:" ";
	        	v.SUBCATEGORIA = v.SUBCATEGORIA != undefined? v.SUBCATEGORIA:" ";
	        	v.DESCR_MOVIL = v.DESCR_MOVIL != undefined? v.DESCR_MOVIL:" ";
	        	v.ID_IMSI = v.ID_IMSI != undefined? v.ID_IMSI:" ";
	        	v.MODELOEQUIPOACT = v.MODELOEQUIPOACT != undefined? v.MODELOEQUIPOACT:" ";
	        	v.CODIGO_SIM = v.CODIGO_SIM != undefined? v.CODIGO_SIM:" ";
	        	v.BODEGAORI = v.BODEGAORI != undefined? v.BODEGAORI:" ";
	        	v.BODEGA_DEST = v.BODEGA_DEST != undefined? v.BODEGA_DEST:" ";
	        	v.DESC_HABILITACION = v.DESC_HABILITACION != undefined? v.DESC_HABILITACION:" ";
	        	v.ID_IMEI_ORIGINAL_PACK = v.ID_IMEI_ORIGINAL_PACK != undefined? v.ID_IMEI_ORIGINAL_PACK:" ";
	        	v.PATENTE = v.PATENTE != undefined? v.PATENTE:" ";
	        	v.ICCID = v.ICCID != undefined? v.ICCID.toString():" ";
	        	v.COD_PDV = v.COD_PDV != undefined? v.COD_PDV:" ";
	        	v.DESCRIP = v.DESCRIP != undefined? v.DESCRIP:" ";
	        	v.TIPO = v.TIPO != undefined? v.TIPO:" ";
	        	v.BOD_SAP = v.BOD_SAP != undefined? v.BOD_SAP:" ";
	        	v.BOD_ORIG_DEST = v.BOD_ORIG_DEST != undefined? v.BOD_ORIG_DEST:" ";
	        	v.ZONA = v.ZONA != undefined? v.ZONA:" ";
	        	v.GERENCIA = v.GERENCIA != undefined? v.GERENCIA:" ";
	        	v.GRUPO_CANAL = v.GRUPO_CANAL != undefined? v.GRUPO_CANAL:" ";
	        	v.CANAL = v.CANAL != undefined? v.CANAL:" ";
	        	v.SOCIO = v.SOCIO != undefined? v.SOCIO:" ";
	        	v.RUT_SOCIO = v.RUT_SOCIO != undefined? v.RUT_SOCIO:" ";
	        	v.NOMBRE_PDV = v.NOMBRE_PDV != undefined? v.NOMBRE_PDV:" ";
	        	v.KAM_OP = v.KAM_OP != undefined? v.KAM_OP:" ";
	        	v.KAM_COM = v.KAM_COM != undefined? v.KAM_COM:" ";
	        	v.JEFE_NEGOCIOS = v.JEFE_NEGOCIOS != undefined? v.JEFE_NEGOCIOS:" ";
	        	v.CANAL_ORIGINAL = v.CANAL_ORIGINAL != undefined? v.CANAL_ORIGINAL:" ";
	        	v.hab = v.hab != undefined? v.hab:" ";
	        	v.rec = v.rec != undefined? v.rec:" ";
	        	v.SUM_of_Q_RECARGAS = v.SUM_of_Q_RECARGAS != undefined? v.SUM_of_Q_RECARGAS:" ";
	        	v.SUM_of_MONTO = v.SUM_of_MONTO != undefined? v.SUM_of_MONTO:" ";
	        	v.MIN_of_FECHA = v.MIN_of_FECHA != undefined? v.MIN_of_FECHA:" ";
	        	var json = [
	    			v.ID_MES,v.ID_DIA_CORTE,v.TIPO_LINEA,v.SUBCATEGORIA,v.DESCR_MOVIL,v.ID_IMSI,v.MODELOEQUIPOACT,
		    		v.CODIGO_SIM,v.BODEGAORI,v.BODEGA_DEST,v.DESC_HABILITACION,v.ID_IMEI_ORIGINAL_PACK,
		    		v.PATENTE,v.ICCID,v.COD_PDV,v.DESCRIP,v.TIPO,v.TIPO2,v.BOD_SAP,
		    		v.BOD_ORIG_DEST,v.ZONA,v.GERENCIA,v.GRUPO_CANAL,v.CANAL,
		    		v.SOCIO,v.RUT_SOCIO,v.NOMBRE_PDV,v.KAM_OP,v.KAM_COM,v.JEFE_NEGOCIOS,v.CANAL_ORIGINAL
		    	]
		    	jsonDatos.push(json)
		    	rowObj[i].TIPO_IMPORT = $("#tipo_carga").val();
	        	i++;
	        	
		    })
		    console.log(rowObj);
	        if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			var columnas = ["ID_MES", "ID_DIA_CORTE","TIPO_LINEA","SUBCATEGORIA","DESCR_MOVIL","ID_IMSI",
			                "MODELOEQUIPOACT", "CODIGO_SIM","BODEGAORI","BODEGA_DEST","DESC_HABILITACION","ID_IMEI_ORIGINAL_PACK",
			                "PATENTE", "ICCID","COD_PDV","DESCRIP","TIPO","TIPO2",
			                "BOD_SAP", "BOD_ORIG_DEST","ZONA","GERENCIA","GRUPO_CANAL","CANAL","SOCIO",
			                "RUT_SOCIO", "NOMBRE_PDV","KAM_OP","KAM_COM","JEFE_NEGOCIOS","CANAL_ORIGINAL"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
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
//	if(tabla){
//		tabla.destroy();
//        $('#tbl_RendimientoVlidadr').empty(); 
//	}
}
function importar(){
	jsonDatos = [];
	console.log()
	if(!$("#fileImport").val()){
		return;
	}
    console.log(rowObj);
	loading.show();
	setTimeout(function(){
		console.log(jsonDatos)
    	$.ajax({
			url : "/recotec/json/REC/INSERT_VALIDACION_SIM",
			type : "PUT",
			data : JSON.stringify(rowObj),
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
    
		//location.reload();
    
    
    return;
    var input = {
        SP: "INSERT_STOCK_SIM",
        FILTERS: jsonDatos
    };
    $.ajax({
		url: IPSERVER2,
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
	var auxFecha = fecha.split("/");
	var dia = auxFecha[1];
	var mes = auxFecha[0];
	var anno = "20"+auxFecha[2];
	if(auxFecha[0] < 10){
		mes = "0"+auxFecha[0];
	}
	if(auxFecha[1] < 10){
		dia = "0"+auxFecha[1];
	}
	return anno+"-"+mes+"-"+dia;
}
function getColumns(paramData){

	var header = [];
	$.each(paramData[0], function (key, value) {
		//console.log(key + '==' + value);
		var obj = {}
		obj["headertext"] = key;
		obj["datatype"] = "string";
		obj["datafield"] = key;
		header.push(obj);
	}); 
	return header;

}