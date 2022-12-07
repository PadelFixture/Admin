var datosExcel;
buscar2();
var tabla;
var tabla2;
var tabla3;
var columnas;
var datos;
var datos2;
var $chartData2 = [];
$('#periodo').datepicker({
	dateFormat:'yy-mm',
	firstDay :1,
	changeMonth : true,
	changeYear: true
	});

function buscar2(){
	$.fn.dataTable.ext.errMode = 'none';
	input = {
        SP: "Resumen_periodo_PDV",
        FILTERS: []
    };
	console.log(input);
	var trazHabi = [];
	var trazRec = [];
	var visitados = [];			
	loading.show();
	setTimeout(function(){
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			data: JSON.stringify(input),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			var columnas = [];
			var datos = [];
			var trazados = [];
			var activos = [];
			var activosMes = [];
			var activosVenta = [];			
			var entregados = [];
			console.log(response.data);
			if(response.data.length > 1){
				
			
			$.each(response.data, function(k,v){
				var MES_6 = 0;
				var MES_5 = 0;
				var MES_4 = 0;
				var MES_3 = 0;
				var MES_2 = 0;
				var MES_1 = 0;
				var MES_0 = 0;
				var temp = [];
				switch (v.DESCRIPCION){
					case 'MES':
						columnas = ['DESCRIPCION',v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						break;
					case 'Potencial de PDV Trazados':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						trazados = temp;
						datos.push(temp);
						break;
					case 'Q PDV Activos':
						MES_6 = parseInt(response.data[3].MES_6)+parseInt(response.data[4].MES_6)-parseInt(response.data[5].MES_6);
						MES_5 = MES_6+parseInt(response.data[4].MES_5)-parseInt(response.data[5].MES_5);
						MES_4 = MES_5+parseInt(response.data[4].MES_4)-parseInt(response.data[5].MES_4);
						MES_3 = MES_4+parseInt(response.data[4].MES_3)-parseInt(response.data[5].MES_3);
						MES_2 = MES_3+parseInt(response.data[4].MES_2)-parseInt(response.data[5].MES_2);
						MES_1 = MES_2+parseInt(response.data[4].MES_1)-parseInt(response.data[5].MES_1);
						MES_0 = MES_1+parseInt(response.data[4].MES_0)-parseInt(response.data[5].MES_0);
						temp = [v.DESCRIPCION,MES_5,MES_4,MES_3,MES_2,MES_1,MES_0];
						activos = temp;
						datos.push(temp);
						temp = ["Q PDV Antiguos",MES_6,MES_5,MES_4,MES_3,MES_2,MES_1];
						datos.push(temp);
						break;
					case 'Q PDV Antiguos':
						break;
					case 'Q PDV Activo en el mes':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						activosMes = temp;
						datos.push(temp);
						MES_5 = activos[1]/trazados[1]*100;
						MES_4 = activos[2]/trazados[2]*100;
						MES_3 = activos[3]/trazados[3]*100;
						MES_2 = activos[4]/trazados[4]*100;
						MES_1 = activos[5]/trazados[5]*100;
						MES_0 = activos[6]/trazados[6]*100;
						temp = ['Tasa PDV Activos',parseInt(MES_5)+"%",parseInt(MES_4)+"%",parseInt(MES_3)+"%",parseInt(MES_2)+"%",parseInt(MES_1)+"%",parseInt(MES_0)+"%"];
						datos.push(temp);
						break;
					case 'Q PDV Activos en Venta':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						datos.push(temp);
						activosVenta = temp;
						temp = [];
						datos.push(temp);
						break;
					case 'Cantidad de visitas PDV':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						visitados = temp;
						datos.push(temp);
						break;
					case 'Cantidad de visitas PDV AC':
						MES_5 = activos[1] > 0 ? v.MES_5/activos[1] : 0;
						MES_4 = activos[2] > 0 ? v.MES_4/activos[2] : 0;
						MES_3 = activos[3] > 0 ? v.MES_3/activos[3] : 0;
						MES_2 = activos[4] > 0 ? v.MES_2/activos[4] : 0;
						MES_1 = activos[5] > 0 ? v.MES_1/activos[5] : 0;
						MES_0 = activos[6] > 0 ? v.MES_0/activos[6] : 0;
						temp = ['Frecuencia PDV Activo',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						break;
					case 'Cantidad de visitas PDV INAC':
						MES_5 = (trazados[1]-activos[1]) > 0 ? v.MES_5/(trazados[1]-activos[1]) : 0;
						MES_4 = (trazados[2]-activos[2]) > 0 ? v.MES_4/(trazados[2]-activos[2]) : 0;
						MES_3 = (trazados[3]-activos[3]) > 0 ? v.MES_3/(trazados[3]-activos[3]) : 0;
						MES_2 = (trazados[4]-activos[4]) > 0 ? v.MES_2/(trazados[4]-activos[4]) : 0;
						MES_1 = (trazados[5]-activos[5]) > 0 ? v.MES_1/(trazados[5]-activos[5]) : 0;
						MES_0 = (trazados[6]-activos[6]) > 0 ? v.MES_0/(trazados[6]-activos[6]) : 0;
						temp = ['Frecuencia PDV Inactivo',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						
						MES_5 = trazados[1] > 0 ? visitados[1]/trazados[1] : 0;
						MES_4 = trazados[2] > 0 ? visitados[2]/trazados[2] : 0;
						MES_3 = trazados[3] > 0 ? visitados[3]/trazados[3] : 0;
						MES_2 = trazados[4] > 0 ? visitados[4]/trazados[4] : 0;
						MES_1 = trazados[5] > 0 ? visitados[5]/trazados[5] : 0;
						MES_0 = trazados[6] > 0 ? visitados[6]/trazados[6] : 0;
						temp = ['PDV Total (Visitado/Trazado)',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						
						MES_5 = visitados[1] > 0 ? v.MES_5/visitados[1] : 0;
						MES_4 = visitados[2] > 0 ? v.MES_4/visitados[2] : 0;
						MES_3 = visitados[3] > 0 ? v.MES_3/visitados[3] : 0;
						MES_2 = visitados[4] > 0 ? v.MES_2/visitados[4] : 0;
						MES_1 = visitados[5] > 0 ? v.MES_1/visitados[5] : 0;
						MES_0 = visitados[6] > 0 ? v.MES_0/visitados[6] : 0;
						temp = ['Entregados / Visitados',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						
						MES_5 = activos[1] > 0 ? v.MES_5/activos[1] : 0;
						MES_4 = activos[2] > 0 ? v.MES_4/activos[2] : 0;
						MES_3 = activos[3] > 0 ? v.MES_3/activos[3] : 0;
						MES_2 = activos[4] > 0 ? v.MES_2/activos[4] : 0;
						MES_1 = activos[5] > 0 ? v.MES_1/activos[5] : 0;
						MES_0 = activos[6] > 0 ? v.MES_0/activos[6] : 0;
						temp = ['Entregados / Activos',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						temp = [];
						datos.push(temp);
						break;
					case 'Q Chip entregados':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						entregados = temp;
						datos.push(temp);
						temp = [];
						datos.push(temp);
						break;
					case 'Stock Mensual Promedio':
						MES_5 = activosVenta[1] > 0 ? v.MES_5/activosVenta[1] : 0;
						MES_4 = activosVenta[2] > 0 ? v.MES_4/activosVenta[2] : 0;
						MES_3 = activosVenta[3] > 0 ? v.MES_3/activosVenta[3] : 0;
						MES_2 = activosVenta[4] > 0 ? v.MES_2/activosVenta[4] : 0;
						MES_1 = activosVenta[5] > 0 ? v.MES_1/activosVenta[5] : 0;
						MES_0 = activosVenta[6] > 0 ? v.MES_0/activosVenta[6] : 0;
						temp = [v.DESCRIPCION,MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						temp = [];
						datos.push(temp);
						break;
					case 'Habilitaciones Trazabilidad':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						datos.push(temp);
						trazHabi = temp;
						break;
					case 'Habilitaciones c/recarga':
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						trazRec = temp;
						datos.push(temp);
						MES_5 = trazHabi[1] > 0 ? v.MES_5/trazHabi[1]*100 : 0;
						MES_4 = trazHabi[2] > 0 ? v.MES_4/trazHabi[2]*100 : 0;
						MES_3 = trazHabi[3] > 0 ? v.MES_3/trazHabi[3]*100 : 0;
						MES_2 = trazHabi[4] > 0 ? v.MES_2/trazHabi[4]*100 : 0;
						MES_1 = trazHabi[5] > 0 ? v.MES_1/trazHabi[5]*100 : 0;
						MES_0 = trazHabi[6] > 0 ? v.MES_0/trazHabi[6]*100 : 0;
						temp = ['Tasa de recarga',parseInt(MES_5)+"%",parseInt(MES_4)+"%",parseInt(MES_3)+"%",parseInt(MES_2)+"%",parseInt(MES_1)+"%",parseInt(MES_0)+"%"];
						datos.push(temp);
						
						MES_5 = activosMes[1] > 0 ? trazHabi[1]/activosMes[1] : 0;
						MES_4 = activosMes[2] > 0 ? trazHabi[2]/activosMes[2] : 0;
						MES_3 = activosMes[3] > 0 ? trazHabi[3]/activosMes[3] : 0;
						MES_2 = activosMes[4] > 0 ? trazHabi[4]/activosMes[4] : 0;
						MES_1 = activosMes[5] > 0 ? trazHabi[5]/activosMes[5] : 0;
						MES_0 = activosMes[6] > 0 ? trazHabi[6]/activosMes[6] : 0;
						temp = ['Hab / PDV Activos',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);						
						temp = [];
						datos.push(temp);
						MES_5 = trazHabi[1] > 0 ? v.MES_5/entregados[1] : 0;
						MES_4 = trazHabi[2] > 0 ? v.MES_4/entregados[2] : 0;
						MES_3 = trazHabi[3] > 0 ? v.MES_3/entregados[3] : 0;
						MES_2 = trazHabi[4] > 0 ? v.MES_2/entregados[4] : 0;
						MES_1 = trazHabi[5] > 0 ? v.MES_1/entregados[5] : 0;
						MES_0 = trazHabi[6] > 0 ? v.MES_0/entregados[6] : 0;
						temp = ['Tasa Entregados / Habilitados',MES_5.toFixed(1),MES_4.toFixed(1),MES_3.toFixed(1),MES_2.toFixed(1),MES_1.toFixed(1),MES_0.toFixed(1)];
						datos.push(temp);
						
						break;
					default:
						temp = [v.DESCRIPCION,v.MES_5,v.MES_4,v.MES_3,v.MES_2,v.MES_1,v.MES_0];
						datos.push(temp);
				}
		 			
				$chartData2[0] = {"DESCRIPCION": response.data[0].MES_5,"TRAZADOS" : response.data[1].MES_5
				,"ACTIVOS" : response.data[2].MES_5,"ANTIGUOS": response.data[3].MES_5,"NUEVOS" : response.data[4].MES_5
				,"PERDIDOS" : response.data[5].MES_5,"ACTIVO_MES": response.data[6].MES_5,"ACTIVOS_VENTA" : response.data[7].MES_5};
				$chartData2[1] = {"DESCRIPCION": response.data[0].MES_4,"TRAZADOS" : response.data[1].MES_4
					,"ACTIVOS" : response.data[2].MES_4,"ANTIGUOS": response.data[3].MES_4,"NUEVOS" : response.data[4].MES_4
					,"PERDIDOS" : response.data[5].MES_4,"ACTIVO_MES": response.data[6].MES_4,"ACTIVOS_VENTA" : response.data[7].MES_4};
				$chartData2[2] = {"DESCRIPCION": response.data[0].MES_3,"TRAZADOS" : response.data[1].MES_3
					,"ACTIVOS" : response.data[2].MES_3,"ANTIGUOS": response.data[3].MES_3,"NUEVOS" : response.data[4].MES_3
					,"PERDIDOS" : response.data[5].MES_3,"ACTIVO_MES": response.data[6].MES_3,"ACTIVOS_VENTA" : response.data[7].MES_3};
				$chartData2[3] = {"DESCRIPCION": response.data[0].MES_2,"TRAZADOS" : response.data[1].MES_2
					,"ACTIVOS" : response.data[2].MES_2,"ANTIGUOS": response.data[3].MES_2,"NUEVOS" : response.data[4].MES_2
					,"PERDIDOS" : response.data[5].MES_2,"ACTIVO_MES": response.data[6].MES_2,"ACTIVOS_VENTA" : response.data[7].MES_2};
				$chartData2[4] = {"DESCRIPCION": response.data[0].MES_1,"TRAZADOS" : response.data[1].MES_1
					,"ACTIVOS" : response.data[2].MES_1,"ANTIGUOS": response.data[3].MES_1,"NUEVOS" : response.data[4].MES_1
					,"PERDIDOS" : response.data[5].MES_1,"ACTIVO_MES": response.data[6].MES_1,"ACTIVOS_VENTA" : response.data[7].MES_1};
				$chartData2[5] = {"DESCRIPCION": response.data[0].MES_0,"TRAZADOS" : response.data[1].MES_0
					,"ACTIVOS" : response.data[2].MES_0,"ANTIGUOS": response.data[3].MES_0,"NUEVOS" : response.data[4].MES_0
					,"PERDIDOS" : response.data[5].MES_0,"ACTIVO_MES": response.data[6].MES_0,"ACTIVOS_VENTA" : response.data[7].MES_0};
				
			})
			if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			tabla = $('#tbl_RendimientoVlidadr').DataTable({
				data: datos,
				columns: finalColumn,
				autoWidth: true,
				ordering: false,
				paging: false,
				dom: 'Bfrtip',
			    buttons: [
			        {  extend: 'excel',
			        	footer: true ,
			            text: 'Excel',
			            className: 'btn btn-success',
			        }
			    ],
			});
			$("#tbl_RendimientoVlidadr_filter").hide();
			$("#tbl_RendimientoVlidadr_length").hide();
			getGrafico2();
			loading.hide();
			} else {
				alert("Se está corriendo el proceso de actualización, favor probar en 2 minutos.");
				loading.hide();
			}
		}).error(function (e) {
			console.log(e);
			
		})
		input2 = {
	        SP: "GET_RESUMEN_AGRUPADO",
	        FILTERS: [ {value: $('#periodo').val()}]
	    };
		
		console.log(input2);
		var datos  = [];
		var datos2 = [];		
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			data: JSON.stringify(input2),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response);
			var totalHabR = 0;
			var totalRecR = 0;
			var totalVisR = 0;
			var totalHabG = 0;
			var totalRecG = 0;
			var totalVisG = 0;
			var MES = 0;
			$.each(response.data, function(k,v){
				MES = 5;
				if(v.MES > 0){
					MES = v.MES;
				}
				
				var porHab = v.Habilitacion / trazHabi[MES] *100;
				var porRec = v.Recargas / trazRec[MES] *100;
				if(v.Recargas == 0){
					porRec = 0;
				}
				var porVis = v.VISITAS / visitados[MES] *100;
				switch (v.TIPO){
				case 'REGION':					
					temp = [v.DESCRIPCION, v.Habilitacion, porHab.toFixed(1)+"%", v.Recargas, porRec.toFixed(1)+"%", v.VISITAS, porVis.toFixed(1)+"%"];
					totalHabR += parseInt(v.Habilitacion);
					totalRecR += parseInt(v.Recargas);
					totalVisR += parseInt(v.VISITAS);
					datos.push(temp);
					break;				
				case 'GIRO':
					temp = [v.DESCRIPCION, v.Habilitacion, porHab.toFixed(1)+"%", v.Recargas, porRec.toFixed(1)+"%", v.VISITAS, porVis.toFixed(1)+"%"];
					totalHabG += parseInt(v.Habilitacion);
					totalRecG += parseInt(v.Recargas);
					totalVisG += parseInt(v.VISITAS);
					datos2.push(temp);
					break;
				}
				
				
			});
			/*console.log(MES);
			console.log(trazHabi[MES]);
			console.log(totalHabR);
			var hab = (trazHabi[MES]  - totalHabR);
			console.log(hab);
			var porHab = hab/ trazHabi[MES] *100;
			console.log(porHab);
			if(hab < 0){
				hab = 0;
				porHab = 0;
			}
			console.log(porHab);
			var porRec = (trazRec[MES]   - totalRecR) / trazRec[MES] *100;
			if((trazRec[MES]   - totalRecR) <= 0){
				porRec = 0;
			}
			var vis = (visitados[MES] - totalVisR);
			var porVis = vis / visitados[MES] *100;
			if(vis < 0){
				porVis = 0;
				vis = 0;
			}
			//temp = ["Sin Trazabilidad", hab,porHab.toFixed(1)+"%",(trazRec[5] - totalRecR), porRec.toFixed(1)+"%",vis,porVis.toFixed(1)+"%"];
			temp = ["Sin Trazabilidad", hab,porHab.toFixed(1)+"%",0, 0+"%",0+"%"];
			datos.push(temp);
			var hab = (trazHabi[v.MES]  - totalHabG);
			var porHab = hab/ trazHabi[v.MES] *100;
			if(hab < 0){
				porHab = 0;
				hab = 0;
			}
			var porRec = (trazRec[v.MES]   - totalRecG) / trazRec[v.MES] *100;
			if((trazRec[v.MES]   - totalRecR) <= 0){
				porRec = 0;
			}
			var vis = (visitados[v.MES] - totalVisG);
			var porVis = vis / visitados[v.MES] *100; 
			if(vis < 0){
				vis = 0;
				porVis = 0;
			}
			temp = ["Sin Trazabilidad", hab,porHab.toFixed(1)+"%",(trazRec[5] - totalRecG), porRec.toFixed(1)+"%",porVis,porVis.toFixed(1)+"%"];
			datos2.push(temp);
			*/
			console.log(datos);
			if(tabla2){
				tabla2.destroy();
		        $('#tbl_RendimientoVlidadr2').empty(); 
			}
			columnas = ["Región","Habilitación","Peso Qhab","CDR","Peso CDR","Visitas","Peso Visita"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			$("#tbl_RendimientoVlidadr2").html('<tfoot><tr><th style="text-align:right">Total:</th><th></th><th></th><th></th><th></th><th></th><th></th></tfoot>')
			tabla2 = $('#tbl_RendimientoVlidadr2').DataTable({
				data: datos,
				columns: finalColumn,
				autoWidth: true,
				ordering: false,
				paging: false,
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
		            var cantidadTotal = api.column(1).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
		            $( api.column(1).footer() ).html(cantidadTotal);
		            var cantidadTotal = api.column(3).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
		            $( api.column(3).footer() ).html(cantidadTotal);
		            var cantidadTotal = api.column(5).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
		            $( api.column(5).footer() ).html(cantidadTotal);
		            
		        }
			});
			$("#tbl_RendimientoVlidadr2_filter").hide();
			$("#tbl_RendimientoVlidadr2_length").hide();
			
			if(tabla3){
				tabla3.destroy();
		        $('#tbl_RendimientoVlidadr3').empty(); 
			}
			columnas = ["GIRO","Habilitación","Peso Qhab","CDR","Peso CDR","Visitas","Peso Visita"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			$("#tbl_RendimientoVlidadr3").html('<tfoot><tr><th style="text-align:right">Total:</th><th></th><th></th><th></th><th></th><th></th><th></th></tfoot>')
			tabla3 = $('#tbl_RendimientoVlidadr3').DataTable({
				data: datos2,
				columns: finalColumn,
				autoWidth: true,
				ordering: false,
				paging: false,
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
		            var cantidadTotal = api.column(1).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
		            $( api.column(1).footer() ).html(cantidadTotal);
		            var cantidadTotal = api.column(3).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
		            $( api.column(3).footer() ).html(cantidadTotal);
		            var cantidadTotal = api.column(5).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
		            $( api.column(5).footer() ).html(cantidadTotal);
		            
		        }
			});
			$("#tbl_RendimientoVlidadr3_filter").hide();
			$("#tbl_RendimientoVlidadr3_length").hide();
			
		}).error(function (e) {
			console.log(e);
			
		})
		
	}, 50);
	
}

function buscar(){
	$.fn.dataTable.ext.errMode = 'none';
	var input2 = {
        URL: "Resumen_periodo_PDV"
    };
	getData(input2).then(function(res){
		console.log(res)
		var header = [];
		var columnas = [];
		var obj2 = {};
		$.each(res.data[0], function (key, value) {
			if(key != "DESCRIPCION"){
				var obj = {}
				obj["head"] = key;
				header.push(obj);
			}else{
				obj2["head"] = key;
			}
		}); 
		header.unshift(obj2)
		var datos = [];
		$.each(res.data, function(k,v){
			var tbl = [];
			for(var i = 0; i < header.length; i++){
				if(isNaN(parseInt([v[header[i].head]]))){
					tbl.push([v[header[i].head]]);
				}else{
					tbl.push(formatNumber(parseInt([v[header[i].head]])));
				}
			}
			datos.push(tbl);
		})
		for(var i = 0; i < header.length; i++){
			columnas.push(header[i].head);
		}
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false,
			paging: false,
		});
		$("#tbl_RendimientoVlidadr_filter").hide();
		$("#tbl_RendimientoVlidadr_length").hide();
	})
}

