//var paginador = function(tabla){
//	table = tabla;
//	th = $(""+tabla.selector+" tr th");
//	var td = $(""+tabla.selector+" tr td");
//	var tr = $(""+tabla.selector+" tr");
//	var count = 0;
//	var countTh = 0;
//	th.each(function(){
//		var SearchBox = "<input type='text' id='"+countTh+"' onkeyup='filterTable(this)' class='form-control input-sm' placeholder='"+$(this).text()+"'>";
//		$(this).append(SearchBox)
//		countTh++;
//	})
//	if(td.length == 0){
//		refheshTable([]);
//		return;
//	}
//	while(count != td.length){
//		for(var i = 0; i < th.length; i++){
//			if(dataTabla[i] == undefined){
//				dataTabla[i] = [];
//			}
//			dataTabla[i].push($(td[count]).text())
//			count++;
//		}
//	}
//	for(var x = 0; x < th.length;x++){
//		for(var i = 0; i < dataTabla[x].length; i++){
//			if(json_tabla_datos[i] == undefined){
//				json_tabla_datos[i] = [];
//			}
//			json_tabla_datos[i].push(dataTabla[x][i])
//		}
//	}
//	console.log(json_tabla_datos)
//	refheshTable(json_tabla_datos)
//}
//var filterTable = function(input){
//	if(input.value == ""){
//		refheshTable(json_tabla_datos);
//		return;
//	}
//	if(!datos_filter_table_search){
//		data = json_tabla_datos;
//	}else{
//		data = datos_filter_table_search;
//	}
//	var newData = [];
//	var find_table = [];
//	var datos = dataTabla[input.id];
//	var cnt = 0;
//	var validate = true;
//	for(var i = 0; i < datos.length; i++){
//		if(datos[i].toUpperCase().indexOf(input.value.toUpperCase()) > -1){
//			find_table.push(datos[i]);
//		}
//	}
//	for(var f = 0; f < find_table.length; f++){
//		for(var i = 0; i < data.length; i++){
//			for(var x = 0; x < data[i].length;x++){
//				if(find_table[f] == data[i][x]){
//					if(newData != []){
//						for(var r = 0; r < newData.length; r++){
//							if(newData[r] == data[i]){
//								validate = false;
//							}
//						}
//						if(validate){
//							newData.push(data[i]);
//						}
//					}else{
//						newData.push(data[i]);
//					}
//				}
//			}
//		}
//		cnt++;
//	}
//	if(cnt == find_table.length){
//		refheshTable(newData);
//	}
//}
//var refheshTable = function(data){
//	datos_filter_table_search = data;
//	var body_id = table[0].tBodies[0].id;
//	var tbl = "";
//	for(var i = 0; i < data.length; i++){
//		tbl += "<tr>";
//		for(var x = 0; x < data[i].length;x++){
//			tbl += "<td>"+data[i][x]+"</td>";
//		}
//		tbl += "</tr>";
//	}
//	if(data.length == 0){
//		tbl += "<tr>";
//		tbl += "<td colspan='"+th.length+"' style='text-align: center;'>No hay Informacion disponible</td>";
//		tbl += "</tr>";
//	}
//	$("#"+body_id+"").html(tbl);
//}
//var datos_filter_table_search;
//var dataTabla = [];
//var th;
//var bodyFilter;
//var tblDatos = [];
//var table;
//var json_tabla_datos = [];
////var paginador = function(table, data){
////	var cabecera = getCabecera(data);
////	var divTable = document.createElement("div");
////	divTable.append(table.html())
////	
////	console.log(divTable)
////	buildColorPalette();
////	var id_tabla = table.id;
////	console.log(table)
////	var tBody = table[0].tBodies[0].id;
////	var longTable = $(""+table.selector+" th");
////	var valuesTd = $(""+table.selector+" tr");
////	var Tds = [];
////	console.log(longTable)
////	valuesTd.each(function(){
////		console.log(this)
////	})
////	$.each(valuesTd, function(k,v){
////		console.log(v)
////	})
////	if(count == 1){
////		for(var i = 0; i < longTable.length; i++){
////			var addThead =	"<td><input type='text' class='form-control input-sm'></td>";
////			$(longTable[i]).append(addThead)
////		}
////		count++;
////	}
////}
////function getCabecera(paramData){
////
////	var header = [];
////	$.each(paramData[0], function (key, value) {
////		//console.log(key + '==' + value);
////		var obj = {}
////		obj["headertext"] = key;
////		obj["datatype"] = "string";
////		obj["datafield"] = key;
////		header.push(obj);
////	}); 
////	return header;
////
////}
////function makeBuscadorPaginas(currColor) {
////    var button = document.createElement('span');
////    button.className = 'btn';
////    button.style.backgroundColor = "#FF8C00";
////    button.innerHTML = currColor
//////    if(i == 0){button.title = 'Sembrado'}
//////    if(i == 1){button.title = 'Listo para Cosechar'}
//////    if(i == 2){button.title = 'Seco'}
//////    if(i == 3){button.title = 'Fumigado'}
//////    if(i == 4){button.title = 'Prohibido el acceso'}
////    
////    return button;
////}
////function buildColorPalette() {
////	var buscador = document.createElement("div");
////	buscador.id = "buscadorPaginas";
////    for (var i = 0; i < paginas.length; ++i) {
////    	var currColor = paginas[i];
////	    var colorButton = makeBuscadorPaginas(currColor);
////	    buscador.appendChild(colorButton);
//////	    colorButtons[currColor] = colorButton;
////    }
////    console.log(buscador)
////    $("#simpleAgro").append(buscador)
////}
////var paginas = ['Primero', 'Anterior', '#FF8C00', 'Siguiente', 'Ultimo'];
////var count = 1;
////var filter = null;
////var records = null;
////var registers = null;
////var paginate = function(){
////	
////};