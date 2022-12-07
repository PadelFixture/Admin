package lib.classSW;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import SWDB.impexp_trabajador;
import SWDB.sw_NominaAnticiposDB;
import lib.db.ConnectionDB;

import wordCreator.utils;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExportarCSV {
	
    public static String truncateCadena(String str, int maxWidth) {
        if (null == str) {
            return null;
        }
    
        if (str.length() <= maxWidth) {
            return str;
        }

        return str.substring(0, maxWidth);
    }

	 private static final Logger LOGGER = Logger.getLogger("newexcel.ExcelOOXML");
		
public static boolean insertTablaSwNomina (NominaAnticipos r) throws Exception{
			Statement ps = null;
		
			String sql="";
			ConnectionDB db = new ConnectionDB();
			
			try{
						
				sql = "INSERT INTO sw_nomina (total_monto,fecha_pago,periodo,concepto,id_concepto,idsociedad)";
				sql += "VALUES ("+r.getTotalmonto()+",'"+r.getFechaanticipo()+"',"+r.getPeriodo()+",'"+r.getNombre_nomina()+"',"+r.getIdconcepto()+","+r.getEmpresa()+")";
						
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				
				
				ps.execute(sql);
			
				
				return true;
			}catch (SQLException e){
				System.out.println("Error: "+ e.getMessage());
			}catch (Exception e){
				System.out.println("Error: "+ e.getMessage());
			}finally {
				ps.close();
			
				db.close();
			}
			return false;
		}
//////////////////////update////////////////////////////////////////////////////
public static boolean updateTablasw_asignacionAnticipos (NominaAnticipos r) throws Exception{
	Statement ps2 = null;
	String sql2 = "";
	
	ConnectionDB db = new ConnectionDB();
	try{
		
	sql2 = "UPDATE sw_asignacionAnticipos SET estado = 1, "
			+ "id_nomina = (select max(id_nomina) from sw_nomina), "
			+ "nombre_autorizada = '"+r.getNombre_autorizada()+"',"
			+ " rut_autorizada = '"+r.getRut_autorizada()+"' WHERE id = "+r.getIdtabla_sw_asignacionAnticipos()+"";
	
	
		
	
	ps2 = db .conn.prepareStatement(sql2);
	ps2.execute(sql2);
	
return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
	
		ps2.close();
		db.close();
	}
	return false;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean getscvMAsEmail (ArrayList<NominaAnticipos> row) throws Exception{
    
	 
	String ruta = utils.csvDetalleNomina();
	Date fechaActual = new Date();
	


	//Formateando la fecha:
	DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
	DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
	
	String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
	String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
	
	String horaf = formatoHora.replaceAll("[:]", "");;
   

	String nombre_nominaT = "";

	
	for(NominaAnticipos emp1 : row){
		
		 nombre_nominaT  = emp1.getNombre_nomina();
		      		  
	}
  String nombreArchivo = nombre_nominaT+horaf+".txt";
  String outputFile = ruta+nombreArchivo;



boolean alreadyExists = new File(outputFile).exists();

if(alreadyExists){
    File ArchivoEmpleados = new File(outputFile);
    ArchivoEmpleados.delete();
}        





try {
	int fechaPago = 0;
	int totalMonto = 0;
	int idEmpresa = 0;
	String codTrabajador = "";
	String nombre_nomina = "";
	String fecha_pago_excel = "";
	String empresatext = "";
    String periodo_string = "";
	
	for(NominaAnticipos emp1 : row){
		 fechaPago = emp1.getFechaanticipoint();
		 totalMonto = emp1.getTotalmonto();
		 idEmpresa = emp1.getEmpresa();
		 codTrabajador   = emp1.getCod_trabajador_string();
		 nombre_nomina  = emp1.getNombre_nomina();
		 fecha_pago_excel = emp1.getFechaanticipo();
		 empresatext = emp1.getEmpresatext();
		 periodo_string = emp1.getPeriodoString();       		  
	}
	
//	obtener rut empresa DB
	NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idEmpresa);
	
			String rutEmpresa1 = ru.getRut();
			String rutEmpresa12 = rutEmpresa1.toUpperCase();
			String rut1 = rutEmpresa12.replaceAll("[.]", "");
			String rutINT = rut1.replaceAll("[-]", "");
			

	//largo formato monto
	int length = 11;
	String SumaMonto = String.format("%0" + length + "d", totalMonto);
	
//	String nuevalinea = System.getProperty("line.separator");
	String nuevalinea = "\r\n";
	String rutEmpresa = rutINT;
    
	NominaAnticipos all = sw_NominaAnticiposDB.getALLSociedad(idEmpresa);
	String Numero_convenio = all.getNumeroconvenio();

	String Numero_nomina = all.getNumeronomina();
	
	String Tipo_nomina = all.getTiponomina();
	
	
	
	String numeroConvenio = Numero_convenio;
    String numeroNomina = Numero_nomina;
    String tipoNomina = Tipo_nomina;
    
    
    String nombre = String.format("%-20s", "010"+rutEmpresa+numeroConvenio+numeroNomina);
    
    String nombrenom = truncateCadena(nombre_nomina,25);
	nombre += String.format("%-25s", nombrenom);
	 int fechaPago2 = fechaPago;
	 
	 nombre += String.format("%-26s", "01"+fechaPago2+SumaMonto+"00");
	 nombre += String.format("%-325s", "N");
	 nombre += tipoNomina;
	 nombre += nuevalinea;
	 
	 System.out.println("aqui estoy 1");
	

	 int totalMonto1 = 0;
	 int totalMontoPagoEfectivo = 0;
	 int contador = 0;
	 int contador2 = 0;
	 int total_registro = 0;
	 
	 for(NominaAnticipos emp : row)
	 {
    	String monto = emp.getMonto_ingresado_string();
		
		int length2 = 14;
		int length_de_11 = 11;
		totalMonto1 = Integer.parseInt(monto);
		
		
		totalMontoPagoEfectivo = Integer.parseInt(SumaMonto);
		
		String SumaMonto1 = String.format("%0" + length2 + "d", totalMonto1);
		String SumaMonto2 = String.format("%0" + length_de_11 + "d", totalMontoPagoEfectivo);
		
		int id_banco = emp.getIdbanco();
		// obtener codigo SBIF
		NominaAnticipos COD_SBIF = sw_NominaAnticiposDB.getCodigoSbif(id_banco);
		/// cambiar idprensa buscar por la llave i no el id 
		String codigo_SBIF = COD_SBIF.getCodigosbif();
		
		String codTrabajador2 = "";
		
		 codTrabajador2   = emp.getCod_trabajador_string();
		
		NominaAnticipos trab_n_cuenta2 = sw_NominaAnticiposDB.getNumeroCuenta(codTrabajador2);
		String N_cuenta_Trab2 = trab_n_cuenta2.getNumero_cuenta_string();
  
    String rut = emp.getRut();
    String nombreTrabajador = emp.getNombre();
    String tipodecuentaTrabajador = emp.getIdtipocuentastring();
    String direccion_trabajador = emp.getDireccion();
    String oficina = emp.getOficina_string();
    
    int numEntero = Integer.parseInt(tipodecuentaTrabajador);
  
    String nombreAutorizada = emp.getNombre_autorizada();
    String rutAutorizado = emp.getRut_autorizado_replace();
    
 // obtener numero cuenta trabajador
 	NominaAnticipos trab_n_cuenta = sw_NominaAnticiposDB.getNumeroCuenta(codTrabajador2);
 	String N_cuenta_Trab = trab_n_cuenta.getNumero_cuenta_string();
 	int idTipoCuenta = trab_n_cuenta.getIdtipocuenta();
 	
 	int cantidadDigito = String.valueOf(idTipoCuenta).length();
 	
 	String digitoString = "";
 	if(cantidadDigito == 1){
 		digitoString = "0"+idTipoCuenta;
 	}else{
 		digitoString = String.valueOf(idTipoCuenta);
 	}
    
 	 if(numEntero == 2 && contador == 0){
     nombre += nuevalinea;
     System.out.println("aqui estoy 2");
   	 nombre += String.format("%-17s", "020"+rutEmpresa+ numeroConvenio);
   	 }else if (numEntero != 2){
//   		 nombre += nuevalinea;
   		System.out.println("aqui estoy 3");
   	   	 nombre += String.format("%-17s", "020"+rutEmpresa+ numeroConvenio);
   	 }
 	 
   	 
   	 // Vale Vista entregado en Meson del Banco de Chile
   	 if(numEntero == 2 && contador == 0){
   	
   		nombre += String.format("%-8s", "10001"+digitoString+"0");
   		nombre += String.format("%-9s",  rutAutorizado);
   		nombre += String.format("%-60s",  nombreAutorizada);
   		nombre += String.format("%-73s", "0"+direccion_trabajador+"");
      	nombre += String.format("%-27s", "BC");
      	nombre += String.format("%-135s", oficina+SumaMonto2+"00"); // monto
     
    	contador = contador + 1;
    	contador2 = contador2 + 1;
   	 } 
   	 // SERVIPAG 
   	 else if(numEntero == 12){ 
   		nombre += String.format("%-8s", "10001"+digitoString+"0");
   		nombre += String.format("%-9s",  rut );
   		nombre += String.format("%-60s",  nombreTrabajador);	
   		nombre += String.format("%-73s", "0"+direccion_trabajador+"");
      	nombre += String.format("%-27s", "D6");
      	nombre += String.format("%-135s", SumaMonto1+"00"); // monto
       	}else{
       		
       		if(contador2 == 1){}else{
       			nombre += String.format("%-8s", "10001"+digitoString+"0");
       	   		nombre += String.format("%-9s",  rut );
       	   		nombre += String.format("%-60s",  nombreTrabajador);
   		nombre += String.format("%-73s", "0"+direccion_trabajador+"");
      	nombre += String.format("%-27s", "D6"+codigo_SBIF + N_cuenta_Trab2);
      	nombre += String.format("%-134s", SumaMonto1+"00"); // monto
      	System.out.println("////////////////////////////////////////");
      	
    	}
      	
   	 }
   	 
   	 if(numEntero == 2 && contador == 1){  
   	 
   	 nombre += String.format("%-71s", "0000"); 
//   	 nombre += nuevalinea;
   	System.out.println("aqui estoy 4");
   	contador = contador + 1;
   	 }else if (numEntero != 2){
   		 nombre += String.format("%-71s", "0000"); 
   	   	 nombre += nuevalinea;
   	  System.out.println("aqui estoy 5");
   	 }
   	total_registro = total_registro +1;
    }
  //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
    File f= new File(outputFile);
    FileUtils.writeStringToFile(f,nombre, "ISO-8859-1");
  	//FileWriter escribir=new FileWriter(outputFile,true);

  	//Escribimos en el archivo con el metodo write 
  	//escribir.write(nombre);

  	//Cerramos la conexion
  	//escribir.close();
  	NominaAnticipos var = impexp_trabajador.getUltimoIdSW();
  	int ultimoID = var.getIdtablaswnomina();
  	
	String ruta2 = outputFile;
	
	impexp_trabajador.updateNOmbreArchivoSW_nomina(ruta2,ultimoID);
	
	String[] parts = fecha_pago_excel.split("-");
	
	String fecha_pago_final = parts[2]+"-"+parts[1]+"-"+parts[0];
//////////////////////EXCEL///////////////////////////

//Creamos el archivo donde almacenaremos la hoja
//de calculo, recuerde usar la extension correcta,
//en este caso .xlsx
	
	 
File archivo = new File(nombre_nomina+horaf+".xlsx");

//Creamos el libro de trabajo de Excel formato OOXML
Workbook workbook = new XSSFWorkbook(); 

//La hoja donde pondremos los datos
Sheet pagina = workbook.createSheet(""+nombre_nomina+"");
pagina.getPrintSetup().setLandscape(true);
pagina.setFitToPage(true);
PrintSetup ps = pagina.getPrintSetup();
ps.setFitWidth( (short) 1);
ps.setFitHeight( (short) 0);

//Creamos el estilo paga las celdas del encabezado
CellStyle style = workbook.createCellStyle();
CellStyle style2 = workbook.createCellStyle();
CellStyle style3 = workbook.createCellStyle();
CellStyle combined2 = workbook.createCellStyle();
CellStyle combined = workbook.createCellStyle();
combined.setAlignment(CellStyle.ALIGN_RIGHT);
combined.setBorderTop(CellStyle.BORDER_DOUBLE);
combined2.setAlignment(CellStyle.ALIGN_CENTER);
style3.setBorderTop(CellStyle.BORDER_DOUBLE);
style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
style.setFillPattern(CellStyle.SOLID_FOREGROUND);
style2.setAlignment(CellStyle.ALIGN_RIGHT);


String empresaMys = empresatext.toUpperCase();
String[] titulos = {empresaMys};

//Creamos una fila en la hoja en la posicion 0
Row fila = pagina.createRow(0);
//Indicamos el estilo que deseamos usar en la celda, en este caso el unico que hemos creado
Cell celda1 = fila.createCell(0);

celda1.setCellValue(titulos[0]);

nombre_nomina = nombre_nomina.toUpperCase();
rutEmpresa1 = rutEmpresa1.toUpperCase();


double amount = totalMonto;    
String totalf = String.format("%,.0f", amount);  


fila = pagina.createRow(1);
String[] TituloFiltros = {""+rutEmpresa1+"","","","","","FECHA DE PAGO",""+fecha_pago_final+""};
for(int i = 0; i < TituloFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(TituloFiltros[i]);
}


String[] PeriodoSplit = periodo_string.split("-");
//
int meses;
//
String mesPalabra = "";
meses=Integer.parseInt(PeriodoSplit[1]);
 
switch ( meses ){
case 1: mesPalabra = "ENERO"; break;
case 2: mesPalabra = "FEBRERO"; break;
case 3: mesPalabra = "MARZO"; break;
case 4: mesPalabra = "ABRIL"; break;
case 5: mesPalabra = "MAYO"; break;
case 6: mesPalabra = "JUNIO"; break;
case 7: mesPalabra = "JULIO"; break;
case 8: mesPalabra = "AGOSTO"; break;
case 9: mesPalabra = "SEPTIEMBRE"; break;
case 10: mesPalabra = "OCTUBRE"; break;
case 11: mesPalabra = "NOVIEMBRE"; break;
case 12: mesPalabra = "DICIEMBRE"; break;
default: mesPalabra = ""; break;
}

fila = pagina.createRow(2);
String[] DetalleFiltros = {"N° DE REGISTROS",""+total_registro+"","","","","PERIODO PROCESO",""+mesPalabra+" "+PeriodoSplit[0]+""};
for(int i = 0; i < DetalleFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(DetalleFiltros[i]);
}


fila = pagina.createRow(3);
String[] DetalleTotal = {"","","","","","TOTAL",""+totalf+""};
for(int i = 0; i < DetalleTotal.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(DetalleTotal[i]);
if(i == 6){
	celda.setCellStyle(style2);
}
}


fila = pagina.createRow(4);

Cell celdat = fila.createCell((short) 1);
celdat.setCellValue("NOMINA PAGO DE ANTICIPOS");
celdat.setCellStyle(combined2);
pagina.addMergedRegion(CellRangeAddress.valueOf("B5:D5"));


fila = pagina.createRow(5);
fila = pagina.createRow(6);

String[] titulo6 = {"CODIGO","NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO"};

for(int i = 0; i < titulo6.length; i++) 
{

Cell celda = fila.createCell(i);
celda.setCellStyle(style3); 
celda.setCellValue(titulo6[i]);
}

fila = pagina.createRow(7);

int numeroFor = 7;
int contador5 = 0;


String monto = "";
String nombre_trabajador = "";
String rut_trabajador = "";
String medio_de_pago = "";
String banco_trabajador = "";
String numero_cuenta = "";
String nombre_oficina = "";
String nombre_huerto = "";
String cod_trabaj = "";

for(NominaAnticipos emplista : row){
	
	cod_trabaj = emplista.getCod_trabajador_string();
	monto = emplista.getMonto_ingresado_string();
	nombre_trabajador = emplista.getNombre();
	rut_trabajador = emplista.getRut();
	medio_de_pago = emplista.getNombretipocuenta();
	banco_trabajador = emplista.getNombrebanco();
	numero_cuenta = emplista.getNumero_cuenta_string();
	String rutfinali = utils.FormatearRUT(rut_trabajador);
	nombre_oficina = emplista.getNombre_oficina_string();
	
	nombre_huerto = emplista.getNombrehuerto();
	nombre_trabajador = nombre_trabajador.toUpperCase();
	rutfinali = rutfinali.toUpperCase();
	medio_de_pago = medio_de_pago.toUpperCase();
	
	nombre_oficina = nombre_oficina.toUpperCase();
	

	
	double amount_monto = Integer.parseInt(monto);   
	String total_monto = String.format("%,.0f", amount_monto);  
	
	if(nombre_oficina == null){
		nombre_oficina = "";
	}
	
	if(banco_trabajador != null){
		banco_trabajador = emplista.getNombrebanco().toUpperCase();
	}else{
		banco_trabajador = "";
	}

	if(numero_cuenta == null){
		numero_cuenta = "";
	}
	
	String[] nombre3 = nombre_trabajador.split(" ");
	String nombre4 = "";
	
	if(nombre3.length == 3){
		 nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2];
	}
	else if(nombre3.length == 2){
		 nombre4 = nombre3[0] +" "+ nombre3[1];
	}
	
	else if (nombre3.length == 4){
		nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3];
	}
	else if (nombre3.length == 1){
		nombre4 = nombre3[0];
	}
	else if (nombre3.length == 5){
		nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4];
	}
	else if (nombre3.length == 6){
		nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5];
	}
	else if (nombre3.length == 7){
		nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5]+" "+ nombre3[6];
	}
	 
	String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+nombre_huerto+""};
	
		Cell celda8 = fila.createCell(0);
		if(numeroFor == 7){celda8.setCellStyle(style3);}
		celda8.setCellValue(titulo7[0]);
	
		Cell celda = fila.createCell(1);
		if(numeroFor == 7){celda.setCellStyle(style3);}
		celda.setCellValue(titulo7[1]);
		
		Cell celda2 = fila.createCell(2);
		if(numeroFor == 7){celda2.setCellStyle(style3);}
		celda2.setCellValue(titulo7[2]);
		
		Cell celda3 = fila.createCell(3);
		if(numeroFor == 7){celda3.setCellStyle(style3);}
		celda3.setCellValue(titulo7[3]);
		
		Cell celda4 = fila.createCell(4);
		if(numeroFor == 7){celda4.setCellStyle(style3);}
		celda4.setCellValue(titulo7[4]);
		
		Cell celda5 = fila.createCell(5);
		if(numeroFor == 7){celda5.setCellStyle(style3);}
		celda5.setCellValue(titulo7[5]);
		
		Cell celda6 = fila.createCell(6);
		celda6.setCellValue(titulo7[6]);
		
		if(numeroFor == 7){celda6.setCellStyle(combined);}
		else{
		celda6.setCellStyle(style2); 
		}
		Cell celda7 = fila.createCell(7);
		if(numeroFor == 7){celda7.setCellStyle(style3);}
		celda7.setCellValue(titulo7[7]);
		
		
		

	numeroFor = numeroFor +1;
	fila = pagina.createRow(numeroFor);
	
	
}


