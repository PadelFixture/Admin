var tabla;
var datos2;
var columnas;
$(document).ready(function(){
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
			var datos = [];
			datos2 = [];
			$.each(data, function(k,v){
				var tbl2 = [v.IDPERFIL, v.NOMBRE];
				datos2.push(tbl2);
				var tbl = [v.IDPERFIL, v.NOMBRE, 
				           "<button title='Editar' onclick='agregar("+JSON.stringify(v)+")' class='btn yellow btn-outline btn-sm' ><i class='fa fa-pencil-square-o fa-lg'></i></button>" ];
				datos.push(tbl);
			})
			if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			columnas = ["Id","Descripcion", "Opciones"];
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
})
function agregar($v){
	if(!$v){
		$v = {
			IDPERFIL: 0,
			NOMBRE: ""
		}
	}
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>"
	pop += 		"<div class='col-xs-12 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre</h5>";
	pop += 			"<input type='text' class='form-control required-modal' id='nombre' value='"+$v.NOMBRE+"'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='addPerfil("+JSON.stringify($v)+")'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Usuario", pop, true, "300px", true);
	selectCss();
}
function addPerfil($v){
	var input = {
		TABLE: "systemPerfil",
		VALUES: {
			idPerfil: $v.IDPERFIL,
			nombre: $("#nombre").val()
		}
	}
	Mantenedor(input);
}
function generarExcelPulento(){
	if(datos2){
		var col = columnas.splice(columnas.length-1, 1)
		var input = {
			HEADER: columnas,
			DATA: datos2,
			NAMES:{
				SHEET: "Perfil",
				FILE: "Perfil"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}