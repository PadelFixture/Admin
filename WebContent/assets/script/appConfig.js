$(document).ready(function(){
	fechas();
	format();
	var divLoading = "<article id='loading' class='loading_dos' style='display: none;'>";
	divLoading += 		"<div id='modal' class='modal' style='display: block;'></div>"
	divLoading += "</article>";
	$("#simpleAgro").append(divLoading);
	$('.form-control.input-md.multiple').select2({
		multiple: true,
		placeholder: "Seleccionar"
	});
	selectCss();
	$('#tbl_Fito_length').hide();
	$(".select2.select2-container.select2-container--bootstrap").attr("style", "");
})
function selectMultuple(){
	$('.form-control.input-md.multiple').select2({
		multiple: true,
		placeholder: "Seleccionar"
	});
}
function getDataExcel(c, f, options){
	var array = [];
	var exclude = "";
	if(options){
		exclude = options.exclude;
	}
	$.each(f, function(k,v){
		var json = {};
		for(var i = 0; i < c.length; i++){
			if(exclude != c[i]){
				json[c[i]] = v[i];
			}
		}
		array.push(json)
	})
	return array;
}
$('.form-control.input-md.multiple').each(function(){
	var glovalInput;
	$(this).change(function(){
		var inputSelect = $(this).html();
		var input = $(this).val();
		var newVal;
		var arrSel = [];
		if(input){
			for(var i = 0; i < input.length; i++){
				if(input[i] != 0){
					arrSel.push(input[i]);
				}
			}
		}
		if(glovalInput && input){
			for(var i = 0; i < input.length; i++){
				if(glovalInput.indexOf(input[i]) == -1){
					newVal = input[i];
				}
			}
			if(newVal == 0){
				$(this).html(inputSelect);
				$(this).val(0);
			}else{
				$(this).html(inputSelect);
				$(this).val(arrSel);
			}
		}
		var input = $(this).val();
		glovalInput = input;
	})
})

