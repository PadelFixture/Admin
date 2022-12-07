var url = window.location.search.substring(1);
var idT = url.split("=");
idT = idT[1];
console.log(idT);
llenarSelect();
var idTorneo = 0;
var aFecha = 0;
var idCategoria = 0;
if(idT !== undefined){
	idTorneo = idT;
	var torneos = {
			SP: "get_torneo",
			FILTERS: {  
				_COMUNA : "0",
		        _ESTADO : "0",
		        _USUARIO : "0",
		        _ID_TORNEO : idT}
		}
	callSp(torneos).then(function(res){
		console.log(res);
		$("#nombre").val(res.data[0].nombreTorneo);
		$("#formato").val(res.data[0].formato);
		$("#descripcion").val(res.data[0].descripcion);
		$("#desde").val(res.data[0].fecha_desde);
		$("#hasta").val(res.data[0].fecha_hasta);
		$("#precio").val(res.data[0].precio);
		$("#tipoPago").val(res.data[0].tipo_pago);
		$("#tipoCuenta").val(res.data[0].tipo_cuenta);
		$("#numeroCuenta").val(res.data[0].cuenta);
		$("#banco").val(res.data[0].banco);
		$("#rut").val(res.data[0].rut);
		$("#nombreCuenta").val(res.data[0].nombreCuenta);
		$("#correo").val(res.data[0].correo);
		$("#ws").val(res.data[0].ws);
		$("#nombreContacto").val(res.data[0].nombreContacto);
	});
}
var tabla = "";
var tablaBloque = "";
var tablaPareja = "";
var tablaClas = "";

$("#liInfo").click(function(){
	$("#liInfo").addClass("active");
	$("#liSede").removeClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").removeClass("active");
	
	$("#info").show();
	$("#sede").hide();
	$("#disponibilidad").hide();
	$("#categoria").hide();
	$("#inscripciones").hide();
	$("#fixture").hide();
	//$("#clasificacion").hide();
	$("#cuadros").hide();
});

$("#liSede").click(function(){
	$("#liInfo").removeClass("active");
	$("#liSede").addClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").removeClass("active");
	//$("#liClasificacion").removeClass("active");
	$("#liCuadro").removeClass("active");
	
	$("#info").hide();
	$("#sede").show();
	$("#disponibilidad").hide();
	$("#categoria").hide();
	$("#inscripciones").hide();
	$("#fixture").hide();
	//$("#clasificacion").hide();
	$("#cuadros").hide();
});

$("#liDisponibilidad").click(function(){
	$("#liInfo").removeClass("active");
	$("#liSede").removeClass("active");
	$("#liDisponibilidad").addClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").removeClass("active");
	//$("#liClasificacion").removeClass("active");
	$("#liCuadro").removeClass("active");
	
	$("#info").hide();
	$("#sede").hide();
	$("#disponibilidad").show();
	$("#categoria").hide();
	$("#inscripciones").hide();
	$("#fixture").hide();
	//$("#clasificacion").hide();
	$("#cuadros").hide();
});

$("#liCategoria").click(function(){
	$("#liInfo").removeClass("active");
	$("#liSede").removeClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").addClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").removeClass("active");
	//$("#liClasificacion").removeClass("active");
	$("#liCuadro").removeClass("active");
	
	$("#info").hide();
	$("#sede").hide();
	$("#disponibilidad").hide();
	$("#categoria").show();
	$("#inscripciones").hide();
	$("#fixture").hide();
});

$("#liInscripcion").click(function(){
	$("#liInfo").removeClass("active");
	$("#liFecha").removeClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").addClass("active");
	$("#liFixture").removeClass("active");
	//$("#liClasificacion").removeClass("active");
	$("#liCuadro").removeClass("active");
	
	$("#info").hide();
	$("#sede").hide();
	$("#disponibilidad").hide();
	$("#categoria").hide();
	$("#inscripciones").show();
	$("#fixture").hide();
	//$("#clasificacion").hide();
	$("#cuadros").hide();
});

$("#liFixture").click(function(){
	$("#liInfo").removeClass("active");
	$("#liSede").removeClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").addClass("active");
	//$("#liClasificacion").removeClass("active");
	$("#liCuadro").removeClass("active");
	
	$("#info").hide();
	$("#sede").hide();
	$("#disponibilidad").hide();
	$("#categoria").hide();
	$("#inscripciones").hide();
	$("#fixture").show();
	//$("#clasificacion").hide();
	$("#cuadros").hide();
});

$("#liClasificacion").click(function(){
	$("#liInfo").removeClass("active");
	$("#liSede").removeClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").removeClass("active");
	//$("#liClasificacion").addClass("active");
	$("#liCuadro").removeClass("active");
	
	$("#info").hide();
	$("#sede").hide();
	$("#disponibilidad").hide();
	$("#categoria").hide();
	$("#inscripciones").hide();
	$("#fixture").hide();
	//$("#clasificacion").show();
	$("#cuadros").hide();
});

$("#liCuadro").click(function(){
	$("#liInfo").removeClass("active");
	$("#liSede").removeClass("active");
	$("#liDisponibilidad").removeClass("active");
	$("#liCategoria").removeClass("active");
	$("#liInscripcion").removeClass("active");
	$("#liFixture").removeClass("active");
	//$("#liClasificacion").removeClass("active");
	$("#liCuadro").addClass("active");
	
	$("#info").hide();
	$("#sede").hide();
	$("#disponibilidad").hide();
	$("#categoria").hide();
	$("#inscripciones").hide();
	$("#fixture").hide();
	//$("#clasificacion").hide();
	$("#cuadros").show();
});

