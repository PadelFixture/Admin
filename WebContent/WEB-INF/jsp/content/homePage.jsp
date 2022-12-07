<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-xs-12 col-sm-12 portlet light bordered" style="width: 100%;">
	<div style=" float: left;" class="col-xs-3 col-sm-3 col-md-3">
		<select id="otro" onchange="javascript: selCoordenadas(this.value);" class="form-control input-sm" style="float: right;">
			<option value='0'>kjhdkjfh</option>
			
		</select>
		<input type="hidden" id="direccionCaren">
	</div>
	<div class="col-xs-3 col-sm-3 col-md-3">
		<input type="text" class="form-control" id="address" placeholder="Ingresa una Dirección">
	</div>
	<div class="col-xs-12 col-sm-4 col-md-4" style=" float: right;">
		<a onclick="addCuartel();" class="btn btn-circle red btn-outline" ><i class="fa fa-plus"></i> Agregar Cuartel</a>
	</div>
</div>

<div class="col-xs-12 col-sm-12 col-md-12">
	<div class="col-xs-12 col-sm-12 col-md-8">
		<div id="mapa" style="width: 100%; height: 500px; float: left;"></div>
		<div class="col-md-12">
			<div id="color_palette" style="display: none;"></div>
				<label style="color: #C70039;font-weight: bold" class="col-sm-2 col-form-label"  style="float: right;">LAT/LGN:</label>
	    		<div class="col-sm-4">
	      			<label style="color: #337ab7;font-weight: bold" id="lnglat"></label>
	    		</div>
			<a id="delete" class="btn btn-circle red btn-outline" style="float: right; display:none;">
				<i class="fa fa-trash-o"></i> Eliminar
			</a>
			<a id="editar" class="btn btn-circle red btn-outline" onclick="javascript: editarCuartel();" style="float: right; display: none;">
				<i class="fa fa-pencil-square-o fa-lg"></i> Editar
			</a>
			<button id="infoExcel" class="btn btn-circle red btn-outline" style="float: right;display: none;">
				<i class="fa fa-file-excel-o fa-lg"></i> Exportar Excel   
			</button>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-4">
		<div class="col-xs-12 col-sm-12 col-md.12 portlet light bordered" >
			<h4>Filtros de Busqueda</h4>
			<div class="col-md-12">
				<h5>Sector</h5>
				<div style="width: 100%;">
					<select class="form-control input-md multiple" id="selSectorFilter" onchange="javascript: filtroSector(this.value);" disabled></select>
				</div>
			</div>
			<div class="col-md-12" >
				<h5>Especie</h5>
				<div style="width: 100%;">
					<select class="form-control input-md multiple" id="selEspecieFilter" onchange="javascript: filtroEspecieSel(this.value);" disabled></select>
				</div>
			</div>
			<div class="col-md-12" >
				<h5>Variedad</h5>
				<div style="width: 100%;">
					<select class="form-control input-md multiple" id="selVariedadFilter" onchange="javascript: filtroVariedadSel(this.value);" disabled></select>
				</div>
			</div>
			<div class="col-md-12">
				<h5>Cuarteles con Georreferencia</h5>
				<div style="width: 100%;">
					<select class="form-control input-md multiple" id="selCuartelFilter" onchange="javascript: filtroCuartelSel(this.value);" disabled></select>
				</div>
			</div>
			<div class="col-md-12">
				<h5>Cuarteles sin Georreferencia</h5>
				<div style="width: 100%;">
					<select class="form-control input-sm" id="selCuartelFilter_SG" disabled></select>
				</div>
			</div>
			<div class="col-md-12 portlet light">
				<a id="addGeoreferencia" class="btn btn-circle red btn-outline" onclick="addGeoreferencia();">
					<i class="fa fa-map-o"></i> Editar Cuartel sin Georrefencia
				</a>
				<a id="updGeoreferencia" class="btn btn-circle red btn-outline" style="display: none;" onclick="updGeoreferencia();">
					<i class="fa fa-map-o"></i> Actualizar Cuartel
				</a>
