<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-xs-12 col-sm-12 col-md-12 ">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label style="color: #337ab7;font-weight: bold;" >Vendedor: </label>
		<div style="width: 100%;">
			<select class="form-control input-sm" id="vendedor" onchange="cambioVendedor(this.value)"></select>
		</div>
	</div>
<!-- 	<div class="col-xs-12 col-sm-6 col-md-3"> -->
<!-- 		<label style="color: #337ab7;font-weight: bold;" >Ruta: </label> -->
<!-- 		<div style="width: 100%;"> -->
<!-- 			<select class="form-control input-sm" onchange="cambioRuta(this.value)" id="Ruta"></select> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
<table class="table table-condensed table-bordered table-scrollable table-sm table-hovertable-striped nowrap" style="white-space: nowrap;" id="tbl_RendimientoVlidadr"></table> 
<div style="text-align: center;" class="">
	<button onclick="agregar()" class="btn green-dark">
		<i class="fa fa-file-excel-o" aria-hidden="true"></i> Guardar
	</button>
</div>
<div style="text-align: center;" class="">
	<button onclick="exec()" class="btn green-dark">
		<i class="fa fa-file-excel-o" aria-hidden="true"></i> Guardar
	</button>
</div>