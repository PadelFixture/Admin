<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light ">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Tipo: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" onchange="cambioEspecie()" id="tipo">
				<option value=""></option>
				<option value="1">Vendedores</option>
				<option value="2">Comercios</option>
			</select>
		</div>
	</div> 
	<div class="col-xs-12 col-sm-6 col-md-6 disabledbutton" id="divImport">
		<div class="col-xs-12 col-sm-12 col-md-12 ">
			<label style="color: #337ab7;font-weight: bold;">Archivo:</label>
		</div>
		<input id="fileImport" type="file" onchange="fileImport(event)" style="display:none">
		<a id="desFiles" title="Descartar Arhivo" onclick="javascript: desFiles();" style="display: none;">
			<i class="fa fa-times"></i>
		</a>
		<label id="fImport" >Ningun archivo seleccionado</label>
		<label for="fileImport" class="btn default"><i class="fa fa-cloud-upload"></i> Importar Archivo</label>
	</div>
</div>
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_RendimientoVlidadr"></table>
<div style="text-align: center;" class="">
	<button onclick="guardar()" class="btn green-dark submit">
		<i class="icon-cloud-upload"></i> Importar Datos
	</button>
</div> 