var arrayJugadores = [];
var arrayTipoCategoria = [];
var arrayNacionalidad = [];
var arrayLadoJugador = [];
var arrayTallaJugador = [];
var arrayGeneroJugador = [];


function llenarSelect(){
	var nacionalidad = {
		SP: "get_dato",
		FILTERS: {  
			_TIPO : "PAIS"}
	}
	callSp(nacionalidad).then(function(res){
		arrayNacionalidad = res.data;
	});
	
	var lado = {
		SP: "get_dato",
		FILTERS: {  
			_TIPO : "LADO"}
	}
	callSp(nacionalidad).then(function(res){
		arrayNacionalidad = res.data;
	});
	var talla = {
		SP: "get_dato",
		FILTERS: {  
			_TIPO : "TALLA"}
	}
	callSp(talla).then(function(res){
		arrayTallaJugador = res.data;
	});
	
	var lado = {
		SP: "get_dato",
		FILTERS: {  
			_TIPO : "LADO"}
	}
	callSp(lado).then(function(res){
		arrayLadoJugador = res.data;
	});
	
	var genero = {
		SP: "get_dato",
		FILTERS: {  
			_TIPO : "GENERO"}
	}
	callSp(genero).then(function(res){
		arrayGeneroJugador = res.data;
	});
	
	
	
	var formato = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "FORMATO"}
		}
	var option = "";
	callSp(formato).then(function(res){
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#formato").html(option)
	});
	var formato = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "FORMATO"}
		}
	var option = "";
	callSp(formato).then(function(res){
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#formato").html(option)
	});
	var jugadores = {
			SP: "get_jugadores",
			FILTERS: {  
			}
		}
	callSp(jugadores).then(function(res){
		arrayJugadores = res.data;
		
	});
	
	var formato = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "FORMATO"}
		}
	var option = "";
	callSp(formato).then(function(res){
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#formato").html(option)
	});
	
	var tipoPago = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "TIPO_PAGO"}
		}
	callSp(tipoPago).then(function(res){
		option = "";
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#tipoPago").html(option)
	});
	
	var tipoCuenta = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "TIPO_CUENTA"}
		}
	
	callSp(tipoCuenta).then(function(res){
		option = "";
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#tipoCuenta").html(option)
	});
	
	var banco = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "BANCO"}
		}
	callSp(banco).then(function(res){
		option = "";
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#banco").html(option)
	});
	
	var sede = {
			SP: "get_club",
			FILTERS: {  
			}
		}
	callSp(sede).then(function(res){
		option = "";
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.club+"</option>";
		});
		$("#sedeClub").html(option)
	});
	
	var tiempo = {
			SP: "get_dato",
			FILTERS: {  
				_TIPO : "TIEMPO_PARTIDO"
			}
		}
	callSp(tiempo).then(function(res){
		option = "";
		$.each(res.data,function(k, v) {
			option += "<option value="+v.id+">"+v.descripcion+"</option>";
		});
		$("#tiempo").html(option)
	});
	
	var categoria = {
			SP: "get_tipo_categoria",
			FILTERS: {  
				
			}
		}
	callSp(categoria).then(function(res){
		option = "";
		arrayTipoCategoria = res.data;
		$.each(res.data,function(k, v) {
			if(k == 0){
				$("#nombreCategoria").val(v.nombre_2);
			}
			option += "<option value="+v.id+">"+v.nombre_2+"</option>";
		});
		$("#idCategoria").html(option)
	});
}

$("#idCategoria").change(function(){
	console.log($("#idCategoria option:selected").text());
	$("#nombreCategoria").val($("#idCategoria option:selected").text());
});

function createTorneo(){
	var nombreFoto = "";
	var datos = {
		SP: "revisarRandom",
		FILTERS: {  
					
		}
	}
	callSp(datos).then(function(res){
		nombreFoto = res.data[0]._RANDOM;
		var formdata = new FormData();
		var image = $('#upload').prop("files")[0];
		var img = image.name.split(".");
		nombreFoto = nombreFoto+"."+img[1];
		formdata.append("documento", image);
		formdata.append("nombreDocumento", image.name);
		var torneo = {
			SP: "create_torneo",
			FILTERS: {  
				_NOMBRE : $("#nombre").val(),
				_FORMATO : $("#formato").val(),
				_DESCRIPCION : $("#descripcion").val(), 
				_FECHA_D : $("#desde").val(), 
				_FECHA_H : $("#hasta").val(), 
				_ESTADO : "28", 
				_BANNER : nombreFoto, 
				_ORGANIZADOR : USER.CLUB
			}
		}
		console.log(torneo);
		callSp(torneo).then(function(res2){
			if(res2.data[0].id > 0){
				idTorneo = res2.data[0].id;
				var formdata = new FormData();
				var image = $('#upload').prop("files")[0];
				formdata.append("documento", image);
				formdata.append("nombreDocumento", nombreFoto);
				console.log(formdata)
				$.ajax({
					url : "/recotec/file/AGRO/INSERT_DOC/",
					dataType : 'script',
					cache : false,
					contentType : false,
					processData : false,
					data : formdata, //Datos
					type : 'post',
					success : function() {
						loading.hide();
						var a = alerta("Datos guardados exitosamente");
					},error: function(er){
						console.log(er)
					}
				});
				
				var datos = {
					SP: "insert_datos_torneo",
					FILTERS: {  
						_ID_TORNEO : res2.data[0].id,
						_PRECIO : $("#precio").val(), 
						_TIPO_PAGO : $("#tipoPago").val(), 
						_TIPO_CUENTA : $("#tipoCuenta").val(), 
						_CUENTA : $("#numeroCuenta").val(), 
						_BANCO :$("#banco").val(), 
						_CORREO : $("#correo").val(), 
						_RUT : $("#rut").val(),
						_WS : $("#ws").val(), 
						_NOMBRE : $("#nombreCuenta").val(), 
						_CONTACTO : $("#nombreContacto").val()
					}
				}
				
				callSp(datos).then(function(res3){
					
				});
			}
			

			
		});
		
	});
	
}
getSedes();
getBloque();
getTablaPosiciones();
getCategorias();
getParejaCategoria();
getParejaFixture();


