<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
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
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >PP: </label>
		<div style="width: 100%;">
			<select id="pp" class="form-control input-sm">
				<option value="-1">Todas</option>
				<option value="1">Si</option>
				<option value="0">No</option>
			</select>
		</div>
	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered ">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Vendedor: </label>
		<div style="width: 100%;">
			<select id="vendedor" class="form-control input-sm"></select>
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
<table class="table table-scrollable table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr"></table>
<div style="text-align: center;" class="">
	<button onclick="generarExcel()" class="btn green-dark">
		<i class="fa fa-file-excel-o" aria-hidden="true"></i> Exportar Excel
	</button>
</div>