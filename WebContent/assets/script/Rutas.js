$(document).ready(function(){
	var cir = {
		TABLE: "circuito",
		WHERE:{
			estado: 1
		}
	}
	Select(cir).then(function(res){
		console.log(res)
		var option = "<option value=''></option>";
		$.each(res.data, function(k,v){
			option += "<option value='"+JSON.stringify(v)+"'>"+v.nombre+"</option>";
		})
		$("#Circuito").html(option)
	})
	getRutas();
	$(".upd").hide();
})
function getRutas(){
	var cir = {
		SP: "GET_RUTAS"
	}
	getSp(cir).then(function(res){
		console.log(res)
		$rutas = res.data;
		var rutas = "<option value=''></option>";
		$.each(res.data, function(k,v){
			rutas += "<option value='"+JSON.stringify(v)+"'>"+v.nombre+"</option>";
		})
		$("#rutas").html(rutas)
	})
	getRutaComercio();
}
var $rutas;
var tabla;
var $comuna;
var $RutaComercio;
var arrRutaComercio = [];
var comArr = [];
function getRutaComercio(){
	var RutaComercio = {
		TABLE: "RutaComercio"
	}
	Select(RutaComercio).then(function(res){
		$RutaComercio = res.data;
		$.each(res.data, function(k,v){
			arrRutaComercio.push(v.idComercio);
		})
	})
}
var com = {
	TABLE: "comunas"
}
Select(com).then(function(comuna){
	$comuna = comuna.data;
	$.each(comuna.data, function(k,v){
		comArr[v.id] = v.geolocation;
	})
})
var jsonComercios = [];
var arrayComercios = [];
var $markers = [];
function cambioCircuito($v){
	if(!$v){
//		if (tabla2) {
//			tabla2.destroy();
//			$('#tblComercios').empty();
//		}
//		if (tabla) {
//			tabla.destroy();
//			$('#tbl_RendimientoVlidadr').empty();
//		}
		return;
	}
	setMapOnAll();
	$v = JSON.parse($v);
	var getCom = {
		TABLE: "COMERCIO",
		WHERE: {COMUNA: $v.comuna}
	}
	Select(getCom).then(function(res){
		map = new google.maps.Map(document.getElementById('mapa'),{
			center: JSON.parse(comArr[$v.comuna]),
			zoom: 13
		});
		var datos = [];
		jsonComercios = res.data;
		$.each(res.data,function(k, v) {
			arrayComercios[v.id] = v.GEOREFERENCIA;
			if(arrRutaComercio.indexOf(v.id) == -1){
				if(v.GEOREFERENCIA){
					var marker = new google.maps.Marker({
			            position: JSON.parse(v.GEOREFERENCIA),
			            map: map,
			            title: v.RAZON_SOCIAL
			        });
					google.maps.event.addListener(marker, 'click', function() {
			        	setComercio(v, marker);
			        });
					$markers.push(marker)
				}else{
					var tbl = ["<input name='check' type='checkbox' onclick='clickCheck(this)' value='"+v.id+"' title='Seleccionar' id='"+v.id+"' class='checkbox comercio'/>",
					           v.RUT,v.RAZON_SOCIAL, v.DIRECCION, formatFecha(v.FECHA_CREACION)];
					datos.push(tbl);
				}
			}
		})
		if (tabla) {
			tabla.destroy();
			$('#tbl_RendimientoVlidadr').empty();
		}
		var columnas = [ "#", "Rut","Razon Social", "Direccion", "Fecha Creacion"];
		var finalColumn = [];
		for (var i = 0; i < columnas.length; i++) {
			finalColumn.push({
				title : columnas[i]
			})
		}
		tabla = $('#tbl_RendimientoVlidadr').DataTable({
			data : datos,
			columns : finalColumn,
			autoWidth : true,
			ordering : false,
			pagingType : "full_numbers",
			language : languageDT(),
			paging: false
		});
		$("#tbl_RendimientoVlidadr_filter").hide();
	})
}
var $arrayComercios = [];
var tabla2;
function setComercio($v, $marker){
	console.log($v)
	var inArray = false;
	var index = 0;
	$.each($arrayComercios, function(k,v){
		if($v.id == v.id){
			inArray = true;
			index = k;
			return false;
		}else{
			inArray = false;;
		}
	})
	console.log(inArray)
	console.log(index)
	if(inArray){
		$marker.setIcon();
		$arrayComercios.splice(index, 1);
	}else{
		$marker.setIcon('../assets/global/img/incidencia.png');
		$arrayComercios.push($v);
	}
	var datos = [];
	$.each($arrayComercios,function(k, v) {
		var tbl = [v.RUT,v.RAZON_SOCIAL, v.DIRECCION];
		datos.push(tbl);
	})
	if (tabla2) {
		tabla2.destroy();
		$('#tblComercios').empty();
	}
	var columnas = ["Rut","Razon Social", "Direccion"];
	var finalColumn = [];
	for (var i = 0; i < columnas.length; i++) {
		finalColumn.push({
			title : columnas[i]
		})
	}
	tabla2 = $('#tblComercios').DataTable({
		data : datos,
		columns : finalColumn,
		autoWidth : true,
		ordering : false,
		pagingType : "full_numbers",
		language : languageDT(),
		paging: false
	});
	$("#tblComercios_filter").hide();
}
function clickCheck($this){
	console.log($this)
	var id = $($this).val();
	var index = 0;
	var $v;
	if($($this).is(':checked')){
		$.each(jsonComercios, function(k,v){
			if(id == v.id){
				$v = v;
			}
		})
		$arrayComercios.push($v);
	}else{
		$.each($arrayComercios, function(k,v){
			if(id == v.id){
				index = k;
				return false;
			}
		})
		$arrayComercios.splice(index, 1);
	}
	var datos = [];
	$.each($arrayComercios,function(k, v) {
		var tbl = [v.RUT,v.RAZON_SOCIAL, v.DIRECCION];
		datos.push(tbl);
	})
	if (tabla2) {
		tabla2.destroy();
		$('#tblComercios').empty();
	}
	var columnas = ["Rut","Razon Social", "Direccion"];
	var finalColumn = [];
	for (var i = 0; i < columnas.length; i++) {
		finalColumn.push({
			title : columnas[i]
		})
	}
	tabla2 = $('#tblComercios').DataTable({
		data : datos,
		columns : finalColumn,
		autoWidth : true,
		ordering : false,
		pagingType : "full_numbers",
		language : languageDT(),
		paging: false
	});
	$("#tblComercios_filter").hide();
}
function initMap(){
	map = new google.maps.Map(document.getElementById('mapa'),{
		center: {lat: -33.4032493, lng: -70.6158353},
		zoom: 10
	});
	var input = document.getElementById("address");
	
	var searchBox = new google.maps.places.SearchBox(input);
	
	map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
    });
	
	var markers = [];
    searchBox.addListener('places_changed', function() {
    	var places = searchBox.getPlaces();
    	if (places.length == 0){
    		return;
    	}
    	markers.forEach(function(marker){
    		marker.setMap(null);
    	});
    	markers = [];
    	var bounds = new google.maps.LatLngBounds();
    	places.forEach(function(place) {
    		if (!place.geometry) {
    			return;
    		}
    		var icon = {
    			url: place.icon,
    			size: new google.maps.Size(71, 71),
    			origin: new google.maps.Point(0, 0),
    			anchor: new google.maps.Point(17, 34),
    			scaledSize: new google.maps.Size(25, 25)
    		};

    		markers.push(new google.maps.Marker({
    			map: map,
    			icon: icon,
    			title: place.name,
    			position: place.geometry.location
    		}));
    		if (place.geometry.viewport) {
    			bounds.union(place.geometry.viewport);
    		} else {
    			bounds.extend(place.geometry.location);
    		}
    	});
    	map.fitBounds(bounds);
    });
    
	drawingManager = new google.maps.drawing.DrawingManager({
		drawingControl: false,
		drawingControlOptions: {
			position: google.maps.ControlPosition.TOP_CENTER,
			drawingModes: ['polygon']
		},
		markerOptions: {
			icon: "../assets/global/img/incidencia.png",
			size: new google.maps.Size(20, 32),
			origin: new google.maps.Point(0, 0),
			anchor: new google.maps.Point(0, 32)
		},
		polylineOptions: {
			draggable: false,
			selected: true,
			strokeWeight: 1.5
		}
		
	});
	google.maps.event.addListener(drawingManager, 'markercomplete', function(marker){
		marker = marker;
		console.log(marker.latLng);
	});
	drawingManager.setMap(map);
}
function agregar(){
	if(!$("#nombre").val()){
		alert("Debe Ingresar un nombre a la Ruta");
		$("#nombre").focus();
		return;
	}
	if($opt == 2){
		updateRutas();
		return;
	}
	var values = [];
	var isCheck = false;
	if($arrayComercios.length != 0){
		isCheck = true;
	}
	if(!isCheck){
		$(".comercio").each(function(){
			if($(this).is(':checked')){
				isCheck = true;
			}
		})
	}
	if(isCheck){
		var cir = JSON.parse($("#Circuito").val());
		var newRuta = {
			TABLE: "rutas",
			VALUES:{
				idCircuito: cir.id,
				nombre: $("#nombre").val()
			},
			ALERTA: false,
			COMMIT: false
		}
		Mantenedor(newRuta).then(function(res){
			if(res.error == 0){
				var ruta = res.data[0];
				var values = [];
				$(".comercio").each(function(){
					if($(this).is(':checked')){
						values.push({
							idRuta: ruta.ID,
							idComercio: $(this).val(),
							estado: 1
						})
					}
				})
				$.each($arrayComercios, function(k,v){
					values.push({
						idRuta: ruta.ID,
						idComercio: v.id,
						estado: 1
					})
				})
				var neuRC = {
					TABLE: "RutaComercio",
					VALUES: values,
					ALERTA: false
				}
				Mantenedor(neuRC).then(function(res){
					if(res.error == 0){
						var a = alerta("Ruta creada con éxito");
						$(a.aceptar).click(function(){
							radioOpt(1)
							getRutas()
						})
					}else{
						alerta(res.mensaje)
					}
				})
			}else{
				alerta(res.mensaje)
			}
		})
	}
}
function setMapOnAll() {
	for (var i = 0; i < $markers.length; i++) {
		$markers[i].setMap(null);
	}
}
function cambioRutas($v){
	if(!$v){
		return;
	}
	setMapOnAll();
	console.log($v)
	$v = JSON.parse($v)
	var getCom = {
		TABLE: "COMERCIO",
		WHERE: {COMUNA: $v.comuna}
	}
	Select(getCom).then(function(resCom){
		jsonComercios = resCom.data;
		console.log(resCom)
		var input = {
			SP: "GET_RUTA_DETALLE",
			FILTERS: [{
				value: $v.id
			}]
		}
		console.log($v)
		var arrCheck = [];
		getSp(input).then(function(res){
			$arrayComercios = res.data;
			console.log(res)
			var datos = [];
			var datos2 = [];
			var arrGeo = [];
			$.each(res.data,function(k, v){
				if(k == 0){
					map = new google.maps.Map(document.getElementById('mapa'),{
						center: JSON.parse(v.geolocation),
						zoom: 13
					});
				}
				if(v.GEOREFERENCIA){
					arrGeo[v.id] = v.GEOREFERENCIA;
					var tbl = [v.RUT,v.RAZON_SOCIAL, v.DIRECCION, formatFecha(v.FECHA_CREACION)];
					datos.push(tbl);
				}else{
					var tbl = ["<input name='check' type='checkbox' onclick='clickCheck(this)' checked value='"+v.id+"' title='Seleccionar' id='"+v.id+"' class='checkbox comercio'/>",
					           v.RUT,v.RAZON_SOCIAL, v.DIRECCION, formatFecha(v.FECHA_CREACION)];
					arrCheck.push(v.id);
					datos2.push(tbl);
				}
			})
			$.each(jsonComercios, function(k,v){
				if(v.GEOREFERENCIA){
					var marker;
					if(arrGeo.indexOf(v.GEOREFERENCIA) != -1){
						console.log(JSON.parse(v.GEOREFERENCIA))
						marker = new google.maps.Marker({
				            position: JSON.parse(v.GEOREFERENCIA),
				            map: map,
				            title: v.RAZON_SOCIAL,
				            icon: '../assets/global/img/incidencia.png'
				        });
					}else{
						marker = new google.maps.Marker({
				            position: JSON.parse(v.GEOREFERENCIA),
				            map: map,
				            title: v.RAZON_SOCIAL,
				        });
					}
					$markers.push(marker)
					google.maps.event.addListener(marker, 'click', function() {
			        	setComercio(v, marker);
			        });
				}else{
					var check = "checked";
					if(arrCheck.indexOf(v.id) == -1){
						check = "unchecked";
					}
					var tbl = ["<input name='check' type='checkbox' onclick='clickCheck(this)' "+check+" value='"+v.id+"' title='Seleccionar' id='"+v.id+"' class='checkbox comercio'/>",
					           v.RUT,v.RAZON_SOCIAL, v.DIRECCION, formatFecha(v.FECHA_CREACION)];
					datos2.push(tbl);
				}
			})
			if (tabla2) {
				tabla2.destroy();
				$('#tblComercios').empty();
			}
			var columnas = ["Rut","Razón Social", "Dirección"];
			var finalColumn = [];
			for (var i = 0; i < columnas.length; i++) {
				finalColumn.push({
					title : columnas[i]
				})
			}
			tabla2 = $('#tblComercios').DataTable({
				data : datos,
				columns : finalColumn,
				autoWidth : true,
				ordering : false,
				pagingType : "full_numbers",
				language : languageDT(),
				paging: false
			});
			$("#tblComercios_filter").hide();
			if (tabla) {
				tabla.destroy();
				$('#tbl_RendimientoVlidadr').empty();
			}
			var columnas = [ "#", "Rut","Razón Social", "Dirección", "Fecha Creación"];
			var finalColumn = [];
			for (var i = 0; i < columnas.length; i++) {
				finalColumn.push({
					title : columnas[i]
				})
			}
			tabla = $('#tbl_RendimientoVlidadr').DataTable({
				data : datos2,
				columns : finalColumn,
				autoWidth : true,
				ordering : false,
				pagingType : "full_numbers",
				language : languageDT(),
				paging: false
			});
			$("#tbl_RendimientoVlidadr_filter").hide();
		})
	})
	
}