pagina.autoSizeColumn(1);
pagina.autoSizeColumn(2);
pagina.autoSizeColumn(3);
pagina.autoSizeColumn(4);
pagina.autoSizeColumn(5);
pagina.autoSizeColumn(6);
pagina.autoSizeColumn(7);
pagina.autoSizeColumn(8);
pagina.autoSizeColumn(9);
pagina.autoSizeColumn(10);


//
//arrayObjetos[0]=new Empleado("Fernando", "Ureña", 23, 1000);
//arrayObjetos[1]=new Empleado("Epi", "Dermis", 30, 1500);
//arrayObjetos[2]=new Empleado("Blas", "Femia", 25, 1200);
//
//String[] titulo7 = {"Jose rodriguez","16.825.856-9","vale vista","alto las condes","", "","290.000"};
//for(int i = 0; i < titulo7.length; i++) 
//{
//
//
//Cell celda = fila.createCell(i);
//celda.setCellValue(titulo7[0]);
//}



//
//Double[] datos = {1.0, 10.0, 45.5, 25.50}; 
//
//
//
//
////Ahora creamos una fila en la posicion 1
//fila = pagina.createRow(5);
//
////Y colocamos los datos en esa fila
//for(int i = 0; i < datos.length; i++) {
//// Creamos una celda en esa fila, en la
//// posicion indicada por el contador del ciclo
//Cell celda = fila.createCell(i);
//
//celda.setCellValue( datos[i] );        
//}

//Ahora guardaremos el archivo

/////////////////////END EXEL//////////////////////////
// Creamos el flujo de salida de datos,
// apuntando al archivo donde queremos 
// almacenar el libro de Excel


FileOutputStream salida = new FileOutputStream(ruta+archivo);

String ruta3 = ruta+archivo;
impexp_trabajador.updateNOmbreArchivoSW_nomina_excel(ruta3,ultimoID);

// Almacenamos el libro de 
// Excel via ese 
// flujo de datos
workbook.write(salida);

// Cerramos el libro para concluir operaciones
workbook.close();

LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
}
catch (FileNotFoundException ex) {
    LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
} 
catch (IOException e) {
    e.printStackTrace();
    LOGGER.log(Level.SEVERE, "Error de entrada/salida");
    return false;
}
return true;
//return null;

}

