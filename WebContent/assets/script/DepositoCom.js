$(document).ready(function(){
	var id = 0;
	var pad = 0;
	var tipo = 0;
	var sub = 0;
	if(USER.TIPO_RECEPTOR == 2){
		pad = USER.COD_RECEPTOR;
		sub = subdis;
	}
	if(USER.TIPO_RECEPTOR == 1){
		sub = USER.COD_RECEPTOR;
		pad = padre;
	}
	var input = {
		SP: "GET_COMERCIO",
		FILTERS: {
			_ID: 0,
			_PADRE: pad,
			_TIPO_PADRE: tipo,
			_SUB_DISTRIBUIDOR: sub
		}
	}
	console.log(input)
	callSp(input).then(function(res){
		var option = "<option value=''></option>";
		$.each(res.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#receptor").html(option);
	});
	
	var getCtp = {
		TABLE: "CONCEPTOS",
		WHERE: {
			TIPO: "EMPRESA",
			ESTADO: 1
		}
	}
	Select(getCtp).then(function(res){
		var option = "<option value=''></option>";
		$.each(res.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.DESCRIPCION+"</option>";
		})
		$("#empresa").html(option);
	})
})
var $archivo = "";
var $tipo = "";
function cambioFile(){
	var name = $("#fileImport").prop("files")[0].name;
	console.log(name)
	$("#fImport").html(name);
	$("#desFiles").show();
	var input = event.target;
    var reader = new FileReader();
	loading.show();
	setTimeout(function(){ 
	    reader.onload = function(){
	        var fileData = reader.result;
	    	$tipo = name.split(".")[1];
	    	$archivo = btoa(fileData);
	    };
	    reader.readAsBinaryString(input.files[0]);
	    loading.hide()
	}, 50);
}
function guardar(){
	var file_data = $("#fileImport").prop("files")[0];
	var name = $("#fileImport").prop("files")[0].name;
	var form_data = new FormData();
	console.log(name)
	var d = new Date();

	var month = d.getMonth() + 1;
	var day = d.getDate();
	var year = d.getFullYear();
	var min = d.getMinutes();
	var hour = d.getHours()
	var seconds = d.getSeconds();

	var nombre = (year + "-" + month + "-" + day + "-"
			+ hour + "-" + min + "-" + seconds);
	form_data.append("file", file_data);
	form_data.append($("#codigo").val(), $("#codigo").val());
	form_data.append($("#codigo").val()+name, $("#codigo").val()+name);
//	return;
	var pase = true;
	if(file_data){
		$.ajax({
			url : "/recotec/UploadFileLicencia",
			dataType : 'script',
			cache : false,
			async : false,
			contentType : false,
			processData : false,
			data : form_data,
			type : 'post',
			success : function() {
				console.log(nombre);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
		    alerta(errorThrown);
			$("#loading").hide();
			pase = false;
		})
	}
	if(pase){
		var input = {
			TABLE: "MB51",
			VALUES: {
				tipo_tt: 1,
				tipo: 2,
				id_receptor: $("#receptor").val(),
				monto: $("#monto").val(),
				id_tt: $("#codigo").val(),
				empresa: $("#empresa").val(),
				usuario: USER.ID,
				foto: $("#codigo").val()+name
			},
			ALERTA: false
		}
		Mantenedor(input).then(function(res){
			alerta("Deposito realizado con exito");
			$("#monto").val("")
			$("#codigo").val("")
			$("#empresa").val("").trigger("change");
		})
	}
	
}
function cambioCodigo($v){
	if(!$("#codigo").val() || !$("#empresa").val()){
		return;
	}
	var getCo = {
		TABLE: "MB51",
		WHERE: {
			id_tt: $("#codigo").val(),
			empresa: $("#empresa").val()
		}
	}
	Select(getCo).then(function(res){
		if(res.data.length > 0){
			alert("Este codigo ya fue ingresado anteriormente");
			$("#codigo").val("");
		}
	})
}