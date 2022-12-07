<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Tipo de Chip: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="tipo"></select>
		</div>
	</div>
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
			<a onclick="buscar()" class="btn btn-info">
				<i class="icon-magnifier"></i>
			</a>
		</div>
	</div>
</div>
<div class="table-scrollable" id="ignore">
	<table class="table table-bordered table-hover table-striped table-condensed display" id="tbl_RendimientoVlidadr">
		
	</table>
</div>
<!-- <div style="text-align: center;" class=""> -->
<!-- 	<button id="excelBtn" onclick="generarExcelPulento()" class="btn green dark"> -->
<!-- 		<i class="fa fa-file-excel-o fa-lg"></i> Exportar Excel -->
<!-- 	</button>  -->
<!-- </div>  -->