public static boolean insertTablaSwNominaFiniquito (NominaAnticipos r) throws Exception{
	Statement ps = null;

	String sql="";
	ConnectionDB db = new ConnectionDB();
	
	try{
		sql = "INSERT INTO sw_nomina (total_monto,fecha_pago,periodo,concepto,id_concepto,idsociedad)";
		sql += "VALUES ("+r.getMontomenosretenciones()+",'"+r.getFechaanticipo()+"',"+r.getPeriodo()+",'"+r.getNombre_nomina()+"',"+r.getIdconcepto()+","+r.getEmpresa()+")";
						
		ps = db.conn.prepareStatement(sql);
		ps.execute(sql);
		
		return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		ps.close();
	
		db.close();
	}
	return false;
}
//////////////////////update////////////////////////////////////////////////////
public static boolean updateTablasw_finiquito(NominaAnticipos r) throws Exception {
		Statement ps2 = null;
		String sql2 = "";

		ConnectionDB db = new ConnectionDB();
		try {

			sql2 = "UPDATE sw_finiquitos SET estado_finiquito = 1,"
					+ " id_nomina = (select max(id_nomina) from sw_nomina),"
					+ "nombre_autorizada = '"+r.getNombre_autorizada()+"',"
					+ " rut_autorizada = '"+r.getRut_autorizada()+"' , retenciones = "+r.getRetencion()+"  WHERE id_finiquito = "
					+ r.getIdtablafiniquito() + "";
			
		
			ps2 = db.conn.prepareStatement(sql2);
			ps2.execute(sql2);
         
			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			ps2.close();
			db.close();
		}
		return false;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean getTxtMAsEmailFiniquitos(ArrayList<NominaAnticipos> row) throws Exception {

		String ruta = utils.FiniquitoTxt();
		Date fechaActual = new Date();
	
		//Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		
		String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
		String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
		
		String horaf = formatoHora.replaceAll("[:]", "");;
	   
		
		String nombre_nominaT = "";
		
		
		for(NominaAnticipos emp1 : row){
			 
			 nombre_nominaT  = emp1.getNombre_nomina();
			 
			        		  
		}
		String nombreArchivo = nombre_nominaT+horaf+".txt";
		String outputFile = ruta+nombreArchivo;



	boolean alreadyExists = new File(outputFile).exists();

	if(alreadyExists){
	    File ArchivoEmpleados = new File(outputFile);
	    ArchivoEmpleados.delete();
	}        





	try {
		int fechaPago = 0;
		int totalMonto = 0;
		int idEmpresa = 0;
		String codTrabajador = "";
		String nombre_nomina = "";
		String fecha_pago_excel = "";
		String empresatext = "";
	    String periodo_string = "";
	    int retencion = 0;
	    int monto_menos_retencion = 0;
		
		for(NominaAnticipos emp1 : row){
			 fechaPago = emp1.getFechaanticipoint();
			 totalMonto = emp1.getTotalmonto();
			 retencion =  emp1.getRetencion();
			 idEmpresa = emp1.getEmpresa();
			 codTrabajador   = emp1.getCod_trabajador_string();
			 nombre_nomina  = emp1.getNombre_nomina();
			 fecha_pago_excel = emp1.getFechaanticipo();
			 empresatext = emp1.getEmpresatext();
			 periodo_string = emp1.getPeriodoString(); 
			 monto_menos_retencion = emp1.getMontomenosretenciones();
			 
			        		  
		}
		
//		obtener rut empresa DB
		NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idEmpresa);
		
				String rutEmpresa1 = ru.getRut();
				String rutEmpresa12 = rutEmpresa1.toUpperCase();
				String rut1 = rutEmpresa12.replaceAll("[.]", "");
				String rutINT = rut1.replaceAll("[-]", "");
				

		//largo formato monto
		int length = 11;
		String SumaMonto = String.format("%0" + length + "d", monto_menos_retencion);
		
//		String nuevalinea = System.getProperty("line.separator");
		String nuevalinea = "\r\n";
		String rutEmpresa = rutINT;
	    
		NominaAnticipos all = sw_NominaAnticiposDB.getALLSociedad(idEmpresa);
		String Numero_convenio = all.getNumeroconvenio();
		String Numero_nomina = all.getNumeronomina();
		String Tipo_nomina = all.getTiponomina();
		
		String numeroConvenio = Numero_convenio;
	    String numeroNomina = Numero_nomina;
	    
	    // tipo de nomina renuneraciones y generico
	    String tipoNomina = "0101";
	    

	    String nombre = String.format("%-20s", "010"+rutEmpresa+numeroConvenio+numeroNomina);
	    
	    String nombrenom = truncateCadena(nombre_nomina,25);
		nombre += String.format("%-25s", nombrenom);
		int fechaPago2 = fechaPago;
		 
		 
		nombre += String.format("%-26s", "01"+fechaPago2+SumaMonto+"00");
		nombre += String.format("%-325s", "N");
		nombre += tipoNomina;
		nombre += nuevalinea;
		

		int totalMonto1 = 0;
		int totalMontoPagoEfectivo = 0;
		int contador = 0;
		int contador2 = 0;
		int total_registro = 0;
	    for(NominaAnticipos emp : row)
	    {
	    	
	    	String monto = emp.getMonto_ingresado_string();
			int length2 = 14;
			int length_de_11 = 11;
			totalMonto1 = Integer.parseInt(monto);
			//totalMonto1 = Integer.parseInt(monto) - Integer.parseInt(""+retencion) ;
			
			totalMontoPagoEfectivo = Integer.parseInt(SumaMonto);
			
			String SumaMonto1 = String.format("%0" + length2 + "d", totalMonto1);
			String SumaMonto2 = String.format("%0" + length_de_11 + "d", totalMontoPagoEfectivo);
			String SumaMonto3 = String.format("%0" + length_de_11 + "d", totalMonto1);
			
			int id_banco = emp.getIdbanco();
			// obtener codigo SBIF
			NominaAnticipos COD_SBIF = sw_NominaAnticiposDB.getCodigoSbif(id_banco);
			/// cambiar idprensa buscar por la llave i no el id 
			String codigo_SBIF = COD_SBIF.getCodigosbif();
			
			String codTrabajador2 = "";
			
			 codTrabajador2   = emp.getCod_trabajador_string();
			
			NominaAnticipos trab_n_cuenta2 = sw_NominaAnticiposDB.getNumeroCuenta(codTrabajador2);
			String N_cuenta_Trab2 = trab_n_cuenta2.getNumero_cuenta_string();
	  
	    String rut = emp.getRut();
	    String nombreTrabajador = emp.getNombre();
	    String tipodecuentaTrabajador = emp.getIdtipocuentastring();
	    String direccion_trabajador = emp.getDireccion();
	    String oficina = emp.getOficina_string();
	    int valorTipoCuenta = emp.getValor_select_tipo_cuenta();
	    
	    int numEntero = Integer.parseInt(tipodecuentaTrabajador);
	 
	    String nombreAutorizada = emp.getNombre_autorizada();
	    String rutAutorizado = emp.getRut_autorizado_replace();
	    
	 // obtener numero cuenta trabajador
	 	NominaAnticipos trab_n_cuenta = sw_NominaAnticiposDB.getNumeroCuenta(codTrabajador2);
	 	String N_cuenta_Trab = trab_n_cuenta.getNumero_cuenta_string();
	 	int idTipoCuenta = trab_n_cuenta.getIdtipocuenta();
	 	
	 	int cantidadDigito = String.valueOf(idTipoCuenta).length();
	 	
	 	String digitoString = "";
	 	if(cantidadDigito == 1){
	 		digitoString = "0"+idTipoCuenta;
	 	}else{
	 		digitoString = String.valueOf(idTipoCuenta);
	 	}
	    
	 	 if(numEntero == 2 && contador == 0){
	     nombre += nuevalinea;
	   	 nombre += String.format("%-17s", "020"+rutEmpresa+ numeroConvenio);
	   	 }else if (numEntero != 2){
	   		 nombre += nuevalinea;
	   	   	 nombre += String.format("%-17s", "020"+rutEmpresa+ numeroConvenio);
	   	 }
	 	
	 	// IF PRINCIPAL PARA SABER SI ESTA SELECCIONADO VALE VISTA A EMPRESA
	 	if(valorTipoCuenta == 4){
	 		
	 		nombre += String.format("%-8s", "10001040");
	   		nombre += String.format("%-9s",  rut );
	   		nombre += String.format("%-60s",  nombreTrabajador);	
	   		nombre += String.format("%-73s", "0");
	      	nombre += String.format("%-27s", "BC");
	      	nombre += String.format("%-135s", "000"+SumaMonto3+"00"); // monto
	      	
	      	 nombre += String.format("%-71s", "0000"); 
	   	   	 nombre += nuevalinea;
	   	   	 
	   		total_registro = total_registro +1;
	   	   	 
	   	   	 System.out.println("aqui estoy ahora");
	 	 }else{
	 		System.out.println("nunca estoy ahora");
	 		// Vale Vista entregado en Meson del Banco de Chile
		   	 if(numEntero == 2 && contador == 0){
		   		
		   		nombre += String.format("%-8s", "10001"+digitoString+"0");
		   		nombre += String.format("%-9s",  rutAutorizado);
		   		nombre += String.format("%-60s",  nombreAutorizada);
		   		nombre += String.format("%-73s", "0"+direccion_trabajador+"");
		      	nombre += String.format("%-27s", "BC");
		      	nombre += String.format("%-135s", oficina+SumaMonto2+"00"); // monto
		      	System.out.println("pase por aqui");
		    	contador = contador + 1;
		    	contador2 = contador2 + 1;
		   	 } 
		   	 // SERVIPAG 
		   	 else if(numEntero == 12){ 
		   		nombre += String.format("%-8s", "10001"+digitoString+"0");
		   		nombre += String.format("%-9s",  rut );
		   		nombre += String.format("%-60s",  nombreTrabajador);	
		   		nombre += String.format("%-73s", "0"+direccion_trabajador+"");
		      	nombre += String.format("%-27s", "D6");
		      	nombre += String.format("%-135s", SumaMonto1+"00"); // monto
		       	}else{
		       		
		       		if(contador2 == 1){}else{
		   		
		   		nombre += String.format("%-8s", "10001"+digitoString+"0");
		   		nombre += String.format("%-9s",  rut );
		   		nombre += String.format("%-60s",  nombreTrabajador);	
		   		nombre += String.format("%-73s", "0"+direccion_trabajador+"");
		      	nombre += String.format("%-27s", "D6"+codigo_SBIF + N_cuenta_Trab2);
		      	nombre += String.format("%-134s", SumaMonto1+"00"); // monto
		      	System.out.println("////////////////////////////////////////");
		      	System.out.println(N_cuenta_Trab2);
		      	System.out.println(SumaMonto1);
		    	System.out.println(digitoString);
		    	}
		       	
		      	
		   	 }
		   	 
		   	 if(numEntero == 2 && contador == 1){  
		   	 
		   	 nombre += String.format("%-71s", "0000"); 
		   	 nombre += nuevalinea;
		   	contador = contador + 1;
		   	 }else if (numEntero != 2){
		   		 nombre += String.format("%-71s", "0000"); 
		   	   	 nombre += nuevalinea;
		   	 }
		   	total_registro = total_registro +1;
	 	 }
	   	 
	   	 
	    }
	  //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
	    File f= new File(outputFile);
	    FileUtils.writeStringToFile(f,nombre, "ISO-8859-1");
	  	//FileWriter escribir=new FileWriter(outputFile,true);

	  	//Escribimos en el archivo con el metodo write 
	  	//escribir.write(nombre);

	  	//Cerramos la conexion
	  	//escribir.close();
	  	NominaAnticipos var = impexp_trabajador.getUltimoIdSW();
	  	int ultimoID = var.getIdtablaswnomina();
	  	
		String ruta2 = outputFile;
		
		impexp_trabajador.updateNOmbreArchivoSW_nomina(ruta2,ultimoID);
		
		String[] parts = fecha_pago_excel.split("-");
		
		String fecha_pago_final = parts[2]+"-"+parts[1]+"-"+parts[0];
	//////////////////////EXCEL///////////////////////////

	//Creamos el archivo donde almacenaremos la hoja
	//de calculo, recuerde usar la extension correcta,
	//en este caso .xlsx
		
		 
	File archivo = new File(nombre_nomina+horaf+".xlsx");

	//Creamos el libro de trabajo de Excel formato OOXML
	Workbook workbook = new XSSFWorkbook(); 

	//La hoja donde pondremos los datos
	Sheet pagina = workbook.createSheet(""+nombre_nomina+"");
	pagina.getPrintSetup().setLandscape(true);
	pagina.setFitToPage(true);
	PrintSetup ps = pagina.getPrintSetup();
	ps.setFitWidth( (short) 1);
	ps.setFitHeight( (short) 0);

	//Creamos el estilo paga las celdas del encabezado
	CellStyle style = workbook.createCellStyle();
	CellStyle style2 = workbook.createCellStyle();
	CellStyle style3 = workbook.createCellStyle();
	CellStyle combined2 = workbook.createCellStyle();
	CellStyle combined = workbook.createCellStyle();
	combined.setAlignment(CellStyle.ALIGN_RIGHT);
	combined.setBorderTop(CellStyle.BORDER_DOUBLE);
	combined2.setAlignment(CellStyle.ALIGN_CENTER);
	style3.setBorderTop(CellStyle.BORDER_DOUBLE);
	style2.setAlignment(CellStyle.ALIGN_RIGHT);
	//Indicamos que tendra un fondo azul aqua
	//con patron solido del color indicado
	style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
    
	String empresaMys = empresatext.toUpperCase();
	String[] titulos = {empresaMys};
	//Creamos una fila en la hoja en la posicion 0
	Row fila = pagina.createRow(0);
	//Indicamos el estilo que deseamos usar en la celda, en este caso el unico que hemos creado
	Cell celda1 = fila.createCell(0);
	celda1.setCellValue(titulos[0]);

	nombre_nomina = nombre_nomina.toUpperCase();
	rutEmpresa1 = rutEmpresa1.toUpperCase();


	double amount = monto_menos_retencion;    
	String totalf = String.format("%,.0f", amount);  
	
	fila = pagina.createRow(1);
	String[] TituloFiltros = {""+rutEmpresa1+"","","","","","FECHA DE PAGO",""+fecha_pago_final+""};
	for(int i = 0; i < TituloFiltros.length; i++) 
	{
	Cell celda = fila.createCell(i);
	celda.setCellValue(TituloFiltros[i]);
	}

	String[] PeriodoSplit = periodo_string.split("-");
	//
	int meses;
	//
	String mesPalabra = "";
	meses=Integer.parseInt(PeriodoSplit[1]);
	 
	switch ( meses ){
	case 1: mesPalabra = "ENERO"; break;
	case 2: mesPalabra = "FEBRERO"; break;
	case 3: mesPalabra = "MARZO"; break;
	case 4: mesPalabra = "ABRIL"; break;
	case 5: mesPalabra = "MAYO"; break;
	case 6: mesPalabra = "JUNIO"; break;
	case 7: mesPalabra = "JULIO"; break;
	case 8: mesPalabra = "AGOSTO"; break;
	case 9: mesPalabra = "SEPTIEMBRE"; break;
	case 10: mesPalabra = "OCTUBRE"; break;
	case 11: mesPalabra = "NOVIEMBRE"; break;
	case 12: mesPalabra = "DICIEMBRE"; break;
	default: mesPalabra = ""; break;
	}
	
	fila = pagina.createRow(2);
	String[] DetalleFiltros = {"N° DE REGISTROS",""+total_registro+"","","","","PERIODO PROCESO",""+mesPalabra+" "+PeriodoSplit[0]+""};
	for(int i = 0; i < DetalleFiltros.length; i++) 
	{
	Cell celda = fila.createCell(i);
	celda.setCellValue(DetalleFiltros[i]);
	}
	
	fila = pagina.createRow(3);
	String[] DetalleTotal = {"","","","","","TOTAL",""+totalf+""};
	for(int i = 0; i < DetalleTotal.length; i++) 
	{
	Cell celda = fila.createCell(i);
	celda.setCellValue(DetalleTotal[i]);
	if(i == 6){
		celda.setCellStyle(style2);
	}
	}
	
	
	fila = pagina.createRow(4);

	Cell celdat = fila.createCell((short) 1);
	celdat.setCellValue("NOMINA PAGO DE FINIQUITO");
	celdat.setCellStyle(combined2);
	pagina.addMergedRegion(CellRangeAddress.valueOf("B5:D5"));


	fila = pagina.createRow(5);
	fila = pagina.createRow(6);

	String[] titulo6 = {"CODIGO","NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO","RETENCION"};

	for(int i = 0; i < titulo6.length; i++) 
	{

	Cell celda = fila.createCell(i);
	celda.setCellStyle(style3); 
	celda.setCellValue(titulo6[i]);
	}

	fila = pagina.createRow(7);


	int numeroFor = 7;
	int contador5 = 0;


	String monto = "";
	String nombre_trabajador = "";
	String rut_trabajador = "";
	String medio_de_pago = "";
	String banco_trabajador = "";
	String numero_cuenta = "";
	String nombre_oficina = "";
	String nombre_huerto = "";
	String cod_trabaj = "";
	String retenciones = "";

	for(NominaAnticipos emplista : row){
		
	    cod_trabaj = emplista.getCod_trabajador_string();
		monto = emplista.getMonto_ingresado_string();
		nombre_trabajador = emplista.getNombre();
		rut_trabajador = emplista.getRut();
		medio_de_pago = emplista.getNombretipocuenta();
		banco_trabajador = emplista.getNombrebanco();
		numero_cuenta = emplista.getNumero_cuenta_string();
		String rutfinali = utils.FormatearRUT(rut_trabajador);
		nombre_oficina = emplista.getNombre_oficina_string();
		int valorTipoCuentaExcel = emplista.getValor_select_tipo_cuenta();
		nombre_huerto = emplista.getNombrehuerto();
		nombre_trabajador = nombre_trabajador.toUpperCase();
		rutfinali = rutfinali.toUpperCase();
		medio_de_pago = medio_de_pago.toUpperCase();
		banco_trabajador = banco_trabajador.toUpperCase();
		nombre_oficina = nombre_oficina.toUpperCase();
		retenciones = ""+emplista.getRetencion();
		
		String[] nombre3 = nombre_trabajador.split(" ");
		String nombre4 = "";
		
		if(nombre3.length == 3){
			 nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2];
		}
		else if(nombre3.length == 2){
			 nombre4 = nombre3[1] +" "+ nombre3[0];
		}
		
		else if (nombre3.length == 4){
			nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3];
		}
		else if (nombre3.length == 1){
			nombre4 = nombre3[0];
		}
		else if (nombre3.length == 5){
			nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4];
		}
		
		else if (nombre3.length == 6){
			nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5];
		}
		
		else if (nombre3.length == 7){
			nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5]+" "+ nombre3[6];
		}
		
		double amount_monto = Integer.parseInt(monto);   
		String total_monto = String.format("%,.0f", amount_monto);  
		
		double amount_monto_retencion = Integer.parseInt(retenciones);   
		String total_monto_retenciones = String.format("%,.0f", amount_monto_retencion); 
		
		
		
		if(nombre_oficina == null){
			nombre_oficina = "";
		}
		if(banco_trabajador != null){
			banco_trabajador = emplista.getNombrebanco();
		}else{
			banco_trabajador = "";
		}

		if(numero_cuenta == null){
			numero_cuenta = "";
		}
		
		if(valorTipoCuentaExcel == 4){
			
			String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"","VALE VISTA ENTREGADO DIRECCTAMENTE A LA EMPRESA","", "",""+total_monto+"",""+nombre_huerto+"",""+total_monto_retenciones+""};
			
			Cell celda8 = fila.createCell(0);
			if(numeroFor == 7){celda8.setCellStyle(style3);}
			celda8.setCellValue(titulo7[0]);
			
			Cell celda = fila.createCell(1);
			if(numeroFor == 7){celda.setCellStyle(style3);}
			celda.setCellValue(titulo7[1]);
			
			Cell celda2 = fila.createCell(2);
			if(numeroFor == 7){celda2.setCellStyle(style3);}
			celda2.setCellValue(titulo7[2]);
			
			Cell celda3 = fila.createCell(3);
			if(numeroFor == 7){celda3.setCellStyle(style3);}
			celda3.setCellValue(titulo7[3]);
			
			Cell celda4 = fila.createCell(4);
			if(numeroFor == 7){celda4.setCellStyle(style3);}
			celda4.setCellValue(titulo7[4]);
			
			Cell celda5 = fila.createCell(5);
			if(numeroFor == 7){celda5.setCellStyle(style3);}
			celda5.setCellValue(titulo7[5]);
			
			Cell celda6 = fila.createCell(6);
			celda6.setCellValue(titulo7[6]);
			
			if(numeroFor == 7){
				celda6.setCellStyle(combined);}
			else{
			celda6.setCellStyle(style2); 
			}
			Cell celda7 = fila.createCell(7);
			if(numeroFor == 7){celda7.setCellStyle(style3);}
			celda7.setCellValue(titulo7[7]);
			
			Cell celda28 = fila.createCell(8);
			celda28.setCellValue(titulo7[8]);
			
			if(numeroFor == 7){
				celda28.setCellStyle(combined);}
			else{
			celda28.setCellStyle(style2); 
			}
			
			
			

		numeroFor = numeroFor +1;
		fila = pagina.createRow(numeroFor);
		}else{
			String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+nombre_huerto+"",""+total_monto_retenciones+""};
		
		Cell celda8 = fila.createCell(0);
		if(numeroFor == 7){celda8.setCellStyle(style3);}
		celda8.setCellValue(titulo7[0]);
			
		Cell celda = fila.createCell(1);
		if(numeroFor == 7){celda.setCellStyle(style3);}
		celda.setCellValue(titulo7[1]);
		
		Cell celda2 = fila.createCell(2);
		if(numeroFor == 7){celda2.setCellStyle(style3);}
		celda2.setCellValue(titulo7[2]);
		
		Cell celda3 = fila.createCell(3);
		if(numeroFor == 7){celda3.setCellStyle(style3);}
		celda3.setCellValue(titulo7[3]);
		
		Cell celda4 = fila.createCell(4);
		if(numeroFor == 7){celda4.setCellStyle(style3);}
		celda4.setCellValue(titulo7[4]);
		
		Cell celda5 = fila.createCell(5);
		if(numeroFor == 7){celda5.setCellStyle(style3);}
		celda5.setCellValue(titulo7[5]);
		
		Cell celda6 = fila.createCell(6);
		celda6.setCellValue(titulo7[6]);
		if(numeroFor == 7){celda6.setCellStyle(combined);}
		else{
		celda6.setCellStyle(style2); 
		}
		Cell celda7 = fila.createCell(7);
		if(numeroFor == 7){celda7.setCellStyle(style3);}
		celda7.setCellValue(titulo7[7]);
		
		Cell celda29 = fila.createCell(8);
		celda29.setCellValue(titulo7[8]);
		
		if(numeroFor == 7){
			celda29.setCellStyle(combined);}
		else{
		celda29.setCellStyle(style2); 
		}
		
		
		

	numeroFor = numeroFor +1;
	fila = pagina.createRow(numeroFor);
		}	
	}

	
	pagina.autoSizeColumn(1);
	pagina.autoSizeColumn(2);
	pagina.autoSizeColumn(3);
	pagina.autoSizeColumn(4);
	pagina.autoSizeColumn(5);
	pagina.autoSizeColumn(6);
	pagina.autoSizeColumn(7);
	pagina.autoSizeColumn(8);
	pagina.autoSizeColumn(9);
	pagina.autoSizeColumn(10);


	

	/////////////////////END EXEL//////////////////////////
	// Creamos el flujo de salida de datos,
	// apuntando al archivo donde queremos 
	// almacenar el libro de Excel
	FileOutputStream salida = new FileOutputStream(ruta+archivo);
	NominaAnticipos var2 = impexp_trabajador.getUltimoIdSW();
  	int ultimoID2 = var2.getIdtablaswnomina();
	String ruta3 = ruta+archivo;
	impexp_trabajador.updateNOmbreArchivoSW_nomina_excel(ruta3,ultimoID2);

	// Almacenamos el libro de 
	// Excel via ese 
	// flujo de datos
	workbook.write(salida);

	// Cerramos el libro para concluir operaciones
	workbook.close();

	LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
	}
	catch (FileNotFoundException ex) {
	    LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
	} 
	catch (IOException e) {
	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, "Error de entrada/salida");
	    return false;
	}
	return true;
	//return null;

}
	/////////////////////////////////////////////nomina liquidacion////////////////////////////////////////
