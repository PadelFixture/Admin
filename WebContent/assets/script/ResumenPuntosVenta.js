var tabla;
var datosExcel;
var datos2;
var columnas;
getDatosResumen();
function getDatosResumen(){
	var input = {
        URL: "GET_RESUMEN_PUNTOS_DE_VENTA",
        FILTERS: [{
        	DESDE: formatFecha($("#fechadesde").val()),
        	HASTA: formatFecha($("#fechahasta").val())
        }]
    };
	//get comercios
	console.log(input);
	getData(input).then(function(response){
		console.log(response)
		var datos = []
		$.each(response.data, function(k,v){
			var tbl = [v.DESCRIPCION,v.QPDV,v.CHIPS356,v.CHIPS374, v.PUBLICIDAD, v.CAPACITADOS];
			datos.push(tbl);
		})
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["","QPDV","Chips 356","Chips 374","Publicidad", "QPDV Capacitados"];
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
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
		$("#tbl_RendimientoVlidadr_filter").hide();
		$("#tbl_RendimientoVlidadr_length").hide();
	})
}
function generarExcelPulento(){
	if(datos){
		var col = columnas.splice(columnas.length-1, 1)
		var input = {
			HEADER: columnas,
			DATA: datos2,
			NAMES:{
				SHEET: "Comercios",
				FILE: "Comercios"
			}
		}
		getExcel(input).then(function(res){
			window.open("/recotec/json/AGRO/DESCARGAR_EXCEL_ORDEN_PF/"+res.mensaje,"_blank");
		})
	}
}