function createSede(){
	var datos = {
		SP: "asignar_sede",
		FILTERS: {  
			_ID_TORNEO : idTorneo,
			_ID_CLUB : $("#sedeClub").val(),
			_CANCHAS : $("#canchas").val()
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getSedes();
	});
}

function createCategoria(){
	var datos = {
		SP: "create_categoria",
		FILTERS: {  
			_ID_TORNEO : idTorneo,
			_ID_CATEGORIA : $("#idCategoria").val(),
			_ID_ORDEN : 0,
			_NOMBRE_CATEGORIA : $("#nombreCategoria").val(),
			_CANTIDAD_PAREJA : $("#cantidadParejas").val(),
			_FORMATO : $("#formato").val()
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getCategorias();
	});
}


function getSedes(){
	var sedes = {
			SP: "get_sede",
			FILTERS: {  
				_ID_TORNEO : idTorneo
			}
		}
	callSp(sedes).then(function(res){
		console.log(res)
		var datos = [];
		$.each(res.data,function(k, v) {
			var tbl = [v.id_sede
			           ,v.club
			           ,v.canchas
			          ,"<button title='Eliminar' onclick='eliminarSede("+v.id_sede+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>"];
					datos.push(tbl);
		})
		if (tabla) {
			tabla.destroy();
			$('#tbl_sede').empty();
		}
		var columnas = [ "#", "Club","Canchas","Eliminar"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({title: columnas[i]});
		}
		tabla = $('#tbl_sede').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			paging: false
		});
		$("#tbl_sede_filter").hide();
	})
}

var arrayCategorias = [];

function getCategorias(){
	var categorias = {
			SP: "get_categoria_torneo",
			FILTERS: {  
				_ID_TORNEO : idTorneo
			}
		}
	var totalParejas = 0;
	var totalPartidos = 0;
	var totalCuposRestantes = 0;
	$("#navCategoriaInscripcion").html("");
	$("#navCuadros").html("");
	
	callSp(categorias).then(function(res){
		console.log(res)
		var datos = [];
		var option = "";
		$.each(res.data,function(k, v) {
			if(k == 0){
				option = "";
				$("#j1").html("");
				$("#j2").html("");
				$.each(arrayJugadores,function(k2, v2) {
					if(v.genero == 14){
						if(v2.genero == 14){
							option += "<option value="+v2.id+">"+v2.nombre+"</option>";
						}
					} else {
						option += "<option value="+v2.id+">"+v2.nombre+"</option>";
					}
				});
				$("#j1").html(option);
				$("#j2").html(option);
				
			}
			arrayCategorias[k] = v;
			if(k == 0){
				$("#navCategoriaInscripcion").append('<li class="active liCatIns" ><a id="aCatIns'+v.id+'" class="aCatIns" href="#">'+v.nombre_2+'</a></li>');
				$("#navCuadros").append('<li class="active liCuad" ><a id="aCuad'+v.id+'" class="aCuad" href="#">'+v.nombre_2+'</a></li>');
			} else {
				$("#navCategoriaInscripcion").append('<li class="liCatIns"><a class="aCuad" id="aCatIns'+v.id+'" href="#">'+v.nombre_2+'</a></li>');
				$("#navCuadros").append('<li class="liCuad"><a class="aCuad" id="aCuad'+v.id+'" href="#">'+v.nombre_2+'</a></li>');
			}
			totalParejas += v.cantidad_parejas;
			totalPartidos += v.partidos;
			totalCuposRestantes += v.cupos_restante;
			var tbl = [v.id
			           ,v.nombre
			           ,v.nombre_2
			           ,v.nFormato
			           ,v.cantidad_parejas
			           ,v.partidos
			           ,v.cupos_restante
			           ,0
			           
			          ,"<button title='Eliminar' onclick='eliminarCategoria("+v.id+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>"];
					datos.push(tbl);
		})
		
		var tbl = [''
			           ,''
			           ,''
			           ,'Total'
			           ,totalParejas
			           ,totalPartidos
			           ,totalCuposRestantes
			           ,''			           
			           ,''];
					datos.push(tbl);
		
		if (tabla) {
			tabla.destroy();
			$('#tbl_categorias').empty();
		}
		var columnas = [ "#", "Categoria","Nombre Categoria","Formato","Cantidad Parjeas","Partidos","Cupos Restante", "Lista de Espera","Eliminar"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({title: columnas[i]});
		}
		tabla = $('#tbl_categorias').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			paging: false
		});
		$("#tbl_categorias_filter").hide();
		$('.liCatIns').click(function(){
			$(".liCatIns").removeClass("active");
			$(this).addClass("active");
		});
		$('.liCuad').click(function(){
			$(".liCuad").removeClass("active");
			$(this).addClass("active");
		});
		$(".aCuad").click(function(){
			var id = $(this).attr("id");
			console.log(id);
			id = id.substring(5,id.length );
			var datosClas = [];
			var grupo = 1;
			$.each(arrayTabla,function(k, v) {
				
				if(v.id_categoria_torneo == id){
					$(".tournament-container").hide();
					$("#div"+id).show();
					if(grupo != v.grupo){
						var tbl = [''
						           ,''
						           ,''
						           ,''
						           ,''
						           ,''
						           ,''
						           ,''
						           ,''
						           ,''];
						datosClas.push(tbl);
						datosClas.push(tbl);
						grupo = v.grupo;
					}
					var tbl = [v.grupo
					           ,decode_utf8(v.pareja)
					           ,v.PTS
					           ,v.J
					           ,v.G
					           ,v.P
					           ,v.SF
					           ,v.SC
					           ,v.Dif
					           ,v.DIFG];
					datosClas.push(tbl);
				}
			});
			if (tablaClas) {
				tablaClas.destroy();
				$('#tbl_clasificacion').empty();
			}
			var columnas = [ "Grupo","Pareja", "Ptos","J","G","P","SF","SC", "Dif","Dif Games"];
			var finalColumn = [];
			for (var i = 0; i < columnas.length; i++) {
				finalColumn.push({title: columnas[i]});
			}
			tablaClas = $('#tbl_clasificacion').DataTable({
				data : datosClas,
				columns : finalColumn,
				autoWidth : true,
				ordering : false,
				pagingType : "full_numbers",
				paging: false
			});
			$("#tbl_clasificacion_filter").hide();
		});
		$(".aCatIns").click(function(){
			var id = $(this).attr("id");
			id = id.substring(7,id.length );
			option = "";
			idCategoria = id;
			getParejaCategoria();
			$.each(arrayCategorias,function(k, v) {
				if(v.id == id){
					$("#j1").html("");
					$("#j2").html("");
					$.each(arrayJugadores,function(k2, v2) {
						if(v.genero == 14){
							if(v2.genero == 14){
								option += "<option value="+v2.id+">"+v2.nombre+"</option>";
							}
						} else {
							option += "<option value="+v2.id+">"+v2.nombre+"</option>";
						}
					});
				}
			})
			$("#j1").html(option);
			$("#j2").html(option);
		});
	})
}

