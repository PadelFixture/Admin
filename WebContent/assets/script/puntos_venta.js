var dia = formatFecha(dateHoy());
dia = dia.split("-");
dia = "01-"+dia[1]+"-"+dia[2];
$("#fechadesde").val(dia);
$("#fechahasta").val(formatFecha(dateHoy()))
var $chartData;
var $chartData3;
var get = {
    SP: "GET_ANNOS",
    FILTERS: [
    ]
};
$.ajax({
	url: IPSERVER + "post",
	method: 'POST',
	dataType: 'json',
	async: false,
//		timeout: 10000,
	data: JSON.stringify(get),
	headers: { 'Content-Type': "application/json", 'connection-properties': configService }
}).success(function (response) {
	console.log(response)
	var option = "";
	var x = 0;
	$.each(response.data, function(k,v){
		if(v.ANNO) {
			if(x == 0){
				option += "<option selected value="+v.ANNO+">"+v.ANNO+"</option>";
			} else {
				option += "<option value="+v.ANNO+">"+v.ANNO+"</option>";
			}
			x++;
		}
		
	})
	$("#anno").html(option)
}).error(function (e) {
	console.log(e);
})

cargaDatos();
function cargaDatos(){
	var input = {
		    SP: "HABILITACIONES_PERIODO",
		    FILTERS: [
		        {value: '356'},
		        {value: $("#anno").val()}
		    ]
		};
		console.log(input);
	loading.show();
	setTimeout(function(){ 
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			async: false,
		//	timeout: 10000,
			data: JSON.stringify(input),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response);
			$chartData = response.data;
			console.log($chartData);
			getGraficosRecepcion_Habilitado();
			getCalidadHabilitaciones();
			getMontoRecarga();
			getHabilitacion_Camada();
			loading.hide();
		});
	}, 50);
}

function getGraficosRecepcion_Habilitado(){
	am4core.ready(function() {

		am4core.useTheme(am4themes_animated);
		
		var chartPie = am4core.create("grafico1", am4charts.PieChart);
		
		chartPie.data = [ {
		  "country": "Lithuania",
		  "litres": 501.9
		}, {
		  "country": "Czechia",
		  "litres": 301.9
		}, {
		  "country": "Ireland",
		  "litres": 201.1
		}, {
		  "country": "Germany",
		  "litres": 165.8
		}, {
		  "country": "Australia",
		  "litres": 139.9
		}, {
		  "country": "Austria",
		  "litres": 128.3
		}, {
		  "country": "UK",
		  "litres": 99
		}, {
		  "country": "Belgium",
		  "litres": 60
		} ];
		
		var pieSeries = chartPie.series.push(new am4charts.PieSeries());
		pieSeries.dataFields.value = "litres";
		pieSeries.dataFields.category = "country";
		pieSeries.slices.template.stroke = am4core.color("#fff");
		pieSeries.slices.template.strokeWidth = 2;
		pieSeries.slices.template.strokeOpacity = 1;
		
		pieSeries.hiddenState.properties.opacity = 1;
		pieSeries.hiddenState.properties.endAngle = -90;
		pieSeries.hiddenState.properties.startAngle = -90;
		$(".g1").html($("#anno").val())
		var chartBar = am4core.create("grafico2", am4charts.XYChart)
		console.log(USER)
			
		var chart = AmCharts.makeChart("grafico2", {
		  type: "serial",
		  theme: "none",
		  categoryField: "mes",
		  rotate: false,
		  startDuration: 1,
		  categoryAxis: {
		    gridPosition: "start",
		    position: "left"
		  },
		  trendLines: [],
		  graphs: [
		      {
		    	  balloonText: "Cantidad Ingresada:[[value]]",
		    	  fillAlphas: 0.8,
		    	  id: "AmGraph-1",
		    	  lineAlpha: 0.2,
		    	  title: "Cantidad Ingresada",
		    	  type: "column",
		    	  valueField: "ingresados"
		      },
		      {
		    	  balloonText: "Habilitado:[[value]]",
		    	  fillAlphas: 0.8,
		    	  id: "AmGraph-2",
		    	  lineAlpha: 0.2,
		    	  title: "Habilitado",
		    	  type: "column",
		    	  valueField: "habilitados"
		      }
		  ],
		  "guides": [],
		  "valueAxes": [
		    {
		      "id": "ValueAxis-1",
		      "position": "top",
		      "axisAlpha": 0,
		      "minimum": 0
		    }
		  ],
		  "allLabels": [],
		  "balloon": {},
		  "titles": [],
		  "dataProvider": $chartData,
		    "export": {
		    	"enabled": true
		     }

		});

	});

}

