<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="radio-inline">
  		<h4><input type="radio" name="optradio" onclick="radioOpt(1)" checked>Agregar</h4>
	</div>
	<div class="radio-inline">
  		<h4><input type="radio" name="optradio"  onclick="radioOpt(2)">Modificar</h4>
	</div>
</div>
 <div class="col-xs-12 col-sm-12 col-md-12">
	<div class="col-xs-12 col-sm-6 col-md-3 add">
		<label style="color: #337ab7;font-weight: bold;" >Circuito: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm " onchange="cambioCircuito(this.value)" id="Circuito">
			</select>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 add">
		<label style="color: #337ab7;font-weight: bold;" >Nombre Ruta: </label>
		<div style="width: 100%;">
			<input class="form-control" onblur="cambioNombre(this.value)" id="nombre">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 upd">
		<label style="color: #337ab7;font-weight: bold;" >Rutas Creadas: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" onchange="cambioRutas(this.value)" id="rutas">
			</select>
		</div>
	</div>
</div>
<input type="hidden" class="form-control dire"  id="address" placeholder="Ingresa una Dirección">
<div class="col-xs-12 col-sm-12 col-md-12" style='padding: 10px;'>
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div id="mapa" style="width: 100%; height: 400px; float: left;"></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-6">
		<table class="table table-condensed table-bordered table-scrollable table-sm table-hovertable-striped nowrap" style="white-space: nowrap;" id="tblComercios"></table> 
	</div>
</div>
<table class="table table-condensed table-bordered table-scrollable table-sm table-hovertable-striped nowrap" style="white-space: nowrap;" id="tbl_RendimientoVlidadr"></table> 
<div style="text-align: center;" class="">
	<button onclick="agregar()" class="btn green-dark">
		<i class="fa fa-file-excel-o" aria-hidden="true"></i> Guardar
	</button>
</div>