IPSERVER = "http://dev-122.expled.cl/fx/post";//QAS
//IPSERVER = "http://recotec.goplicity.com/recotec-sim/";//PRD
IPSERVER_2 = "http://dev-122.expled.cl/fx/call-sp";//QAS
//IPSERVER_2 = "http://recotec.goplicity.com/recotec-sim/call-sp";//PRD
var configService = 'Recote_Sim';
//var CODPRETOLEO = "10000032";//Desarrollo
var PERIODO = '2018';
var CODPRETOLEO = "10000048";//Produccion
var IPSERVERWORK = "";
function selectCss(){
	var selects = document.getElementsByTagName("select");
	$.each(selects, function(){
		if($(this)[0].className.indexOf("form-control input-sm") != -1){
			$(this).select2({
				multiple: false,
				placeholder: "Seleccionar"
			});
		}
	})
	$(".select2.select2-container.select2-container--bootstrap").attr("style", "");
}
function select3(){
	$('.form-control.input-xs').select2({
		multiple: false,
		placeholder: "Seleccionar"
	});
}
var loading = function loading(){}
loading.hide = function(){
	$("#loading").hide();
}
loading.show = function(){
	$("#loading").show();
}
function viewFecha(fecha){
	fecha = fecha.split("-");
	fecha = fecha[2]+"-"+fecha[1]+"-"+fecha[0];
	return fecha;
}
function classSlect(){
	$('.form-control.input-sm').select2({
		multiple: false,
		placeholder: "Seleccone"
	});
}
function formatNumber (numero) {
	const noTruncarDecimales = {maximumFractionDigits: 20};
	var resultado = "";
	numero = numero.toString();
    if(numero[0] == "-"){
        nuevoNumero = numero.replace(/\./g,'').substring(1);
    }else{
        nuevoNumero = numero.replace(/\./g,'');
    }
    if(numero.indexOf(",") >= 0){
        nuevoNumero = nuevoNumero.substring(0,nuevoNumero.indexOf(","));
    }
    for (var j, i = nuevoNumero.length - 1, j = 0; i >= 0; i--, j++){
        resultado = nuevoNumero.charAt(i) + ((j > 0) && (j % 3 == 0)? ".": "") + resultado;
    }
    if(numero.indexOf(",") >= 0){
        resultado += numero.substring(numero.indexOf(","));
    }
    if(numero[0] == "-"){
        return "-"+resultado;
    }else{
        return resultado;
    }
}
function formatNumberDB(n){
	n = String(n).split(".").join("").split(",").join(".");
	return n*1;
}
function format() {
	var number = $('.number');
	if(number.length != 0){
		for(var i = 0; i < number.length; i++){
			number[i].addEventListener('keyup', function(e){
				var element = e.target;
				var value = element.value;
				element.value = formatNumber(value);
			})
		}
	}
}
function justNumbers(e){
	var keynum = window.event ? window.event.keyCode : e.which;
	if ((keynum == 8) || (keynum == 46))
	return true;
	 
	return /\d/.test(String.fromCharCode(keynum));
}
function justUpperLeters(e){
	e.value = String(e.value).toUpperCase();
//	$(e).val(e.value.toUpperCase())
}
function spaceOff(e){
	$(e).val(e.value.replace(/ /g, ""))
}
function resNot(id){
	$.each(auxDataFito, function(k,v){
		if(v.id_programa == id){
			detalleNotificacion(v);
		}
	})
}
var especie;
var variedad;
var huertos;
function rEspecie(){
	return especie;
}
var requerido;
$(".submit").click(function(){
	$(".labelRe").each(function(){
		$(this).remove();
	})
	requerido = $(".required");
	requerido.each(function(){
		var content = $(this)[0].parentNode;
		if(!$(this).val()){
			for(var i = 0; i < content.childNodes.length; i++){
				if(content.childNodes[i].className == "labelRe"){
					content.removeChild(content.childNodes[i]);
				}
			}
			var label = "<label class='labelRe' style='color: #FF0000;'>Requerido</label>";
			$(content).append(label);
		}else{
			for(var i = 0; i < content.childNodes.length; i++){
				if(content.childNodes[i].className == "labelRe"){
					content.removeChild(content.childNodes[i]);
				}
			}
		}
	})
})
$(".required").keyup(function(){
	var content = $(this)[0].parentNode;
	if($(this).val()){
		for(var i = 0; i < content.childNodes.length; i++){
			if(content.childNodes[i].className == "labelRe"){
				content.removeChild(content.childNodes[i]);
			}
		}
	}
})
$(".required").change(function(){
	var content = $(this)[0].parentNode;
	if($(this).val()){
		for(var i = 0; i < content.childNodes.length; i++){
			if(content.childNodes[i].className == "labelRe"){
				content.removeChild(content.childNodes[i]);
			}
		}
	}
})
function dateHoy(){
	var hoy = new Date();
	var yyyy = hoy.getFullYear();
	var mm = hoy.getMonth()+1;
	var dd = hoy.getDate();
	if(mm < 10){
		mm = "0"+mm;
	}
	if(dd < 10){
		dd = "0"+dd;
	}
	hoy = (yyyy+"-"+mm+"-"+dd);
	return hoy;
}
function valDias(val){
	var res = val.id.substring(4, 10000000);
	var sub = val.id.substring(0, 4);
	if(val.id == "fechaDesde"){
		var fechaHasta = $("#fechaHasta").val();
		if(fechaHasta != ""){
			var fechaDesde = val.value.split("-");
			fechaDesde = new Date(fechaDesde[0], fechaDesde[1], fechaDesde[2]);
			fechaHasta = fechaHasta.split("-");
			fechaHasta = new Date(fechaHasta[0], fechaHasta[1], fechaHasta[2]);
			if(fechaDesde > fechaHasta){
				alerta("La fecha desde no puede mayor a fecha hasta");
				$("#"+val.id).val("");
				return;
			}
		}
	}else if(val.id == "fechaHasta"){
		var fechaDesde = $("#fechaDesde").val();
		if(fechaDesde != ""){
			var fechaHasta = val.value.split("-");
			fechaHasta = new Date(fechaHasta[0], fechaHasta[1], fechaHasta[2]);
			fechaDesde = fechaDesde.split("-");
			fechaDesde = new Date(fechaDesde[0], fechaDesde[1], fechaDesde[2]);
			if(fechaDesde > fechaHasta){
				alerta("La fecha desde no puede mayor a fecha hasta");
				$("#"+val.id).val("");
				return;
			}
		}
	}else{
		var hoy = dateHoy();
		hoy = hoy.split("-");
		hoy = new Date(hoy[0], hoy[1], hoy[2]);
		var fechaSelect = val.value.split("/");
		fechaSelect = new Date(fechaSelect[2], fechaSelect[0], fechaSelect[1]);
		if(fechaSelect < hoy){
			alerta("La fecha seleccionada no puede ser menor a la actual");
			$("#"+val.id).val("");
			return;
		}else if(sub == "date"){
			var fAlerta = sumQuince(fechaSelect, -15);
			var dd = fAlerta.getDate();
			var mm = fAlerta.getMonth();
			var yyyy = fAlerta.getFullYear();
			if(mm < 10){
				mm = "0"+mm;
			}
			if(dd < 10){
				fAlerta = mm+'/0'+dd+'/'+yyyy;
			}else{
				fAlerta = mm+'/'+dd+'/'+yyyy;
			}
			$("#fechaAlerta"+res).val(fAlerta);
		}
	}
}
function sumQuince(fecha, sum){
	fecha.setDate(fecha.getDate() + sum);
	return fecha;
}
function fechas(){
	var fecha = document.getElementsByName("fecha");
	for(var i = 0; i < fecha.length; i++){
		$(fecha[i]).attr("placeholder", "dd-mm-aaaa")
		$(fecha[i]).attr("autocomplete", "off")
		$(fecha[i]).datepicker({
		    format: 'dd/mm/yyyy',
		    firstDay : 1,
		    autoclose: true
		});
		$(fecha[i]).on("change", function(e){
			var f = e.target.value.split("/");
			if(f[1] != undefined && f[2] != undefined){
				e.target.value = f[1]+"-"+f[0]+"-"+f[2];
			}
			var periodo = f[2]+f[0];
		})
	}
}
var confirmar = function popUpQuestion(){}
confirmar.confirm = function(text, options){
	var html = "<h4>"+text+"</h4>";
	var required;
	var submit;
	if(options){
		var labelR = "<h5 class='labelRe' id='labelRequired' style='color: #FF0000; display: none;font-weight: bold;'>Requerido</h5>";
		html += "<h5 style='text-align: center;font-weight: bold;'>"+options.title+"</h5>";
		if(options.required == true){
			required = "required";
			submit = "submit";
		}else{
			required = "";
			submit = "";
		}
		if(options.input == "textarea"){
			html += "<textarea id="+options.id+" class='form-control "+required+"'></textarea>";
			
		}else{
			html += "<input type="+options.input+" id="+options.id+" class='form-control "+required+"'>"
		}
		if(options.required){
			html += labelR;
		}
	}
	var modal2 = {};
	modal2.popUp = swal({
		title: "Confirmar",
		html: html,
		position: 'top',
		animation: false,
		customClass: 'animated fadeInDown',
		width: "500px",
		showCancelButton: true,
		cancelButtonColor: '#d33',
		confirmButtonClass: 'swal2-confirm swal2-styled '+submit+'',
		confirmButtonText: 'Aceptar',
		preConfirm: function(){
			if(options && options.required == true){
				if(!$("#"+options.id).val()){
					$("#labelRequired").show();
					return false;
				}
			}
		},
		confirmButtonColor: '#008000',
		cancelButtonText: 'Cancelar',
		focusCancel: true,
		showCloseButton: false,
		showConfirmButton: true,
		allowOutsideClick: false,
		allowEscapeKey: false,
//		customClass: "col-xs-12 col-sm-12 col-md-12 portlet light"
	});
	modal2.aceptar = swal.getConfirmButton();
	modal2.cancelar = swal.getCancelButton();
	return modal2;
}
confirmar.question = function(options){
	var modal2 = {};
	modal2.popUp = swal({
		title: "Corfirmar",
		html: "<h4>"+options.question+"</h4>",
		position: 'top',
		animation: false,
		customClass: 'animated fadeInDown',
		width: "500px",
		showCancelButton: true,
		confirmButtonText: options.btnGreen,
		confirmButtonClass: 'btn btn-circle blue btn-sm',
		cancelButtonText: options.btnRed,
		cancelButtonClass: 'btn btn-circle blue btn-sm',
		focusCancel: true,
		showCloseButton: false,
		showConfirmButton: true,
		allowOutsideClick: false,
		allowEscapeKey: false
	});
	modal2.btnGreen = swal.getConfirmButton();
	modal2.btnRed = swal.getCancelButton();
	return modal2;
}
function formatFecha(fecha){
	if(!fecha) {
		return '';
	}
	fecha = fecha.split("-");
	fecha = fecha[2]+"-"+fecha[1]+"-"+fecha[0];
	return fecha;
}
function popUp(title, html, animation, width, showCloseButton){
	swal({
		position: 'center',
		title: title,
		html: html,
		animation: !animation,
		customClass: 'animated fadeInDown',
		width: width,
		showCloseButton: showCloseButton,
		showConfirmButton: false,
		focusConfirm: true,
		allowOutsideClick: false,
		allowEscapeKey: false,
		allowEnterKey: false
	});
	activateRequiredModal();
//	$(".swal2-header").draggable();
}
function activateRequiredModal(){
	$(".required-modal").keyup(function(){
		var content = $(this)[0].parentNode;
		if($(this).val()){
			for(var i = 0; i < content.childNodes.length; i++){
				if(content.childNodes[i].className == "labelRe"){
					content.removeChild(content.childNodes[i]);
				}
			}
		}
	})
	$(".required-modal").change(function(){
		var content = $(this)[0].parentNode;
		if($(this).val()){
			for(var i = 0; i < content.childNodes.length; i++){
				if(content.childNodes[i].className == "labelRe"){
					content.removeChild(content.childNodes[i]);
				}
			}
		}
	})
}
function closeModal(){
	swal.closeModal();
}
function alerta(html, body){
	var buton = [];
	if(!body){
		html = "<h4>"+html+"</h4>";
	}
	swal({
		title: "Alerta",
		html: html,
		position: 'top',
		animation: false,
		customClass: 'animated bounceIn',
		width: "500px",
		showCloseButton: false,
		showConfirmButton: true,
		focusConfirm: true,
		allowOutsideClick: false,
		allowEscapeKey: false
	});
	buton.aceptar = $(swal.getConfirmButton()).click(function(){});
	return buton;
}
function alerta2(html){
	var buton = [];
	html = "<h4>"+html+"</h4>";
	swal({
		title: "Alerta",
		html: html,
		position: 'top',
		animation: false,
		customClass: 'animated bounceIn',
		width: "500px",
		showCloseButton: false,
		showConfirmButton: true,
		focusConfirm: true,
		allowOutsideClick: false,
		allowEscapeKey: false
	});
	buton.aceptar = $(swal.getConfirmButton()).click(function(){});
	return buton;
}
function confirmar(text){
	text = "<h4>"+text+"</h4>";
	var modal2 = swal({
		title: "Corfirmar",
		html: text,
		position: 'top',
		animation: false,
		customClass: 'animated fadeInDown',
		width: "500px",
		showCancelButton: true,
		cancelButtonColor: '#d33',
		confirmButtonText: 'Aceptar',
		confirmButtonColor: '#008000',
		cancelButtonText: 'Cancelar',
		focusCancel: true,
		showCloseButton: false,
		showConfirmButton: true,
		allowOutsideClick: false,
		allowEscapeKey: false
	});
	var aceptar = $(".swal2-confirm.swal2-styled");
	return aceptar;
}
function notify(type, html){
	Command: toastr[type](html)
	toastr.options = {
		closeButton: false,
	  	debug: false,
	  	newestOnTop: true,
	  	progressBar: false,
	  	positionClass: "toast-top-right",
	  	preventDuplicates: false,
	  	showDuration: "300",
	  	hideDuration: "1000",
	  	timeOut: "3000",
	  	extendedTimeOut: "1000",
	  	showEasing: "swing",
	  	hideEasing: "linear",
	  	showMethod: "fadeIn",
	  	hideMethod: "fadeOut"
	}
}
function confirmRechazar(id){
	confirmar("¿Seguro que quiere rechazar esta Orden?");
	$(swal.getConfirmButton()).click(function(){
		// CODE FOR CONFIRM POPUP
	})
}
function getINFO(){
	var location = document.location.href;
	if(location.indexOf('?')>0){
		var getString = location.split('?')[1];
		var GET = getString.split('&');
		var get = {};
		
		for(var i = 0, l = GET.length; i < l; i++){
			var tmp = GET[i].split('=');
            get[tmp[0]] = unescape(decodeURI(tmp[1]));
		}
		return get;
	}
}
function parseFolio(value){
    var res="";
    if(!isNaN(parseInt(value))){
         res = ("0000000000" + value).slice (-10);
    }else{
        res = false;
    }
    return res;
}
function parseVeinte(value){
    var res="";
    if(!isNaN(parseInt(value))){
         res = ("00000000000000000000" + value).slice (-20);
    }else{
        res = false;
    }
    return res;
}
function validateModal(){
	var validate = true;
	$(".labelRe").each(function(){
		$(this).remove();
	})
	requerido = $(".required-modal");
	requerido.each(function(){
		var content = $(this)[0].parentNode;
		if(!$(this).val()){
			for(var i = 0; i < content.childNodes.length; i++){
				if(content.childNodes[i].className == "labelRe"){
					content.removeChild(content.childNodes[i]);
				}
			}
			var label = "<h5 class='labelRe' style='color: #FF0000;'>Requerido</h5>";
			$(content).append(label);
			validate = false;
		}else{
			for(var i = 0; i < content.childNodes.length; i++){
				if(content.childNodes[i].className == "labelRe"){
					content.removeChild(content.childNodes[i]);
				}
			}
		}
	})
	return validate;
}
function parseNumericFloat(value){
	value = value.replace(".","");
	value = value.replace(",",".");
	return parseFloat(value);
}
function revisarDigito( dvr ){	
	dv = dvr + ""	
	if ( dv != '0' && dv != '1' && dv != '2' && dv != '3' && dv != '4' && dv != '5' && dv != '6' && dv != '7' && dv != '8' && dv != '9' && dv != 'k'  && dv != 'K')	{	
		var a = alerta("Debe ingresar un digito verificador valido");
		$(a.aceptar).click(function(){
			$("#rut").focus();		
			$("#rut").select();		
			return false	
		})
	}	
	return true;
}

