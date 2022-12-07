<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	 <div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Distribuidor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" onchange="getReceptores()" id="distribuidor"></select>
		</div>
	</div>	
	 <%if(session.getAttribute("PERFIL").equals("1")){%>	
		<div class="col-xs-12 col-sm-6 col-md-3">
			<label style="color: #337ab7;font-weight: bold;" >Sub Distribuidor: </label>
			<div style="width: 100%;">
				<select class="form-control input-sm" onchange="cambioSub(this.value)" id="subdistribuidor"></select>
			</div>
		</div>	
	<%}%>
	 <%if(session.getAttribute("PERFIL").equals("1") || session.getAttribute("PERFIL").equals("2")){%>	
		<div class="col-xs-12 col-sm-6 col-md-3">
			<label style="color: #337ab7;font-weight: bold;" >Vendedor: </label>
			<div style="width: 100%;">
				<select class="form-control input-sm" onchange="cambioVendedor(this.value)" id="vendedor"></select>
			</div>
		</div>	
	<%}%>
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Comercio: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="comercio"></select>
		</div>
	</div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="col-xs-12"><label style="color: #337ab7;font-weight: bold;" >Fecha Desde: </label></div>
		<div class="col-xs-6 col-sm-6 col-md-6">
			<input type="text" class="form-control" readonly name="fecha" id="fechadesde">
		</div>
		<div class="col-xs-6 col-sm-6 col-md-6">
			<input type="time" class="form-control" id="horadesde" value="00:00">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="col-xs-12"><label style="color: #337ab7;font-weight: bold;" >Fecha Hasta: </label></div>
		<div class="col-xs-6 col-sm-6 col-md-6">
			<input type="text" class="form-control" readonly name="fecha" id="fechahasta">
		</div>
		<div class="col-xs-6 col-sm-6 col-md-6">
			<input type="time" class="form-control" id="horahasta" value="23:59">
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
	<table class="table table-bordered table-hover table-striped table-condensed" id="tbl_RendimientoVlidadr">
		
	</table>
</div>
<!-- <div style="text-align: center;" class=""> -->
<!-- 	<button id="excelBtn" onclick="generarExcelPulento()" class="btn green dark"> -->
<!-- 		<i class="fa fa-file-excel-o fa-lg"></i> Exportar Excel -->
<!-- 	</button>  -->
<!-- </div>  -->