public static boolean insertTablaSwNominaLiquidacion (NominaAnticipos r) throws Exception{
		Statement ps = null;

		String sql="";
		ConnectionDB db = new ConnectionDB();
		
		try{
					
			sql = "INSERT INTO sw_nomina (total_monto,fecha_pago,periodo,concepto,id_concepto,idsociedad)";
			sql += "VALUES ("+r.getTotalmonto()+",'"+r.getFechaanticipo()+"',"+r.getPeriodo()+",'"+r.getNombre_nomina()+"',"+r.getIdconcepto()+","+r.getEmpresa()+")";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute(sql);
			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
		
			db.close();
		}
		return false;
	}
//////////////////////nomina liquidacion update sw_liquidacion////////////////////////////////////////////////////
public static boolean updateTablasw_liquidacion(NominaAnticipos r) throws Exception {
		Statement ps2 = null;
		String sql2 = "";

		ConnectionDB db = new ConnectionDB();
		try {

			sql2 = "UPDATE sw_liquidacion SET estado_liquidacion = 1, "
					+ "id_nomina = (select max(id_nomina) from sw_nomina), "
					+ "nombre_autorizada = '"+r.getNombre_autorizada()+"',"
					+ " rut_autorizada = '"+r.getRut_autorizada()+"' WHERE id_liquidacion = " + r.getIdtablaliquidacion() + "";
			
			ps2 = db.conn.prepareStatement(sql2);
			ps2.execute(sql2);

			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			ps2.close();
			db.close();
		}
		return false;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean getTxtMAsEmailLiquidacion(ArrayList<NominaAnticipos> row) throws Exception {

		String ruta = utils.LiquidacionTxt();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		
		String nombre_nominaT = "";
		

		for (NominaAnticipos emp1 : row) {
			
			nombre_nominaT = emp1.getNombre_nomina();
			      

		}
		
		String nombreArchivo = nombre_nominaT + horaf + ".txt";
		String outputFile = ruta + nombreArchivo;

		boolean alreadyExists = new File(outputFile).exists();

		if (alreadyExists) {
			File ArchivoEmpleados = new File(outputFile);
			ArchivoEmpleados.delete();
		}

		try {
			int fechaPago = 0;
			int totalMonto = 0;
			int idEmpresa = 0;
			String codTrabajador = "";
			String nombre_nomina = "";
			String fecha_pago_excel = "";
			String empresatext = "";
			String periodo_string = "";

			for (NominaAnticipos emp1 : row) {
				fechaPago = emp1.getFechaanticipoint();
				totalMonto = emp1.getTotalmonto();
				idEmpresa = emp1.getEmpresa();
				codTrabajador = emp1.getCod_trabajador_string();
				nombre_nomina = emp1.getNombre_nomina();
				fecha_pago_excel = emp1.getFechaanticipo();
				empresatext = emp1.getEmpresatext();
				periodo_string = emp1.getPeriodoString();       

			}

			// obtener rut empresa DB
			NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idEmpresa);

			String rutEmpresa1 = ru.getRut();
			String rutEmpresa12 = rutEmpresa1.toUpperCase();
			String rut1 = rutEmpresa12.replaceAll("[.]", "");
			String rutINT = rut1.replaceAll("[-]", "");

			// largo formato monto
			int length = 11;
			String SumaMonto = String.format("%0" + length + "d", totalMonto);

			// String nuevalinea = System.getProperty("line.separator");
			String nuevalinea = "\r\n";
			String rutEmpresa = rutINT;

			NominaAnticipos all = sw_NominaAnticiposDB.getALLSociedad(idEmpresa);
			String Numero_convenio = all.getNumeroconvenio();

			String Numero_nomina = all.getNumeronomina();

			String Tipo_nomina = all.getTiponomina();

			String numeroConvenio = Numero_convenio;
			String numeroNomina = Numero_nomina;
			// tipo de nomina renuneraciones y generico
			String tipoNomina = "0101";

			String nombre = String.format("%-20s", "010" + rutEmpresa + numeroConvenio + numeroNomina);
			
		
			String nombrenom = truncateCadena(nombre_nomina,25);
			nombre += String.format("%-25s", nombrenom);
			int fechaPago2 = fechaPago;

			nombre += String.format("%-26s", "01" + fechaPago2 + SumaMonto + "00");
			nombre += String.format("%-325s", "N");
			nombre += tipoNomina;
			nombre += nuevalinea;

			System.out.println("aqui estoy 1");

			int totalMonto1 = 0;
			int totalMontoPagoEfectivo = 0;
			int contador = 0;
			int contador2 = 0;
			int total_registro = 0;

			for (NominaAnticipos emp : row) {
				String monto = emp.getMonto_ingresado_string();

				int length2 = 14;
				int length_de_11 = 11;
				totalMonto1 = Integer.parseInt(monto);

				totalMontoPagoEfectivo = Integer.parseInt(SumaMonto);

				String SumaMonto1 = String.format("%0" + length2 + "d", totalMonto1);
				String SumaMonto2 = String.format("%0" + length_de_11 + "d", totalMontoPagoEfectivo);

				int id_banco = emp.getIdbanco();
				// obtener codigo SBIF
				NominaAnticipos COD_SBIF = sw_NominaAnticiposDB.getCodigoSbif(id_banco);
				/// cambiar idprensa buscar por la llave i no el id
				String codigo_SBIF = COD_SBIF.getCodigosbif();

				String codTrabajador2 = "";

				codTrabajador2 = emp.getCod_trabajador_string();

				NominaAnticipos trab_n_cuenta2 = sw_NominaAnticiposDB.getNumeroCuenta(codTrabajador2);
				String N_cuenta_Trab2 = trab_n_cuenta2.getNumero_cuenta_string();

				String rut = emp.getRut();
				String nombreTrabajador = emp.getNombre();
				String tipodecuentaTrabajador = emp.getIdtipocuentastring();
				String direccion_trabajador = emp.getDireccion();
				String oficina = emp.getOficina_string();

				int numEntero = Integer.parseInt(tipodecuentaTrabajador);

				String nombreAutorizada = emp.getNombre_autorizada();
				String rutAutorizado = emp.getRut_autorizado_replace();

				// obtener numero cuenta trabajador
				NominaAnticipos trab_n_cuenta = sw_NominaAnticiposDB.getNumeroCuenta(codTrabajador2);
				String N_cuenta_Trab = trab_n_cuenta.getNumero_cuenta_string();
				int idTipoCuenta = trab_n_cuenta.getIdtipocuenta();

				int cantidadDigito = String.valueOf(idTipoCuenta).length();

				String digitoString = "";
				if (cantidadDigito == 1) {
					digitoString = "0" + idTipoCuenta;
				} else {
					digitoString = String.valueOf(idTipoCuenta);
				}

				if (numEntero == 2 && contador == 0) {
					nombre += nuevalinea;
					System.out.println("aqui estoy 2");
					nombre += String.format("%-17s", "020" + rutEmpresa + numeroConvenio);
				} else if (numEntero != 2) {
					// nombre += nuevalinea;
					System.out.println("aqui estoy 3");
					nombre += String.format("%-17s", "020" + rutEmpresa + numeroConvenio);
				}

				// Vale Vista entregado en Meson del Banco de Chile
				if (numEntero == 2 && contador == 0) {
			
					nombre += String.format("%-8s", "10001"+digitoString+"0");
			   		nombre += String.format("%-9s",  rutAutorizado);
			   		nombre += String.format("%-60s",  nombreAutorizada);
					nombre += String.format("%-73s", "0" + direccion_trabajador + "");
					nombre += String.format("%-27s", "BC");
					nombre += String.format("%-135s", oficina + SumaMonto2 + "00"); // monto

					contador = contador + 1;
					contador2 = contador2 + 1;
				}
				// SERVIPAG
				else if (numEntero == 12) {
					
					nombre += String.format("%-8s", "10001"+digitoString+"0");
			   		nombre += String.format("%-9s",  rut );
			   		nombre += String.format("%-60s",  nombreTrabajador);	
					nombre += String.format("%-73s", "0" + direccion_trabajador + "");
					nombre += String.format("%-27s", "D6");
					nombre += String.format("%-135s", SumaMonto1 + "00"); // monto
				} else {

					if (contador2 == 1) {
					} else {
						
						nombre += String.format("%-8s", "10001"+digitoString+"0");
				   		nombre += String.format("%-9s",  rut );
				   		nombre += String.format("%-60s",  nombreTrabajador);	
						nombre += String.format("%-73s", "0" + direccion_trabajador + "");
						nombre += String.format("%-27s", "D6" + codigo_SBIF + N_cuenta_Trab2);
						nombre += String.format("%-134s", SumaMonto1 + "00"); // monto
						System.out.println("////////////////////////////////////////");

					}

				}

				if (numEntero == 2 && contador == 1) {

					nombre += String.format("%-71s", "0000");
					// nombre += nuevalinea;
					System.out.println("aqui estoy 4");
					contador = contador + 1;
				} else if (numEntero != 2) {
					nombre += String.format("%-71s", "0000");
					nombre += nuevalinea;
					System.out.println("aqui estoy 5");
				}
				total_registro = total_registro + 1;
			}
			// Crear objeto FileWriter que sera el que nos ayude a escribir
			// sobre archivo
			File f = new File(outputFile);
			FileUtils.writeStringToFile(f, nombre, "ISO-8859-1");
			// FileWriter escribir=new FileWriter(outputFile,true);

			// Escribimos en el archivo con el metodo write
			// escribir.write(nombre);

			// Cerramos la conexion
			// escribir.close();
			NominaAnticipos var = impexp_trabajador.getUltimoIdSW();
			int ultimoID = var.getIdtablaswnomina();

			String ruta2 = outputFile;

			impexp_trabajador.updateNOmbreArchivoSW_nomina(ruta2, ultimoID);

			String[] parts = fecha_pago_excel.split("-");

			String fecha_pago_final = parts[2] + "-" + parts[1] + "-" + parts[0];
			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File(nombre_nomina + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("" + nombre_nomina + "");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth( (short) 1);
			ps.setFitHeight( (short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			combined2.setAlignment(CellStyle.ALIGN_CENTER);
			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			// Indicamos que tendra un fondo azul aqua
			// con patron solido del color indicado
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            
			
			String EmpresaMYS = empresatext.toUpperCase();
			String[] titulos = {EmpresaMYS};
			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);
			celda1.setCellValue(titulos[0]);

			nombre_nomina = nombre_nomina.toUpperCase();
			rutEmpresa1 = rutEmpresa1.toUpperCase();

			double amount = totalMonto;
			String totalf = String.format("%,.0f", amount);
			
			fila = pagina.createRow(1);
			String[] TituloFiltros = {""+rutEmpresa1+"","","","","","FECHA DE PAGO",""+fecha_pago_final+""};
			for(int i = 0; i < TituloFiltros.length; i++) 
			{
			Cell celda = fila.createCell(i);
			celda.setCellValue(TituloFiltros[i]);
			}
			
			String[] PeriodoSplit = periodo_string.split("-");
			//
			int meses;
			//
			String mesPalabra = "";
			meses=Integer.parseInt(PeriodoSplit[1]);
			 
			switch ( meses ){
			case 1: mesPalabra = "ENERO"; break;
			case 2: mesPalabra = "FEBRERO"; break;
			case 3: mesPalabra = "MARZO"; break;
			case 4: mesPalabra = "ABRIL"; break;
			case 5: mesPalabra = "MAYO"; break;
			case 6: mesPalabra = "JUNIO"; break;
			case 7: mesPalabra = "JULIO"; break;
			case 8: mesPalabra = "AGOSTO"; break;
			case 9: mesPalabra = "SEPTIEMBRE"; break;
			case 10: mesPalabra = "OCTUBRE"; break;
			case 11: mesPalabra = "NOVIEMBRE"; break;
			case 12: mesPalabra = "DICIEMBRE"; break;
			default: mesPalabra = ""; break;
			}
			
			fila = pagina.createRow(2);
			String[] DetalleFiltros = {"N° DE REGISTROS",""+total_registro+"","","","","PERIODO PROCESO",""+mesPalabra+" "+PeriodoSplit[0]+""};
			for(int i = 0; i < DetalleFiltros.length; i++) 
			{
			Cell celda = fila.createCell(i);
			celda.setCellValue(DetalleFiltros[i]);
			}
			
			fila = pagina.createRow(3);
			String[] DetalleTotal = {"","","","","","TOTAL",""+totalf+""};
			for(int i = 0; i < DetalleTotal.length; i++) 
			{
			Cell celda = fila.createCell(i);
			celda.setCellValue(DetalleTotal[i]);
			if(i == 6){
				celda.setCellStyle(style2);
			}
			}

			fila = pagina.createRow(4);

			Cell celdat = fila.createCell((short) 1);
			celdat.setCellValue("NOMINA PAGO DE LIQUIDACIÓN");
			celdat.setCellStyle(combined2);
			pagina.addMergedRegion(CellRangeAddress.valueOf("B5:D5"));
		

			

		
			fila = pagina.createRow(5);
			fila = pagina.createRow(6);

			String[] titulo6 = {"CODIGO","NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO"};

			for(int i = 0; i < titulo6.length; i++) 
			{

			Cell celda = fila.createCell(i);
			celda.setCellStyle(style3); 
			celda.setCellValue(titulo6[i]);
			}

			fila = pagina.createRow(7);

			int numeroFor = 7;
			int contador5 = 0;

			String monto = "";
			String nombre_trabajador = "";
			String rut_trabajador = "";
			String medio_de_pago = "";
			String banco_trabajador = "";
			String numero_cuenta = "";
			String nombre_oficina = "";
			String nombre_huerto = "";
			String cod_trabaj = "";

			for (NominaAnticipos emplista : row) {
				
				cod_trabaj = emplista.getCod_trabajador_string();
				monto = emplista.getMonto_ingresado_string();
				nombre_trabajador = emplista.getNombre();
				rut_trabajador = emplista.getRut();
				medio_de_pago = emplista.getNombretipocuenta();
				banco_trabajador = emplista.getNombrebanco();
				numero_cuenta = emplista.getNumero_cuenta_string();
				String rutfinali = utils.FormatearRUT(rut_trabajador);
				nombre_oficina = emplista.getNombre_oficina_string();
				nombre_huerto = emplista.getNombrehuerto();
				nombre_trabajador = nombre_trabajador.toUpperCase();
				rutfinali = rutfinali.toUpperCase();
				medio_de_pago = medio_de_pago.toUpperCase();
				
				nombre_oficina = nombre_oficina.toUpperCase();

				double amount_monto = Integer.parseInt(monto);
				String total_monto = String.format("%,.0f", amount_monto);

				LOGGER.log(Level.INFO, "banco es " + banco_trabajador + "", banco_trabajador);
				if (nombre_oficina == null) {
					nombre_oficina = "";
				}
				if (banco_trabajador != null) {
					banco_trabajador = emplista.getNombrebanco().toUpperCase();
				} else {
					banco_trabajador = "";
				}

				if (numero_cuenta == null) {
					numero_cuenta = "";
				}
				
				
				
				String[] nombre3 = nombre_trabajador.split(" ");
				String nombre4 = "";
				
				if(nombre3.length == 3){
					 nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2];
				}
				else if(nombre3.length == 2){
					 nombre4 = nombre3[0] +" "+ nombre3[1];
				}
				
				else if (nombre3.length == 4){
					nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3];
				}
				else if (nombre3.length == 1){
					nombre4 = nombre3[0];
				}
				
				else if (nombre3.length == 5){
					nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4];
				}
				
				else if (nombre3.length == 6){
					nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5];
				}
				
				else if (nombre3.length == 7){
					nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5]+" "+ nombre3[6];
				}
				
				
				String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+nombre_huerto+""};


				Cell celda8 = fila.createCell(0);
				if (numeroFor == 7) {
					celda8.setCellStyle(style3);
				}
				celda8.setCellValue(titulo7[0]);

				Cell celda = fila.createCell(1);
				if(numeroFor == 7){celda.setCellStyle(style3);}
				celda.setCellValue(titulo7[1]);

				Cell celda2 = fila.createCell(2);
				if(numeroFor == 7){celda2.setCellStyle(style3);}
				celda2.setCellValue(titulo7[2]);

				Cell celda3 = fila.createCell(3);
				if(numeroFor == 7){celda3.setCellStyle(style3);}
				celda3.setCellValue(titulo7[3]);

				Cell celda4 = fila.createCell(4);
				if(numeroFor == 7){celda4.setCellStyle(style3);}
				celda4.setCellValue(titulo7[4]);

				Cell celda5 = fila.createCell(5);
				if(numeroFor == 7){celda5.setCellStyle(style3);}
				celda5.setCellValue(titulo7[5]);

				Cell celda6 = fila.createCell(6);
				celda6.setCellValue(titulo7[6]);
				
				if(numeroFor == 7){celda6.setCellStyle(combined);}
				else{
				celda6.setCellStyle(style2); 
				}
				Cell celda7 = fila.createCell(7);
				if(numeroFor == 7){celda7.setCellStyle(style3);}
				celda7.setCellValue(titulo7[7]);

				numeroFor = numeroFor + 1;
				fila = pagina.createRow(numeroFor);

			}

			
			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			pagina.autoSizeColumn(3);
			pagina.autoSizeColumn(4);
			pagina.autoSizeColumn(5);
			pagina.autoSizeColumn(6);
			pagina.autoSizeColumn(7);
			pagina.autoSizeColumn(8);
			pagina.autoSizeColumn(9);
			pagina.autoSizeColumn(10);

			// Ahora guardaremos el archivo

			///////////////////// END EXEL//////////////////////////
			// Creamos el flujo de salida de datos,
			// apuntando al archivo donde queremos
			// almacenar el libro de Excel

			FileOutputStream salida = new FileOutputStream(ruta + archivo);

			String ruta3 = ruta + archivo;
			impexp_trabajador.updateNOmbreArchivoSW_nomina_excel(ruta3, ultimoID);

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();

			LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Error de entrada/salida");
			return false;
		}
		return true;
		// return null;

	}