<!-- 				<a id="cancelar_add_geo" class="btn btn-circle red btn-outline" onclick="cancelAdd_Geo();"> -->
<!-- 					<i class="fa fa-times"></i> Cancelar -->
<!-- 				</a> -->
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered" id="addCuartel" style="display: none">
		<div id="data" >
		<div class="col-xs-12 col-sm-12 col-md-12 portlet">
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="sector">(*) Sector:</label>
				<select id="sector" name="map" class="form-control input-sm form-add map block required limps">
					<option value="">Seleccione</option>
				</select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="estado">(*) Estado:</label>
				<select id="estado" name="map" onchange="cambioEstado(this);" class="form-control input-sm form-add map block required limps">
					<option value="1">Productivo</option>
					<option value="2">No Productivo</option>
					<option value="3">Arrancado</option>
				</select>
			</div>
			<div id="divOrdenCoMacro" style="display: none;" class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="ceco">(*) Macro del Proyecto:</label>
				<select id="ordencomacro" name="map" onchange="selectMacro(this)" class="form-control input-sm form-add map block limps"></select>
			</div>
			<div id="divOrdenCo" style="display: none;" class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="ceco">(*) OrdenCo:</label>
				<select id="ordenco" name="map" onchange="selectOrdenCo(this)" class="form-control input-sm form-add map block limps"></select>
			</div>
			<div id="divCeco" class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="ceco">(*) CeCO:</label>
				<select id="ceco" name="map" class="form-control input-sm form-add map blocked required limps"></select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="ano_plantacion">Código Ceco:</label>
				  <input type="text" id="cod_ceco" disabled class="form-control form-add map block limp" min="1969">
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="especie">(*) Especie:</label>
				<select id="especie" name="map" onchange="selectEscpecie();" class="form-control input-sm form-add map block required limps" >
					<option value="">Seleccione</option>
				</select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="variedad">(*) Variedad:</label>
				<select id="variedad" name="map" class="form-control input-sm form-add map block required limps" ></select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="ano_plantacion">(*) Año Plantación:</label>
				<input type="number" id="ano_plantacion" name="map" onkeyup="cambio_ano(this);" class="form-control form-add map block required limp" min="1969">
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="patron">Patrón:</label>
				<input type="text" id="patron" name="" class="form-control form-add block limp"><br>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="alias">(*) Alias:</label>
				<input type="text" id="alias" name="map" placeholder="Ejemplo: C-06" class="form-control form-add map block limp">
			</div>
			
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12 portlet">
			<div style="text-align: center;">
				<h4 style="color: #337ab7;font-weight: bold" for="title_distancia">Marco Plantación</h4>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12 portlet">
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="superficie">(*) Superficie:</label>
				<input type="text" onkeypress="return justNumbers(event);" onchange="sumDistancias(this);" id="superficie" name="map" class="form-control form-add map block required limp" >
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<div style="width: 100%;">
					<div style="width: 48%; float: left;">
						<label style="color: #337ab7;font-weight: bold" for="distancia_hancho">Entre Hileras:</label>
						<input type="text" onkeypress="return justNumbers(event);" id="distancia_hancho" onchange="sumDistancias(this);" class="form-control form-add block limp" placeholder="Ancho">
					</div>
					<div style="width: 48%; float: right;">
						<label style="color: #337ab7;font-weight: bold" for="distancia_largo">Sobre Hileras:</label>
						<input type="text" onkeypress="return justNumbers(event);" id="distancia_largo" onchange="sumDistancias(this);" class="form-control form-add block limp" placeholder="Largo">
					</div>
				</div>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<div style="width: 100%;">
					<div  style="width: 48%; float: left;">
						<label style="color: #337ab7;font-weight: bold" for="plantas_has">P/Has.:</label>
						<input type="text" id="plantas_has" class="form-control form-add block limp" readonly >
					</div>
					<div style="width: 48%; float: right;">
						<label style="color: #337ab7;font-weight: bold" for="plantas">(*) P/Totales:</label>
						<input type="text" id="plantas"  name="map" class="form-control form-add map block required limp">
					</div>
				</div>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="formacion">Formación:</label>
				<select id="formacion" name="map" class="form-control input-sm form-add map block limps"></select>
			</div>
			
		</div>
		
		<div class="col-xs-12 col-sm-12 col-md-12 portlet">
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="vivero">Nombre del Vivero:</label>
				<input type="text" id="vivero" class="form-control form-add block limp">
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="tipo_planta">Tipo de Planta:</label>
				<select id="tipo_planta" name="map" class="form-control input-sm form-add map block limps"></select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="tipo_control_heladas">Tipo Control Heladas:</label>
				<select id="tipo_control_heladas" name="map" class="form-control input-sm form-add map block limps"></select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="tipo_proteccion">Tipo Protección:</label>
				<select id="tipo_proteccion" name="map" class="form-control input-sm form-add map block limps"></select>
			</div>
			
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12 portlet">
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="limitantes_suelo">Limitantes de Suelo:</label>
				<select id="limitantes_suelo" name="map" class="form-control input-sm form-add map block limps"></select>
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="polinizante">% Polinizante :</label>
				<input type="text" id="polinizante" name="" class="form-control form-add block limp">
			</div>
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="tipo_plantacion">Tipo de Plantación:</label>
				<select id="tipo_plantacion" name="map" class="form-control input-sm form-add map block limp"></select>
			</div>
			
			<div class="col-xs-3 col-sm-3 col-md-3">
				<label style="color: #337ab7;font-weight: bold" for="clon">Clón:</label>
				<input type="text" id="clon" class="form-control form-add block limp"><br>
			</div>
		</div>
			<div style="text-align: center;">
				<a id="save" class="btn btn-circle red btn-outline submit" onclick="javascript: saveMap();">
					<i class="icon-cloud-upload"></i> Guardar
				</a>
				<a id="update" class="btn btn-circle red btn-outline submit" onclick="javascript: updateMap();" style="display: none;">
					<i class="fa fa-refresh"></i> Actualizar
				</a>
				<a id="modificar" class="btn btn-circle red btn-outline submit" onclick="javascript: modificar();" style="display: none;">
					<i class="fa fa-edit"></i> Modificar
				</a>
				<a id="cancelar" class="btn btn-circle red btn-outline" onclick="javascript: cancelar(this);" >
					<i class="fa fa-close"></i> Cancelar
				</a>
				<input type="hidden" id="coordenadas" />
			</div>
		</div>
	</div>
