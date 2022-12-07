var tabla;
getRegion();
getProvincia();
getComuna();
var region;
var provincia;
var comuna;
var desde;
var hasta;
//getGraficos();
$('#periodo').datepicker({
	dateFormat:'yy-mm'
});
var sub = 0;
if(USER.TIPO_RECEPTOR == 1){
	sub = USER.COD_RECEPTOR;
}
function cargar(){
	loading.show();
	setTimeout(function(){
		nuevoChart();
		getGraficos();
		buscar();
		getDatos3();		
		loading.hide();
	}, 50);
}

function nuevoChart(){
	var fechadesde = formatFecha($("#fechadesde").val());
	var fechahasta = formatFecha($("#fechahasta").val());
	var input = "";
	
	
	input = {
	        SP: "GET_HABILITACIONES_PERMANENCIA_2",
	        FILTERS: [
	            {value: fechadesde},
	            {value: fechahasta},
	            {value: 356},
	            {value: sub},
	            {value: $("#region").val()},
	            {value: $("#provincia").val()},
	            {value: $("#comuna").val()},
	        ]
	    };
	
    console.log(input);
	
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async : false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		console.log(response);
		am4core.ready(function(){
		am4core.useTheme(am4themes_animated);
		// Themes end

		var chart = am4core.create("grafico4", am4charts.XYChart);

		var valueAxisX = chart.xAxes.push(new am4charts.ValueAxis());
		valueAxisX.renderer.ticks.template.disabled = true;
		valueAxisX.renderer.axisFills.template.disabled = true;
		valueAxisX.renderer.minGridDistance = 10;
		valueAxisX.renderer.max = 50;

		var valueAxisY = chart.yAxes.push(new am4charts.ValueAxis());
		valueAxisY.renderer.ticks.template.disabled = true;
		valueAxisY.renderer.axisFills.template.disabled = true;		
		valueAxisY.renderer.strictMinMax = true;
		valueAxisY.renderer.minGridDistance = 10;
		valueAxisY.renderer.max = 50;
		
		var series = chart.series.push(new am4charts.LineSeries());
		series.dataFields.valueX = "PORCENTAJE_HABILITACION";
		series.dataFields.valueY = "PORCENTAJE_RECARGA";
		series.dataFields.value = "VENDIDOS";
		series.strokeOpacity = 0;
		series.sequencedInterpolation = true;
		series.tooltip.pointerOrientation = "vertical";

		var bullet = series.bullets.push(new am4core.Circle());
		bullet.fill = am4core.color("#ff0000");
		bullet.propertyFields.fill = "COLOR";
		bullet.strokeOpacity = 0;
		bullet.strokeWidth = 2;
		bullet.fillOpacity = 0.5;
		bullet.stroke = am4core.color("#ffffff");
		bullet.hiddenState.properties.opacity = 0;
		bullet.tooltipText = "[bold]{COMERCIO}:[/]\nVendidos: {value.value}\nStock: {STOCK}\nHabilitados: {valueX.value}%\nRecargados:{valueY.value}%";

		var outline = chart.plotContainer.createChild(am4core.Circle);
		outline.fillOpacity = 0;
		outline.strokeOpacity = 0.8;
		outline.stroke = am4core.color("#ff0000");
		outline.strokeWidth = 2;
		outline.hide(0);

		var blurFilter = new am4core.BlurFilter();
		outline.filters.push(blurFilter);

		bullet.events.on("over", function(event) {
		    var target = event.target;
		    outline.radius = target.pixelRadius + 2;
		    outline.x = target.pixelX;
		    outline.y = target.pixelY;
		    outline.show();
		})

		bullet.events.on("out", function(event) {
		    outline.hide();
		})

		var hoverState = bullet.states.create("hover");
		hoverState.properties.fillOpacity = 1;
		hoverState.properties.strokeOpacity = 1;

		series.heatRules.push({ target: bullet, min: 2, max: 60, property: "radius" });

		bullet.adapter.add("tooltipY", function (tooltipY, target) {
		    return -target.radius;
		})

		chart.cursor = new am4charts.XYCursor();
		chart.cursor.behavior = "zoomXY";
		chart.cursor.snapToSeries = series;

		chart.scrollbarX = new am4core.Scrollbar();
		chart.scrollbarY = new am4core.Scrollbar();

		chart.data = response.data;
		
	})
	
	}).error(function (e) {
		console.log(e);
	})
	
}
function getGraficos(){
	am4core.ready(function() {
		var input2 = {
	        SP: "PUNTOS_VENTAS_SEGMENTACION",
	        FILTERS: [
	            {value: $("#region").val()},
	            {value: $("#provincia").val()},
	            {value: $("#comuna").val()},
	            {value: formatFecha($("#fechadesde").val())},
	            {value: formatFecha($("#fechahasta").val())},
	            {value: sub}
	        ]
	    };
		console.log(input2);
		var dataResponse = [];
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			async: false,
//				timeout: 10000,
			data: JSON.stringify(input2),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response)
			dataResponse = response;
			am4core.useTheme(am4themes_animated);
			var chartPie = am4core.create("grafico1", am4charts.PieChart);
			chartPie.data = response.data;
			var pieSeries = chartPie.series.push(new am4charts.PieSeries());
			pieSeries.dataFields.value = "QPDV";
			pieSeries.dataFields.category = "TIPO";
			pieSeries.slices.template.stroke = am4core.color("#fff");
			pieSeries.slices.template.strokeWidth = 2;
			pieSeries.slices.template.strokeOpacity = 1;
			
			pieSeries.hiddenState.properties.opacity = 1;
			pieSeries.hiddenState.properties.endAngle = -90;
			pieSeries.hiddenState.properties.startAngle = -90;
			pieSeries.slices.template.events.on("hit", function(ev) {
			  console.log(ev.target.dataItem.category);
			  cargaDetalle(ev.target.dataItem.category);
			});
//			var chartBar = am4core.create("grafico2", am4charts.XYChart)
		}).error(function (e) {
			console.log(e);
		})
		region = $("#region").val();
		provincia = $("#provincia").val();
		comuna = $("#comuna").val();
		desde = formatFecha($("#fechadesde").val());
		hasta = formatFecha($("#fechahasta").val());
		/*var input1 = {
	        SP: "GESTION_SEGMENTACION",
	        FILTERS: [
	            {value: $("#region").val()},
	            {value: $("#provincia").val()},
	            {value: $("#comuna").val()},
	            {value: formatFecha($("#fechadesde").val())},
	            {value: formatFecha($("#fechahasta").val())}
	        ]
	    };
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			 async: false,
//				timeout: 10000,
			data: JSON.stringify(input1),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (res) {*/
			console.log(dataResponse)
			var datos = [];
			var qpdv = 0;
			var vend = 0;
			var habi = 0;
			var rec = 0;
			$.each(dataResponse.data, function(k,v){
				qpdv += v.QPDV*1;
				vend += v.Q_RECEPCION*1;
				habi += v.HABILITADOS*1;
				rec += v.RECARGAS*1;
				var tbl = [v.TIPO,v.QPDV,v.Q_RECEPCION, v.HABILITADOS, (isNaN(v.PORCENTAJE_HABILITADO*1)?0:v.PORCENTAJE_HABILITADO*1).toFixed(1)+"% ",v.RECARGAS ,(isNaN(v.PORCENTAJE_RECARGADOS*1)?0:v.PORCENTAJE_RECARGADOS*1).toFixed(1)+"% "];
				datos.push(tbl)
			})
			var columnas = ["Segmentacion","QPDV","Vendidos", "HABILITADOS", "Tasa Habilitaciones(%)","Recargados", "Tasa Recargas(%)"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			if(tabla){
				tabla.destroy();
		        $('#tbl_RendimientoVlidadr').empty(); 
			}
			$("#tbl_RendimientoVlidadr").html('<tfoot><tr><th>Total:</th><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>');
			tabla = $('#tbl_RendimientoVlidadr').DataTable({
				data: datos,
				columns: finalColumn,
				autoWidth: true,
				ordering: false,
				footerCallback: function ( row, data, start, end, display ) {
		            var api = this.api(), data;
		            var intVal = function ( i ) {
		                return typeof i === 'string' ?i.replace(/[\$,]/g, '')*1 :
		                typeof i === 'number' ?i : 0;
		            };
		            t2 = api.column(2).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
//		            t2 = api.column(4).data().reduce( function (a, b) {
//	                    return intVal(a) + intVal(b);
//	                }, 0 );
		            $( api.column(1).footer() ).html(qpdv);
		            $( api.column(2).footer() ).html(vend);
		            $( api.column(3).footer() ).html(habi);
		            $( api.column(5).footer() ).html(rec);
		        }
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
		            });
		            
		            
		    	}else{
		    		$(this).html("");
		    	}
		    });
		/*}).error(function (e) {
			console.log(e);
		})*/
		

	});
}
var $REGIONES;
var $PROVINCIAS;
var $COMUNAS;
function getRegion(){
	var input = {
        SP: "GET_CHILE",
        FILTERS: [
            {value: 'REGIONES'},
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
		$REGIONES = response.data;
		var option = "<option value='0'>Todas</option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.region+"</option>";
		})
		$("#region").html(option);
		
	}).error(function (e) {
		console.log(e);
	})
}
function getProvincia(p){
	var input = {
        SP: "GET_CHILE",
        FILTERS: [
            {value: 'PROVINCIAS'},
            {value: p == undefined? 0:p}
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
		console.log($PROVINCIAS)
		if($PROVINCIAS == undefined){
			$PROVINCIAS = response.data;
		}
		var option = "<option value='0'>Todas</option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.provincia+"</option>";
		})
		$("#provincia").html(option);
	}).error(function (e) {
		console.log(e);
	})
}
function getComuna(comuna){
	var input = {
        SP: "GET_CHILE",
        FILTERS: [
            {value: 'COMUNAS'},
            {value: comuna == undefined? 0:comuna}
        ]
    };
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		async: false,
		data: JSON.stringify(input),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		if(!$COMUNAS){
			$COMUNAS = response.data;
		}
		var option = "<option value='0'>Todas</option>";
		$.each(response.data, function(k,v){
			option += "<option value='"+v.id+"'>"+v.comuna+"</option>";
		})
		$("#comuna").html(option);
		
	}).error(function (e) {
		console.log(e);
	})
}


