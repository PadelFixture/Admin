getDistribuidor();
getCategoria();
var tabla;
function getDistribuidor(){
	$.fn.dataTable.ext.errMode = 'none';
	
	var input = {
        SP: "GET_AMERICANO",
        FILTERS: {
        	
        }
    };
	callSp(input).then(function(response){
		console.log(response);
		var datos = [];
		$.each(response.data, function(k,v){
			var tbl = [v.id, v.nombre, v.formato,viewFecha(v.fecha),v.categoria,
			           //"<button title='Editar' onclick='editar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>"+
			           "<button title='Eliminar' onclick='eliminar("+JSON.stringify(v)+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>" ];
			datos.push(tbl);
		})
		
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var columnas = ["id","nombre", "formato","fecha","categoria", "Opciones"];
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
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
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
	})
}


function agregar(){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-3 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input class='form-control required-modal' id='nombre' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-3 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Categoria</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='categoria'>";
	pop += 				getCatSelect();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-3 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Formato</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='formato'>";
	pop += 				"<option value='Copa 8'>Copa 8</option>";
	pop += 				"<option value='TCT 8'>TCT 8</option>";
	pop += 				"<option value='TCT 6'>TCT 6</option>";
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-3 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Fecha</h5>";
	pop += 			"<input type='date' class='form-control required-modal'  id='fecha' value=''>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarAmericano()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Americano", pop, true, "950px", true);
	selectCss()
}

var $CAT;
function getCategoria(){
	var input = {
        SP: "GET_CATEGORIA",
        FILTERS: {
        	
        }
    };
	callSp(input).then(function(response){
		console.log(response);
		$CAT = response.data;
		
	})
}

function getCatSelect(cat){
	var option = "<option value=''></option>";
	console.log($CAT)
	$.each($CAT, function(k,v){
		if(v.id == cat){
			option += "<option value='"+v.id+"' selected>"+v.DESCRIPCION+"</option>";
		}else{
			option += "<option value='"+v.id+"'>"+v.DESCRIPCION+"</option>";
		}
	})
	return option;
}

function agregarAmericano(){
	if(!$("#nombre").val()){
		return;
	}else if(!$("#categoria").val()){
		return;
	}
	else if(!$("#fecha").val()){
		return;
	}
	loading.show();
	setTimeout(function(){ 
		var input = {
			SP: "INSERT_AMERICANO",
			FILTERS:{
				_NOMBRE: $("#nombre").val(),
				_FECHA: $("#fecha").val(),
				_FORMATO : $("#formato").val(),
				_CATEGORIA : $("#categoria").val()
			}
		};
		console.log(input);
		callSp(input).then(function(response){
			closeModal();
			loading.hide();
			setTimeout(function(){ 
				getDistribuidor();
			}, 50);
			
		})
	}, 50);
}

function eliminar($v){
	var c = confirmar.confirm("¿Seguro quiere eliminar los datos de "+$v.nombre+"?");
	$(c.aceptar).click(function(){
		setTimeout(function(){ 
			var input = {
			SP: "DELETE_AMERICANO",
				FILTERS:{
					_ID: $v.id
				}
			};
			callSp(input).then(function(response){
				setTimeout(function(){ 
					getDistribuidor();
				}, 50);
			
			})
		}, 50);
	})
}