///////////////////////////////// excel pre nomina liquidacion //////////////////////////////////////////////////
public static String getPreExcelLiquidacion(ArrayList<NominaAnticipos> row) throws Exception {

		String ruta = utils.LiquidacionTxt();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		

		String horaf = formatoHora.replaceAll("[:]", "");
		String ruta3 = "";
		
		try {
			int totalMonto = 0;
			int idEmpresa = 0;
			String nombre_nomina = "";
			String fecha_pago_excel = "";
			String empresatext = "";
			String periodo_string = "";

			for (NominaAnticipos emp1 : row) {
				totalMonto = emp1.getTotalmonto();
				idEmpresa = emp1.getEmpresa();
				nombre_nomina = emp1.getNombre_nomina();
				fecha_pago_excel = emp1.getFechaanticipo();
				empresatext = emp1.getEmpresatext();
				periodo_string = emp1.getPeriodoString();       

			}

			// obtener rut empresa DB
			NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idEmpresa);

			String rutEmpresa1 = ru.getRut();
			

			int total_registro = 0;

			for (NominaAnticipos emp : row) {
				
				total_registro = total_registro + 1;
			}



			String[] parts = fecha_pago_excel.split("-");

			String fecha_pago_final = parts[2] + "-" + parts[1] + "-" + parts[0];
			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File(nombre_nomina + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("" + nombre_nomina + "");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth( (short) 1);
			ps.setFitHeight( (short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			combined2.setAlignment(CellStyle.ALIGN_CENTER);
			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			// Indicamos que tendra un fondo azul aqua
			// con patron solido del color indicado
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            
			
			String EmpresaMYS = empresatext.toUpperCase();
			String[] titulos = {EmpresaMYS};
			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);
			celda1.setCellValue(titulos[0]);

			nombre_nomina = nombre_nomina.toUpperCase();
			rutEmpresa1 = rutEmpresa1.toUpperCase();

			double amount = totalMonto;
			String totalf = String.format("%,.0f", amount);
			
			fila = pagina.createRow(1);
			String[] TituloFiltros = {""+rutEmpresa1+"","","","","","FECHA DE PAGO",""+fecha_pago_final+""};
			for(int i = 0; i < TituloFiltros.length; i++) 
			{
			Cell celda = fila.createCell(i);
			celda.setCellValue(TituloFiltros[i]);
			}
			
			String[] PeriodoSplit = periodo_string.split("-");
			//
			int meses;
			//
			String mesPalabra = "";
			meses=Integer.parseInt(PeriodoSplit[1]);
			 
			switch ( meses ){
			case 1: mesPalabra = "ENERO"; break;
			case 2: mesPalabra = "FEBRERO"; break;
			case 3: mesPalabra = "MARZO"; break;
			case 4: mesPalabra = "ABRIL"; break;
			case 5: mesPalabra = "MAYO"; break;
			case 6: mesPalabra = "JUNIO"; break;
			case 7: mesPalabra = "JULIO"; break;
			case 8: mesPalabra = "AGOSTO"; break;
			case 9: mesPalabra = "SEPTIEMBRE"; break;
			case 10: mesPalabra = "OCTUBRE"; break;
			case 11: mesPalabra = "NOVIEMBRE"; break;
			case 12: mesPalabra = "DICIEMBRE"; break;
			default: mesPalabra = ""; break;
			}
			
			fila = pagina.createRow(2);
			String[] DetalleFiltros = {"N° DE REGISTROS",""+total_registro+"","","","","PERIODO PROCESO",""+mesPalabra+" "+PeriodoSplit[0]+""};
			for(int i = 0; i < DetalleFiltros.length; i++) 
			{
			Cell celda = fila.createCell(i);
			celda.setCellValue(DetalleFiltros[i]);
			}
			
			fila = pagina.createRow(3);
			String[] DetalleTotal = {"","","","","","TOTAL",""+totalf+""};
			for(int i = 0; i < DetalleTotal.length; i++) 
			{
			Cell celda = fila.createCell(i);
			celda.setCellValue(DetalleTotal[i]);
			if(i == 6){
				celda.setCellStyle(style2);
			}
			}

			fila = pagina.createRow(4);

			Cell celdat = fila.createCell((short) 1);
			celdat.setCellValue("NOMINA PAGO DE LIQUIDACIÓN");
			celdat.setCellStyle(combined2);
			pagina.addMergedRegion(CellRangeAddress.valueOf("B5:D5"));
		

			

		
			fila = pagina.createRow(5);
			fila = pagina.createRow(6);

			String[] titulo6 = {"CODIGO","NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO"};

			for(int i = 0; i < titulo6.length; i++) 
			{

			Cell celda = fila.createCell(i);
			celda.setCellStyle(style3); 
			celda.setCellValue(titulo6[i]);
			}

			fila = pagina.createRow(7);

			int numeroFor = 7;
			int contador5 = 0;

			String monto = "";
			String nombre_trabajador = "";
			String rut_trabajador = "";
			String medio_de_pago = "";
			String banco_trabajador = "";
			String numero_cuenta = "";
			String nombre_oficina = "";
			String nombre_huerto = "";
			String cod_trabaj = "";

			for (NominaAnticipos emplista : row) {
				
				cod_trabaj = emplista.getCod_trabajador_string();
				monto = emplista.getMonto_ingresado_string();
				nombre_trabajador = emplista.getNombre();
				rut_trabajador = emplista.getRut();
				medio_de_pago = emplista.getNombretipocuenta();
				banco_trabajador = emplista.getNombrebanco();
				numero_cuenta = emplista.getNumero_cuenta_string();
				String rutfinali = utils.FormatearRUT(rut_trabajador);
				nombre_oficina = emplista.getNombre_oficina_string();
				nombre_huerto = emplista.getNombrehuerto();
				nombre_trabajador = nombre_trabajador.toUpperCase();
				rutfinali = rutfinali.toUpperCase();
				medio_de_pago = medio_de_pago.toUpperCase();
				
				nombre_oficina = nombre_oficina.toUpperCase();

				double amount_monto = Integer.parseInt(monto);
				String total_monto = String.format("%,.0f", amount_monto);

				LOGGER.log(Level.INFO, "banco es " + banco_trabajador + "", banco_trabajador);
				if (nombre_oficina == null) {
					nombre_oficina = "";
				}
				if (banco_trabajador != null) {
					banco_trabajador = emplista.getNombrebanco().toUpperCase();
				} else {
					banco_trabajador = "";
				}

				if (numero_cuenta == null) {
					numero_cuenta = "";
				}
				
				
				
				String[] nombre3 = nombre_trabajador.split(" ");
				String nombre4 = "";
				
				if(nombre3.length == 3){
					 nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2];
				}
				else if(nombre3.length == 2){
					 nombre4 = nombre3[0] +" "+ nombre3[1];
				}
				
				else if (nombre3.length == 4){
					nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3];
				}
				else if (nombre3.length == 1){
					nombre4 = nombre3[0];
				}
				
				else if (nombre3.length == 5){
					nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4];
				}
				
				else if (nombre3.length == 6){
					nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5];
				}
				
				else if (nombre3.length == 7){
					nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5]+" "+ nombre3[6];
				}
				
				
				String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+nombre_huerto+""};


				Cell celda8 = fila.createCell(0);
				if (numeroFor == 7) {
					celda8.setCellStyle(style3);
				}
				celda8.setCellValue(titulo7[0]);

				Cell celda = fila.createCell(1);
				if(numeroFor == 7){celda.setCellStyle(style3);}
				celda.setCellValue(titulo7[1]);

				Cell celda2 = fila.createCell(2);
				if(numeroFor == 7){celda2.setCellStyle(style3);}
				celda2.setCellValue(titulo7[2]);

				Cell celda3 = fila.createCell(3);
				if(numeroFor == 7){celda3.setCellStyle(style3);}
				celda3.setCellValue(titulo7[3]);

				Cell celda4 = fila.createCell(4);
				if(numeroFor == 7){celda4.setCellStyle(style3);}
				celda4.setCellValue(titulo7[4]);

				Cell celda5 = fila.createCell(5);
				if(numeroFor == 7){celda5.setCellStyle(style3);}
				celda5.setCellValue(titulo7[5]);

				Cell celda6 = fila.createCell(6);
				celda6.setCellValue(titulo7[6]);
				
				if(numeroFor == 7){celda6.setCellStyle(combined);}
				else{
				celda6.setCellStyle(style2); 
				}
				Cell celda7 = fila.createCell(7);
				if(numeroFor == 7){celda7.setCellStyle(style3);}
				celda7.setCellValue(titulo7[7]);

				numeroFor = numeroFor + 1;
				fila = pagina.createRow(numeroFor);

			}

			
			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			pagina.autoSizeColumn(3);
			pagina.autoSizeColumn(4);
			pagina.autoSizeColumn(5);
			pagina.autoSizeColumn(6);
			pagina.autoSizeColumn(7);
			pagina.autoSizeColumn(8);
			pagina.autoSizeColumn(9);
			pagina.autoSizeColumn(10);

			// Ahora guardaremos el archivo

			///////////////////// END EXEL//////////////////////////
			// Creamos el flujo de salida de datos,
			// apuntando al archivo donde queremos
			// almacenar el libro de Excel

			FileOutputStream salida = new FileOutputStream(ruta + archivo);

			 ruta3 = ruta + archivo;
			System.out.println(ruta3);

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();

			LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Error de entrada/salida");
		
		}
		
		return ruta3;
		// return null;

	}