function getCalidadHabilitaciones(){
	am4core.ready(function() {am4core.useTheme(am4themes_animated);
	// Themes end

	// Create chart instance
	var chart = am4core.create("chartdiv2", am4charts.XYChart);

	//

	// Increase contrast by taking evey second color
	chart.colors.step = 2;

	// Add data
	chart.data = generateChartData();
	console.log(chart.data);

	// Create axes
	var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
	categoryAxis.dataFields.category = "date";
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
	  series.dataFields.categoryX = "date";
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

	createAxisAndSeries("vendidos", "Vendidos", false, "circle");
	createAxisAndSeries("habilitados", "Habilitados", true, "triangle");
	createAxisAndSeries("con_recarga_0_30", "Recargas al 0-30", true, "rectangle");
	createAxisAndSeries("q_recarga_0_30", "Q Recargas 0-30", true, "rectangle");
	createAxisAndSeries("con_recarga_30_60", "Recargas al 30-60", true, "rectangle");
	createAxisAndSeries("q_recarga_30_60", "Q Recargas 30-60", true, "rectangle");

	// Add legend
	chart.legend = new am4charts.Legend();

	// Add cursor
	chart.cursor = new am4charts.XYCursor();

	// generate some random data, quite different range
	function generateChartData() {
	  var chartData = [];
	  var firstDate = new Date();
	  firstDate.setDate(firstDate.getDate() - 100);
	  firstDate.setHours(0, 0, 0, 0);

	  var visits = 1600;
	  var hits = 2900;
	  var views = 8700;
	  console.log($chartData);
	  $.each($chartData, function(k,v){
		  var json = {
		      date: v.mes,
		      vendidos: v.vendidos,
		      habilitados: v.habilitados,
		      con_recarga_0_30: v.con_recarga_0_30,
		      q_recarga_0_30: v.q_recarga_0_30,
		      q_recarga_30_60: v.q_recarga_30_60,
		      con_recarga_30_60: v.con_recarga_30_60
		  }
		  chartData.push(json)
	  })
	  return chartData;
	}});
}

