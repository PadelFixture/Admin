function desFiles(){
	$("#desFiles").hide();
	$("#fImport").html("Ningun archivo seleccionado");
	$("#fileImport").val("");
	
}
var tabla;
var rowObj;
var datosInput = [];
var periodo;
function fileImport(event){
	$.fn.dataTable.ext.errMode = 'none';
	console.log(name)
	var especie = $("#especie").val();
	var name = $("#fileImport").prop("files")[0].name;
	console.log(name)
	$("#bodyExcel").html("");
	$("#fImport").html(name);
	$("#desFiles").show();
	var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
        var fileData = reader.result;
        var wb = XLSX.read(fileData, {type : 'binary'});
        var datos = []
        wb.SheetNames.forEach(function(sheetName){
	        rowObj =XLSX.utils.sheet_to_row_object_array(wb.Sheets[sheetName]);
	        var jsonObj = JSON.stringify(rowObj);
	        var body = "";
	        datosInput = rowObj;
	        console.log(rowObj)
	        $.each(rowObj, function(k,v){
	        	if(v.ICCID == "89560100001056982592"){
	        		console.log(v)
	        	}
	        	
        		var tbl = [v.PERIODO, v.NOMBRE_MES, v.SUC_VENTA, v.Nombre_PDV, v.Canal, v.Region, v.Ciudad, v.Comuna, v.Jefe_de_Negocios, v.Socio, v.DESC_SEGMENTO, 
        		           v.TIPO_PRODUCTO, v.PORTABILIDAD, v.FECHA_NEGOCIO, v.NOMBRE_DIA, v.HORA_NEGOCIO, v.NUM_NEGOCIO, v.SISTEMA, v.COD_TIPONEGOCIO, v.TIPONEGOCIO, 
        		           v.GRUPO_NEGOCIO, v.Gerencia, v.Grupo_Canal, v.Cluster, v.TIPO_ZONA, v.cod_JNZ, v.CONTROLLER, v.KAM_COM, v.KAM_OP, v.Rut_Socio, v.RUT_VENDEDOR, 
        		           v.DESC_CARGO, v.RUT_CLIENTE, v["RUT REPETIDO"], v.COMISIONABLE, v.NOMBRE_CLIENTE, v.COD_GRUPOCLIENTE, v.DESC_GRUPOCLIENTE, v.COD_TIPOCLIENTE, 
        		           v.DESC_TIPOCLIENTE, v.BSCS_Nuevo, v.NRO_MOVIL, v.NEGOCIO, v.MER_ORIGEN, v.F_PAGO, v.CODI_PLAN, v.DESC_PLAN, v.SEGMENTO_PLAN, v.PRECIO_PLAN, 
        		           v.CODIGO_PRODUCTO, v.MODELO, v.PATENTE, v.IMSI, v.ICCID, v.ID_MOVIL, v.DESC_LDA, v.ID_TIPOPRODUCTO, v.DESC_GESTION, v.DESC_NEGOCIO, 
        		           v.DESC_MOVIMIENTO_ACC, v.DESC_TIPO_MOVIMIENTO, v.GRUPO_CANAL_DISTR, v.CANAL_DISTR, v.SOCIO_DISTR, v.RRHH_CLASE_PROD, v.FECH_SOLIC, 
        		           v.APP, v["SOCIO REAL"], v.PLATAFORMA, v.REVISION];
        		datos.push(tbl);
	        })
    		if(tabla){
    			tabla.destroy();
    	        $('#tbl_RendimientoVlidadr').empty(); 
    		}
    		var columnas = ["PERIODO", "NOMBRE_MES", "SUC_VENTA", "Nombre_PDV", "Canal", "Region", "Ciudad", "Comuna", "Jefe_de_Negocios", "Socio", "DESC_SEGMENTO", 
    		                "TIPO_PRODUCTO", "PORTABILIDAD", "FECHA_NEGOCIO", "NOMBRE_DIA", "HORA_NEGOCIO", "NUM_NEGOCIO", "SISTEMA", "COD_TIPONEGOCIO", "TIPONEGOCIO", 
    		                "GRUPO_NEGOCIO", "Gerencia", "Grupo_Canal", "Cluster", "TIPO_ZONA", "cod_JNZ", "CONTROLLER", "KAM_COM", "KAM_OP", "Rut_Socio", "RUT_VENDEDOR", 
    		                "DESC_CARGO", "RUT_CLIENTE", "RUT REPETIDO", "COMISIONABLE", "NOMBRE_CLIENTE", "COD_GRUPOCLIENTE", "DESC_GRUPOCLIENTE", "COD_TIPOCLIENTE", 
    		                "DESC_TIPOCLIENTE", "BSCS_Nuevo", "NRO_MOVIL", "NEGOCIO", "MER_ORIGEN", "F_PAGO", "CODI_PLAN", "DESC_PLAN", "SEGMENTO_PLAN", "PRECIO_PLAN", 
    		                "CODIGO_PRODUCTO", "MODELO", "PATENTE", "IMSI", "ICCID", "ID_MOVIL", "DESC_LDA", "ID_TIPOPRODUCTO", "DESC_GESTION", "DESC_NEGOCIO", 
    		                "DESC_MOVIMIENTO_ACC", "DESC_TIPO_MOVIMIENTO", "GRUPO_CANAL_DISTR", "CANAL_DISTR", "SOCIO_DISTR", "RRHH_CLASE_PROD", "FECH_SOLIC", "APP", 
    		                "SOCIO REAL", "PLATAFORMA", "REVISION"];
    		var finalColumn = [];
    		for(var i = 0; i < columnas.length; i++){
    			finalColumn.push({title: columnas[i]})
    		}
    		tabla = $('#tbl_RendimientoVlidadr').DataTable({
    			data: datos,
    			columns: finalColumn, 
    			autoWidth: true,
    			ordering: false,
    		});
        })
    };
    reader.readAsBinaryString(input.files[0]);
}
function guardar(){
	var input = {
		TABLE: "carga_One_Click",
		VALUES: datosInput
	};
	Mantenedor(input)
}