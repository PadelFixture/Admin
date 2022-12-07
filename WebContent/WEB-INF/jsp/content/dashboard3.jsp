<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-3 ">
		<label style="color: #337ab7;font-weight: bold;">Año:</label>
		<select id="anno" onchange="getGraficos();" class="form-control input-sm"></select>
	</div>
	 <div class="col-xs-12 col-sm-6 col-md-6 portlet light bordered" style="display:none">
		<div style='background-color:powderblue;height: 40px;' >
			<center><h4 style='padding-top: 10px'>Categorizacion</h4><center>
		</div>
		<div  id="grafico1" style='height: 350px;'></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Recepción / Activaciones</h4><center>
		</div>
		<div  id="grafico2" style='height: 350px;'></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" style="display:none">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Calidad Habilitaciones</h4><center>
		</div>
		<div  id="grafico3" style='height: 350px;'></div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" style="display:none">
		<div class='grafic-title'>
			<center><h4 style='padding-top: 10px'>Monto de Recargas</h4><center>
		</div>
		<div  id="grafico4" style='height: 350px;'></div>
	</div>
	
</div>
.