///////////////////////////////// excel pre nomina anticipo /////////////////////////////////////////////////////
public static String getPreExcelAnticipo (ArrayList<NominaAnticipos> row) throws Exception{
	    
		 
		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();
		


		//Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		
		String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
		
		String horaf = formatoHora.replaceAll("[:]", "");;
	 
	String ruta3 = "";

	try {
		int fechaPago = 0;
		int totalMonto = 0;
		int idEmpresa = 0;
		String nombre_nomina = "";
		String fecha_pago_excel = "";
		String empresatext = "";
	    String periodo_string = "";
		
		for(NominaAnticipos emp1 : row){
			 fechaPago = emp1.getFechaanticipoint();
			 totalMonto = emp1.getTotalmonto();
			 idEmpresa = emp1.getEmpresa();
			 nombre_nomina  = emp1.getNombre_nomina();
			 fecha_pago_excel = emp1.getFechaanticipo();
			 empresatext = emp1.getEmpresatext();
			 periodo_string = emp1.getPeriodoString();       		  
		}
		
//		obtener rut empresa DB
		NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idEmpresa);
		
		String rutEmpresa1 = ru.getRut();
			
	 
		 int total_registro = 0;
		 
		 for(NominaAnticipos emp : row)
		 {
			 total_registro = total_registro +1;
		 }
	  	
	  	
		
		String[] parts = fecha_pago_excel.split("-");
		
		String fecha_pago_final = parts[2]+"-"+parts[1]+"-"+parts[0];
	//////////////////////EXCEL///////////////////////////

	//Creamos el archivo donde almacenaremos la hoja
	//de calculo, recuerde usar la extension correcta,
	//en este caso .xlsx
		
		 
	File archivo = new File(nombre_nomina+horaf+".xlsx");

	//Creamos el libro de trabajo de Excel formato OOXML
	Workbook workbook = new XSSFWorkbook(); 

	//La hoja donde pondremos los datos
	Sheet pagina = workbook.createSheet(""+nombre_nomina+"");
	pagina.getPrintSetup().setLandscape(true);
	pagina.setFitToPage(true);
	PrintSetup ps = pagina.getPrintSetup();
	ps.setFitWidth( (short) 1);
	ps.setFitHeight( (short) 0);

	//Creamos el estilo paga las celdas del encabezado
	CellStyle style = workbook.createCellStyle();
	CellStyle style2 = workbook.createCellStyle();
	CellStyle style3 = workbook.createCellStyle();
	CellStyle combined2 = workbook.createCellStyle();
	CellStyle combined = workbook.createCellStyle();
	combined.setAlignment(CellStyle.ALIGN_RIGHT);
	combined.setBorderTop(CellStyle.BORDER_DOUBLE);
	combined2.setAlignment(CellStyle.ALIGN_CENTER);
	style3.setBorderTop(CellStyle.BORDER_DOUBLE);
	style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style2.setAlignment(CellStyle.ALIGN_RIGHT);


	String empresaMys = empresatext.toUpperCase();
	String[] titulos = {empresaMys};

	//Creamos una fila en la hoja en la posicion 0
	Row fila = pagina.createRow(0);
	//Indicamos el estilo que deseamos usar en la celda, en este caso el unico que hemos creado
	Cell celda1 = fila.createCell(0);

	celda1.setCellValue(titulos[0]);

	nombre_nomina = nombre_nomina.toUpperCase();
	rutEmpresa1 = rutEmpresa1.toUpperCase();


	double amount = totalMonto;    
	String totalf = String.format("%,.0f", amount);  


	fila = pagina.createRow(1);
	String[] TituloFiltros = {""+rutEmpresa1+"","","","","","FECHA DE PAGO",""+fecha_pago_final+""};
	for(int i = 0; i < TituloFiltros.length; i++) 
	{
	Cell celda = fila.createCell(i);
	celda.setCellValue(TituloFiltros[i]);
	}


	String[] PeriodoSplit = periodo_string.split("-");
	//
	int meses;
	//
	String mesPalabra = "";
	meses=Integer.parseInt(PeriodoSplit[1]);
	 
	switch ( meses ){
	case 1: mesPalabra = "ENERO"; break;
	case 2: mesPalabra = "FEBRERO"; break;
	case 3: mesPalabra = "MARZO"; break;
	case 4: mesPalabra = "ABRIL"; break;
	case 5: mesPalabra = "MAYO"; break;
	case 6: mesPalabra = "JUNIO"; break;
	case 7: mesPalabra = "JULIO"; break;
	case 8: mesPalabra = "AGOSTO"; break;
	case 9: mesPalabra = "SEPTIEMBRE"; break;
	case 10: mesPalabra = "OCTUBRE"; break;
	case 11: mesPalabra = "NOVIEMBRE"; break;
	case 12: mesPalabra = "DICIEMBRE"; break;
	default: mesPalabra = ""; break;
	}

	fila = pagina.createRow(2);
	String[] DetalleFiltros = {"N° DE REGISTROS",""+total_registro+"","","","","PERIODO PROCESO",""+mesPalabra+" "+PeriodoSplit[0]+""};
	for(int i = 0; i < DetalleFiltros.length; i++) 
	{
	Cell celda = fila.createCell(i);
	celda.setCellValue(DetalleFiltros[i]);
	}


	fila = pagina.createRow(3);
	String[] DetalleTotal = {"","","","","","TOTAL",""+totalf+""};
	for(int i = 0; i < DetalleTotal.length; i++) 
	{
	Cell celda = fila.createCell(i);
	celda.setCellValue(DetalleTotal[i]);
	if(i == 6){
		celda.setCellStyle(style2);
	}
	}


	fila = pagina.createRow(4);

	Cell celdat = fila.createCell((short) 1);
	celdat.setCellValue("NOMINA PAGO DE ANTICIPOS");
	celdat.setCellStyle(combined2);
	pagina.addMergedRegion(CellRangeAddress.valueOf("B5:D5"));


	fila = pagina.createRow(5);
	fila = pagina.createRow(6);

	String[] titulo6 = {"CODIGO","NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO"};

	for(int i = 0; i < titulo6.length; i++) 
	{

	Cell celda = fila.createCell(i);
	celda.setCellStyle(style3); 
	celda.setCellValue(titulo6[i]);
	}

	fila = pagina.createRow(7);

	int numeroFor = 7;
	int contador5 = 0;


	String monto = "";
	String nombre_trabajador = "";
	String rut_trabajador = "";
	String medio_de_pago = "";
	String banco_trabajador = "";
	String numero_cuenta = "";
	String nombre_oficina = "";
	String nombre_huerto = "";
	String cod_trabaj = "";

	for(NominaAnticipos emplista : row){
		
		cod_trabaj = emplista.getCod_trabajador_string();
		monto = emplista.getMonto_ingresado_string();
		nombre_trabajador = emplista.getNombre();
		rut_trabajador = emplista.getRut();
		medio_de_pago = emplista.getNombretipocuenta();
		banco_trabajador = emplista.getNombrebanco();
		numero_cuenta = emplista.getNumero_cuenta_string();
		String rutfinali = utils.FormatearRUT(rut_trabajador);
		nombre_oficina = emplista.getNombre_oficina_string();
		
		nombre_huerto = emplista.getNombrehuerto();
		nombre_trabajador = nombre_trabajador.toUpperCase();
		rutfinali = rutfinali.toUpperCase();
		medio_de_pago = medio_de_pago.toUpperCase();
		
		nombre_oficina = nombre_oficina.toUpperCase();
		

		
		double amount_monto = Integer.parseInt(monto);   
		String total_monto = String.format("%,.0f", amount_monto);  
		
		if(nombre_oficina == null){
			nombre_oficina = "";
		}
		
		if(banco_trabajador != null){
			banco_trabajador = emplista.getNombrebanco().toUpperCase();
		}else{
			banco_trabajador = "";
		}

		if(numero_cuenta == null){
			numero_cuenta = "";
		}
		
		String[] nombre3 = nombre_trabajador.split(" ");
		String nombre4 = "";
		
		if(nombre3.length == 3){
			 nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2];
		}
		else if(nombre3.length == 2){
			 nombre4 = nombre3[0] +" "+ nombre3[1];
		}
		
		else if (nombre3.length == 4){
			nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3];
		}
		else if (nombre3.length == 1){
			nombre4 = nombre3[0];
		}
		else if (nombre3.length == 5){
			nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4];
		}
		else if (nombre3.length == 6){
			nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5];
		}
		else if (nombre3.length == 7){
			nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5]+" "+ nombre3[6];
		}
		 
		String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+nombre_huerto+""};
		
			Cell celda8 = fila.createCell(0);
			if(numeroFor == 7){celda8.setCellStyle(style3);}
			celda8.setCellValue(titulo7[0]);
		
			Cell celda = fila.createCell(1);
			if(numeroFor == 7){celda.setCellStyle(style3);}
			celda.setCellValue(titulo7[1]);
			
			Cell celda2 = fila.createCell(2);
			if(numeroFor == 7){celda2.setCellStyle(style3);}
			celda2.setCellValue(titulo7[2]);
			
			Cell celda3 = fila.createCell(3);
			if(numeroFor == 7){celda3.setCellStyle(style3);}
			celda3.setCellValue(titulo7[3]);
			
			Cell celda4 = fila.createCell(4);
			if(numeroFor == 7){celda4.setCellStyle(style3);}
			celda4.setCellValue(titulo7[4]);
			
			Cell celda5 = fila.createCell(5);
			if(numeroFor == 7){celda5.setCellStyle(style3);}
			celda5.setCellValue(titulo7[5]);
			
			Cell celda6 = fila.createCell(6);
			celda6.setCellValue(titulo7[6]);
			
			if(numeroFor == 7){celda6.setCellStyle(combined);}
			else{
			celda6.setCellStyle(style2); 
			}
			Cell celda7 = fila.createCell(7);
			if(numeroFor == 7){celda7.setCellStyle(style3);}
			celda7.setCellValue(titulo7[7]);
			
			
			

		numeroFor = numeroFor +1;
		fila = pagina.createRow(numeroFor);
		
		
	}


	pagina.autoSizeColumn(1);
	pagina.autoSizeColumn(2);
	pagina.autoSizeColumn(3);
	pagina.autoSizeColumn(4);
	pagina.autoSizeColumn(5);
	pagina.autoSizeColumn(6);
	pagina.autoSizeColumn(7);
	pagina.autoSizeColumn(8);
	pagina.autoSizeColumn(9);
	pagina.autoSizeColumn(10);


	FileOutputStream salida = new FileOutputStream(ruta+archivo);

	ruta3 = ruta+archivo;

	// Almacenamos el libro de 
	// Excel via ese 
	// flujo de datos
	workbook.write(salida);

	// Cerramos el libro para concluir operaciones
	workbook.close();

	LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
	}
	catch (FileNotFoundException ex) {
	    LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
	} 
	catch (IOException e) {
	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, "Error de entrada/salida");
	}
	return ruta3;
	//return null;

	}