</div>
.

<div id="historial" style="display: none;">
	<div style="text-align: center;">
		<h4>Historial de Campo</h4>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12">
		<ul class="nav nav-tabs">
			<li id="aplicaciones" class="active">
				<a onclick="javascript: app_click();">Aplicaciones</a>
			</li>
			<li id="labores">
				<a onclick="javascript: works_click();">Labores</a>
			</li>
			<li id="incidencias">
				<a onclick="javascript: in_click();">Incidencias</a>
			</li>
			<li id="archivos">
				<a onclick="javascript: docs_click();">Archivos</a>
			</li>
		</ul>
	</div>
	
	<div id="app_div" style="display: block;">
		<div class="table-responsive" id="ignore">
			<table class="table table-bordered table-hover table-striped table-condensed" id="tbl_Info">
				<thead>
					<tr>
						<th >N° Orden</th>
						<th>Pesticida</th>
						<th>Ingrediente Activo</th>
						<th>Condiciones Climaticas</th>
						<th>Fecha</th>
						<th>Motivo</th>
						<th>Periodo</th>
						<th>Dosis</th>
					</tr>
				</thead>
				<tbody id="tblInfo">
<!-- 					<tr><td colspan='8' style='text-align: center;'>No se han registrado Aplicaciones a este Huerto</td></tr>				 -->
					<tr>
						<td>77</td>
						<td>Succes 48</td>
						<td>SPINOSAD</td>
						<td>Despejado</td>
						<td>08-06-2017</td>
						<td>Control</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>89</td>
						<td>Phyton-27</td>
						<td>Sulfato de Cobre Pentahidratado</td>
						<td>Nublado</td>
						<td>20-06-2017</td>
						<td>Botrytis</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>95</td>
						<td>Teldor 50 WP</td>
						<td>Fenhexamid</td>
						<td>Despejado</td>
						<td>07-06-2017</td>
						<td>Control de Botrytis</td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="works_div" style="display: none;">
		<div class="table-responsive" id="ignore">
			<table class="table table-bordered table-hover table-striped table-condensed" id="tbl_Info">
				<thead>
					<tr>
						<th>Fecha</th>
						<th>Labor</th>
						<th>Cantidad</th>
						<th>Unidad</th>
						<th>Duracion</th>
						<th>Trabajador</th>
					</tr>
				</thead>
				<tbody id="bodyLabores"></tbody>
			</table>
		</div>
	</div>
	
	<div id="in_div" style="display: none;">
		<div class="table-responsive" id="ignore">
			<table class="table table-bordered table-hover table-striped table-condensed" id="tbl_Info">
				<thead>
					<tr>
						<th >Fecha</th>
						<th>Descripcion</th>
						<th>Tipo Incidencia</th>
						<th>Estado</th>
						<th>Accion</th>
						<th>Sector Afectado</th>
						<th>Urgencia</th>
						<th>Encargado</th>
					</tr>
				</thead>
				<tbody id="bodyIncidencia"></tbody>
			</table>
		</div>
	</div>
	
	<div id="docs_div" style="display: none;">
		<a>Archivo</a>
	</div>
</div>
<article id="loading" class="loading_dos" style="display: block;">
	<div id="modal" class="modal" style="display: block;"></div>
</article>