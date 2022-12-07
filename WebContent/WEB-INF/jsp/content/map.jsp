<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-xs-12 col-sm-12 portlet light bordered" style="width: 100%;">
	<div class="col-xs-3 col-sm-3 col-md-3">
		<input type="text" class="form-control" id="address" placeholder="Ingresa una Dirección">
	</div>
</div>

<div class="col-xs-12 col-sm-12 col-md-12">
	<div id="mapa" style="width: 100%; height: 700px; float: left;"></div>
</div>
.
<article id="loading" class="loading_dos" style="display: block;">
	<div id="modal" class="modal" style="display: block;"></div>
</article>