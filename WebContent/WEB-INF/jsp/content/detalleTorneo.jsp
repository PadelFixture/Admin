<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
   <ul class="nav navbar-nav">
      <li class="active" id="liInfo"><a href="#">Info</a></li>
      <li id="liSede"><a href="#" >Sedes</a></li>
      <li id="liDisponibilidad"><a href="#" >Disponibilidad</a></li>
      <li id="liCategoria"><a href="#" >Categorias</a></li>
      <li id="liInscripcion"><a href="#" >Inscripciones</a></li>
      <li id="liFixture"><a href="#" >Fixture</a></li>
      <!--  <li id="liClasificacion"><a href="#" >Clasificacion</a></li>-->
      <li id="liCuadro"><a href="#" >Cuadros</a></li>
    </ul>
  </div>
</nav>
<div id="info" >
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Nombre: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="nombre">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Descripción: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='textarea' class='form-control'  id="descripcion">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Fecha Desde: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='date' class='form-control'  id="desde">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Fecha Hasta: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='date' class='form-control'  id="hasta">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold">Banner:</label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<label for="upload" title="Subir Archivo" alt="Subir Archivo"class="btn green fileinput-button"> <i
			class="fa fa-upload"></i>Subir Archivo</label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			 <input id="upload" class="form-control" value="Subir Archivo" type="file" accept="application/jpg" >
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Precio: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="precio">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Tipo Pago: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<select class="form-control input-sm" id="tipoPago">
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Tipo Cuenta: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<select class="form-control input-sm" id="tipoCuenta">
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Numero Cuenta: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="numeroCuenta">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Banco: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<select class="form-control input-sm" id="banco">
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Rut: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="rut">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Nombre Cuenta: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="nombreCuenta">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Correo: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="correo">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Whatapp: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="ws">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Nombre Contacto: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="nombreContacto">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-6 col-md-6" >
		</div>
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<button onclick="createTorneo()" class="btn green-dark submit">
				<i class="icon-cloud-upload"></i> Guardar
			</button>
		</div>
	</div>
</div>
<div id="sede" style="display:none">
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Sede: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<select class="form-control input-sm" id="sedeClub">
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Cantidad Canchas: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='number' class='form-control'  id="canchas">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-6 col-md-6" >
		</div>
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<button onclick="createSede()" class="btn green-dark submit">
				<i class="icon-cloud-upload"></i> Agregar
			</button>
		</div>
	</div>
	<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_sede"></table>
</div>
<div id="disponibilidad" style="display:none">
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Fecha: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='date' class='form-control'  id="fecha">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Hora desde: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='time' class='form-control'  id="horad">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Hora Hasta: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='time' class='form-control'  id="horah">
				
			</div>
		</div>
		
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Tiempo: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<select class="form-control input-sm" id="tiempo">
				</select>
			</div>
		</div>
		
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-6 col-md-6" >
		</div>
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<button onclick="createBloque()" class="btn green-dark submit">
				<i class="icon-cloud-upload"></i> Agregar
			</button>
		</div>
	</div>
	<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_bloques"></table>	
	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	   <ul class="nav navbar-nav" id="navFechas">
	      <!--  <li class="active" id="liInfo"><a href="#">Info</a></li>
	      <li id="liSede"><a href="#" >Sedes</a></li>
	      <li id="liDisponibilidad"><a href="#" >Disponibilidad</a></li>
	      <li id="liCategoria"><a href="#" >Categorias</a></li>
	      <li id="liInscripcion"><a href="#" >Inscripciones</a></li>
	      <li id="liCuadro"><a href="#" >Cuados</a></li>-->
	    </ul>
	  </div>
	</nav>
	<div id="tableBloques"></div>
</div>
<div id="categoria" style="display:none">
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Categoria: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<select class="form-control input-sm" id="idCategoria">
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Nombre Categoria: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='text' class='form-control'  id="nombreCategoria">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Formato: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<select class="form-control input-sm" id="formato">
			</select>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 " >
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Cantidad Parejas: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<div style="width: 100%;">
				<input type='number' class='form-control'  id="cantidadParejas">
				
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-6 col-md-6" >
		</div>
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<button onclick="createCategoria()" class="btn green-dark submit">
				<i class="icon-cloud-upload"></i> Agregar
			</button>
		</div>
	</div>
	<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_categorias"></table>	
