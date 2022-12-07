
function getGraficos(){
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
		
		var chartBar = am4core.create("grafico2", am4charts.XYChart)
		var input = {
	        SP: "HABILITACIONES_PERIODO",
	        FILTERS: [
	            {value: 374},
	            {value: $("#anno").val()}
	        ]
	    };
		console.log(USER)
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			async: false,
//			timeout: 10000,
			data: JSON.stringify(input),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response)
			
			var chart = AmCharts.makeChart("grafico2", {
				  type: "serial",
				  theme: "none",
				  categoryField: "PERIODO",
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
				    	  valueField: "CANTIDAD_INGRESADO"
				      },
				      {
				    	  balloonText: "Habilitado:[[value]]",
				    	  fillAlphas: 0.8,
				    	  id: "AmGraph-2",
				    	  lineAlpha: 0.2,
				    	  title: "Habilitado",
				    	  type: "column",
				    	  valueField: "HABILITADO"
				      }
				  ],
				  "guides": [],
				  "valueAxes": [
				    {
				      "id": "ValueAxis-1",
				      "position": "top",
				      "axisAlpha": 0
				    }
				  ],
				  "allLabels": [],
				  "balloon": {},
				  "titles": [],
				  "dataProvider": response.data,
				    "export": {
				    	"enabled": true
				     }

				});
		}).error(function (e) {
			console.log(e);
		})
		var input2 = {
	        SP: "DETALLE_HABILITACION_PERIODO",
	        FILTERS: [
	            {value: 374},
	            {value: $("#anno").val()}
	        ]
	    };
		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			 async: false,
//			timeout: 10000,
			data: JSON.stringify(input2),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response)
			var chart = AmCharts.makeChart("grafico3", {
				  type: "serial",
				  theme: "none",
				  categoryField: "ID_MES",
				  rotate: false,
				  startDuration: 1,
				  categoryAxis: {
				    gridPosition: "start",
				    position: "left"
				  },
				  trendLines: [],
				  graphs: [
				      {
				    	  balloonText: "Habilitado:[[value]]",
				    	  fillAlphas: 0.8,
				    	  id: "AmGraph-1",
				    	  lineAlpha: 0.2,
				    	  title: "Habilitado",
				    	  type: "column",
				    	  valueField: "HABILITADO"
				      },
				      {
				    	  balloonText: "Recargado:[[value]]",
				    	  fillAlphas: 0.8,
				    	  id: "AmGraph-2",
				    	  lineAlpha: 0.2,
				    	  title: "Recargado",
				    	  type: "column",
				    	  valueField: "CON_RECARGA"
				      },
				      {
				    	  balloonText: "Cantidad Recargas:[[value]]",
				    	  fillAlphas: 0.8,
				    	  id: "AmGraph-3",
				    	  lineAlpha: 0.2,
				    	  title: "Cantidad Recargas",
				    	  type: "column",
				    	  valueField: "CANTIDAD_RECARGAS"
				      }
				  ],
				  "guides": [],
				  "valueAxes": [
				    {
				      "id": "ValueAxis-1",
				      "position": "top",
				      "axisAlpha": 0
				    }
				  ],
				  "allLabels": [],
				  "balloon": {},
				  "titles": [],
				  "dataProvider": response.data,
				    "export": {
				    	"enabled": true
				     }

				});
		}).error(function (e) {
			console.log(e);
		})
		

		$.ajax({
			url: IPSERVER + "post",
			method: 'POST',
			dataType: 'json',
			async: false,
//			timeout: 10000,
			data: JSON.stringify(input2),
			headers: { 'Content-Type': "application/json", 'connection-properties': configService }
		}).success(function (response) {
			console.log(response)
			var chart = AmCharts.makeChart("grafico4", {
				  type: "serial",
				  theme: "none",
				  categoryField: "ID_MES",
				  rotate: false,
				  startDuration: 1,
				  categoryAxis: {
				    gridPosition: "start",
				    position: "left"
				  },
				  trendLines: [],
				  graphs: [
				      {
				    	  balloonText: "Monto Recarga:[[value]]",
				    	  fillAlphas: 0.8,
				    	  id: "AmGraph-1",
				    	  lineAlpha: 0.2,
				    	  title: "Habilitado",
				    	  type: "column",
				    	  valueField: "MONTO_TOTAL_RECARGAS"
				      }
				  ],
				  "guides": [],
				  "valueAxes": [
				    {
				      "id": "ValueAxis-1",
				      "position": "top",
				      "axisAlpha": 0
				    }
				  ],
				  "allLabels": [],
				  "balloon": {},
				  "titles": [],
				  "dataProvider": response.data,
				    "export": {
				    	"enabled": true
				     }

				});
			var a = document.getElementsByTagName("a");
			setTimeout(function(){ 
				$(".amcharts-chart-div").each(function(){
					$(this)[0].childNodes[1].innerHTML = "";
				})
			}, 500);
			
		}).error(function (e) {
			console.log(e);
		})

	});
}
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
//	timeout: 10000,
	data: JSON.stringify(get),
	headers: { 'Content-Type': "application/json", 'connection-properties': configService }
}).success(function (response) {
	console.log(response)
	var option = "";
	$.each(response.data, function(k,v){
		if(v.ANNO){
			option += "<option selected value="+v.ANNO+">"+v.ANNO+"</option>";
		}
	})
	$("#anno").html(option)
	getGraficos()
}).error(function (e) {
	console.log(e);
})