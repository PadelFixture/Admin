<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 " id="divSubdistribuidor" style="display:none">
	<div class="col-xs-12 col-sm-3 col-md-3" >
		<label style="color: #337ab7;font-weight: bold;" >Subdistribuidor: </label>
		
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3" >
		<div style="width: 100%;">
			<select class="form-control input-sm" id="subdistribuidor">
			</select>
		</div>
	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 " id="divVendedor" style="display:none">
	<div class="col-xs-12 col-sm-3 col-md-3" >
		<label style="color: #337ab7;font-weight: bold;" >Vendedor: </label>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3" >
		<div style="width: 100%;">
			<select class="form-control input-sm" id="vendedor">
			</select>
		</div>
	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 " id="divComercio" style="display:none">
	<div class="col-xs-12 col-sm-3 col-md-3" >
		<label style="color: #337ab7;font-weight: bold;" >Comercio: </label>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3" >
		<div style="width: 100%;">
			<select class="form-control input-sm" id="comercio">
			</select>
		</div>
	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-3 col-md-3" >
		<label style="color: #337ab7;font-weight: bold;" >Sim Desde: </label>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3" >
		<input type='text' class='form-control'  id="desde">
	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-3 col-md-3" >
		<label style="color: #337ab7;font-weight: bold;" >Sim Hasta: </label>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3" >
		<input type='text' class='form-control'  id="hasta">
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3" >
		<button onclick="cargar()" class="btn green-dark submit">
			<i class="icon-cloud-upload"></i> Cargar
		</button>
	</div>
</div>
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_RendimientoVlidadr"></table>
<div style="text-align: center;" class="">
	<button onclick="vender()" id="vender" class="btn green-dark submit" style="display:none">
		<i class="icon-cloud-upload"></i> Vender
	</button>
</div> 