function getMontoRecarga(){
	am4core.ready(function() {am4core.useTheme(am4themes_animated);
	// Themes end

	// Create chart instance
	var chart = am4core.create("chartdiv", am4charts.XYChart);

	//

	// Increase contrast by taking evey second color
	chart.colors.step = 2;

	// Add data
	chart.data = generateChartData();

	// Create axes
	var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
	categoryAxis.dataFields.category = "date";
	categoryAxis.title.text = "";
	categoryAxis.renderer.grid.template.location = 315;
	categoryAxis.renderer.labels.template.rotation = 315;
	categoryAxis.renderer.grid.template.location = 315;
	categoryAxis.renderer.minGridDistance = 20;
	categoryAxis.renderer.cellStartLocation = 0.1;
	categoryAxis.renderer.cellEndLocation = 0.9;

	// Create series
	function createAxisAndSeries(field, name, opposite, bullet) {
	  var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
	  if(chart.yAxes.indexOf(valueAxis) != 0){
	  	valueAxis.syncWithAxis = chart.yAxes.getIndex(0);
	  }
	  
	  
	  
	  var interfaceColors = new am4core.InterfaceColorSet();
	  
	  switch(bullet) {
	    case "triangle":
	      var series = chart.series.push(new am4charts.ColumnSeries());
	  	  series.dataFields.valueY = field;
	  	  series.dataFields.categoryX = "date";
	  	  series.strokeWidth = 2;
//	  	  series.yAxis = valueAxis;
	  	  series.name = name;
	  	  series.tooltipText = "{date} {name}: [bold]{valueY}[/]";
	  	  series.tensionX = 0.8;
//	  	  series.showOnInit = true;
	  	  console.log(series.dataFields.dateX)
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
	      var series = chart.series.push(new am4charts.LineSeries());
	  	  series.dataFields.valueY = field;
	  	  series.dataFields.categoryX = "date";
	  	  series.strokeWidth = 2;
//	  	  series.yAxis = valueAxis;
	  	  series.name = name;
	  	  series.tooltipText = "{date} {name}: [bold]{valueY}[/]";
	  	  series.tensionX = 0.8;
//	  	  series.showOnInit = true;
	  	  console.log(series.dataFields.dateX)
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

	createAxisAndSeries("MONTO_0_30", "0-30", false, "triangle");
	createAxisAndSeries("MONTO_30_60", "30-60", true, "triangle");
	createAxisAndSeries("MONTO_TOTAL", "TOTAL", true, "rectangle");
	//createAxisAndSeries("Q_RECARGAS_0_30", "Recargas 0 a 30", true, "rectangle");
	//createAxisAndSeries("Q_RECARGAS_30_60", "Recargas 30 a 60", true, "rectangle");
	//createAxisAndSeries("Q_RECARGAS_60_90", "Recargas 60 a 90", true, "rectangle");

	// Add legend
	chart.legend = new am4charts.Legend();

	// Add cursor
	chart.cursor = new am4charts.XYCursor();

	// generate some random data, quite different range
	function generateChartData() {
	  var chartData = [];
	  var firstDate = new Date();
	  firstDate.setDate(firstDate.getDate() - 100);
	  firstDate.setHours(0, 0, 0, 0);

	  var visits = 1600;
	  var hits = 2900;
	  var views = 8700;
	  $.each($chartData, function(k,v){
		  var total = parseInt(v.monto_recarga_0_30) + parseInt(v.monto_recarga_30_60);
		  var json = {
		      date: v.mes,
		      MONTO_0_30: v.monto_recarga_0_30,
		      MONTO_30_60: v.monto_recarga_30_60,
		      MONTO_TOTAL: total
		      
		  }
		  console.log(json);
		  chartData.push(json)
	  })
	  return chartData;
	}});
}


function getHabilitacion_Camada(){
	am4core.ready(function() {am4core.useTheme(am4themes_animated);
	// Themes end

	// Create chart instance
	var chart = am4core.create("chartdiv3", am4charts.XYChart);

	//

	// Increase contrast by taking evey second color
	chart.colors.step = 2;

	// Add data
	console.log(generateChartData());
	chart.data = generateChartData();

	// Create axes
	var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
	categoryAxis.dataFields.category = "date";
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
	  series.dataFields.categoryX = "date";
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
	createAxisAndSeries("VENDIDOS", "Vendidos", false, "circle");
	createAxisAndSeries("INGRESADOS", "Ingresados", false, "circle");
	createAxisAndSeries("HABILITADO", "Habilitados", true, "triangle");
	createAxisAndSeries("NOHABILITADO", "No Habilitados", true, "rectangle");
	

	// Add legend
	chart.legend = new am4charts.Legend();

	// Add cursor
	chart.cursor = new am4charts.XYCursor();

	// generate some random data, quite different range
	function generateChartData() {
	  var chartData = [];
	  var firstDate = new Date();
	  firstDate.setDate(firstDate.getDate() - 100);
	  firstDate.setHours(0, 0, 0, 0);

	  var visits = 1600;
	  var hits = 2900;
	  var views = 8700;
	  $.each($chartData, function(k,v){
		  var json = {
			INGRESADOS:	  v.ingresados,
		    NOHABILITADO: v.ingresados-v.habilitados_camada*1,
			HABILITADO: v.habilitados_camada,
			date: v.mes,
			VENDIDOS: v.vendidos_camada
		  }
		  chartData.push(json)
	  })
	  return chartData;
	}});
}




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
			
			
			am4core.ready(function() {am4core.useTheme(am4themes_animated);
			// Themes end

			// Create chart instance
			var chart = am4core.create("chartdiv5", am4charts.XYChart);

			//

			// Increase contrast by taking evey second color
			chart.colors.step = 2;

			// Add data
			chart.data = generateChartData();

			// Create axes
			var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
			categoryAxis.dataFields.category = "date";
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
			  series.dataFields.categoryX = "date";
			  series.strokeWidth = 2;
//			  series.yAxis = valueAxis;
			  series.name = name;
			  series.tooltipText = "{name}: [bold]{valueY}[/]";
			  series.tensionX = 0.8;
//			  series.showOnInit = true;
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
			createAxisAndSeries("CAPACITADO", "Capacitado", false, "circle");
			createAxisAndSeries("NOCAPACITADO", "No Capacitado", false, "circle");
			

			// Add legend
			chart.legend = new am4charts.Legend();

			// Add cursor
			chart.cursor = new am4charts.XYCursor();

			// generate some random data, quite different range
			function generateChartData() {
			  var chartData = [];
			  var firstDate = new Date();
			  firstDate.setDate(firstDate.getDate() - 100);
			  firstDate.setHours(0, 0, 0, 0);

			  var visits = 1600;
			  var hits = 2900;
			  var views = 8700;
			  
			   	var tCap = 0;
				var tNoCap = 0;
				$.each(response2.data, function(k,v){
					tCap += v.CAPACITADO*1;
					tNoCap += v.NOCAPACITADO*1;
					var json = {
					CAPACITADO:	  v.CAPACITADO,
					NOCAPACITADO: v.NOCAPACITADO,
					date: v.GIRO
					
				  }
				  chartData.push(json)
				})
				var total = tCap + tNoCap;
				$("#cap").html(tCap);
				$("#nocap").html(tNoCap);
				$("#total").html(total);
			  return chartData;
			}});
			
			
			
			

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

var tabla;
var tabla2;
var datos2;
var columnas;
getDatosResumen();
getDatosResumen2();
function getDatosResumen(){
	console.log(formatFecha(dateHoy()));
	console.log(dateHoy());
	var input = {
        URL: "GET_RESUMEN_PUNTOS_DE_VENTA",
        FILTERS: [{
        	DESDE: dateHoy(),
        	HASTA: dateHoy()
        }]
    };
	//get comercios
	console.log(input);
	getData(input).then(function(response){
		console.log(response)
		var datos = [];
		var QPDV = 0;
		var QPDVVEN = 0;
		var QPDVVIS = 0;
		var QPDVPUB = 0;
		var CAPACITADOS = 0;
		var CHIPS356 = 0;
		var CHIPS374 = 0;
		$.each(response.data, function(k,v){
			var tbl = [v.DESCRIPCION,v.QPDV,v.QPDVVEN,v.QPDVVIS, v.QPDVPUB,  v.CAPACITADOS ,v.CHIPS356,v.CHIPS374];
			QPDV += v.QPDV;
			QPDVVEN += v.QPDVVEN;
			QPDVVIS += v.QPDVVIS;
			QPDVPUB += v.QPDVPUB;
			CAPACITADOS += v.CAPACITADOS;
			CHIPS356 += v.CHIPS356;
			CHIPS374 += v.CHIPS374;	
			datos.push(tbl);
		})
		var total = ["TOTAL", QPDV,QPDVVEN,QPDVVIS,QPDVPUB,CAPACITADOS, CHIPS356, CHIPS374];
		datos.push(total);
		if(tabla){
			tabla.destroy();
	        $('#tbl_RendimientoVlidadr').empty(); 
		}
		columnas = ["","QPDV","QPDV VENDIDOS","QPDV VISITADOS","QPDV PUBLICIDAD","QPDV CAPACITADOS","Chips 356","Chips 374"];
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
		$(".pagination").hide();
		$(".dataTables_info").hide();
	})
}
function getDatosResumen2(){
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
		var datos = [];
		var QPDV = 0;
		var QPDVVEN = 0;
		var QPDVVIS = 0;
		var QPDVPUB = 0;
		var CAPACITADOS = 0;
		var CHIPS356 = 0;
		var CHIPS374 = 0;
		
		$.each(response.data, function(k,v){
			var tbl = [v.DESCRIPCION,v.QPDV,v.QPDVVEN,v.QPDVVIS, v.QPDVPUB,  v.CAPACITADOS ,v.CHIPS356,v.CHIPS374];
			QPDV += v.QPDV;
			QPDVVEN += v.QPDVVEN;
			QPDVVIS += v.QPDVVIS;
			QPDVPUB += v.QPDVPUB;
			CAPACITADOS += v.CAPACITADOS;
			CHIPS356 += v.CHIPS356;
			CHIPS374 += v.CHIPS374;
			
			datos.push(tbl);
		})
		var total = ["TOTAL", QPDV,QPDVVEN,QPDVVIS,QPDVPUB,CAPACITADOS, CHIPS356, CHIPS374];
		datos.push(total);
		if(tabla2){
			tabla2.destroy();
	        $('#tbl_RendimientoVlidadr2').empty(); 
		}
		columnas = ["","QPDV","QPDV VENDIDOS","QPDV VISITADOS","QPDV PUBLICIDAD","QPDV CAPACITADOS","Chips 356","Chips 374"];
		var finalColumn = [];
		for(var i = 0; i < columnas.length; i++){
			finalColumn.push({title: columnas[i]})
		}
		tabla2 = $('#tbl_RendimientoVlidadr2').DataTable({
			data: datos,
			columns: finalColumn,
			autoWidth: true,
			ordering: false
		});
		datosExcel = getDataExcel(columnas, datos, {exclude: 'Opciones'});
		$("#tbl_RendimientoVlidadr2_filter").hide();
		$("#tbl_RendimientoVlidadr2_length").hide();
		$(".pagination").hide();
		$(".dataTables_info").hide();
	})
}