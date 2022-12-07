<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-1"> 
 		<label style="color: #337ab7;font-weight: bold;" >Tipo de Chip: </label> 
		<div style="width: 100%;">
 			<select class="form-control input-sm" id="tipo"></select> 
		</div> 
 	</div> 
	<div class="col-xs-12 col-sm-6 col-md-2">
		<label style="color: #337ab7;font-weight: bold;" >Periodo </label>
		<div style="width: 100%;">
			<input type="text" class="form-control" autocomplete="off" id="periodo">
		</div>
	</div>
<!-- 	<div class="col-xs-12 col-sm-6 col-md-3"> -->
<!-- 		<label style="color: #337ab7;font-weight: bold;" >Fecha Hasta: </label> -->
<!-- 		<div style="width: 100%;"> -->
<!-- 			<input type="text" class="form-control" readonly name="fecha" id="fechahasta"> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div class="col-xs-12 col-sm-6 col-md-1">
		<label style="color: #337ab7;font-weight: bold;" >Buscar: </label>
		<div style="width: 100%;">
			<a onclick="buscar()" class="btn btn-info">
				<i class="icon-magnifier"></i>
			</a>
		</div>
	</div>
</div>
<div class="table-scrollable" id="ignore">
	<table class="table table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr">
		
	</table>
</div>

<style>
	div.container { max-width: 1200px }
	.page-header.navbar.navbar-fixed-top{
		z-index: 0 !important;
	}
</style>