</div>
<div id="inscripciones" style="display:none">
	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	   <ul class="nav navbar-nav" id="navCategoriaInscripcion">
	      <!--  <li class="active" id="liInfo"><a href="#">Info</a></li>
	      <li id="liSede"><a href="#" >Sedes</a></li>
	      <li id="liDisponibilidad"><a href="#" >Disponibilidad</a></li>
	      <li id="liCategoria"><a href="#" >Categorias</a></li>
	      <li id="liInscripcion"><a href="#" >Inscripciones</a></li>
	      <li id="liCuadro"><a href="#" >Cuados</a></li>-->
	    </ul>
	  </div>
	</nav>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Jugador 1: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<select class="form-control input-sm" id="j1">
			</select>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-3 col-md-3" >
			<label style="color: #337ab7;font-weight: bold;" >Jugador 2: </label>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
			<select class="form-control input-sm" id="j2">
			</select>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6" >
			<button onclick="createPareja()" class="btn green-dark submit">
				<i class="icon-cloud-upload"></i> Agregar
			</button>
		</div>
	</div>
	<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_pareja"></table>	
		<div class="col-xs-12 col-sm-12 col-md-12 ">
		<div class="col-xs-12 col-sm-3 col-md-3" >
		</div>
		<div class="col-xs-12 col-sm-6 col-md-3" >
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6" >
			<button onclick="popAgregarJugador()" class="btn green-dark submit">
				<i class="icon-cloud-upload"></i> Agregar Jugador
			</button>
		</div>
	</div>
</div>
<div id="fixture" style="display:none">
	<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_fixture">
	</table>	
</div>
<!--  <div id="clasificación" style="display:none">	
</div>-->
<div id="cuadros" style="display:none">	
	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	   <ul class="nav navbar-nav" id="navCuadros">
	    </ul>
	  </div>
	</nav>
	<table class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap" id="tbl_clasificacion">
	</table>
	<div class="tournament-container" id="div85" style="display:none">
	  <div class="tournament-headers">
	    <h3>Cuartos de final</h3>
	    <h3>Semi Final</h3>
	    <h3>Final</h3>
	    <h3>Winner</h3>
	  </div>
	
	  <div class="tournament-brackets">
	    <ul class="bracket bracket-2">
	      <li class="team-item">G Villanueva/P Ortiz<time>09:00</time> J Viera/J Sanchez</li>
	      <li class="team-item">P Silva/R Gonzalez <time>09:00</time>N Sanchez/D Rebolledo</li>
	      <li class="team-item">I Meneses/G Saez <time>09:00</time> S Lizana/T Tahan</li>
	      <li class="team-item">J Cantillana/F Cazorla <time>09:00</time>J Del Canto/E Miranda</li>
	    </ul>  
	    <ul class="bracket bracket-3">
	      <li class="team-item">SF1 <time>12:00</time> SF2</li>
	      <li class="team-item">SF3 <time>12:00</time> SF4</li>
	    </ul>  
	    <ul class="bracket bracket-4">
	      <li class="team-item">F1 <time>14:00</time> F2</li>
	    </ul>  
	
	    <ul class="bracket bracket-4">
	      <li class="team-item"> Champions</li>
	    </ul>  
	  </div>
	</div>
	<div class="tournament-container" id="div86" style="display:none">
	  <div class="tournament-headers">
	    <h3>Cuartos de final</h3>
	    <h3>Semi Final</h3>
	    <h3>Final</h3>
	    <h3>Winner</h3>
	  </div>
	
	  <div class="tournament-brackets">
	    <ul class="bracket bracket-2">
	      <li class="team-item">P Gonzalez/I Escaida<time>10:00</time> A Lopez/N Bernales</li>	      
	      <li class="team-item">M Lira/F Pickman<time>10:00</time> S Torrealba/R Gonzalez</li>
		  <li class="team-item">R Morales/M Morales<time>10:00</time>T Zuniga/T Perez</li>
	      <li class="team-item">I Meneses/S Hinojosa<time>10:00</time>R Izquierdo/S Ponce</li>
	    </ul>  
	    <ul class="bracket bracket-3">
	      <li class="team-item">SF1 <time>12:00</time> SF2</li>
	      <li class="team-item">SF3 <time>12:00</time> SF4</li>
	    </ul>  
	    <ul class="bracket bracket-4">
	      <li class="team-item">F1 <time>14:00</time> F2</li>
	    </ul>  
	
	    <ul class="bracket bracket-4">
	      <li class="team-item"> Champions</li>
	    </ul>  
	  </div>
	</div>
	<div class="tournament-container" id="div89" style="display:none">
	  <div class="tournament-headers">
	    <h3>Cuartos de final</h3>
	    <h3>Semi Final</h3>
	    <h3>Final</h3>
	    <h3>Winner</h3>
	  </div>
	
	  <div class="tournament-brackets">
	    <ul class="bracket bracket-2">
	      <li class="team-item"><time></time> </li>
	      <li class="team-item">A Aravena/O Diaz<time>11:00</time>P Manrriquez/B Jofre</li>
	      <li class="team-item">G Bugueño/S Sprung<time>11:00</time>M Baeza/F Galaz</li>
	      <li class="team-item"><time></time></li>
	    </ul>  
	    <ul class="bracket bracket-3">
	      <li class="team-item">A Colignon/S Colignon <time>12:00</time>SF2</li>
	      <li class="team-item">M Rojas/C Iñiguez <time>12:00</time> SF3</li>
	    </ul>  
	    <ul class="bracket bracket-4">
	      <li class="team-item">F1 <time>14:00</time> F2</li>
	    </ul>  
	
	    <ul class="bracket bracket-4">
	      <li class="team-item"> Champions</li>
	    </ul>  
	  </div>
	</div>
	<div class="tournament-container" id="div88" style="display:none">
	  <div class="tournament-headers">
	    <h3>Semi Final</h3>
	    <h3>Final</h3>
	    <h3>Winner</h3>
	  </div>
	
	  <div class="tournament-brackets" >
	    <ul class="bracket bracket-3">
	      <li class="team-item">M Uribe/C Herrera <time>11:00</time>C Christensen/J Tahan</li>
	      <li class="team-item">N Herrera/R Ceron <time>11:00</time>A Olguin/F Díaz</li>
	    </ul>  
	    <ul class="bracket bracket-4">
	      <li class="team-item">F1<time>14:00</time> F2</li>
	    </ul>  
	
	    <ul class="bracket bracket-4">
	      <li class="team-item"> Champions</li>
	    </ul>  
	  </div>
	</div>
