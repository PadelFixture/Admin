cambioTipoReceptor(1)
function cambioTipoReceptor($v){
	if($v*1 == 1){
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
	}else{
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
	}
}
var tabla;
function buscar(){
	var getSp = {
		SP: "GET_DEPOSITO_REPORT",
		FILTERS: {
			_TIPO: $("#tipo").val(),
			_COMERCIO: $("#tipo").val() == 2?$("#receptor").val():0,
			_SUBD: $("#tipo").val() == 1?$("#receptor").val():0
		}
	}
	console.log(getSp)
	callSp(getSp).then(function(res){
		console.log(res)
		var datos = [];
		$.each(res.data, function(k,v){
			var tbl = [v.Tipo_Movimiento,v.Tipo_Receptor, v.fecha, v.NOMBRE,v.id_tt, v.DESCRIPCION, v.monto];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["Tipo Movimiento", "Tipo Receptor", "Fecha/Hora Movimiento","Razon Social","Codigo Transaccion","Empresa", "Monto"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false
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
	});
}