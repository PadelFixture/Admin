<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 portlet bordered light" id="Saldo">
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Tipo Receptor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="tipo" onchange="cambioTipoReceptor(this.value)">
				<option value='1'>Sub Distribuidor</option>
				<option value='2'>Comercio</option>
			</select>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Receptor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="receptor"></select>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Monto: </label>
		<div class='input-icon'>
			<i class='fa fa-usd'></i>
			<input class="form-control" id="monto">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Codigo Transferencia: </label>
		<input class="form-control" id="codigo">
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Entidad: </label>
		<input class="form-control" id="empresa">
	</div>
</div>
<div style="text-align: center;" class="transferencia">
	<button onclick="guardar();" class="btn green-dark">
		 <i class="fa fa-exchange"></i> Transferir
	</button>
</div>