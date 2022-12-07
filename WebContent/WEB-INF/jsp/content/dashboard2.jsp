<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<div class='grafic-title2'>
			<div style="text-align: center;">
				<h4>Capacitados: <small id="cap" ></small></h4><h4>No Capacitados: <small id="nocap" ></small></h4>
				
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
.<style>
#chartdiv4 {
  width: 100%;
  height: 500px;
}
#chartdiv5 {
  width: 100%;
  height: 500px;
}
</style>