</div>

<style>
html {
  height: 100%;
  width: 100%;
}

body {
  font-family: sans-serif;
  margin: 0;
  height: 100%;
}

.tournament-container {}

.tournament-headers {
  flex-grow: 1;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  border-bottom: 1px solid #ccc;
  
  h3 {
    width: 25%;
    text-align:center;
    font-weight: 400;
    border-right: 1px dashed #ccc;
    margin: 0;
    padding:1rem;
  }
}

.tournament-brackets {
  display: flex;
  flex-direction: row; 
  list-style-type: none;
  background: #fdfdfd;
  margin-bottom: 50px;
}

.bracket {
  padding-left: 0;
  display: flex;
  margin: 0;
  padding: 30px 0;
  flex-grow: 1;
  flex-direction: column;
  justify-content: space-around;
  list-style-type: none;
  border-right: 1px dashed #ccc;
  flex: 1;
}

.team-item {
  background-color: #f4f4f4;
  padding: .5rem;
  display: block;
  margin: .5rem 10px;
  position: relative;
  vertical-align: middle;
  line-height: 2;
  text-align: center;
}

.team-item:after {
  content:'';
  border-color: #4f7a38;
  border-width: 2px;
  position: absolute;
  display: block;
  width: 10px;
  right: -11px;
}

.team-item:nth-of-type(odd):after {
  border-right-style: solid;
  border-top-style: solid;
  height: 100%;
  top: 50%;
}

.team-item:nth-of-type(even):after {
  border-right-style: solid;
  border-bottom-style: solid;
  height: 100%;
  top: -50%;
}

.team-item:before {
  content:'';
  border-top: 2px solid #4f7a38;
  position: absolute;
  height: 2px;
  width: 10px;
  left: -10px;
  top: 50%;
}

.bracket-2 {
  .team-item:nth-of-type(odd):after {
    height: 200%;
    top: 50%;
  }
  .team-item:nth-of-type(even):after {
    height: 200%;
    top: -150%;
  }
}

.bracket-3 {
  .team-item:nth-of-type(odd):after {
    height: 350%;
    top: 50%;
  }
  .team-item:nth-of-type(even):after {
    height: 350%;
    top: -300%;
  }
}

.bracket-4 {
  .team-item:nth-of-type(odd):after {
    height: 700%;
    top: 50%;
  }
  .team-item:nth-of-type(even):after {
    height: 700%;
    top: -650%;
  }
}

.bracket:first-of-type {
  .team-item:before {
    display: none;
  }
}

.bracket-4 {
  .team-item:after {
    display: none;
  }
}

.bracket:last-of-type {
  .team-item:before,
  .team-item:after {
    display: none;
  }
}

.team-item time {
  display: inline-block;
  background-color: #dbdbdb;
  font-size: .8rem;
  padding: 0 .6rem;
}
</style>

..