function cambioNombre($v){
	if($v){
		$.each($rutas, function(k,v){
			if($v == v.nombre){
				var c = confirm("Ya exite una ruta con este nombre, ¿Desea modificarla?");
				if(c){
					cambioRutas(JSON.stringify(v))
				}
			}
		})
	}
}
var $opt = 1;
function radioOpt(opt){
	if(opt == 1){
		$opt = 1;
		vaciarTablas()
		$(".upd").hide();
		$(".add").show();
		initMap();
		$("#rutas").val("").trigger("change");
		$("#Circuito").val("").trigger("change");
	}else{
		$opt = 2;
		vaciarTablas()
		$(".upd").show();
		$(".add").hide();
		initMap();
		$("#rutas").val("").trigger("change");
		$("#Circuito").val("").trigger("change");
	}
}
function vaciarTablas(){
	var columnas = [ "#", "Rut","Razon Social", "Direccion", "Fecha Creacion"];
	var finalColumn = [];
	for (var i = 0; i < columnas.length; i++) {
		finalColumn.push({title : columnas[i]})
	}
	if (tabla) {
		tabla.destroy();
		$('#tbl_RendimientoVlidadr').empty();
	}
	tabla = $('#tbl_RendimientoVlidadr').DataTable({
		data : [],
		columns : finalColumn,
		autoWidth : true,
		ordering : false,
		pagingType : "full_numbers",
		language : languageDT(),
		paging: false
	});
	var columnas = ["Rut","Razon Social", "Direccion"];
	var finalColumn = [];
	for (var i = 0; i < columnas.length; i++) {
		finalColumn.push({
			title : columnas[i]
		})
	}
	if (tabla2) {
		tabla2.destroy();
		$('#tblComercios').empty();
	}
	tabla2 = $('#tblComercios').DataTable({
		data : [],
		columns : finalColumn,
		autoWidth : true,
		ordering : false,
		pagingType : "full_numbers",
		language : languageDT(),
		paging: false
	});
	$("#tblComercios_filter").hide();
	$("#tbl_RendimientoVlidadr_filter").hide();
}
function updateRutas(){
	var values = [];
	var ruta = JSON.parse($("#rutas").val())
	var isCheck = false;
	if($arrayComercios.length != 0){
		isCheck = true;
	}
	if(!isCheck){
		$(".comercio").each(function(){
			if($(this).is(':checked')){
				isCheck = true;
			}
		})
	}
	if(isCheck){
		var delRuta = {
			TABLE: "RutaComercio",
			WHERE: {idRuta: ruta.id},
			COMMIT: false,
			ALERTA: false
		}
		Delete(delRuta).then(function(res){
			if(res.error == 0){
				var values = [];
				$(".comercio").each(function(){
					if($(this).is(':checked')){
						values.push({
							idRuta: ruta.id,
							idComercio: $(this).val(),
							estado: 1
						})
					}
				})
				$.each($arrayComercios, function(k,v){
					values.push({
						idRuta: ruta.id,
						idComercio: v.id,
						estado: 1
					})
				})
				var neuRC = {
					TABLE: "RutaComercio",
					VALUES: values,
					ALERTA: false
				}
				Mantenedor(neuRC).then(function(res){
					if(res.error == 0){
						var a = alerta("Ruta modificada con exito");
						$(a.aceptar).click(function(){
							radioOpt(2)
							getRutas()
						})
						
					}else{
						alerta(res.mensaje)
					}
				})
			}else{
				alerta(res.mensaje)
			}
		})
	}
}