//////////////////////////////// excel pre nomina finiquitos ////////////////////////////////////////////////////
public static String getPreExcelFiniquitos(ArrayList<NominaAnticipos> row) throws Exception {

	String ruta = utils.FiniquitoTxt();
	Date fechaActual = new Date();

	//Formateando la fecha:
	DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
	DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
	
	String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
	String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
	
	String horaf = formatoHora.replaceAll("[:]", "");;
   

String ruta3 = "";


try {
	int fechaPago = 0;
	int totalMonto = 0;
	int idEmpresa = 0;
	String codTrabajador = "";
	String nombre_nomina = "";
	String fecha_pago_excel = "";
	String empresatext = "";
    String periodo_string = "";
    int retencion = 0;
    int monto_menos_retencion = 0;
	
	for(NominaAnticipos emp1 : row){
		 fechaPago = emp1.getFechaanticipoint();
		 totalMonto = emp1.getTotalmonto();
		 retencion =  emp1.getRetencion();
		 idEmpresa = emp1.getEmpresa();
		 codTrabajador   = emp1.getCod_trabajador_string();
		 nombre_nomina  = emp1.getNombre_nomina();
		 fecha_pago_excel = emp1.getFechaanticipo();
		 empresatext = emp1.getEmpresatext();
		 periodo_string = emp1.getPeriodoString(); 
		 monto_menos_retencion = emp1.getMontomenosretenciones();
		 
		        		  
	}
	
//	obtener rut empresa DB
	NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idEmpresa);
	
			String rutEmpresa1 = ru.getRut();

	int total_registro = 0;
    for(NominaAnticipos emp : row)
    {
   		total_registro = total_registro +1;
   	   	 
 	 }
 	
	String[] parts = fecha_pago_excel.split("-");
	
	String fecha_pago_final = parts[2]+"-"+parts[1]+"-"+parts[0];
//////////////////////EXCEL///////////////////////////

//Creamos el archivo donde almacenaremos la hoja
//de calculo, recuerde usar la extension correcta,
//en este caso .xlsx
	
	 
File archivo = new File(nombre_nomina+horaf+".xlsx");

//Creamos el libro de trabajo de Excel formato OOXML
Workbook workbook = new XSSFWorkbook(); 

//La hoja donde pondremos los datos
Sheet pagina = workbook.createSheet(""+nombre_nomina+"");
pagina.getPrintSetup().setLandscape(true);
pagina.setFitToPage(true);
PrintSetup ps = pagina.getPrintSetup();
ps.setFitWidth( (short) 1);
ps.setFitHeight( (short) 0);

//Creamos el estilo paga las celdas del encabezado
CellStyle style = workbook.createCellStyle();
CellStyle style2 = workbook.createCellStyle();
CellStyle style3 = workbook.createCellStyle();
CellStyle combined2 = workbook.createCellStyle();
CellStyle combined = workbook.createCellStyle();
combined.setAlignment(CellStyle.ALIGN_RIGHT);
combined.setBorderTop(CellStyle.BORDER_DOUBLE);
combined2.setAlignment(CellStyle.ALIGN_CENTER);
style3.setBorderTop(CellStyle.BORDER_DOUBLE);
style2.setAlignment(CellStyle.ALIGN_RIGHT);
//Indicamos que tendra un fondo azul aqua
//con patron solido del color indicado
style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
style.setFillPattern(CellStyle.SOLID_FOREGROUND);

String empresaMys = empresatext.toUpperCase();
String[] titulos = {empresaMys};
//Creamos una fila en la hoja en la posicion 0
Row fila = pagina.createRow(0);
//Indicamos el estilo que deseamos usar en la celda, en este caso el unico que hemos creado
Cell celda1 = fila.createCell(0);
celda1.setCellValue(titulos[0]);

nombre_nomina = nombre_nomina.toUpperCase();
rutEmpresa1 = rutEmpresa1.toUpperCase();


double amount = monto_menos_retencion;    
String totalf = String.format("%,.0f", amount);  

fila = pagina.createRow(1);
String[] TituloFiltros = {""+rutEmpresa1+"","","","","","FECHA DE PAGO",""+fecha_pago_final+""};
for(int i = 0; i < TituloFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(TituloFiltros[i]);
}

String[] PeriodoSplit = periodo_string.split("-");
//
int meses;
//
String mesPalabra = "";
meses=Integer.parseInt(PeriodoSplit[1]);
 
switch ( meses ){
case 1: mesPalabra = "ENERO"; break;
case 2: mesPalabra = "FEBRERO"; break;
case 3: mesPalabra = "MARZO"; break;
case 4: mesPalabra = "ABRIL"; break;
case 5: mesPalabra = "MAYO"; break;
case 6: mesPalabra = "JUNIO"; break;
case 7: mesPalabra = "JULIO"; break;
case 8: mesPalabra = "AGOSTO"; break;
case 9: mesPalabra = "SEPTIEMBRE"; break;
case 10: mesPalabra = "OCTUBRE"; break;
case 11: mesPalabra = "NOVIEMBRE"; break;
case 12: mesPalabra = "DICIEMBRE"; break;
default: mesPalabra = ""; break;
}

fila = pagina.createRow(2);
String[] DetalleFiltros = {"N° DE REGISTROS",""+total_registro+"","","","","PERIODO PROCESO",""+mesPalabra+" "+PeriodoSplit[0]+""};
for(int i = 0; i < DetalleFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(DetalleFiltros[i]);
}

fila = pagina.createRow(3);
String[] DetalleTotal = {"","","","","","TOTAL",""+totalf+""};
for(int i = 0; i < DetalleTotal.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(DetalleTotal[i]);
if(i == 6){
	celda.setCellStyle(style2);
}
}


fila = pagina.createRow(4);

Cell celdat = fila.createCell((short) 1);
celdat.setCellValue("NOMINA PAGO DE FINIQUITO");
celdat.setCellStyle(combined2);
pagina.addMergedRegion(CellRangeAddress.valueOf("B5:D5"));


fila = pagina.createRow(5);
fila = pagina.createRow(6);

String[] titulo6 = {"CODIGO","NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO","RETENCION"};

for(int i = 0; i < titulo6.length; i++) 
{

Cell celda = fila.createCell(i);
celda.setCellStyle(style3); 
celda.setCellValue(titulo6[i]);
}

fila = pagina.createRow(7);


int numeroFor = 7;
int contador5 = 0;


String monto = "";
String nombre_trabajador = "";
String rut_trabajador = "";
String medio_de_pago = "";
String banco_trabajador = "";
String numero_cuenta = "";
String nombre_oficina = "";
String nombre_huerto = "";
String cod_trabaj = "";
String retenciones = "";

for(NominaAnticipos emplista : row){
	
    cod_trabaj = emplista.getCod_trabajador_string();
	monto = emplista.getMonto_ingresado_string();
	nombre_trabajador = emplista.getNombre();
	rut_trabajador = emplista.getRut();
	medio_de_pago = emplista.getNombretipocuenta();
	banco_trabajador = emplista.getNombrebanco();
	numero_cuenta = emplista.getNumero_cuenta_string();
	String rutfinali = utils.FormatearRUT(rut_trabajador);
	nombre_oficina = emplista.getNombre_oficina_string();
	int valorTipoCuentaExcel = emplista.getValor_select_tipo_cuenta();
	nombre_huerto = emplista.getNombrehuerto();
	nombre_trabajador = nombre_trabajador.toUpperCase();
	rutfinali = rutfinali.toUpperCase();
	medio_de_pago = medio_de_pago.toUpperCase();
	banco_trabajador = banco_trabajador.toUpperCase();
	nombre_oficina = nombre_oficina.toUpperCase();
	retenciones = ""+emplista.getRetencion();
	
	String[] nombre3 = nombre_trabajador.split(" ");
	String nombre4 = "";
	
	if(nombre3.length == 3){
		 nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2];
	}
	else if(nombre3.length == 2){
		 nombre4 = nombre3[1] +" "+ nombre3[0];
	}
	
	else if (nombre3.length == 4){
		nombre4 = nombre3[0] +" "+ nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3];
	}
	else if (nombre3.length == 1){
		nombre4 = nombre3[0];
	}
	else if (nombre3.length == 5){
		nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4];
	}
	
	else if (nombre3.length == 6){
		nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5];
	}
	
	else if (nombre3.length == 7){
		nombre4 = nombre3[0] +" "+nombre3[1] +" "+ nombre3[2] +" "+ nombre3[3] +" "+ nombre3[4]+" "+ nombre3[5]+" "+ nombre3[6];
	}
	
	double amount_monto = Integer.parseInt(monto);   
	String total_monto = String.format("%,.0f", amount_monto);  
	
	double amount_monto_retencion = Integer.parseInt(retenciones);   
	String total_monto_retenciones = String.format("%,.0f", amount_monto_retencion); 
	
	
	
	if(nombre_oficina == null){
		nombre_oficina = "";
	}
	if(banco_trabajador != null){
		banco_trabajador = emplista.getNombrebanco();
	}else{
		banco_trabajador = "";
	}

	if(numero_cuenta == null){
		numero_cuenta = "";
	}
	
	if(valorTipoCuentaExcel == 4){
		
		String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"","VALE VISTA ENTREGADO DIRECCTAMENTE A LA EMPRESA","", "",""+total_monto+"",""+nombre_huerto+"",""+total_monto_retenciones+""};
		
		Cell celda8 = fila.createCell(0);
		if(numeroFor == 7){celda8.setCellStyle(style3);}
		celda8.setCellValue(titulo7[0]);
		
		Cell celda = fila.createCell(1);
		if(numeroFor == 7){celda.setCellStyle(style3);}
		celda.setCellValue(titulo7[1]);
		
		Cell celda2 = fila.createCell(2);
		if(numeroFor == 7){celda2.setCellStyle(style3);}
		celda2.setCellValue(titulo7[2]);
		
		Cell celda3 = fila.createCell(3);
		if(numeroFor == 7){celda3.setCellStyle(style3);}
		celda3.setCellValue(titulo7[3]);
		
		Cell celda4 = fila.createCell(4);
		if(numeroFor == 7){celda4.setCellStyle(style3);}
		celda4.setCellValue(titulo7[4]);
		
		Cell celda5 = fila.createCell(5);
		if(numeroFor == 7){celda5.setCellStyle(style3);}
		celda5.setCellValue(titulo7[5]);
		
		Cell celda6 = fila.createCell(6);
		celda6.setCellValue(titulo7[6]);
		
		if(numeroFor == 7){
			celda6.setCellStyle(combined);}
		else{
		celda6.setCellStyle(style2); 
		}
		Cell celda7 = fila.createCell(7);
		if(numeroFor == 7){celda7.setCellStyle(style3);}
		celda7.setCellValue(titulo7[7]);
		
		Cell celda28 = fila.createCell(8);
		celda28.setCellValue(titulo7[8]);
		
		if(numeroFor == 7){
			celda28.setCellStyle(combined);}
		else{
		celda28.setCellStyle(style2); 
		}
		
		
		

	numeroFor = numeroFor +1;
	fila = pagina.createRow(numeroFor);
	}else{
		String[] titulo7 = {""+cod_trabaj+"",""+nombre4+"",""+rutfinali+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+nombre_huerto+"",""+total_monto_retenciones+""};
	
	Cell celda8 = fila.createCell(0);
	if(numeroFor == 7){celda8.setCellStyle(style3);}
	celda8.setCellValue(titulo7[0]);
		
	Cell celda = fila.createCell(1);
	if(numeroFor == 7){celda.setCellStyle(style3);}
	celda.setCellValue(titulo7[1]);
	
	Cell celda2 = fila.createCell(2);
	if(numeroFor == 7){celda2.setCellStyle(style3);}
	celda2.setCellValue(titulo7[2]);
	
	Cell celda3 = fila.createCell(3);
	if(numeroFor == 7){celda3.setCellStyle(style3);}
	celda3.setCellValue(titulo7[3]);
	
	Cell celda4 = fila.createCell(4);
	if(numeroFor == 7){celda4.setCellStyle(style3);}
	celda4.setCellValue(titulo7[4]);
	
	Cell celda5 = fila.createCell(5);
	if(numeroFor == 7){celda5.setCellStyle(style3);}
	celda5.setCellValue(titulo7[5]);
	
	Cell celda6 = fila.createCell(6);
	celda6.setCellValue(titulo7[6]);
	if(numeroFor == 7){celda6.setCellStyle(combined);}
	else{
	celda6.setCellStyle(style2); 
	}
	Cell celda7 = fila.createCell(7);
	if(numeroFor == 7){celda7.setCellStyle(style3);}
	celda7.setCellValue(titulo7[7]);
	
	Cell celda29 = fila.createCell(8);
	celda29.setCellValue(titulo7[8]);
	
	if(numeroFor == 7){
		celda29.setCellStyle(combined);}
	else{
	celda29.setCellStyle(style2); 
	}
	
	
	

numeroFor = numeroFor +1;
fila = pagina.createRow(numeroFor);
	}	
}


