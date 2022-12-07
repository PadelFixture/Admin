function guardar(){
	var input = {
		TABLE: "MB51",
		VALUES: {
			tipo_tt: 1,
			tipo: 1,
			id_receptor: $("#receptor").val(),
			monto: $("#monto").val(),
			id_tt: $("#codigo").val(),
			empresa: $("#empresa").val(),
			usuario: USER.ID
		},
		ALERTA: false
	}
	Mantenedor(input).then(function(res){
		
	})
}
function cambioTipoReceptor($v){
	var getRec = {
		TABLE: "RECEPTOR",
		WHERE: {
			TIPO_RECEPTOR: $v
		}
	}
	Select(getRec).then(function(res){
		console.log(res);
		var option = "<option value=''></option>";
		$.each(res.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.RAZON_SOCIAL+"</option>";
		})
		$("#receptor").html(option);
	})
}