function eliminarSede(id){
	var datos = {
		SP: "delete_sede_torneo",
		FILTERS: {  
			_ID : id
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getSedes();
	});
}

function eliminarCategoria(id){
	var datos = {
		SP: "delete_categoria_torneo",
		FILTERS: {  
			_ID : id
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getCategorias();
	});
}

function createBloque(){
	var datos = {
		SP: "create_bloque",
		FILTERS: {  
			_ID_TORNEO : idTorneo,
			_FECHA : $("#fecha").val(),
			_HORAD : $("#horad").val(),
			_HORAH : $("#horah").val(),
			_TIEMPO : $("#tiempo").val()
		}
	}
	console.log(datos);
	callSp(datos).then(function(res){
		console.log(res);
		getBloque();
	});
}

function getBloque(){
	var bloques = {
		SP: "get_detalle_bloque",
		FILTERS: {  
			_ID_TORNEO : idTorneo
		}
	}
	$("#navFechas").html("");
	callSp(bloques).then(function(res){
		console.log(res)
		var datos = [];
		var total = 0;
		var totalB = 0;
		$.each(res.data,function(k, v) {
			if(k == 0){
				$("#navFechas").append('<li class="active liFecha2" ><a id="aFecha'+k+'" class="aFecha" href="#">'+v.fecha+'</a></li>');
			} else {
				$("#navFechas").append('<li class="liFecha2"><a class="aFecha" id="aFecha'+k+'" href="#">'+v.fecha+'</a></li>');
			}
			total += v.cant;
			totalB += v.cantBloqueada;
			var tbl = [v.id
			           ,v.fecha
			           ,v.hora_d
			           ,v.hora_h
			           ,v.tiempo
			           ,v.cant
			           ,v.cantBloqueada
			          ,"<button title='Eliminar' onclick='eliminarBloque("+v.id+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>"];
					datos.push(tbl);
		})
		var tbl = ['','','','','Total',total,totalB,''];
		datos.push(tbl);
		
		if (tablaBloque) {
			tablaBloque.destroy();
			$('#tbl_bloques').empty();
		}
		var columnas = [ "#", "Dia de Juego","Hora Inicio"," Hora Fin", "Tiempo","Bloques", "Bloqueados" ,"Eliminar"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({title: columnas[i]});
		}
		tablaBloque = $('#tbl_bloques').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			paging: false
		});
		$("#tbl_bloques_filter").hide();
		$('.liFecha2').click(function(){
			$(".liFecha2").removeClass("active");
			$(this).addClass("active");
		});
		$('.aFecha').click(function(){
			console.log($(this).html());
			console.log($(this).attr('id'));
			aFecha = $(this).attr('id');
			var canchas = {
				SP: "get_cancha",
				FILTERS: {  
					_ID_TORNEO : idTorneo
				}
			}
			var bloques_cancha = {
				SP: "get_bloque_cancha",
				FILTERS: {  
					_ID_TORNEO : idTorneo,
					_FECHA : viewFecha($(this).html())
				}
			}
			console.log(bloques_cancha);
			var html = "<table class='table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap' >";
			callSp(canchas).then(function(res){
				console.log(res);
				callSp(bloques_cancha).then(function(res2){
					html += "<tr>";
					html += "<td>#</td>";
					$.each(res.data,function(k, v) {
						html += "<td>"+v.cancha+"</td>";
					});
					html += "</tr>";
					var hora = [];
					//$.each(res.data,function(k, v) {
					console.log(res2);
					html += "<tr>";
					var x = 0;
					$.each(res2.data,function(k2, v2) {
						//if(v2.id_cancha == v.id){
							var color = "#89EA4F";
							if(v2.estado == 1){
								color = "#F7542B";
							}
							if(hora.indexOf(v2.hora) == -1){
								if(x > 0){
									html += "</tr>";
									html += "<tr>";
								}
								hora.push(v2.hora);
								html += "<td  >"+v2.hora+"</td>";	
							} 
							html += "<td id='"+v2.id+"' style='background-color:"+color+"' class='tdBloquear'></td>";
							x++;
						//}							
					});
					html += "</tr>";
					//});
					html += "</table>";
					$("#tableBloques").html(html);
					$(".tdBloquear").click(function(){
						console.log($(this).attr('id'));
						var bloquear_cancha = {
							SP: "bloquear_bloque_cancha",
							FILTERS: {  
								_ID : $(this).attr('id')
							}
						}
						console.log(bloquear_cancha);
						callSp(bloquear_cancha).then(function(res){
							console.log('#'+aFecha);
							$('#'+aFecha).trigger("click");
							getBloque();
						});
					});
					
				});
				
			});
		});
		
		$('#aFecha0').trigger("click");
		
		
		
	});
}

