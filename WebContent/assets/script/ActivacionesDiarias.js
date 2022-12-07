function desFiles(){
	$("#desFiles").hide();
	$("#fImport").html("Ningun archivo seleccionado");
	$("#fileImport").val("");
}
var tabla;
var rowObj;
var datosInput = [];
var periodo;
function fileImport(event){
	$.fn.dataTable.ext.errMode = 'none';
	console.log(name)
	var especie = $("#especie").val();
	var name = $("#fileImport").prop("files")[0].name;
	console.log(name)
	$("#bodyExcel").html("");
	$("#fImport").html(name);
	$("#desFiles").show();
	var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
        var fileData = reader.result;
        var wb = XLSX.read(fileData, {type : 'binary'});
        var datos = []
        wb.SheetNames.forEach(function(sheetName){
	        rowObj =XLSX.utils.sheet_to_row_object_array(wb.Sheets[sheetName]);
	        console.log(rowObj)
	        var jsonObj = JSON.stringify(rowObj);
	        var body = "";
	        var header = [];
	        $.each(rowObj[0], function(k,v){
	        	header.push(k);
	        })
	        datosInput = [];
//	        debugger;
	        $.each(rowObj, function(k,v){
	        	if(v.ICCID == ''){
	        		v.ICCID = v["TXNID"];
	        		
	        	}
	        	if(v["TXNID"] != '' && v["TXNID"] != undefined){
	        		console.log(v["TXNID"]);
	        		var tbl = [v["TXNID"], v["ACTIVATIONTYPE"], v["APPLICATIONID"], v["APPLICATIONVERSION"], v.STATUS, v.STATE, v["OPERATIONNAME"], v.REQUESTSTARTTIME, v.REQUESTDATE,
	    			           v.REQUESTHOUR, v.REQUESTSTARTHOUR, v.LASTSTATUSMODIFIEDTIME, v.LASTSTATUSMODDATE, v.LASTSTATUSMODHOUR, v.LOGIN, v.USERNAME, v.IMEISELLER, v.DOCUMENTTYPE,
	    			           v.DOCUMENTVALUE, v.FULLNAME, v.EMAIL, v.ICCID, v.MSISDN, v.IMEI, v.TYPEOFSALE, v.TARIFFPLANNAME, v.TARIFFPLANDESCRIPTION, v.PORTEDMSISDN, v.ORIGINOPERATOR, 
	    			           v.SUBSCRIBERTYPE, v.ORIGINOPERATORACTDATE, v.BONUS, v.PORTAQUERYSTATUS, v.PORTANODEID, v.PORTANODEREASON, v.DEBTCAPACITY, v.EQUIPMENTMAX, v.DEBTSTATE, 
	    			           v.DEBTREASON, v.LINESALLOWED, v.CONTRACTACCEPT, v.DATAPROTECT, v.BIOMETRIC, v.NONBIOMETRIC, v.ORDERID, v.SHOPPINGCARTID, v.PARTNER, v.LEADER, 
	    			           v.TYPEBIOMETRIC, v.HIGHVALUE, v.EVALUATECREDITRESPONSE, v.ENTELPAYCODE, v.BILLINGCYCLE, v.COMMUNE];
	        		datos.push(tbl);	
	    			
	        	}
    			
        		var json = v;
	        	var fecha1 = v.REQUESTDATE? formatFecha(v.REQUESTDATE.split("/").join("-")):v.REQUESTDATE;
	        	var fecha2 = v.LASTSTATUSMODDATE? formatFecha(v.LASTSTATUSMODDATE.split("/").join("-")): v.LASTSTATUSMODDATE;
        		json.ESTADO = v.STATUS;
        		if(v.REQUESTDATE){
        			json.REQUESTDATE = fecha1;
        			periodo = fecha1;
        		}else{
        			delete json['REQUESTDATE'];
        		}
        		if(v.LASTSTATUSMODDATE){
        			json.LASTSTATUSMODDATE = fecha2;
        		}else{
        			delete json['LASTSTATUSMODDATE'];
        		}
        		if(!v.LASTSTATUSMODHOUR){
        			delete json['LASTSTATUSMODHOUR'];
        		}
        		if(v.LASTSTATUSMODIFIEDTIME){
        			var fecha5;
        			fecha5 = v.LASTSTATUSMODIFIEDTIME.split(" ");
		        	fecha6 = formatFecha(fecha5[0].split("/").join("-"));
		        	fecha5 = fecha6+" "+fecha5[1];
        			json.LASTSTATUSMODIFIEDTIME = fecha5;
        		}else{
        			delete json['LASTSTATUSMODIFIEDTIME'];
        		}
        		datosInput.push(json);
	        })
    		if(tabla){
    			tabla.destroy();
    	        $('#tbl_RendimientoVlidadr').empty(); 
    		}
    		var columnas = ["TXNID", "ACTITIONTYPE", "APPLICATIONID", "APPLICATIONRSION", "ESTATUS", "STATE", "OPERATIONNAME", "REQUESTSTARTTIME", "REQUESTDATE",
    			           "REQUESTHOUR", "REQUESTSTARTHOUR", "LASTSTATUSMODIFIEDTIME", "LASTSTATUSMODDATE", "LASTSTATUSMODHOUR", "LOGIN", "USERNAME", "IMEISELLER", "DOCUMENTTYPE",
    			           "DOCUMENTLUE", "FULLNAME", "EMAIL", "ICCID", "MSISDN", "IMEI", "TYPEOFSALE", "TARIFFPLANNAME", "TARIFFPLANDESCRIPTION", "PORTEDMSISDN", "ORIGINOPERATOR", 
    			           "SUBSCRIBERTYPE", "ORIGINOPERATORACTDATE", "BONUS", "PORTAQUERYSTATUS", "PORTANODEID", "PORTANODEREASON", "DEBTCAPACITY", "EQUIPMENTMAX", "DEBTSTATE", 
    			           "DEBTREASON", "LINESALLOWED", "CONTRACTACCEPT", "DATAPROTECT", "BIOMETRIC", "NONBIOMETRIC", "ORDERID", "SHOPPINGCARTID", "PARTNER", "LEADER", 
    			           "TYPEBIOMETRIC", "HIGHLUE", "ELUATECREDITRESPONSE", "ENTELPAYCODE", "BILLINGCYCLE", "COMMUNE"];
    		var finalColumn = [];
    		for(var i = 0; i < columnas.length; i++){
    			finalColumn.push({title: columnas[i]})
    		}
    		tabla = $('#tbl_RendimientoVlidadr').DataTable({
    			data: datos,
    			columns: finalColumn, 
    			autoWidth: true,
    			ordering: false,
    		});
        })
    };
    reader.readAsBinaryString(input.files[0]);
}
async function guardar(){
//	console.log(datosInput)
//	var del = {
//		URL: "DELETE_PERIODO",
//		FILTERS:[{
//			PERIODO: periodo
//		}]
//	};
	
//	await getData(del).then(function(res){
		//console.log(res)
		var input = {
			TABLE: "ACTIVACIONES_DIARIAS",
			VALUES: datosInput
		};
		Mantenedor(input)
//	})
	
}