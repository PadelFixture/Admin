<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
	<div class="col-xs-11 col-sm-11 col-md-11 portlet">
		<div class="col-xs-12 col-sm-3 col-md-3">
			<label style="color: #337ab7;font-weight: bold;">Tipo:</label>
			<select id="tipo" onchange="cambioTipo(this.value);" class="form-control input-sm"></select>
		</div>
	</div>
<!-- 	<div class="col-xs-1 col-sm-1 col-md-1"> -->
<!-- 	 	<div class="col-xs-12 col-sm-12 col-md-12"> -->
<!-- 	 		<label style="color: #337ab7;font-weight: bold;" >Buscar </label> -->
<!-- 	 		<div style="width: 100%;"> -->
<!-- 				<a onclick="BuscarCalificacion()" class="btn btn-info"> -->
<!-- 					<i class="icon-magnifier"></i> -->
<!-- 				</a> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
<h4 style="color: #337ab7;font-weight: bold; text-align: center;" >Detalles: </h4>
<div class="table-responsive">
	<table class="table table-bordered table-hover table-striped table-condensed dataTable no-footer" id="tbl_fa" >
		<thead>
			<tr>
				<th style="width: 15%; min-width: 100px;" id="calificacion">Calificacion</th>
				<th style="width: 15%; min-width: 100px;" id="min">Cantidad Minima</th>
				<th style="width: 15%; min-width: 100px;" id="max">Cantidad Maxima</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Malo</td>
				<td>  <input type='number' class='form-control' id="bajo_min"disabled></td>
				<td>  <input type='number' class='form-control' id="bajo_max"></td>
			</tr>
			
			<tr>
				<td>Medio</td>
				<td>  <input type='number' class='form-control'  id="promedio_min"disabled></td>
				<td>  <input type='number' class='form-control'  id="promedio_max"></td>
			</tr>
			
			<tr>
				<td>Bueno</td>
				<td>  <input type='number' class='form-control' id="bueno_min"disabled></td>
				<td>  <input type='number' class='form-control' id="bueno_max"></td>
			</tr>
			
			<tr>
				<td>Muy Bueno</td>
				<td>  <input type='number' class='form-control' id="destacado_min" disabled></td>
				<td>  <input type='number' class='form-control' id="destacado_max" disabled></td>
			</tr>
		</tbody>
		<tbody id="BodyCalificacion"></tbody>
	</table>
</div>

<div style="text-align: center;">
	<a id="Guardar" onclick="javascript: Guardar()" class="btn green-dark ">
		<i class="icon-cloud-upload"></i>  Guardar
	</a>
</div>
<article id="loading" class="loading_dos" style="display: block;">
	<div id="modal" class="modal" style="display: block;"></div>
</article>