function eliminarBloque(id){
	var datos = {
		SP: "delete_detalle_bloque",
		FILTERS: {  
			_ID_DETALLE_BLOQUE : id
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getBloque();
	});
}

function createPareja(){
	var datos = {
		SP: "create_pareja_torneo",
		FILTERS: {  
			_ID : 0,
			_ID_CATEGORIA : idCategoria,
			_JUGADOR1 : $("#j1").val(),
			_JUGADOR2 : $("#j2").val(),
			_ESTADO_PAGOj1 : 13,
			_ESTADO_PAGOj2 : 13,
			_CABEZA : 0
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getParejaCategoria();
	});
}

function getParejaCategoria(){
	var datos = {
		SP: "get_pareja_torneo",
		FILTERS: {  
			_ID_TORNEO : idTorneo,
			_ID_CATEGORIA : idCategoria
			
		}
	}
	console.log(datos);
	callSp(datos).then(function(res){
		console.log(res);
		var datos = [];
		$.each(res.data,function(k, v) {
			var ch = "";
			if(v.cabezaSerie == 'SI'){
				ch = "checked";
			}
			var swcs = '<input type="checkbox" '+ch+'  data-toggle="toggle" data-size="xs" data-width="5" data-height="5">';
			if(v.codigo_estado_pago_j1 == '12'){
				ch = "checked";
			}
			var swj1 = '<input type="checkbox" '+ch+'  data-toggle="toggle" data-size="xs" data-width="5" data-height="5">';
			if(v.codigo_estado_pago_j2 == '12'){
				ch = "checked";
			}
			var swj2 = '<input type="checkbox" '+ch+'  data-toggle="toggle" data-size="xs" data-width="5" data-height="5">';
			
			var tbl = [v.id
			           ,swcs
			           ,v.jugador1
			           ,v.jugador2
			           ,swj1
			           ,swj2
			           ,"<button title='Disponibilidad' onclick='verDisponibilidad("+v.id+")' class='btn green btn-outline btn-sm' ><i class='fa fa-calendar'></i></button>"
			           ,"<button title='Eliminar' onclick='eliminarPareja("+v.id+")' class='btn red btn-outline btn-sm' ><i class='fa fa-times'></i></button>"];
					datos.push(tbl);
		})
		if (tablaPareja) {
			tablaPareja.destroy();
			$('#tbl_pareja').empty();
		}
		var columnas = [ "#", "Cabeza Serie","Jugador 1","Estado de pago J1","Jugador 2", "Estado Pago J2","Disponibilidad","Eliminar"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({title: columnas[i]});
		}
		tablaPareja = $('#tbl_pareja').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			paging: false
		});
		$("#tbl_pareja_filter").hide();
		
	});
}

function eliminarPareja(id){
	var datos = {
		SP: "delete_pareja_torneo",
		FILTERS: {  
			_ID : id
		}
	}
	callSp(datos).then(function(res){
		console.log(res);
		getParejaCategoria();
	});
}

function getCategoriaJugador(){
	var option = "";
	$.each(arrayTipoCategoria,function(k, v) {
		option += "<option value="+v.id+">"+v.nombre_2+"</option>";
	});
	return option;
}
function getNacionalidad(){
	var option = "";
	$.each(arrayNacionalidad,function(k, v) {
		option += "<option value="+v.id+">"+v.descripcion+"</option>";
	});
	return option;
}
function getLadoJugador(){
	var option = "";
	$.each(arrayLadoJugador,function(k, v) {
		option += "<option value="+v.id+">"+v.descripcion+"</option>";
	});
	return option;
}
function getTallaJugador(){
	var option = "";
	$.each(arrayTallaJugador,function(k, v) {
		option += "<option value="+v.id+">"+v.descripcion+"</option>";
	});
	return option;
}
function getGeneroJugador(){
	var option = "";
	$.each(arrayGeneroJugador,function(k, v) {
		option += "<option value="+v.id+">"+v.descripcion+"</option>";
	});
	return option;
}

