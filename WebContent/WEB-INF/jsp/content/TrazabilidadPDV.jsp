<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_RendimientoVlidadr"></table>
<div class="col-xs-12 col-sm-6 col-md-2">
	<label style="color: #337ab7;font-weight: bold;" >Periodo </label>
	<div style="width: 100%;">
		<input type="text" class="form-control" autocomplete="off" id="periodo">
	</div>
</div>
<div class="col-xs-12 col-sm-6 col-md-1">
	<label style="color: #337ab7;font-weight: bold;" >Buscar: </label>
	<div style="width: 100%;">
		<a onclick="buscar2()" class="btn btn-info">
			<i class="icon-magnifier"></i>
		</a>
	</div>
</div>
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_RendimientoVlidadr2"></table>
<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_RendimientoVlidadr3"></table>
<div class="col-xs-12 col-sm-12 col-md-12 portlet light bordered">
	<div id="chartdiv2"></div>
</div>
<style>
#chartdiv2 {
  width: 100%;
  height: 500px;
}
td {
    text-transform: uppercase;
    font-size: 12px !important;
    height: 10px;
}
</style>
..