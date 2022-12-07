<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="col-md-12 portlet light" style="margin-top: 10px;">
	<div class="row">
		<div class="col-md-1 ">
			<label style="color: #337ab7;">Perfil</label>
		</div>
		<div class="col-md-3">
			<select id="CodigoTra" class="form-control input-sm input-circle mayusculasWork "></select>
		 </div>
	</div>
	
</div>
<div class="col-md-12 portlet light" style="margin-top: 10px;"> 
	<div class="row">
			 <div class="col-md-2">
				<a class="btn btn-circle blue btn-outline" onclick="javascript: Enviar();"> 
					<i class="fa fa-floppy-o fa-lg">Grabar</i>
				</a>
			</div>
	</div>
</div>
<div class="col-md-12 portlet light" style="margin-top: 10px;">
	<div class="col-sm-6">
  		<div id="treeview2" class="treeview">
  		</div>
	</div>
	<div class="col-sm-6">
  		<div id="treeview" class="treeview"></div>
	</div>
</div>


<p style="color:white">.</p>
<article id="loading" class="loading_dos" style="display: block;">
	<div id="modal" class="modal" style="display: block;"></div>
</article>