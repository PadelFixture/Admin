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
		        jsonObj = jsonObj.split("-").join("");
		        jsonObj = jsonObj.split("/").join("");
		        rowObj = JSON.parse(jsonObj);
		        //console.log(rowObj)
		        loading.hide();
	        })
	        console.log(rowObj)
	        var jsonDatos = [];
	        if($("#proveedor").val() == "WOM"){
	        	$.each(rowObj, function(k,v){
		        	var json = [
		    			 v.PEDIDO,
			    		v.MATERIAL,
			    		v.CODIGO,
			    		v.SERIE_1,
			    		"",
			    		v.FECHA != undefined?getDateFormat(v.FECHA):""
			    	]
			    	jsonDatos.push(json)
			    })
	        }else{
	        	console.log(rowObj)
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
	        }
	        
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
	console.log(rowObj)
	if(!$("#fileImport").val()){
		return;
	}else if(!$("#subdistribuidor").val()){
		return;
	}
	var vr = JSON.parse($("#subdistribuidor").val());
//	$.each(rowObj, function(k,v){
//		if($("#proveedor").val() == "WOM"){
//			var json = {
//	    		_FACTURA: v.PEDIDO,
//	    		_MATERIAL: v.MATERIAL,
//	    		_IMEI: "",
//	    		_ICCID: v.SERIE_1,
//	    		_IMSI: "",
//	    		_FECHA: getDateFormat(v.FECHA),
//	    		_RECEPTOR: vr.id,
//	    		_TIPO_RECEPTOR: vr.TIPO_RECEPTOR,
//	    		_PROVEEDOR: $("#proveedor").val()
//	    	}
////	    	if(!v.PEDIDO){
////	    		json._FACTURA = 0;
////	    	}
//		}else{
//			var json = {
//	    		_FACTURA: v.Factura,
//	    		_MATERIAL: v.Material,
//	    		_IMEI: v.Imei,
//	    		_ICCID: v.Iccid,
//	    		_IMSI: v.Imsi,
//	    		_FECHA: getDateFormat(v.Fdoc),
//	    		_RECEPTOR: vr.id,
//	    		_TIPO_RECEPTOR: vr.TIPO_RECEPTOR,
//	    		_PROVEEDOR: $("#proveedor").val()
//	    	}
//	    	if(!v.Factura){
//	    		json._FACTURA = 0;
//	    	}
//		}
//    	
//    	jsonDatos.push(json)
//    })
	
	
	var xRow = 0;
	var indexDocument = 0;
	jsonDatos[indexDocument] = [];
	$.each(rowObj, function(k,v){
		if(xRow < 30000){
			if($("#proveedor").val() == "WOM"){
				var json = {
		    		_FACTURA: v.PEDIDO,
		    		_MATERIAL: v.MATERIAL,
		    		_IMEI: "",
		    		_ICCID: v.SERIE_1,
		    		_IMSI: "",
		    		_FECHA: getDateFormat(v.FECHA),
		    		_RECEPTOR: vr.id,
		    		_TIPO_RECEPTOR: vr.TIPO_RECEPTOR,
		    		_PROVEEDOR: $("#proveedor").val()
		    	}
				jsonDatos[indexDocument].push(json)
			}else{
				var json = {
		    		_FACTURA: v.Factura,
		    		_MATERIAL: v.Material,
					_IMEI: v.Imei == undefined?"":v.Imei,
		    		_ICCID: v.Iccid,
		    		_IMSI: v.Imsi,
		    		_FECHA: getDateFormat(v.Fdoc),
		    		_RECEPTOR: vr.id,
		    		_TIPO_RECEPTOR: vr.TIPO_RECEPTOR,
		    		_PROVEEDOR: $("#proveedor").val()
		    	}
		    	if(!v.Factura){
		    		json._FACTURA = 0;
		    	}
				jsonDatos[indexDocument].push(json)
			}
		}else{
			xRow = 0;
			indexDocument++;
			jsonDatos[indexDocument] = [];
			if($("#proveedor").val() == "WOM"){
				var json = {
		    		_FACTURA: v.PEDIDO,
		    		_MATERIAL: v.MATERIAL,
		    		_IMEI: "",
		    		_ICCID: v.SERIE_1,
		    		_IMSI: "",
		    		_FECHA: getDateFormat(v.FECHA),
		    		_RECEPTOR: vr.id,
		    		_TIPO_RECEPTOR: vr.TIPO_RECEPTOR,
		    		_PROVEEDOR: $("#proveedor").val()
		    	}
				jsonDatos[indexDocument].push(json)
			}else{
				var json = {
					_FACTURA: v.Factura,
					_MATERIAL: v.Material,
					_IMEI: v.Imei == undefined?"":v.Imei,
					_ICCID: v.Iccid,
					_IMSI: v.Imsi,
					_FECHA: getDateFormat(v.Fdoc),
					_RECEPTOR: vr.id,
					_TIPO_RECEPTOR: vr.TIPO_RECEPTOR,
					_PROVEEDOR: $("#proveedor").val()
		    	}
		    	if(!v.Factura){
		    		json._FACTURA = 0;
		    	}
				jsonDatos[indexDocument].push(json)
			}
		}
    	xRow++;
    })
	console.log(jsonDatos)
	var count = 0;
	loading.show();
	setTimeout(function(){
		$.each(jsonDatos, function(k,v){
			var input = {
				SP: "INSERT_STOCK_SIM",
				FILTERS: jsonDatos[k],
				LOADING: false
			}
			console.log(input)
			callSp(input).then(function(res){
				count++;
				if(count == jsonDatos.length){
					alert("Informacion Registrada con exito")
					loading.hide()
				}
			})
		})
		
	}, 10);
}
getReceptores();
function getReceptores(){
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: 0},
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
function parseFolio(value){
    var res="";
    if(!isNaN(parseInt(value))){
         res = ("0000000000" + value).slice (-2);
    }else{
        res = false;
    }
    return res;
}
function getDateFormat(fecha){
	/*var auxFecha = fecha.split(".");
	
	//var auxFecha = fecha.split("-");
	var dia = parseFolio(auxFecha[1]);
	var mes = parseFolio(auxFecha[0]);
	var anno = "20"+auxFecha[2];
	/*if(auxFecha[0] < 10){
		mes = "0"+auxFecha[0];
	}
	if(auxFecha[1] < 10){
		dia = "0"+auxFecha[1];
	}*/
	//console.log(anno+"-"+mes+"-"+dia);
	return fecha.substring(4, 8)+"-"+fecha.substring(2, 4)+"-"+fecha.substring(0, 2);
}