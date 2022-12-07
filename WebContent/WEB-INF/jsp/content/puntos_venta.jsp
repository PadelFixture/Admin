<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-3 ">
		<label style="color: #337ab7;font-weight: bold;">Año:</label>
		<select id="anno" onchange="cargaDatos();" class="form-control input-sm"></select>
	</div>
	 <div class="col-xs-12 col-sm-6 col-md-6 portlet light bordered" style="display:none">
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>Categorizacion</h4><center>
		</div>
		<div  id="grafico1" style='height: 350px;'></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Recepción / Habilitaciones</h4><h4 class="g1"></h4><center>
		</div>
		<div  id="grafico2" style='height: 350px;'></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Calidad Habilitaciones </h4><h4 class="g1"></h4><center>
		</div>
		<div id="chartdiv2"></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Monto de Recargas</h4><h4 class="g1"></h4><center>
		</div>
		<div id="chartdiv"></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Habilitaciones por camada</h4><h4 class="g1"></h4><center>
		</div>
		<div id="chartdiv3"></div>
	</div>
	
</div>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
  	<div class="col-xs-11 col-sm-11 col-md-11">
		<div class="col-xs-12 col-sm-6 col-md-3">
			<label style="color: #337ab7;font-weight: bold;" >Region: </label>
			<div style="width: 100%;">
				<select id="region" onchange="getProvincia(this.value);" class="form-control input-sm"></select>
			</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3">
			<label style="color: #337ab7;font-weight: bold;" >Provincia: </label>
			<div style="width: 100%;">
				<select id="provincia" onchange="getComuna(this.value);" class="form-control input-sm"></select>
			</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3">
			<label style="color: #337ab7;font-weight: bold;" >Comuna: </label>
			<div style="width: 100%;">
				<select id="comuna" class="form-control input-sm"></select>
			</div>
		</div>
	</div>
  	<div class="col-xs-1 col-sm-1 col-md-1">
  		<div class="col-xs-12 col-sm-12 col-md-12">
  			<label style="color: #337ab7;font-weight: bold;" >Buscar </label>
  			<div style="width: 100%;">
				<a onclick="getGraficos()" class="btn btn-info">
					<i class="icon-magnifier"></i>
				</a>
			</div>
		</div>
  	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	 <div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" >
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>Puntos de Venta</h4><center>
		</div>
		<div  id="chartdiv4" style='height: 500px;'></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
		<div class='grafic-title2' style='height: 90px;'>
			<div style="text-align: center;">
				<h4>Capacitados: <small id="cap" ></small></h4>
				<h4>No Capacitados: <small id="nocap" ></small></h4>
				<h4>Total: <small id="total" ></small></h4>
				
			</div>
		</div>
		<div  id="chartdiv5" style='height: 500px;'></div>
	</div>
<!-- 	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered"> -->
<!-- 		<div class='grafic-title'> -->
<!-- 			<center><h4 style='padding-top: 10px'>Calidad Habilitaciones</h4><center> -->
<!-- 		</div> -->
<!-- 		<div  id="grafico3" style='height: 350px;'></div> -->
<!-- 	</div> -->
<!-- 	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered"> -->
<!-- 		<div class='grafic-title'> -->
<!-- 			<center><h4 style='padding-top: 10px'>Monto de Recargas</h4><center> -->
<!-- 		</div> -->
<!-- 		<div  id="grafico4" style='height: 350px;'></div> -->
<!-- 	</div> -->
	
</div>
<div class="col-xs-12 col-sm-12 col-md-12 ">
<div class='grafic-title2'>
	<div style="text-align: center;">
		<h4>Resumen Puntos de Ventas </h4>
		
	</div>
</div>
<div >
	<div style="text-align: center;">
		<h4>Resumen Diario </h4>
	</div>
</div>
<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr"></table>
<div >
	<div style="text-align: center;">
		<h4>Acumulado Periodo </h4>
	</div>
</div>
  <div class="col-xs-12 col-sm-12 col-md-12 ">
    <div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Fecha Desde: </label>
		<div style="width: 100%;">
			<input type="text" class="form-control" readonly name="fecha" id="fechadesde">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Fecha Hasta: </label>
		<div style="width: 100%;">
			<input type="text" class="form-control" readonly name="fecha" id="fechahasta">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-1">
		<label style="color: #337ab7;font-weight: bold;" >Buscar: </label>
		<div style="width: 100%;">
			<a onclick="getDatosResumen2()" class="btn btn-info">
				<i class="icon-magnifier"></i>
			</a>
		</div>
	</div>
</div>
<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr2"></table>
</div>
.
<style>
#chartdiv {
  width: 100%;
  height: 500px;
}
#chartdiv2 {
  width: 100%;
  height: 500px;
}
#chartdiv3 {
  width: 100%;
  height: 500px;
}
#chartdiv4 {
  width: 100%;
  height: 500px;
}
#chartdiv5 {
  width: 100%;
  height: 500px;
}
.amcharts-g2 {
  stroke-dasharray: 3px 3px;
  stroke-linejoin: round;
  stroke-linecap: round;
  -webkit-animation: am-moving-dashes 1s linear infinite;
  animation: am-moving-dashes 1s linear infinite;
}

@-webkit-keyframes am-moving-dashes {
  100% {
    stroke-dashoffset: -31px;
  }
}
@keyframes am-moving-dashes {
  100% {
    stroke-dashoffset: -31px;
  }
}

.amcharts-graph-column-front {
  -webkit-transition: all .3s .3s ease-out;
  transition: all .3s .3s ease-out;
}
.amcharts-graph-column-front:hover {
  fill: #496375;
  stroke: #496375;
  -webkit-transition: all .3s ease-out;
  transition: all .3s ease-out;
}

.amcharts-g3 {
  stroke-linejoin: round;
  stroke-linecap: round;
  stroke-dasharray: 500%;
  stroke-dasharray: 0 /;    /* fixes IE prob */
  stroke-dashoffset: 0 /;   /* fixes IE prob */
  -webkit-animation: am-draw 40s;
  animation: am-draw 40s;
}
@-webkit-keyframes am-draw {
    0% {
        stroke-dashoffset: 500%;
    }
    100% {
        stroke-dashoffset: 0%;
    }
}
@keyframes am-draw {
    0% {
        stroke-dashoffset: 500%;
    }
    100% {
        stroke-dashoffset: 0%;
    }
}
</style>