function popAgregarJugador(){
	var pop = "";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nombre Jugador</h5>";
	pop += 			"<input class='form-control required-modal' id='nombreJugador' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Fecha Nacimiento</h5>";
	pop += 			"<input type='date' class='form-control required-modal' id='fecha_nacimiento' value=''>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Correo</h5>";
	pop += 			"<input type='text' class='form-control required-modal' id='correoJugador' value=''>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Categoria</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='categoriaJugador'>";
	pop += 				getCategoriaJugador();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Nacionalidad</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='nacionalidad'>";
	pop += 				getNacionalidad();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Lado</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='ladoJugador'>";
	pop += 				getLadoJugador();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Talla</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='tallaJugador'>";
	pop += 				getTallaJugador();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Genero</h5>";
	pop += 			"<select class='form-control input-sm required-modal' id='generoJugador'>";
	pop += 				getGeneroJugador();
	pop += 			"</select>";
	pop += 		"</div>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Instagram</h5>";
	pop += 			"<input type='text' class='form-control input-sm required-modal' id='instagram'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
	pop += 		"<div class='col-xs-4 portlet bordered'>";
	pop += 			"<h5 style='color: #337ab7;font-weight: bold'>Whastapp</h5>";
	pop += 			"<input type='text' class='form-control required-modal' id='wsJugador'>";
	pop += 		"</div>";
	pop += 	"</div>";
	pop += 		"<div style='text-align: center;'>";
	pop += 			"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarJugador()'> Aceptar</a>";
	pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
	pop += 		"</div>";
	popUp("Agregar Comercio", pop, true, "850px", true);
	selectCss();
}

function agregarJugador(){
	var datos = {
			SP: "create_jugador",
			FILTERS: {  
				_NOMBRE : $("#nombreJugador").val(),
				_NICK : "",
				_FECHA_NACIMIENTO : $("#fecha_nacimiento").val(),
				_CORREO : $("#correoJugador").val(),
				_CATEGORIA : $("#categoriaJugador").val(),
				_NACIONALIDAD : $("#nacionalidad").val(),
				_LADO : $("#ladoJugador").val(),
				_TALLA : $("#tallaJugador").val(),
				_GENERO : $("#generoJugador").val(),
				_INSTAGRAM : $("#instagram").val(),
				_WS : $("#wsJugador").val()
			}
		}
		console.log(datos);
		callSp(datos).then(function(res){
			console.log(res);
			llenarSelect();
		});
}

var idPareja = 0;
function verDisponibilidad(id){
	idPareja = id; 
	var datos = {
		SP: "get_bloqueos_pareja",
		FILTERS: {  
			_ID : id,
			_ID_TORNEO : idTorneo
		}
	}
	console.log(datos);
	var arrayFecha = [];
	callSp(datos).then(function(res){
		console.log(res);
		var pop = "";
		pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
		$.each(res.data,function(k, v) {
			var color = "#89EA4F";
			if(v.estadoPareja > 0){
				color = "#F7542B";
			}
			if(arrayFecha.indexOf(v.fecha) == -1){
				arrayFecha.push(v.fecha);
				if(k > 0 ){
					pop += 		"</table>";
					pop += 		"</div>";
				}
				pop += 		"<div class='col-xs-3 portlet'>";
				pop += 		"<table class='table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap'>";
				
				pop += 		"<tr>";
				pop += 			"<th >"+viewFecha(v.fecha)+"</th>";
				pop += 		"</tr>";
				
				pop += 		"<tr>";
				pop += 			"<td id='bk"+v.id+"' class='bkDisponibilidad' style='background-color:"+color+"'>"+v.hora+"</td>";
				pop += 		"</tr>";
			} else {
				pop += 		"<tr>";
				pop += 			"<td id='bk"+v.id+"' class='bkDisponibilidad' style='background-color:"+color+"'>"+v.hora+"</td>";
				pop += 		"</tr>";
			}
			
		});
		pop += 		"</table>";
		pop += 		"</div>";
		
		pop += 	"</div>";
		pop += 	"<div style='text-align: center;'>";
		pop += 		"<a class='btn green-dark submit-modal' id='btnCentralizacion' onclick='agregarJugador()'> Aceptar</a>";
		pop += 		"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
		pop += 	"</div>";
		popUp("Bloquear Horarios", pop, true, "850px", true);
		selectCss();
		$(".bkDisponibilidad").click(function(){
			var idBloque = $(this).attr("id");
			idBloque = idBloque.split("bk")[1];
			console.log(idBloque);
			bloqueoPareja(idBloque, id)
		});
	});
	
}

function bloqueoPareja(idBloque, idPareja){
	var datos = {
		SP: "bloquear_bloque_pareja",
		FILTERS: {  
			_ID_PAREJA : idPareja+"",
			_ID_BLOQUE : idBloque
		}
	}
	console.log(datos);
	var arrayFecha = [];
	callSp(datos).then(function(res){
		console.log(res);
		var color = "#F7542B";
		if(res.data[0].response == 0){
			color = "#89EA4F";
		}
		console.log(color);
		$("#bk"+idBloque).css("background-color",color);
	});
}