pagina.autoSizeColumn(1);
pagina.autoSizeColumn(2);
pagina.autoSizeColumn(3);
pagina.autoSizeColumn(4);
pagina.autoSizeColumn(5);
pagina.autoSizeColumn(6);
pagina.autoSizeColumn(7);
pagina.autoSizeColumn(8);
pagina.autoSizeColumn(9);
pagina.autoSizeColumn(10);




/////////////////////END EXEL//////////////////////////
// Creamos el flujo de salida de datos,
// apuntando al archivo donde queremos 
// almacenar el libro de Excel
FileOutputStream salida = new FileOutputStream(ruta+archivo);

	ruta3 = ruta+archivo;
	
// Almacenamos el libro de 
// Excel via ese 
// flujo de datos
workbook.write(salida);

// Cerramos el libro para concluir operaciones
workbook.close();

LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
}
catch (FileNotFoundException ex) {
    LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
} 
catch (IOException e) {
    e.printStackTrace();
    LOGGER.log(Level.SEVERE, "Error de entrada/salida");
}
return ruta3;
//return null;

}


////////////////////////////////excel retenciones ////////////////////////////////////////////////////
	public static String getexcelRetenciones(ArrayList<sw_haberesDescuentos> row) throws Exception {

		String ruta = utils.FiniquitoTxt();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		

		String ruta3 = "";

		try {
			
			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("RETENCIONES" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("RETENCIONES");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			combined2.setAlignment(CellStyle.ALIGN_CENTER);
			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			// Indicamos que tendra un fondo azul aqua
			// con patron solido del color indicado
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);






//			double amount = monto_menos_retencion;
//			String totalf = String.format("%,.0f", amount);

		


			Row fila = pagina.createRow(0);

			String[] titulo6 = { "CODIGO", "RUT", "NOMBRE", "PERIODO", "CONCEPTO", "MONTO", "EMPRESA"};

			for (int i = 0; i < titulo6.length; i++) {

				Cell celda = fila.createCell(i);
				celda.setCellStyle(style3);
				celda.setCellValue(titulo6[i]);
			}

			fila = pagina.createRow(1);

			int numeroFor = 1;

			String cod_trabaj = "";
			String rut_trabajador = "";
			String nombre_trabajador = "";
			String periodo_trabajador = "";
			String concepto_trabajador = "";
			int monto;
			String empresa_trabajador = "";
			
			
	        
			for (sw_haberesDescuentos emplista : row) {

				
				CellStyle stylenew;
				stylenew=workbook.createCellStyle();

		        DataFormat format=workbook.createDataFormat();
		        stylenew.setDataFormat(format.getFormat("#,##0"));
		        
				cod_trabaj = emplista.getCodigo_trabajador();
				rut_trabajador = emplista.getRut();
				nombre_trabajador = emplista.getNombre();
				periodo_trabajador = emplista.getPeriodoS();
				concepto_trabajador = emplista.getConcepto();
				monto = emplista.getMonto();
				empresa_trabajador = emplista.getEmpresa();
				
				


					String[] titulo7 = { "" + cod_trabaj + "", "" + rut_trabajador + "", "" + nombre_trabajador + "",
							 "" + periodo_trabajador + "",
							"" + concepto_trabajador + "", "" + monto + "", "" + empresa_trabajador + "" };
						
					Cell celda0 = fila.createCell(0);
					celda0.setCellValue(titulo7[0]);
					
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2]);
					
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3]);
					
					Cell celda4 = fila.createCell(4);
					celda4.setCellValue(titulo7[4]);
					
					
				       
				     BigDecimal total = new BigDecimal(titulo7[5]).setScale(4, BigDecimal.ROUND_HALF_UP);
				     double totalfinal = total.doubleValue();
				     
					Cell celda5 = fila.createCell(5);
					celda5.setCellValue(totalfinal);
					celda5.setCellStyle(stylenew);
					
					Cell celda6 = fila.createCell(6);
					celda6.setCellValue(titulo7[6]);
					

					numeroFor = numeroFor + 1;
					fila = pagina.createRow(numeroFor);
				} 
			

			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			pagina.autoSizeColumn(3);
			pagina.autoSizeColumn(4);
			pagina.autoSizeColumn(5);
			pagina.autoSizeColumn(6);
			
			///////////////////// END EXEL//////////////////////////
			// Creamos el flujo de salida de datos,
			// apuntando al archivo donde queremos
			// almacenar el libro de Excel
			FileOutputStream salida = new FileOutputStream(ruta + archivo);
			
			
			ruta3 = ruta + archivo;
			

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();

			LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Error de entrada/salida");
		}
		return ruta3;
		// return null;

	}

}
