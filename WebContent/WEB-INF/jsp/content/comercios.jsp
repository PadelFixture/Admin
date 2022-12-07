<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Sub Distribuidor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="subdistribuidor">
			</select>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Vendedores: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="vendedores">
			</select>
		</div>
	</div>
</div>
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap " id="tbl_RendimientoVlidadr"></table>
<div style="text-align: center;" class="">
	<button onclick="agregar()" class="btn green-dark submit">
		<i class="icon-cloud-upload"></i> Agregar
	</button>
	<button id="excelBtn" onclick="generarExcelPulento()" class="btn green dark">
		<i class="fa fa-file-excel-o fa-lg"></i> Exportar Excel
	</button> 
</div>
.