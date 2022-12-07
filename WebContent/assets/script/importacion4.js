var rowObj;
var tabla;
console.log(Number.parseFloat(895601E+19).toFixed(2))
$("#fileImport").change(function(){
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
		        var jsonObj = JSON.stringify(rowObj);
		        console.log(JSON.stringify(rowObj[0]))
		        jsonObj = jsonObj.split(".").join("");
		        rowObj = JSON.parse(jsonObj);
		        loading.hide();
	        })
	        $.each(rowObj, function(k,v){
	        	v.PERIODO = v.PERIODO != undefined? v.PERIODO:" ";
	        	v.NOMBRE_MES = v.NOMBRE_MES != undefined? v.NOMBRE_MES:" ";
	        	v.SUC_VENTA = v.SUC_VENTA != undefined? v.SUC_VENTA:" ";
	        	v.NOMBRE_PDV = v.NOMBRE_PDV != undefined? v.NOMBRE_PDV:" ";
	        	v.DESCR_MOVIL = v.DESCR_MOVIL != undefined? v.DESCR_MOVIL:" ";
	        	v.Canal = v.Canal != undefined? v.Canal:" ";
	        	v.REGION = v.REGION != undefined? v.REGION:" ";
	        	v.CIUDAD = v.CIUDAD != undefined? v.CIUDAD:" ";
	        	v.COMUNA = v.COMUNA != undefined? v.COMUNA:" ";
	        	v.JEFE_DE_NEGOCIOS = v.JEFE_DE_NEGOCIOS != undefined? v.JEFE_DE_NEGOCIOS:" ";
	        	v.SOCIO = v.SOCIO != undefined? v.SOCIO:" ";
	        	v.DESC_SEGMENTO = v.DESC_SEGMENTO != undefined? v.DESC_SEGMENTO:" ";
	        	v.TIPO_PRODUCTO = v.TIPO_PRODUCTO != undefined? v.TIPO_PRODUCTO:" ";
	        	v.PORTABILIDAD = v.PORTABILIDAD != undefined? v.PORTABILIDAD:" ";
	        	v.FECHA_NEGOCIO = v.FECHA_NEGOCIO != undefined? v.FECHA_NEGOCIO:" ";
	        	v.NOMBRE_DIA = v.NOMBRE_DIA != undefined? v.NOMBRE_DIA:" ";
	        	v.HORA_NEGOCIO = v.HORA_NEGOCIO != undefined? v.HORA_NEGOCIO:" ";
	        	v.NUM_NEGOCIO = v.NUM_NEGOCIO != undefined? v.NUM_NEGOCIO:" ";
	        	v.SISTEMA = v.SISTEMA != undefined? v.SISTEMA:" ";
	        	v.COD_TIPONEGOCIO = v.COD_TIPONEGOCIO != undefined? v.COD_TIPONEGOCIO:" ";
	        	v.TIPONEGOCIO = v.TIPONEGOCIO != undefined? v.TIPONEGOCIO:" ";
	        	v.GRUPO_NEGOCIO = v.GRUPO_NEGOCIO != undefined? v.GRUPO_NEGOCIO:" ";
	        	v.GERENCIA = v.GERENCIA != undefined? v.GERENCIA:" ";
	        	v.GRUPO_CANAL = v.GRUPO_CANAL != undefined? v.GRUPO_CANAL:" ";
	        	v.CLUSTER = v.CLUSTER != undefined? v.CLUSTER:" ";
	        	v.TIPO_ZONA = v.TIPO_ZONA != undefined? v.TIPO_ZONA:" ";
	        	v.COD_JNZ = v.COD_JNZ != undefined? v.COD_JNZ:" ";
	        	v.CONTROLLER = v.CONTROLLER != undefined? v.CONTROLLER:" ";
	        	v.KAM_COM = v.KAM_COM != undefined? v.KAM_COM:" ";
	        	v.KAM_OP = v.KAM_OP != undefined? v.KAM_OP:" ";
	        	v.RUT_SOCIO = v.RUT_SOCIO != undefined? v.RUT_SOCIO:" ";
	        	v.RUT_VENDEDOR = v.RUT_VENDEDOR != undefined? v.RUT_VENDEDOR:" ";
	        	v.DESC_CARGO = v.DESC_CARGO != undefined? v.DESC_CARGO:" ";
	        	v.RUT_CLIENTE = v.RUT_CLIENTE != undefined? v.RUT_CLIENTE:" ";
	        	v['RUT repetido'] = v['RUT repetido'] != undefined? v['RUT repetido']:" ";
	        	v.NOMBRE_CLIENTE = v.NOMBRE_CLIENTE != undefined? v.NOMBRE_CLIENTE:" ";
	        	v.COD_GRUPOCLIENTE = v.COD_GRUPOCLIENTE != undefined? v.COD_GRUPOCLIENTE:" ";
	        	v.DESC_GRUPOCLIENTE = v.DESC_GRUPOCLIENTE != undefined? v.DESC_GRUPOCLIENTE:" ";
	        	v.COD_TIPOCLIENTE = v.COD_TIPOCLIENTE != undefined? v.COD_TIPOCLIENTE:" ";
	        	v.DESC_TIPOCLIENTE = v.DESC_TIPOCLIENTE != undefined? v.DESC_TIPOCLIENTE:" ";
	        	v.BSCS_NUEVO = v.BSCS_NUEVO != undefined? v.BSCS_NUEVO:" ";
	        	v.NRO_MOVIL = v.NRO_MOVIL != undefined? v.NRO_MOVIL:" ";
	        	v.NEGOCIO = v.NEGOCIO != undefined? v.NEGOCIO:" ";
	        	v.MER_ORIGEN = v.MER_ORIGEN != undefined? v.MER_ORIGEN:" ";
	        	v.F_PAGO = v.F_PAGO != undefined? v.F_PAGO:" ";
	        	v.CODI_PLAN = v.CODI_PLAN != undefined? v.CODI_PLAN:" ";
	        	v.DESC_PLAN = v.DESC_PLAN != undefined? v.DESC_PLAN:" ";
	        	v.SEGMENTO_PLAN = v.SEGMENTO_PLAN != undefined? v.SEGMENTO_PLAN:" ";
	        	v.CODIGO_PRODUCTO = v.CODIGO_PRODUCTO != undefined? v.CODIGO_PRODUCTO:" ";
	        	v.MODELO = v.MODELO != undefined? v.MODELO:" ";
	        	v.PATENTE = v.PATENTE != undefined? v.PATENTE:" ";
	        	v.IMSI = v.IMSI != undefined? v.IMSI:" ";
	        	v.ICCID = v.ICCID != undefined? v.ICCID:" ";
	        	v.ID_MOVIL = v.ID_MOVIL != undefined? v.ID_MOVIL:" ";
	        	v.DESC_LDA = v.DESC_LDA != undefined? v.DESC_LDA:" ";
	        	v.ID_TIPOPRODUCTO = v.ID_TIPOPRODUCTO != undefined? v.ID_TIPOPRODUCTO:" ";
	        	v.DESC_GESTION = v.DESC_GESTION != undefined? v.DESC_GESTION:" ";
	        	v.DESC_NEGOCIO = v.DESC_NEGOCIO != undefined? v.DESC_NEGOCIO:" ";
	        	v.DESC_MOVIMIENTO_ACC = v.DESC_MOVIMIENTO_ACC != undefined? v.DESC_MOVIMIENTO_ACC:" ";
	        	v.DESC_TIPO_MOVIMIENTO = v.DESC_TIPO_MOVIMIENTO != undefined? v.DESC_TIPO_MOVIMIENTO:" ";
	        	v.GRUPO_CANAL_DISTR = v.GRUPO_CANAL_DISTR != undefined? v.GRUPO_CANAL_DISTR:" ";
	        	v.CANAL_DISTR = v.CANAL_DISTR != undefined? v.CANAL_DISTR:" ";
	        	v.SOCIO_DISTR = v.SOCIO_DISTR != undefined? v.SOCIO_DISTR:" ";
	        	v.FLAG_ACTIVADO = v.FLAG_ACTIVADO != undefined? v.FLAG_ACTIVADO:" ";
	        })
	        var header = [];
	        $.each(rowObj[0], function(key,value){
	        	var obj = {}
                obj["h"] = key;
                obj["d"] = key.split("_").join(" ");
                header.push(obj);
	        })
	        var jsonDatos = [];
	        $.each(rowObj, function(k,v){
	        	var json = [];
	        	$.each(header, function(ka,va){
	        		json.push(v[va.h]);
	        	})
		    	jsonDatos.push(json)
		    })
	        if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			var columnas = [];
			$.each(header, function(k,v){
				columnas.push(v.d);
			})
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