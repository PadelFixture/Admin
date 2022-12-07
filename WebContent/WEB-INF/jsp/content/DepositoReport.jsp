<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Tipo Receptor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="tipo" onchange="cambioTipoReceptor(this.value)">
				<option value='1'>Sub Distribuidor</option>
				<option value='2'>Comercio</option>
			</select>
		</div>
	</div>
	 <div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Receptor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="receptor"></select>
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
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_RendimientoVlidadr"></table>