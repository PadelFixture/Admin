$(document).ready(function() {
	$('#loading').hide();
	SetValor();
	var input = {
        SP: "GET_CONCEPTOS",
        FILTERS: [
            {value: "CALIFICACION"},
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//				timeout: 10000,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		var option = "<option value=''></option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.DESCRIPCION+"</option>";
		})
		$("#tipo").html(option)
	}).error(function (e) {
		console.log(e);
	})
});
var arrayFormAplic;
//function cambioTipo($v){
//	console.log(2)
//}
function cambioTipo(codigo){
	
	var input = {
        SP: "GET_CALIFICACION",
        FILTERS: [
            {value: $("#tipo").val()},
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//			timeout: 10000,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		if(response.data.length != 0){
			$.each(response.data, function(k,v){
				$('#bajo_max').val(v.BAJO);
				$('#promedio_min').val(v.BAJO);
				$('#promedio_max').val(v.MEDIO);
				$('#bueno_min').val(v.MEDIO);
				$('#bueno_max').val(v.ALTO);
				$('#destacado_min').val(v.ALTO);
			})
		}else{
			var a = alerta("Aun no se genera la calificación para este Campo y Labor");
			$(a.aceptar).click(function(){
				SetValor();
			})
			return;
		}
	}).error(function (e) {
		console.log(e);
	})
}
function SetValor (){
	$('#bajo_max').val(-10);
	$('#promedio_min').val(-10);
	$('#promedio_max').val(10);
	$('#bueno_min').val(10);
	$('#bueno_max').val(20);
	$('#destacado_min').val(20);
}



var arrayCampo;
var arrayEspecie;

var selectEspecie;
function Guardar() {
	var input = {
        SP: "INSERT_CALIFICACION",
        FILTERS: [
            {value: $("#tipo").val()},
            {value: $("#bajo_max").val()},
            {value: $("#promedio_max").val()},
            {value: $("#bueno_max").val()},
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
//				timeout: 10000,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		loading.hide();
		setTimeout(function(){ 
			var a = alerta("Datos Guardados Satisfactoriamente");
			$(a.aceptar).click(function(){
				cambioTipo(0);
			})
		}, 50);
	}).error(function (e) {
		console.log(e);
	})
	
}
var selectLabor;
var arrayFaena;
$("#bajo_max").on("change keyup",function(){
	$("#promedio_min").val($(this).val());
});

$("#promedio_max").on("change keyup",function(){
	$("#bueno_min").val($(this).val());
});

$("#bueno_max").on("change keyup",function(){
	$("#destacado_min").val($(this).val());
});