function getGrafico2(){
	am4core.ready(function() {am4core.useTheme(am4themes_animated);
	
	// Create chart instance
	var chart = am4core.create("chartdiv2", am4charts.XYChart);

	//

	// Increase contrast by taking evey second color
	chart.colors.step = 2;

	// Add data
	//chart.data = generateChartData();
	chart.data = $chartData2;
	console.log($chartData2);
	//chart.data = $chartData2;
	
	// Create axes
	var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
	categoryAxis.dataFields.category = "DESCRIPCION";
	categoryAxis.renderer.minGridDistance = 50;
	categoryAxis.title.text = "";
	categoryAxis.renderer.grid.template.location = 315;
	categoryAxis.renderer.labels.template.rotation = 315;
	categoryAxis.renderer.grid.template.location = 315;
	categoryAxis.renderer.cellStartLocation = 0.1;
	categoryAxis.renderer.cellEndLocation = 0.9;
	

	// Create series
	function createAxisAndSeries(field, name, opposite, bullet) {
	  var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
	  if(chart.yAxes.indexOf(valueAxis) != 0){
	  	valueAxis.syncWithAxis = chart.yAxes.getIndex(0);
	  }
	  
	  var series = chart.series.push(new am4charts.LineSeries());
	  series.dataFields.valueY = field;
	  series.dataFields.categoryX = "DESCRIPCION";
	  series.strokeWidth = 2;
//	  series.yAxis = valueAxis;
	  series.name = name;
	  series.tooltipText = "{name}: [bold]{valueY}[/]";
	  series.tensionX = 0.8;
//	  series.showOnInit = true;
	  console.log(series.dataFields.dateX)
	  
	  var interfaceColors = new am4core.InterfaceColorSet();
	  
	  switch(bullet) {
	    case "triangle":
	      var bullet = series.bullets.push(new am4charts.Bullet());
	      bullet.width = 12;
	      bullet.height = 12;
	      bullet.horizontalCenter = "middle";
	      bullet.verticalCenter = "middle";
	      
	      var triangle = bullet.createChild(am4core.Triangle);
	      triangle.stroke = interfaceColors.getFor("background");
	      triangle.strokeWidth = 2;
	      triangle.direction = "top";
	      triangle.width = 12;
	      triangle.height = 12;
	      break;
	    case "rectangle":
	      var bullet = series.bullets.push(new am4charts.Bullet());
	      bullet.width = 10;
	      bullet.height = 10;
	      bullet.horizontalCenter = "middle";
	      bullet.verticalCenter = "middle";
	      
	      var rectangle = bullet.createChild(am4core.Rectangle);
	      rectangle.stroke = interfaceColors.getFor("background");
	      rectangle.strokeWidth = 2;
	      rectangle.width = 10;
	      rectangle.height = 10;
	      break;
	    default:
	      var bullet = series.bullets.push(new am4charts.CircleBullet());
	      bullet.circle.stroke = interfaceColors.getFor("background");
	      bullet.circle.strokeWidth = 2;
	      break;
	  }
	  
	  valueAxis.renderer.line.strokeOpacity = 1;
	  valueAxis.renderer.line.strokeWidth = 2;
	  valueAxis.renderer.line.stroke = series.stroke;
	  valueAxis.renderer.labels.template.fill = series.stroke;
	  valueAxis.renderer.opposite = opposite;
	}


	createAxisAndSeries("TRAZADOS","POTENCIAL DE PDV TRAZADOS",  true, "triangle");
	createAxisAndSeries("ACTIVOS" ,"Q PDV ACTIVOS",  true, "rectangle");
	createAxisAndSeries("ANTIGUOS","Q PDV ANTIGUOS",  true, "rectangle");
	createAxisAndSeries("NUEVOS"  ,"Q PDV NUEVOS",  true, "rectangle");
	createAxisAndSeries("PERDIDOS","Q PDV PERDIDOS",  true, "rectangle");
	createAxisAndSeries("ACTIVO_MES" ,"Q PDV ACTIVO EN EL MES",  true, "rectangle");
	createAxisAndSeries("ACTIVOS_VENTA","Q PDV ACTIVO EN EL MES",  true, "rectangle");

	// Add legend
	chart.legend = new am4charts.Legend();

	// Add cursor
	chart.cursor = new am4charts.XYCursor();

	// generate some random data, quite different range
	});
}