function getParejaFixture(){
	var bloques = {
		SP: "get_bloques",
		FILTERS: {  
			_ID_TORNEO : idTorneo
		}
	}
	var detalle_bloque = {
		SP: "get_detalle_bloque",
		FILTERS: {  
			_ID_TORNEO : idTorneo
		}
	}
	
	var canchas = {
		SP: "get_cancha",
		FILTERS: {  
			_ID_TORNEO : idTorneo
		}
	}
	
	var fixture = {
		SP: "get_fixture",
		FILTERS: {  
			_ID_TORNEO : idTorneo,
			_ID_CATEGORIA : 0,
			_ID_FASE : 'G'
		}
	}
	var html = "";
	callSp(detalle_bloque).then(function(res){
		console.log(res);
		callSp(canchas).then(function(res2){
			console.log(res2);
			callSp(bloques).then(function(res3){
				console.log(res3);
				callSp(fixture).then(function(res4){
					console.log(res4);
					$.each(res.data,function(k, v) {				
						html += "<tr>";
						html += "<th colspan='"+res2.data.length+1+"'>"+v.fecha+"</th>" 
						html += "</tr>";
						html += "<tr>";
						html += "<th>Hora</th>"; 
						$.each(res2.data,function(k2, v2) {
							html += "<th>"+v2.cancha+"</th>"; 
						});
						html += "</tr>";
						$.each(res3.data,function(k3, v3) {							
							if(v3.FECHA == v.fecha){
								html += "<tr>";
								var clase = v3.FECHA+v3.HORA;
								clase = clase.replaceAll("-","");
								clase = clase.replaceAll(":","");
								html += "<td style='width: 60px' class='OpenDetalleFixture' id="+clase+">"+v3.HORA+ "&nbsp;<i class='fa fa-search-plus'></i></td>";
								var cancha1 = 0;
								$.each(res4.data,function(k4, v4) {
									$.each(res2.data,function(k2, v2) {
										if(v3.FECHA == v4.fecha && v3.HORA == v4.hora && v2.id == v4.idCancha){
											if(v4.id > 0){
												var div = "<div class='col-xs-12 col-sm-12 col-md-12 divFixture' id='df"+v4.id+"' >";
												div += 		"<div class='col-xs-12 portlet'>";
												div += 			"<label style='color: #337ab7;font-weight: bold;'>"+v4.categoria+"</label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-6 portlet'>";
												div += 			"<label style='color: #337ab7;font-weight: bold;'>Grupo "+v4.grupo+"</label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-6 portlet'>";
												div += 			"<label style='color: #337ab7;font-weight: bold;'>Jor "+v4.ronda+"</label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-6 portlet'>";
												div += 			"<label style='color: #337ab7;font-weight: bold;'></label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-6 portlet'>";
												div += 			"<label style='color: #337ab7;font-weight: bold;'>"+v4.hora+"h</label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-12 portlet "+clase+"' style='display:none'>";
												div += 			"<label style='color: #337ab7;'>"+decode_utf8(v4.jugador1P1)+" / "+decode_utf8(v4.jugador2P1)+" " +"</label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-12 portlet "+clase+"' style='display:none'>";
												div += 			"<label style='color: #337ab7;'>--------------------------------------------------------</label>";
												div += 		"</div>";
												div += 		"<div class='col-xs-12 portlet "+clase+"' style='display:none'>";
												div += 			"<label style='color: #337ab7;'>"+decode_utf8(v4.jugador1P2)+" / "+decode_utf8(v4.jugador2P2)+" " +"</label>";
												div += 		"</div>";
												div += 	"</div>";
												html += "<td id='"+clase+"_"+v2.id+"'>"+div+"</td>";
											} else {
												div = 		"<div class='col-xs-12 portlet>";
												div += 			"<label style='color: #337ab7;'>Pista Libre</label>";
												div += 		"</div>";
												html += "<td>"+div+"</td>";
											}
										} 
									});	
								});
								html += "</tr>";
							}													
						});
					});
					
					$("#tbl_fixture").append(html);
					$(".OpenDetalleFixture").click(function(){
						console.log($(this).attr("id"));
						var id = $(this).attr("id");
						if ($("."+id).is(':hidden'))
						   $("."+id).show();
						else
						   $("."+id).hide();
					});
					
					$(".divFixture").click(function(){
						var id = $(this).attr("id").split("df")[1];
						var pareja_fixture = {
							SP: "get_pareja_fixture",
							FILTERS: {  
								_ID : id
							}
						}
						var detallePartido = {
							SP: "get_detalle_partido",
							FILTERS: {  
								_ID : id
							}
						}
						callSp(pareja_fixture).then(function(resPF){
							callSp(detallePartido).then(function(resD){
								console.log(resD);
								var set0r1 = 0;
								var set0r2 = 0;
								var set1r1 = 0;
								var set1r2 = 0;
								var set2r1 = 0;
								var set2r2 = 0;
								var set3r1 = 0;
								var set3r2 = 0;
								var resDP = [];
								if(resD.data.length > 1){
									resDP = resD.data;
									if(resDP[0].r1 != undefined){set0r1 = resDP[0].r1;}
									if(resDP[0].r2 != undefined){set0r2 = resDP[0].r2;}
									if(resDP[1].r1 != undefined){set1r1 = resDP[1].r1;}
									if(resDP[1].r2 != undefined){set1r2 = resDP[1].r2;}
									if(resDP[2].r1 != undefined){set2r1 = resDP[2].r1;}
									if(resDP[2].r2 != undefined){set2r2 = resDP[2].r2;}
									if(resDP[3].r1 != undefined){set3r1 = resDP[3].r1;}
									if(resDP[3].r2 != undefined){set3r2 = resDP[3].r2;}
								}
								
								var pop = "";
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>Categorías: "+resPF.data[0].categoria+"</label>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>Fase de grupo - Partido "+resPF.data[0].id_ronda+"</label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>Categorías: "+resPF.data[0].fecha+"</label>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>"+decode_utf8(resPF.data[0].j1p1)+"</label></br>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>"+decode_utf8(resPF.data[0].j2p1)+"</label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>VS</label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>"+decode_utf8(resPF.data[0].j1p2)+"</label></br>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>"+decode_utf8(resPF.data[0].j2p2)+"</label>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<button id ='bt"+resPF.data[0].id+"_"+resPF.data[0].j1p1+"' onclick='cambioEstado("+resPF.data[0].id+")' class='btn "+resPF.data[0].color_p1+" submit'>"+resPF.data[0].estado_p1+"</button>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<button id ='bt"+resPF.data[0].id+"_"+resPF.data[0].j1p2+"' onclick='cambioEstado("+resPF.data[0].id+")' class='btn "+resPF.data[0].color_p2+" submit'>"+resPF.data[0].estado_p2+"</button>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' disabled class='form-control' value='"+set0r1+"' id='setP1'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'>Sets</label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' disabled class='form-control' value='"+set0r2+"' id='setP2'>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' class='form-control' value='"+set1r1+"'  id='set1P1'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'></label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' class='form-control' value='"+set1r2+"' id='set1P2'>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' class='form-control' value='"+set2r1+"' id='set2P1'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'></label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' class='form-control' value='"+set2r2+"' id='set2P2'>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' class='form-control' value='"+set3r1+"' id='set3P1'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 			"<label style='color: #337ab7;font-weight: bold;'></label>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop +=      	"<input type='number' class='form-control' value='"+set3r2+"' id='set3P2'>";
								pop += 		"</div>";
								pop += 	"</div>";
								
								pop += 	"<div class='col-xs-12 col-sm-12 col-md-12'>";
								pop += 		"<div class='col-xs-4 portlet'>";
								if(set0r1 > set0r2) {
									pop +=      	"<button class='btn green submit'>Ganador</button>";
								}
									
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								pop += 		"</div>";
								pop += 		"<div class='col-xs-4 portlet'>";
								if(set0r1 < set0r2) {
									pop +=      	"<button class='btn green submit'>Ganador</button>";
								}
								pop += 		"</div>";
								pop += 	"</div>";
						
								pop += 		"<div style='text-align: center;'>";
								pop += 			"<a class='btn green-dark submit-modal' onclick='guardarResultado("+resPF.data[0].id+")'> Guardar</a>";
								pop += 			"<a class='btn red' onclick='closeModal()'>Cancelar</a>";
								pop += 		"</div>";
								popUp("Resultado Partido", pop, true, "850px", true);
								selectCss();
							});
						});
					});
				});
			});
		});
		
	});
}

