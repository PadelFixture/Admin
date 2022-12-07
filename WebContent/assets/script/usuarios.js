getDistribuidor();
var tabla;
var datos2;
var columnas;
function getDistribuidor(){
	$.ajax({
		url: "/recotec/json/REC/GET_USUARIOS", 
		type:	"GET",
		dataType: 'json',
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(data){
			console.log(data);
			var datos = [];
			datos2 = [];
			$.each(data, function(k,v){
				var tbl2 = [v.IDUSUARIOS, v.USUARIO,v.PASSWORD, v.DESCRIPCION];
				datos2.push(tbl2);
				var tbl = [v.IDUSUARIOS, v.USUARIO,v.PASSWORD, v.DESCRIPCION,
				           "<button title='Editar' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
				           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
				datos.push(tbl);
			})
			if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			columnas = ["Codigo","Usuario","Password", "Tipo Usuario", "Opciones"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			tabla = $('#tbl_RendimientoVlidadr').DataTable({
				data: datos,
				columns: finalColumn,
				autoWidth: true,
				ordering: false,
				fixedHeader: true
			});
			$("#tbl_RendimientoVlidadr_filter").hide();

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
		}
	})
}
function agregar(){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Usuario</h5>";
	pop += 			"<input class='form-control required-modal' id='usuario'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Tipo Usuario</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='perfil'>"+getPerfil()+"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Tipo Receptor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' onchange='getReceptores(this.value)' id='tipo_receptor'>"+getTipoReceptor(0)+"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Receptor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='receptor'></select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Contraseña</h5>";
	pop += 			"<input type='password' autocomplete='off' value='' class='form-control required-modal' id='pass'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Confirmar Contraseña</h5>";
	pop += 			"<input type='password' autocomplete='off' value='' class='form-control required-modal' id='copass'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='addUsuario()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Usuario", pop, true, "450px", true);
	selectCss();
}
function editar($v){
	console.log($v)
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Usuario</h5>";
	pop += 			"<input class='form-control required-modal' id='usuario' value='"+$v.USUARIO+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Tipo Usuario</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='perfil'>"+getPerfil($v.PERFIL)+"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Tipo Receptor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' onchange='getReceptores(this.value)' id='tipo_receptor'>"+getTipoReceptor($v.COD_RECEPTOR)+"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Receptor</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='receptor'>"+getReceptores($v.COD_RECEPTOR)+"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Contraseña</h5>";
	pop += 			"<input type='password' autocomplete='off' value='' class='form-control required-modal' id='pass' value='"+$v.PASSWORD+"'>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-6 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Confirmar Contraseña</h5>";
	pop += 			"<input type='password' autocomplete='off' value='' class='form-control required-modal' id='copass' value='"+$v.PASSWORD+"'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='modificar("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Modificar Datos de "+$v.USUARIO, pop, true, "450px", true);
	selectCss();
}
function getTipoReceptor(cod){
	var sPerfil = "<option value=''></option>";
	var input = {
        SP: "GET_CONCEPTOS",
        FILTERS: [
            {value: "RECEPTOR"}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		data: JSON.stringify(input),
		async: false,
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		var vendedor = "";
		$.each(response.data, function(k,v){
			if(cod*1 == v.id*1){
				sPerfil += "<option value='"+v.id+"' >"+v.DESCRIPCION+"</option>";
			}else{
				sPerfil += "<option value='"+v.id+"'>"+v.DESCRIPCION+"</option>";
			}
		})
	}).error(function (e) {
		console.log(e);
	})
	return sPerfil;
}
function getReceptores(id){
	var sPerfil = "<option value=''></option>";
	var input = {
        SP: "GET_RECEPTOR",
        FILTERS: [
            {value: id},
            {value: 0},
            {value: 0}
        ]
    };
	console.log(input);
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		$.each(response.data, function(k,v){
			if(id*1 == v.id*1){
				sPerfil += "<option value='"+v.id+"' selected>"+v.RAZON_SOCIAL+"</option>";
			}else{
				sPerfil += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
			}
		})
		$("#receptor").html(sPerfil);
	}).error(function (e) {
		console.log(e);
	})
	return sPerfil;
}
function getPerfil(perfil){
	var sPerfil = "<option value=''></option>";
	$.ajax({
		url: "/recotec/json/REC/GET_PERFIL",
		type:	"GET",
		dataType: 'json',
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(data){
			console.log(data);
			$.each(data, function(k,v){
				if(perfil == v.IDPERFIL){
					sPerfil += "<option value='"+v.IDPERFIL+"' selected>"+v.NOMBRE+"</option>";
				}else{
					sPerfil += "<option value='"+v.IDPERFIL+"'>"+v.NOMBRE+"</option>";
				}
			})
		}
	})
	return sPerfil;
}
function eliminar($v){
	console.log($v)
	var c = confirmar.confirm("¿Seguro quiere eliminar los datos de "+$v.RAZON_SOCIAL+"?");
	$(c.aceptar).click(function(){
		setTimeout(function(){ 
			var input = {
		        _ID: $v.IDUSUARIOS,
		    };
			console.log(input)
		    $.ajax({
				url: '/recotec/json/REC/DELETE_USUARIO',
				method: 'PUT',
				dataType: 'json',
				data:JSON.stringify(input),
			}).success(function (response) {
				console.log(response);
				closeModal();
				loading.hide();
				setTimeout(function(){ 
					getDistribuidor();
				}, 50);
			}).error(function (e) {
				console.log(e);
			})
		}, 50);
		var input = {
			TABLE: "USUARIOS",
			SET:{ESTADO: 2},
			WHERE: {idUSUARIOS: $v.IDUSUARIOS}
		};
		console.log(input)
		Update(input).then(function(res){
			console.log(res)
			setTimeout(function(){ 
				getDistribuidor();
			}, 50);
		})
	})
}
function modificar($v){
	console.log($v)
	if(!$("#usuario").val()){
		alerta("Ingrese un Nombre");
		return;
	}else if(!$("#perfil").val()){
		alerta("Seleccione un tipo de Usuario");
		return;
	}else if(!$("#tipo_receptor").val()){
		alerta("Las contraseñas no coinciden");
		return;
	}else if(!$("#receptor").val()){
		alerta("Seleccione un tipo de Receptor");
		return;
	}
	if($("#pass").val() != $("#copass").val()){
		alerta("Las contraseñas no coinciden");
		return;
	}
	loading.show();
	setTimeout(function(){ 
	var input = {
        _ID: $v.IDUSUARIOS,
        USUARIO: $("#usuario").val(),
        PERFIL: $("#perfil").val(),
        PASS: $("#pass").val() == ""? $v.PASSWORD: $("#pass").val(),
        RECEPTOR: $("#receptor").val(),
    };
	console.log(input)
    $.ajax({
		url: '/recotec/json/REC/UPDATE_USUARIOS',
		method: 'PUT',
		dataType: 'json',
		data:JSON.stringify(input),
	}).success(function (response) {
		console.log(response);
		closeModal();
		setTimeout(function(){ 
			getDistribuidor();
		}, 50);
		loading.hide();
	}).error(function (e) {
		console.log(e);
	})
	}, 50);
}
function addUsuario(){
	if(!$("#usuario").val()){
		return;
	}else if(!$("#perfil").val()){
		return;
	}else if(!$("#receptor").val()){
		return;
	}else if(!$("#pass").val()){
		return;
	}else if(!$("#copass").val()){
		return;
	}
	if($("#pass").val() != $("#copass").val()){
		alerta("Las contraseñas no coinciden");
		return;
	}
	var input = {
		TABLE: "USUARIOS",
		VALUES: {
			USUARIO: $("#usuario").val(),
			PASSWORD: $("#pass").val(),
			COD_RECEPTOR: $("#receptor").val(),
			PERFIL: $("#perfil").val(),
			ESTADO: 1
		},
		ALERTA: false
	}
	Mantenedor(input).then(function(res){
		if(res.error == 0){
			var a = alerta("Usuario ingresado con exito");
			$(a.aceptar).click(function(){
				closeModal();
				closeModal();
				getDistribuidor();
			})
		}else{
			alerta(res.mensaje)
		}
	})
}
function generarExcelPulento(){
	if(datos2){
		var col = columnas.splice(columnas.length-1, 1)
		var input = {
			HEADER: columnas,
			DATA: datos2,
			NAMES:{
				SHEET: "Usuarios",
				FILE: "Usuarios"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}