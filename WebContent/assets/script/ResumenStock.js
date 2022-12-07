var tabla3;
var datosExcel;
var datos2;
var columnas;
function getDatos3(){
	var input = {
        URL: "STOCK_CANTIDAD",
    };
	//get comercios
	console.log(input);
	getData(input).then(function(res){
		console.log(res)
		var Arrdatos = []
		var datos = []
		$.each(res.data, function(k,v){
			var tbl = {
				tipo: "0-5",
				min: v["0-5_356"],
				max: v["0-5_374"],
			};
			Arrdatos.push(tbl)
			tbl = {
				tipo: "5-10",
				min: v["5-10_356"],
				max: v["5-10_374"],
			};
			Arrdatos.push(tbl)
			tbl = {
				tipo: "10-20",
				min: v["10-20_356"],
				max: v["10-20_374"],
			};
			Arrdatos.push(tbl)
			tbl = {
				tipo: "20-50",
				min: v["20-50_356"],
				max: v["20-50_374"],
			};
			Arrdatos.push(tbl)
			tbl = {
				tipo: "50-100",
				min: v["50-100_356"],
				max: v["50-100_374"],
			};
			Arrdatos.push(tbl)
			tbl = {
				tipo: "100+",
				min: v["100+_356"],
				max: v["100+_374"],
			};
			Arrdatos.push(tbl)
		})
		console.log(Arrdatos)
		$.each(Arrdatos, function(k,v){
			var tbl = [v.tipo, v.min, v.max];
			datos.push(tbl)
		})
		if(tabla3){
			tabla3.destroy();
	        $('#tbl_RendimientoVlidadr3').empty(); 
		}
		columnas = ["Categoria","QPDV(Chip 356)","QPDV(Chip 374)"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla3 = $('#tbl_RendimientoVlidadr3').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false
		});
		$("#tbl_RendimientoVlidadr3_filter").hide();
		$("#tbl_RendimientoVlidadr3_length").hide();
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