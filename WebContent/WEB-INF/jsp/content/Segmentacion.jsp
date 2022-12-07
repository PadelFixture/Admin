<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
  	<div class="col-xs-11 col-sm-11 col-md-11">
  		<!--  <div class="col-xs-12 col-sm-6 col-md-3">
			<label style="color: #337ab7;font-weight: bold;" >Periodo </label>
			<div style="width: 100%;">
				<input type="text" class="form-control" id="periodo">
			</div>
		</div>-->
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
  	<!--  <div class="col-xs-1 col-sm-1 col-md-1">
  		<div class="col-xs-12 col-sm-12 col-md-12">
  			<label style="color: #337ab7;font-weight: bold;" >Buscar </label>
  			<div style="width: 100%;">
				<a onclick="cargar()" class="btn btn-info">
					<i class="icon-magnifier"></i>
				</a>
			</div>
		</div>
  	</div>-->
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
			<a onclick="cargar()" class="btn btn-info">
				<i class="icon-magnifier"></i>
			</a>
		</div>
	</div>
</div>
<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr"></table>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	 <div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" >
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>Puntos de Venta</h4><center>
		</div>
		<div  id="grafico1" style='height: 600px;'></div>
	</div>
	
</div>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	 <div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" >
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>Puntos de Venta</h4><center>
		</div>
		<div  id="grafico4" style='height: 600px;'></div>
	</div>
	
</div>



<div class="col-xs-12 col-sm-12 col-md-12 ">
	 <div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" >
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>Categorización Puntos de Venta</h4><center>
		</div>
	</div>
	
</div>
<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr2"></table>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	 <div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" >
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>RESUMEN STOCK </h4><center>
		</div>
	</div>
	
</div>
<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr3"></table>