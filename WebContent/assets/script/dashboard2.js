
getRegion();
getProvincia();
getComuna();
getGraficos4();
function getGraficos4(){
	am4core.ready(function() {
		var input2 = {
	        SP: "GET_COMERCIOS_AGRUPADOS",
	        FILTERS: [
	            {value: "GIRO"},
	            {value: $("#region").val()},
	            {value: $("#provincia").val()},
	            {value: $("#comuna").val()}
	        ]
	    };
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
			am4core.useTheme(am4themes_animated);
			var chartPie = am4core.create("chartdiv4", am4charts.PieChart);
			chartPie.data = response.data;
			var pieSeries = chartPie.series.push(new am4charts.PieSeries());
			pieSeries.dataFields.value = "CANTIDAD";
			pieSeries.dataFields.category = "DESCRIPCION";
			pieSeries.slices.template.stroke = am4core.color("#fff");
			pieSeries.slices.template.strokeWidth = 1;
			pieSeries.slices.template.strokeOpacity = 1;
			
			pieSeries.hiddenState.properties.opacity = 1;
			pieSeries.hiddenState.properties.endAngle = -90;
			pieSeries.hiddenState.properties.startAngle = -90;
			
//			var chartBar = am4core.create("grafico2", am4charts.XYChart)
		}).error(function (e) {
			console.log(e);
		})
		var input1 = {
	        SP: "GET_CAPACITADOS",
	        FILTERS: [
	            {value: "GIRO"},
	            {value: $("#region").val()},
	            {value: $("#provincia").val()},
	            {value: $("#comuna").val()}
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
		}).success(function (response2) {
			console.log(response2)
			var tCap = 0;
			var tNoCap = 0;
			$.each(response2.data, function(k,v){
				tCap += v.CAPACITADO*1;
				tNoCap += v.NOCAPACITADO*1;
			})
			$("#cap").html(tCap);
			$("#nocap").html(tNoCap);
			am4core.useTheme(am4themes_animated);
			var chart = am4core.create("chartdiv5", am4charts.XYChart);
			var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
			chart.data = response2.data;
			categoryAxis.dataFields.category = "GIRO";
			categoryAxis.title.text = "";
			categoryAxis.renderer.grid.template.location = 315;
			categoryAxis.renderer.labels.template.rotation = 315;
			categoryAxis.renderer.grid.template.location = 315;
			categoryAxis.renderer.minGridDistance = 20;
			categoryAxis.renderer.cellStartLocation = 0.1;
			categoryAxis.renderer.cellEndLocation = 0.9;

			var  valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
			valueAxis.min = 0;
			valueAxis.title.text = "";

			// Create series
			function createSeries(field, name, stacked) {
			  var series = chart.series.push(new am4charts.ColumnSeries());
			  series.dataFields.valueY = field;
			  series.dataFields.categoryX = "GIRO";
			  series.name = name;
			  series.columns.template.tooltipText = "{GIRO} {name}: [bold]{valueY}[/]";
			  series.stacked = stacked;
			  series.columns.template.width = am4core.percent(95);
			}

			createSeries("CAPACITADO", "Capacitado", true);
			createSeries("NOCAPACITADO", "No Capacitado", true);

			// Add legend
			chart.legend = new am4charts.Legend();
		}).error(function (e) {
			console.log(e);
		})
		

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