function revisarDigito2( crut ){	
	largo = crut.length;	
	if ( largo < 2 ){
		var a = alerta("Debe ingresar el rut completo");
		$(a.aceptar).click(function(){
			$("#rut").focus();		
			$("#rut").select();		
			return false	
		})
	}	
	if ( largo > 2 ){
		rut = crut.substring(0, largo - 1);	
	}else{
		rut = crut.charAt(0);
	}
	dv = crut.charAt(largo-1);	
	revisarDigito( dv );
	if ( rut == null || dv == null ){
		return 0
	}
	var dvr = '0'	
	suma = 0	
	mul  = 2
	for (i= rut.length -1 ; i >= 0; i--)	{	
		suma = suma + rut.charAt(i) * mul		
		if (mul == 7){
			mul = 2	
		}else{
			mul++
		}
	}	
	res = suma % 11	
	if (res==1)		
		dvr = 'k'	
	else if (res==0)		
		dvr = '0'	
	else{		
		dvi = 11-res		
		dvr = dvi + ""	
	}
	if ( dvr != dv.toLowerCase() ){		
		var a = alerta("EL rut es incorrecto");
		$(a.aceptar).click(function(){
			$("#rut").focus();		
			$("#rut").select();		
			return false	
		})
	}
	return true
}

function Rut(texto){	
	if(texto == ""){
		return;
	}
	var tmpstr = "";	
	for ( i=0; i < texto.length ; i++ ){
		if ( texto.charAt(i) != ' ' && texto.charAt(i) != '.' && texto.charAt(i) != '-' ){
			tmpstr = tmpstr + texto.charAt(i);	
		}
	}
	texto = tmpstr;	
	largo = texto.length;	

	if ( largo < 2 ){	
		var a = alerta("Debe ingresar el rut completo");
		$(a.aceptar).click(function(){
			$("#rut").focus();		
			$("#rut").select();		
			return false	
		})	
	}
	for (i=0; i < largo ; i++ ){			
		if ( texto.charAt(i) !="0" && texto.charAt(i) != "1" && texto.charAt(i) !="2" && texto.charAt(i) != "3" && texto.charAt(i) != "4" && texto.charAt(i) !="5" && texto.charAt(i) != "6" && texto.charAt(i) != "7" && texto.charAt(i) !="8" && texto.charAt(i) != "9" && texto.charAt(i) !="k" && texto.charAt(i) != "K" ){			
			var a = alerta("El valor ingresado no corresponde a un R.U.T valido");
			$(a.aceptar).click(function(){
				$("#rut").focus();		
				$("#rut").select();		
				return false	
			})		
		}	
	}	

	var invertido = "";	
	for ( i=(largo-1),j=0; i>=0; i--,j++ )		
		invertido = invertido + texto.charAt(i);	
	var dtexto = "";	
	dtexto = dtexto + invertido.charAt(0);	
	dtexto = dtexto + '-';	
	cnt = 0;	

	for ( i=1,j=2; i<largo; i++,j++ ){	
		if ( cnt == 3 ){			
			dtexto = dtexto + '.';			
			j++;			
			dtexto = dtexto + invertido.charAt(i);			
			cnt = 1;		
		}		
		else{				
			dtexto = dtexto + invertido.charAt(i);			
			cnt++;		
		}	
	}	

	invertido = "";	
	for ( i=(dtexto.length-1),j=0; i>=0; i--,j++ ){
		invertido = invertido + dtexto.charAt(i);
		$("#rut").val(invertido.toUpperCase()) 	
	}
	if ( revisarDigito2(texto) ){
		return true;
	}
	return false;
}
function getExcel(INPUT){
	loading.show();
	return new Promise( function(resolve) {
		setTimeout(function(){
			$.ajax({
				url : "/recotec/json/AGRO/JSON_EXCEL",
				type : "PUT",
				data : JSON.stringify(INPUT),
				processData: false,
				contentType: false,
				dataType: "json",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					loading.hide();
					if(data.error != 0){
						alerta(data.mensaje);
					}
					resolve(data);
				},error: function(e){
					console.log(e)
				},complete: function(){
					loading.hide()
				}
			})
		}, 10);
	})
}
function Mantenedor(input){
	return new Promise( function(resolve) {
		loading.show();
		setTimeout(function(){
			$.ajax({
				url : "/recotec/json/AGRO/MANTENEDOR",
				type : "PUT",
				data : JSON.stringify(input),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					console.log(data)
					loading.hide();
					if(input.ALERTA == undefined || input.ALERTA == true){
						var a = alerta(data.mensaje);
						$(a.aceptar).click(function(){
							closeModal();
						})
					} 
					resolve(data);
				},error: function(e){
					console.log(e)
					alerta(e.textStatus);
				},complete:function(){
					loading.hide()
				}
			})
		}, 10);
	})
}
function Update(input){
	return new Promise( function(resolve) {
		loading.show();
		setTimeout(function(){
			$.ajax({
				url : "/recotec/json/AGRO/UPDATE",
				type : "PUT",
				data : JSON.stringify(input),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					loading.hide();
					if(input.ALERTA == undefined || input.ALERTA == true){
						var a = alerta(data.mensaje);
						$(a.aceptar).click(function(){
							closeModal();
						})
					}
					resolve(data);
				},error: function(e){
					console.log(e)
					alerta(e.textStatus);
				},complete:function(){
					loading.hide()
				}
			})
		}, 10);
	})
}
function getData(INPUT){
	loading.show()
	var data = "";
	var h = [];
	var fil = "";
	if(INPUT.FILTERS){
		$.each(INPUT.FILTERS[0], function(key,v){
			var obj = {}
			obj["head"] = key;
			h.push(obj);
		})
		var fil = "";
		$.each(INPUT.FILTERS, function(k,v){
			$.each(h, function(ka,va){
				fil += ka == 0 ? "?"+va.head+"="+v[va.head] : "&"+va.head+"="+v[va.head];
			})
		})
	}
	return new Promise( function(resolve) {
		setTimeout(function(){
			$.ajax({
				url: "/recotec/json/AGRO/"+INPUT.URL+fil,
				type:	"GET",
				dataType: 'json',
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					data.error != 0?console.log(data.mensaje):'';
					data = data;
					if(INPUT.ALERTA){
						if(data.error != 0){
							alerta(data.mensaje);
						}
					}
					resolve(data);
				},error: function(e){
					console.log(e)
				},complete: function(){
					loading.hide()
					return data;
				}
			})
		}, 10);
	})
}
function Select(input){
	return new Promise( function(resolve, reject) {
		loading.show();
		setTimeout(function(){
			$.ajax({
				url : "/recotec/json/AGRO/SELECT",
				type : "PUT",
				data : JSON.stringify(input),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					loading.hide();
					resolve(data);
				},error: function(e){
					console.log(e)
					alerta(e.textStatus);
					reject(e);
				},complete:function(){
					loading.hide()
				}
			})
		}, 10);
	})
}
function languageDT(){
	return{
	    "decimal":        "",
	    "emptyTable":     "No hay Informacion disponible",
	    "info":           "Mostrando _START_ a _END_ de _TOTAL_ registros",
	    "infoEmpty":      "Showing 0 to 0 of 0 entries",
	    "infoFiltered":   "(filtrado de _MAX_ registros totales)",
	    "infoPostFix":    "",
	    "thousands":      ",",
	    "lengthMenu":     "Mostrar _MENU_ registros",
	    "loadingRecords": "Cargando...",
	    "processing":     "Procesando...",
	    "search":         "Buscar:",
	    "zeroRecords":    "No se encontraron registros coincidentes",
	    "paginate": {
	        "first":      "Primero",
	        "last":       "Último",
	        "next":       "Siguiente",
	        "previous":   "Anterior"
	    },
	    "aria": {
	        "sortAscending":  ": activate to sort column ascending",
	        "sortDescending": ": activate to sort column descending"
	    }
	}
}
function getSp(INPUT){
	var data;
	return new Promise( function(resolve) {
		loading.show();
		setTimeout(function(){
			$.ajax({
				url: "/recotec/json/RECOTEC",
				type:	"PUT",
				dataType: 'json',
				data: JSON.stringify(INPUT),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					data.error != 0?console.log(data.mensaje):'';
					data = data;
					if(INPUT.ALERTA){
						if(data.error != 0){
							alerta(data.mensaje);
						}
					}
					resolve(data);
				},error: function(e){
					console.log(e)
				},complete: function(){
					loading.hide()
					return data;
				}
			})
		}, 10);
	})
}
function Delete(input){
	return new Promise( function(resolve) {
		loading.show();
		setTimeout(function(){
			$.ajax({
				url : "/recotec/json/AGRO/DELETE",
				type : "PUT",
				data : JSON.stringify(input),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					loading.hide();
					if(input.ALERTA == undefined || input.ALERTA == true){
						var a = alerta(data.mensaje);
						$(a.aceptar).click(function(){
							closeModal();
						})
					}
					resolve(data);
				},error: function(e){
					console.log(e)
					alerta(e.textStatus);
				},complete:function(){
					loading.hide()
				}
			})
		}, 10);
	})
}
function callSp(INPUT){
	if(INPUT.LOADING == undefined){
		INPUT.LOADING = true;
	}
	var data;
	return new Promise( function(resolve) {
		INPUT.LOADING?loading.show():'';
		setTimeout(function(){
			$.ajax({
				url: "/recotec/json/CALLSP",
				type:	"PUT",
				dataType: 'json',
				data: JSON.stringify(INPUT),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					data.error != 0?console.log(data.mensaje):'';
					data = data;
					if(INPUT.ALERTA){
						if(data.error != 0){
							alerta(data.mensaje);
						}
					}
					resolve(data);
				},error: function(e){
					console.log(e)
				},complete: function(){
					INPUT.LOADING?loading.hide():'';
					return data;
				}
			})
		}, 10);
	})
}
function encode_utf8(s) {
  return unescape(encodeURIComponent(s));
}
function decode_utf8(s) {
	//return s;
	return decodeURIComponent(escape(s));
}
