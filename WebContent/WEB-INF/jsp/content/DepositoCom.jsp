<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 portlet bordered light" id="Saldo">
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
		<label style="color: #337ab7;font-weight: bold;" >Entidad: </label>
		<select class="form-control input-sm" id="empresa" onchange="cambioCodigo(this.value)"></select>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-3 portlet bordered">
		<label style="color: #337ab7;font-weight: bold;" >Codigo Transferencia: </label>
		<input class="form-control" id="codigo" onblur="cambioCodigo(this.value)">
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4 ">
		<div class="col-xs-12 col-sm-12 col-md-12 ">
			<label style="color: #337ab7;font-weight: bold;">Archivo:</label>
		</div>
		<input id="fileImport" type="file" onchange="cambioFile()" style="display:none">
		<a id="desFiles" title="Descartar Arhivo" onclick="javascript: desFiles();" style="display: none;">
			<i class="fa fa-times"></i>
		</a>
		<label id="fImport" >Ningun archivo seleccionado</label>
		<label for="fileImport" class="btn default"><i class="fa fa-cloud-upload"></i> Importar Archivo</label>
	</div>
</div>
<div style="text-align: center;" class="transferencia">
	<button onclick="guardar();" class="btn green-dark">
		 <i class="fa fa-exchange"></i> Transferir
	</button>
</div>