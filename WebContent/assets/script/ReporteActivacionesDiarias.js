var datos = [];
var columnas;
var tabla;
function buscar(){
	var periodo = $("#periodo").val();
	var input = {
		URL: "GET_ACTIVACIONES_DIARIAS",
		FILTERS: [{
			PERIODO: periodo+"-01"
		}]
	}
	getData(input).then(function(res){
		console.log(res)
		$.each(res.data, function(k,v){
			var tbl = [v["TXNID"], v["ACTIVATIONTYPE"], v["APPLICATIONID"], v["APPLICATIONVERSION"], v.ESTADO, v.STATE, v["OPERATIONNAME"], v.REQUESTSTARTTIME, v.REQUESTDATE,
			           v.REQUESTHOUR, v.REQUESTSTARTHOUR, v.LASTSTATUSMODIFIEDTIME, v.LASTSTATUSMODDATE, v.LASTSTATUSMODHOUR, v.LOGIN, v.USERNAME, v.IMEISELLER, v.DOCUMENTTYPE,
			           v.DOCUMENTVALUE, v.FULLNAME, v.EMAIL, v.ICCID, v.MSISDN, v.IMEI, v.TYPEOFSALE, v.TARIFFPLANNAME, v.TARIFFPLANDESCRIPTION, v.PORTEDMSISDN, v.ORIGINOPERATOR, 
			           v.SUBSCRIBERTYPE, v.ORIGINOPERATORACTDATE, v.BONUS, v.PORTAQUERYSTATUS, v.PORTANODEID, v.PORTANODEREASON, v.DEBTCAPACITY, v.EQUIPMENTMAX, v.DEBTSTATE, 
			           v.DEBTREASON, v.LINESALLOWED, v.CONTRACTACCEPT, v.DATAPROTECT, v.BIOMETRIC, v.NONBIOMETRIC, v.ORDERID, v.SHOPPINGCARTID, v.PARTNER, v.LEADER, 
			           v.TYPEBIOMETRIC, v.HIGHVALUE, v.EVALUATECREDITRESPONSE, v.ENTELPAYCODE, v.BILLINGCYCLE, v.COMMUNE];
			datos.push(tbl);
        })
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["TXNID", "ACTITIONTYPE", "APPLICATIONID", "APPLICATIONVERSION", "ESTATUS", "STATE", "OPERATIONNAME", "REQUESTSTARTTIME", "REQUESTDATE",
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
}
function generarExcelPulento(){
	if(datos){
		var input = {
			HEADER: columnas,
			DATA: datos,
			NAMES:{
				SHEET: dateHoy(),
				FILE:  "Activaciones Diarias "+$("#periodo").val()
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}