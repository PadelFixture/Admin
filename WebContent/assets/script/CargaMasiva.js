$(document).ready(function(){
	getComerciosRut()
})
var body2 = "";
var chechTr = [];
function desFiles(){
	$("#desFiles").hide();
	$("#fImport").html("Ningun archivo seleccionado");
	$("#fileImport").val("");
}
var tabla;
var rowObj;
var datosInput = [];
var $tipo;
function cambioEspecie(){
	var especie = $("#tipo").val();
	if(especie){
		$("#divImport").removeClass("disabledbutton");
	}else{
		$("#divImport").addClass("disabledbutton");
	}
}
var ArrayRuts = [];
function getComerciosRut(){
	ArrayRuts = [];
	var getC = {
		TABLE: "COMERCIO",
		COLUMN: ["RUT"]
	}
	Select(getC).then(function(res){
		console.log(res)
		$.each(res.data, function(k,v){
			ArrayRuts.push(v.RUT);
		})
	})
}
function fileImport(event){
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
	        var jsonObj = JSON.stringify(rowObj);
	        var body = "";
	        var header = [];
	        $.each(rowObj[0], function(k,v){
	        	header.push(k);
	        })
	        datosInput = [];
	        var ArrayRuts2 = [];
	        $.each(rowObj, function(k,v){
	        	if(sheetName == "VENDEDOR" && $("#tipo").val()*1 == 1){
	        		$tipo = 1;
	        		console.log(v)
	        		var json = {
	        			id: v["Id"],
	        			rut: v["Rut"],
	        			RAZON_SOCIAL: v["Razon Social"],
	        			PROPIO: v["Propio"],
	        			COMUNA: v["Comuna"],
	        			DIRECCION: v["Direccion"],
	        			TIPO_RECEPTOR: v["Tipo Receptor"],
	        			PADRE: v["Padre"],
	        			ESTADO: v["Estado"],
	        			CORREO: v["Correo"],
	        			TELEFONO: v["Telefono"]
	        		}
	        		datosInput.push(json);
	    			var tbl = [v["Id"], v["Rut"], v["Razon Social"], v["Propio"], v.Comuna, v.Direccion, v["Tipo Receptor"], v.Padre, v.Estado, v.Correo, v.Telefono];
	    			datos.push(tbl);
	        	}else if(sheetName == "COMERCIO" && $("#tipo").val()*1 == 2){
	        		$tipo = 2;
	        		console.log(v)
	        		if(ArrayRuts.indexOf(v["Rut"]) == -1 && ArrayRuts2.indexOf(v["Rut"]) == -1){
	        			var json = {
    	        			id: v["Id"],
    	        			RAZON_SOCIAL: v["Razon Social"],
    	        			GIRO: v["Giro"],
    	        			RUT: v["Rut"],
    	        			DIRECCION: v["Propio"],
    	        			COMUNA: v["Comuna"],
    	        			PADRE: v["Direccion"],
    	        			TIPO_PADRE: v["Tipo Receptor"],
    	        			PADRE: v["Padre"],
    	        			ESTADO: v["Estado"],
    	        			CORREO: v["Correo"],
    	        			TELEFONO: v["Telefono"],
    	        			CAPACITADO: v.Capacitado,
    	        			FECHA_CREACION: dateHoy()
    	        		}
    	        		datosInput.push(json);
	        			ArrayRuts2.push(v["Rut"]);
    	    			var tbl = [v["Id"], v["Razon Social"], v["Giro"], v["Rut"], v.Direccion, v.Comuna, v["Padre"], v["Tipo Padre"], v.Estado, v.Correo, v.Telefono, v.Capacitado];
    	    			datos.push(tbl);
	        		}
	        		
	        	}
	        })
    		if(tabla){
    			tabla.destroy();
    	        $('#tbl_RendimientoVlidadr').empty(); 
    		}
	        var columnas;
	        if($("#tipo").val()*1 == 1){
	        	columnas = ["Id","Rut","Razon Social","Propio","Comuna","Direccion","Tipo Receptor","Padre","Estado","Correo","Telefono"];
	        }else{
	        	columnas = ["Id", "Razon Social", "Giro","Rut","Direccion","Comuna","Padre","Tipo Padre","Estado","Correo","Telefono", "Capacitado"];
	        }
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
function guardar(){
	if($("#tipo").val()*1 == 1){
		if($tipo != 1){
			alerta("El tipo de registro seleccionado no coincide con el archivo");
			return;
		}
		var input = {
			TABLE: "RECEPTOR",
			VALUES: datosInput
		}
		Mantenedor(input)
		getComerciosRut()
	}else if($("#tipo").val()*1 == 2){
		if($tipo != 2){
			alerta("El tipo de registro seleccionado no coincide con el archivo");
			return;
		}
		var input = {
			TABLE: "COMERCIO",
			VALUES: datosInput
		}
		Mantenedor(input)
		getComerciosRut()
	}else{
		alerta("No ha seleccionado un tipo de registro");
		return;
	}
	
}