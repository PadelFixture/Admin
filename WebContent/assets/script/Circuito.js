$(document).ready(function(){
	initLoad();
})
var $vendedor;
var $arrayVendedor = [];
var ven = {
	SP: "GET_RECEPTOR",
	FILTERS: [
	    {value: 2},
	    {value: 0},
	    {value: 0},
	]
}
getSp(ven).then(function(res){
	console.log(res)
	$vendedor = res.data;
	$arrayVendedor[null] = "";
	$.each(res.data, function(k,v){
		$arrayVendedor[v.id] = v.RAZON_SOCIAL;
	})
})
function getVendedor(){
	var option = "<option value=''></option>";
	$.each($vendedor, function(k,v){
		option += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
	})
	return option;
}
var tabla;
var $circuitos;
function initLoad(){
	var cir = {
		TABLE: "circuito",
		WHERE:{estado: 1}
	}
	Select(cir).then(function(res){
		console.log(res);
		$circuitos = res.data;
		var datos = [];
		$.each(res.data,function(k, v) {
			var tbl = [v.id,v.nombre, $arrayVendedor[v.idVendedor], formatFecha(v.fecha_creacion), comArr[v.comuna],
			           "<button title='Reasignar Vendedor' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
			           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn green-dark btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
			datos.push(tbl);
		})
		if (tabla) {
			tabla.destroy();
			$('#tbl_RendimientoVlidadr').empty();
		}
		var columnas = [ "Id", "Nombre", "Vendedor","Fecha Creacion", "Comuna", "Opciones"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({
				title : columnas[i]
			})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			language : languageDT()
		});
		$("#tbl_RendimientoVlidadr_filter").hide();
	})
}
var $comuna;
var $region;
var $provincia;
var comArr = [];
var com = {
	TABLE: "comunas"
}
Select(com).then(function(comuna){
	$comuna = comuna.data;
	$.each(comuna.data, function(k,v){
		comArr[v.id] = v.comuna;
	})
})
var reg = {
	TABLE: "regiones"
}
Select(reg).then(function(res){
	$region = res.data;
})
var pro = {
	TABLE: "provincias"
}
Select(pro).then(function(res){
	$provincia = res.data;
})
function getRegion(){
	var option = "<option value = ''></option>";
	$.each($region, function(k,v){
		option += "<option value = '"+v.id+"'>"+v.region+"</option>";
	})
	return option;
}
function getProvincia($v){
	console.log($provincia)
	var option = "<option value = ''></option>";
	$.each($provincia, function(k,v){
		if(v.region_id == $v*1){
			option += "<option value = '"+v.id+"'>"+v.provincia+"</option>";
		}
	})
	$("#provincia").html(option);
}
function getComuna($v){
	var option = "<option value = ''></option>";
	$.each($comuna, function(k,v){
		if(v.provincia_id == $v){
			option += "<option value = '"+v.id+"'>"+v.comuna+"</option>";
		}
	})
	$("#comuna").html(option);
}
function agregar(){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Vendedor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='vendedor'>"+getVendedor()+"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input class='form-control required-modal' id='nombre'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Región</h5>";
	pop += 			"<select class='form-control input-sm required-modal' onchange='getProvincia(this.value)' id='region'>"+getRegion()+"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Provincia</h5>";
	pop += 			"<select class='form-control input-sm required-modal' onchange='getComuna(this.value)' id='provincia'></select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Comuna</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='comuna'></select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='guardar()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Circuito", pop, true, "450px", true);
	selectCss();
}
function editar($v){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-12 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Vendedor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='vendedor'>"+getVendedor()+"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div style='text-align: center;'>";
	pop += 		"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='actualizar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 		"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 	"</div>";
	popUp("Reasignar Vendedor", pop, true, "450px", true);
	selectCss();
	$("#vendedor").val($v.idVendedor).trigger("change")
}
function actualizar($v){
	var upd = {
		TABLE: "circuito",
		SET: {idVendedor: $("#vendedor").val()},
		WHERE: {id: $v.id},
		ALERTA: false
	}
	Update(upd).then(function(res){
		if(res.error == 0){
			alert("Verdedor reasigando con exito");
			closeModal();
			initLoad()
		}else{
			alert(res.mensaje)
		}
	})
}
function guardar(){
	if(validateModal()){
		var ifNombre = true;
		$.each($circuitos, function(k,v){
			if($("#nombre").val().toUpperCase() == v.nombre.toUpperCase()){
				ifNombre = false;
			}
		})
		if(ifNombre){
			var input = {
				TABLE: "circuito",
				VALUES: {
					nombre: $("#nombre").val(),
					fecha_creacion: dateHoy(),
					Usuario: USER.ID,
					comuna: $("#comuna").val(),
					estado: 1,
					idVendedor: $("#vendedor").val()
				},
				ALERTA: false
			}
			Mantenedor(input).then(function(res){
				if(res.error == 0){
					alert("Circuito creado con exito");
					closeModal()
					initLoad();
				}else{
					alert(res.mensaje)
				}
			})
		}else{
			alert("El circuito con el nombre "+$("#nombre").val()+" ya existe");
		}
	}
}
function eliminar($v){
	var c = confirm("¿Desea eliminar este circuito?");
	if(c){
		var upd = {
			TABLE: "circuito",
			SET: {estado: 0},
			WHERE: {id: $v.id},
			ALERTA: false
		}
		Update(upd).then(function(res){
			if(res.error == 0){
				alert("Circuito eliminado con exito");
				closeModal()
				initLoad();
			}else{
				alert(res.mensaje)
			}
		})
	}
}