var $chartData;
var input2 = {
    SP: "GET_VENDIDOS_RECARGAS",
    FILTERS: [
        {value: "2020"}
    ]
};
$.ajax({
	url: IPSERVER + "post",
	method: 'POST',
	dataType: 'json',
	async: false,
	data: JSON.stringify(input2),
	headers: { 'Content-Type': "application/json", 'connection-properties': configService }
}).success(function (response) {
	console.log(response.data)
	$chartData = response.data;
	getGrafico()
}).error(function (e) {
	console.log(e);
})
var meses = ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];
function getGrafico(){
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
	createAxisAndSeries("HABILITADO", "Habilitados", true, "triangle");
	createAxisAndSeries("CON_RECARGA", "Recargados 0-30", true, "rectangle");
	createAxisAndSeries("Q_RECARGAS_0_30", "Q Recargas 0-30", true, "rectangle");
	createAxisAndSeries("CON_RECARGA_30_60", "Recargados 30-60", true, "rectangle");
	createAxisAndSeries("Q_RECARGAS_30_60", "Q Recargas 30-60", true, "rectangle");

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
		      date: v.DESCRIPCION,
		      VENDIDOS: v.VENDIDOS,
		      HABILITADO: v.HABILITADO,
		      CON_RECARGA: v.CON_RECARGA,
		      Q_RECARGAS_0_30: v.Q_RECARGAS_0_30,
		      Q_RECARGAS_30_60: v.Q_RECARGAS_30_60,
		      CON_RECARGA_30_60: v.CON_RECARGA_30_60
		  }
		  chartData.push(json)
	  })
	  return chartData;
	}});
}
function generateChartData() {
	var chartData;
	chartData = $chartData;
	return chartData;
}
grafico3()
function grafico3(){
	am4core.ready(function() {
		var datosChart2 = [];
		$.each($chartData, function(k,v){
			datosChart2.push({
				NOHABILITADO: v.VENDIDOS*1-v.HABILITADO*1,
				HABILITADO: v.HABILITADO,
				DESCRIPCION: v.DESCRIPCION
			})
		})
		am4core.useTheme(am4themes_animated);
		var chart = am4core.create("chartdiv2", am4charts.XYChart);
		var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
		chart.data = datosChart2;
		categoryAxis.dataFields.category = "DESCRIPCION";
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
		  series.dataFields.categoryX = "DESCRIPCION";
		  series.name = name;
		  series.columns.template.tooltipText = "{name}: [bold]{valueY}[/]";
		  series.stacked = stacked;
		  series.columns.template.width = am4core.percent(95);
		}

		createSeries("HABILITADO", "Habilitado", true);
		createSeries("NOHABILITADO", "No Habilitado", true);

		// Add legend
		chart.legend = new am4charts.Legend();
		
		// Themes begin
		/*var chartBar = am4core.create("chartdiv2", am4charts.XYChart)
		am4core.useTheme(am4themes_animated);
		chartBar.data = datosChart2;
		
		// Create axes
		var categoryAxis = chartBar.xAxes.push(new am4charts.CategoryAxis());
		categoryAxis.dataFields.category = "DESCRIPCION";
		categoryAxis.renderer.grid.template.location = 270;
		categoryAxis.renderer.labels.template.rotation = 270;
		categoryAxis.renderer.labels.template.hideOversized = false;
		categoryAxis.renderer.minGridDistance = 20;
		categoryAxis.renderer.labels.template.horizontalCenter = "right";
		categoryAxis.renderer.labels.template.verticalCenter = "middle";
		categoryAxis.tooltip.label.rotation = 270;
		categoryAxis.tooltip.label.horizontalCenter = "right";
		categoryAxis.tooltip.label.verticalCenter = "middle";


		var valueAxis = chartBar.yAxes.push(new am4charts.ValueAxis());
		valueAxis.renderer.inside = true;
		valueAxis.renderer.labels.template.disabled = true;
		valueAxis.min = 0;
		function createSeries(field, name) {
			  
			  // Set up series
			  var series = chartBar.series.push(new am4charts.ColumnSeries());
			  series.name = name;
			  series.dataFields.valueY = field;
			  series.dataFields.categoryX = "DESCRIPCION";
			  series.sequencedInterpolation = true;
			  
			  // Make it stacked
			  series.stacked = true;
			  
			  // Configure columns
			  series.columns.template.width = am4core.percent(60);
			  series.columns.template.tooltipText = "[bold]{name}[/]\n[font-size:14px]{categoryX}: {valueY}";
			  
			  // Add label
			  var labelBullet = series.bullets.push(new am4charts.LabelBullet());
			  labelBullet.label.text = "{valueY}";
			  labelBullet.locationY = 0.5;
			  
			  return series;
		}

		createSeries("NOHABILITADO", "No Habilitado");
		createSeries("HABILITADO", "Habilitados");
		// Legend
		chartBar.legend = new am4charts.Legend();*/

	});
}