function guardarResultado(idFixture){
	
	var set1P1 = "0";if($("#set1P1").val() != ""){ set1P1 = $("#set1P1").val();}
	var set1P2 = "0";if($("#set1P2").val() != ""){ set1P2 = $("#set1P2").val();}
	var set2P1 = "0";if($("#set2P2").val() != ""){ set2P1 = $("#set2P1").val();}
	var set2P2 = "0";if($("#set2P2").val() != ""){ set2P2 = $("#set2P2").val();}
	var set3P1 = "0";if($("#set3P1").val() != ""){ set3P1 = $("#set3P1").val();}
	var set3P2 = "0";if($("#set3P2").val() != ""){ set3P2 = $("#set3P2").val();}
		
	
	var RES1 = {
		SP: "guardar_resultado",
		FILTERS: {  
			_ID : idFixture+"",
			_R1 : set1P1,
			_R2 : set1P2,
			_SET : '1'
		}
	}
	var RES2 = {
			SP: "guardar_resultado",
			FILTERS: {  
				_ID : idFixture+"",
				_R1 : set2P1,
				_R2 : set2P2,
				_SET : '2'
			}
	}
	var RES3 = {
			SP: "guardar_resultado",
			FILTERS: {  
				_ID : idFixture+"",
				_R1 : set3P1,
				_R2 : set3P2,
				_SET : '3'
			}
	}
	console.log(RES1);
	callSp(RES1).then(function(res){
		console.log(res);		
	});
	console.log(RES2);
	callSp(RES2).then(function(res){
		console.log(res);
	});
	console.log(RES3);
	callSp(RES3).then(function(res){
		console.log(res.data[0].response);
		
	});
	
	
	
}

function getDetallePartido(idFixture){
	var detallePartido = {
		SP: "guardar_resultado",
		FILTERS: {  
			_ID : idFixture+""
		}
	}
	callSp(detallePartido).then(function(res){
		console.log(res);
		getTablaPosiciones();
		getCategorias();
	});
}
var arrayTabla = [];
function getTablaPosiciones(){
	var tabla = {
		SP: "get_grupo_torneo",
		FILTERS: {  
			_ID_TORNEO : idTorneo+""
		}
	}
	callSp(tabla).then(function(res){
		console.log(res);
		arrayTabla = res.data;
	});
}


