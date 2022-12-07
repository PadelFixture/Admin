var tabla2;
var datosExcel;
var datos2;
var columnas;
//getDatos()
function buscar(){
	var input = {
        URL: "RESUMEN_COMERCIO_CATEGORIZACION",
        FILTERS: [{
        	DESDE: formatFecha($("#fechadesde").val()),
        	HASTA: formatFecha($("#fechahasta").val())
        }]
    };
	//get comercios
	console.log(input);
	getData(input).then(function(response){
		console.log(response)
		var datos = [];
		var tRecepcion = 0;
		var tHab = 0;
		var tRec = 0;
		$.each(response.data, function(k,v){
			tRecepcion += v.Q_RECEPCION;
			tHab += v.HABILITADOS;
			tRec += v.RECARGAS;
			var tbl = [v.DESCRIPCION, v.Q_RECEPCION,v.QPDV,v.MIX.toFixed(1)+" %",v.TASA_HAB.toFixed(1)+" %", v.TASA_REC.toFixed(1)+" %"];
			datos.push(tbl);
		})
		var $pHab = ((tHab/tRecepcion)*100).toFixed(1);
		var $pRec = ((tRec/tRecepcion)*100).toFixed(1);
		if(tabla2){
			tabla2.destroy();
	        $('#tbl_RendimientoVlidadr2').empty(); 
		}
		$("#tbl_RendimientoVlidadr2").html('<tfoot><tr><th>Total:</th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>');
		columnas = ["Categoria", "Q SIM","QPDV","Mix (%)","Tasa Habilitados(%)","Tasa Recargados (%)"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla2 = $('#tbl_RendimientoVlidadr2').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: true,
			dom: 'Bfrtip',
		    buttons: [
		        {  extend: 'excel',
		        	footer: true ,
		            text: 'Excel',
		            className: 'btn btn-success',
		        }
		    ],
			footerCallback: function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	            var intVal = function ( i ) {
	                return typeof i === 'string' ?i.replace(/[\$,]/g, '')*1 :
	                typeof i === 'number' ?i : 0;
	            };
	            t2 = api.column(2).data().reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
//	            t2 = api.column(4).data().reduce( function (a, b) {
//                    return intVal(a) + intVal(b);
//                }, 0 );
	            $( api.column(1).footer() ).html(tRecepcion);
	            $( api.column(2).footer() ).html(t2);
	            $( api.column(3).footer() ).html(100);
	            $( api.column(4).footer() ).html($pHab);
	            $( api.column(5).footer() ).html($pRec);
	        }
		});
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
		$("#tbl_RendimientoVlidadr2_filter").hide();
		$("#tbl_RendimientoVlidadr2_length").hide();
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