var tabla2;
var datosExcel;
var datos2;
var columnas;
//getDatos()
function buscar(){
	var input = {
        URL: "RESUMEN_COMERCIO_CATEGORIZACION",
        FILTERS: [{
        	_REGION: $("#region").val(),
	        _PROVINCIA: $("#provincia").val(),
	        _COMUNA: $("#comuna").val(),
	        _DESDE: formatFecha($("#fechadesde").val()),
	        _HASTA: formatFecha($("#fechahasta").val())
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
			/*dom: 'Bfrtip',
		    buttons: [
		        {  extend: 'excel',
		        	footer: true ,
		            text: 'Excel',
		            className: 'btn btn-success',
		        }
		    ],*/ 
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
	            //$( api.column(4).footer() ).html($pHab);
	            //$( api.column(5).footer() ).html($pRec);
	        }
		});
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
		$("#tbl_RendimientoVlidadr2_filter").hide();
		$("#tbl_RendimientoVlidadr2_length").hide();
	})
}


var tabla3;
var datosExcel;
var datos2;
var columnas;
function getDatos3(){
	var input = {
        URL: "STOCK_CANTIDAD",
        FILTERS: [{
        	_REGION: $("#region").val(),
	        _PROVINCIA: $("#provincia").val(),
	        _COMUNA: $("#comuna").val(),
	        _DESDE: formatFecha($("#fechadesde").val()),
	        _HASTA: formatFecha($("#fechahasta").val())
        }]
    };
	//get comercios
	console.log(input);
	getData(input).then(function(res){
		console.log(res)
		var Arrdatos = []
		var datos = []
		var qpdv1 = 0;
		var qpdv2 = 0;	
		$.each(res.data, function(k,v){
			qpdv1 += v["0-5_356"] + v["5-10_356"] + v["10-20_356"] + v["20-50_356"] + v["50-100_356"] + v["100+_356"];
			qpdv2 += v["0-5_374"] + v["5-10_374"] + v["10-20_374"] + v["20-50_374"] + v["50-100_374"] + v["100+_374"];
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
		$("#tbl_RendimientoVlidadr3").html('<tfoot><tr><th>Total:</th><th></th><th></th></tr></tfoot>');
		columnas = ["Categoria","QPDV(Chip 356)","QPDV(Chip 374)"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla3 = $('#tbl_RendimientoVlidadr3').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false,
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
	            $( api.column(1).footer() ).html(qpdv1);
	            $( api.column(2).footer() ).html(qpdv2);
	        }
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
var tabla4;
function cargaDetalle(tipo){
	$.fn.dataTable.ext.errMode = 'none';
	var input1 = {
        SP: "PUNTOS_VENTAS_SEGMENTACION_DETALLE",
        FILTERS: [
            {value: region},
            {value: provincia},
            {value: comuna},
            {value: desde},
            {value: hasta},
            {value: tipo},
        ]
    };
	console.log(input1);
	var html = "";
	html +=	'<div class="col-xs-12 col-sm-12 col-md-12 portlet light">';
	html +=  	'<div class="col-xs-12 col-sm-12 col-md-12"><br></div>';
	html +=		'<div class="col-xs-12 col-sm-12 col-md-12">';
	html +=			'<div style="width: 100%; height: 500px; float: left; overflow:auto;">';
	html +=				'<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr4"></table>';
	html +=			'</div>';
	html +=		'</div>';
	html +=  	'<div class="col-xs-12 col-sm-12 col-md-12"><br></div>';
	html +=  	'<div class="col-xs-12 col-sm-12 col-md-12">';
	html +=			'<div class="col-sm-6 col-md-6">';
	html +=				'<label style="color: #337ab7;font-weight: bold" id="lnglat"></label>';
	html +=			'</div>';
	html +=		'</div>';
	html +=  	'<div class="col-xs-12 col-sm-12 col-md-12"><br></div>';
	html +=		'<div class="col-xs-12 col-sm-12 col-md-12">'+
					'<div class="btn btn-circle red btn-outline" onclick="closeModal()" id="cancelarFormaAp">Cerrar</div>'+ 
				'</div>'
	html +=	'</div>';
	loading.show();
	setTimeout(function(){
		
	
	popUp("Detalle", html, true, "900px", true);
	
	$.ajax({
		url: IPSERVER + "post",
		method: 'POST',
		dataType: 'json',
		 async: false,
//				timeout: 10000,
		data: JSON.stringify(input1),
		headers: { 'Content-Type': "application/json", 'connection-properties': configService }
	}).success(function (response) {
		var datos = [];
		var Q_RECEPCION = 0;
		var HABILITADO = 0;
		var PENDIENTE_HABILITAR = 0;
		console.log(response.data);
		$.each(response.data, function(k,v){
			Q_RECEPCION += v.Q_RECEPCION*1;
			HABILITADO += v.HABILITADOS*1;
			var pend_habil = (v.Q_RECEPCION*1) - (v.HABILITADOS*1);
			PENDIENTE_HABILITAR += pend_habil;
			var tbl = [v.COMERCIO, v.DIRECCION,v.comuna, v.provincia, v.region, v.Q_RECEPCION, v.HABILITADOS, pend_habil, v.TIPO];
			datos.push(tbl);
		})
		
		if(tabla4){
			tabla4.destroy();
	        $('#tbl_RendimientoVlidadr4').empty(); 
		}
		$("#tbl_RendimientoVlidadr4").html('<tfoot><tr><th>Total:</th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>');
		columnas = ["Comercio", "Direccion","Comuna","Provincia","Region","Q Recepcion", "HABILITADO", "PENDIENTE HABILITAR", "TIPO"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		console.log(datos);
		tabla4 = $('#tbl_RendimientoVlidadr4').DataTable({
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
	            $( api.column(5).footer() ).html(Q_RECEPCION);
	            $( api.column(6).footer() ).html(HABILITADO);
	            $( api.column(7).footer() ).html(PENDIENTE_HABILITAR);
	        }
		});
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
		$("#tbl_RendimientoVlidadr4_filter").hide();
		$("#tbl_RendimientoVlidadr4_length").hide();
		$(".buttons-excel").css("margin-top", "64px");

	});
	loading.hide();
	}, 50);
}
