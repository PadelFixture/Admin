package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;

import lib.classSW.AnticiposIndividuales;
import lib.classSW.Campo;
import lib.classSW.EnviarMailPreNominaAnticipo;
import lib.classSW.HorasAsistencia;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.PreNominaAnticipo;
import lib.classSW.TipoContrato;
import lib.db.ConnectionDB;
import wordCreator.utils;

@Controller
public class sw_AsignacionAnticiposDB {
	private static final Logger LOGGER = Logger.getLogger("newexcel.ExcelOOXML");
//-----------cargar por periodo  pantalla asignacion anticipos Simple-------------------------------------------------------------------------
	public static ArrayList<AnticiposIndividuales> getPeriodoAsignacionSimple(int p) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.periodo = "+p+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				AnticiposIndividuales hd = new AnticiposIndividuales();
				
				hd.setId(rs.getInt("id"));
				hd.setPeriodo(rs.getInt("periodo"));
				hd.setFecha(rs.getString("fecha"));
				hd.setCodtrabajador(rs.getInt("cod_trabajador"));
				hd.setTipopago(rs.getInt("tipoPago"));
				hd.setMontoingresado(rs.getInt("monto_ingresado"));
				hd.setEmpresa(rs.getInt("empresa"));
				hd.setDivision(rs.getInt("division"));
				hd.setSubDivision(rs.getInt("subDivision"));
				hd.setGrupo(rs.getInt("grupo"));
				hd.setSubgrupo(rs.getInt("subgrupo"));
				hd.setNombre(rs.getString("nombre"));
				hd.setAppaterno(rs.getString("apellidoPaterno"));
				hd.setAppmaterno(rs.getString("apellidoMaterno"));

				lista.add(hd);
				
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
//-----------cargar por Empresa-------------------------------------------------------------------------
		public static ArrayList<AnticiposIndividuales> getEmpresaAsignacionSimple(int p) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.empresa = "+p+"";
	      
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					AnticiposIndividuales hd = new AnticiposIndividuales();
					
					hd.setId(rs.getInt("id"));
					hd.setPeriodo(rs.getInt("periodo"));
					hd.setFecha(rs.getString("fecha"));
					hd.setCodtrabajador(rs.getInt("cod_trabajador"));
					hd.setTipopago(rs.getInt("tipoPago"));
					hd.setMontoingresado(rs.getInt("monto_ingresado"));
					hd.setEmpresa(rs.getInt("empresa"));
					hd.setDivision(rs.getInt("division"));
					hd.setSubDivision(rs.getInt("subDivision"));
					hd.setGrupo(rs.getInt("grupo"));
					hd.setSubgrupo(rs.getInt("subgrupo"));
					hd.setNombre(rs.getString("nombre"));
					hd.setAppaterno(rs.getString("apellidoPaterno"));
					hd.setAppmaterno(rs.getString("apellidoMaterno"));

					lista.add(hd);
					
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}
			return lista;
		}
//------------cargar por Division-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getDivisionAsignacionSimple(int p) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.division = "+p+"";
			      
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setTipopago(rs.getInt("tipoPago"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));

							lista.add(hd);
							
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
//------------cargar por SubDivision-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getSubDivisionAsignacionSimple(int p) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.subDivision = "+p+"";
			      
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));

							lista.add(hd);
							
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
//------------cargar por Grupo-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getGrupoAsignacionSimple(int p) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.grupo = "+p+"";
			      
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setTipopago(rs.getInt("tipoPago"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));

							lista.add(hd);
							
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}                                                 
				
//------------cargar-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getBuscarAsignacionSimple(String periodo,String fechaPago,String nombre_trabajador,String sociedad,String tipo_division,String tipo_subdivision,String grupo,String tipo_contrato,String estado_proceso) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select distinct sw.id,sw.monto_ingresado,sw.periodo, "
								+ "sw.fecha, sw.cod_trabajador,sw.empresa,tr.nombre,"
								+ " tr.apellidoPaterno,tr.apellidoMaterno,tr.division,tr.rut,tr.idHuerto,"
								+ "tr.idSubDivision as division ,tr.idSubDivision as subDivision,"
								+ "tr.grupo as grupo,tr.idSubGrupo as subgrupo ,tr.idCECO,"
								+ "(select descripcion from parametros pa inner join cuentaBancaria ca  where llave = ca.idTipoCuenta and pa.codigo = 'TIPO_DE_CUENTA' and ca.cuentaPrimaria = 1 and ca.codigoTrabajador = cod_trabajador ) as tipo_cuenta,"
								+ "(select nCuenta from cuentaBancaria ca where ca.cuentaPrimaria = 1 and ca.codigoTrabajador = cod_trabajador ) as numerocuenta,"
								+ "(select descripcion from parametros pa inner join cuentaBancaria ca  where llave = ca.idBanco and pa.codigo = 'BANCO' and ca.cuentaPrimaria = 1 and ca.codigoTrabajador = cod_trabajador ) as nombrebanco,"
								+ "(select rut from sociedad where idSociedad = sw.empresa ) as rutempresa,"
								+ "(select descripcion from campo where campo = idHuerto) as nombreHuerto "
								+ "from sw_asignacionAnticipos sw "
								+ "inner join trabajadores tr on tr.codigo = sw.cod_trabajador "
								+ " inner join contratos CO ON CO.id = sw.idContrato "
								+ "where  1 = 1 ";
							
						
						
						if("null".equals(periodo)){}else{sql += " and sw.periodo = "+periodo+"";}
						if("null".equals(fechaPago)){}else{sql += " and sw.fecha = '"+fechaPago+"'";}
						if("null".equals(nombre_trabajador)){}else{sql += " and sw.cod_trabajador = "+nombre_trabajador+"";}
						if("null".equals(sociedad)){}else{sql += " and sw.empresa = "+sociedad+"";}
						if("null".equals(tipo_division)){}else{sql += " and CO.idHuertoContrato = '"+tipo_division+"'";}
						if("null".equals(tipo_subdivision)){}else{sql += " and tr.idZona = '"+tipo_subdivision+"'";}
						if("null".equals(grupo)){}else{sql += " and tr.idCECO = '"+grupo+"'";}
						if("null".equals(tipo_contrato)){}else{sql += " and CO.tipoContrato = "+tipo_contrato+"";}
						if("null".equals(estado_proceso)){}else{sql += " and tr.estadoProceso = "+estado_proceso+" ";}	
						
						sql += " order by tr.apellidoPaterno ";
						
						System.out.println(sql);
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));
							hd.setRut(rs.getString("rut"));
							hd.setTipo_cuenta(rs.getString("tipo_cuenta"));
							hd.setNumerocuenta(rs.getString("numerocuenta"));
							hd.setNombrebanco(rs.getString("nombrebanco"));
							hd.setRutempresa(rs.getString("rutempresa"));
							hd.setIdhuerto(rs.getString("nombreHuerto"));
							hd.setCeco(rs.getString("idCECO"));
							
							
							

							lista.add(hd);
							
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static String GenerarExcelPreNominaAnticipos (ArrayList<PreNominaAnticipo> row) throws Exception{

	String RutaArchivo = "";
	int ultimoID = 0;
String ruta = utils.PreNominaAnticipo();

Date fechaActual = new Date();

//Formateando la fecha:
DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
try {

String TotalMontoPre_Nomina = "";
String rut_empresa = "";
String fecha_pago_pre_nomina = "";
int total_registro = 0;
String periodo = "";
String empresatext = "";
String huertotext = "";
String zonatext = "";
           
			for (PreNominaAnticipo emp1 : row) {

				TotalMontoPre_Nomina = emp1.getTotal_monto();
				rut_empresa = emp1.getRut_empresa();
				fecha_pago_pre_nomina = emp1.getFecha_pago();
				total_registro = total_registro +1;
				periodo = emp1.getPeriodo();
				empresatext = emp1.getEmpresatext();
				huertotext = emp1.getHuertotext();
				zonatext = emp1.getZonatext();
			}




//////////////////////EXCEL///////////////////////////

//Creamos el archivo donde almacenaremos la hoja
//de calculo, recuerde usar la extension correcta,
//en este caso .xlsx

String horaf = formatoHora.replaceAll("[:]", "");
String Nombrearchivo = "PRE_NOMINA_ANTICIPO_"+horaf+".xlsx";
File archivo = new File("PRE_NOMINA_ANTICIPO_"+horaf+".xlsx");

//Creamos el libro de trabajo de Excel formato OOXML
Workbook workbook = new XSSFWorkbook(); 

//La hoja donde pondremos los datos
Sheet pagina = workbook.createSheet("Pre-Nomina");
pagina.getPrintSetup().setLandscape(true);
pagina.setFitToPage(true);
PrintSetup ps = pagina.getPrintSetup();
ps.setFitWidth( (short) 1);
ps.setFitHeight( (short) 0);
//Creamos el estilo paga las celdas del encabezado
CellStyle style = workbook.createCellStyle();
CellStyle style2 = workbook.createCellStyle();
CellStyle style3 = workbook.createCellStyle();
CellStyle style4 = workbook.createCellStyle();


style2.setAlignment(CellStyle.ALIGN_RIGHT);
style3.setBorderTop(CellStyle.BORDER_DOUBLE);

style4.setBorderTop(CellStyle.BORDER_DOUBLE);


CellStyle combined = workbook.createCellStyle();
combined.setAlignment(CellStyle.ALIGN_RIGHT);
combined.setBorderTop(CellStyle.BORDER_DOUBLE);

CellStyle combined2 = workbook.createCellStyle();
combined2.setAlignment(CellStyle.ALIGN_CENTER);




//Indicamos que tendra un fondo azul aqua
//con patron solido del color indicado
style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
style.setFillPattern(CellStyle.SOLID_FOREGROUND);


String EmpresaMAYU = empresatext.toUpperCase();
String[] titulos = {EmpresaMAYU};
Row fila = pagina.createRow(0);
Cell celda1 = fila.createCell(0);
celda1.setCellValue(titulos[0]);

rut_empresa = rut_empresa.toUpperCase();



fila = pagina.createRow(1);
String[] TituloFiltros = {""+rut_empresa+"","","","","","FECHA DE PAGO",""+fecha_pago_pre_nomina+""};
for(int i = 0; i < TituloFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(TituloFiltros[i]);
}

String[] PeriodoSplit = periodo.split("-");

int meses;

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
String[] DetalleTotal = {"","","","","","TOTAL",""+TotalMontoPre_Nomina+""};
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
String[] titulo6 = {"NOMBRE","RUT","MEDIO DE PAGO","BANCO", "N° CUENTA","MONTO","HUERTO"};

for(int i = 0; i < titulo6.length; i++) 
{

Cell celda = fila.createCell(i);
celda.setCellStyle(style3); 
celda.setCellValue(titulo6[i]);
}

fila = pagina.createRow(7);

int numeroFor = 7;

String monto = "";
String nombre_trabajador = "";
String rut_trabajador = "";
String medio_de_pago = "";
String banco_trabajador = "";
String numero_cuenta = "";
String nombre_oficina = "";
String NombreHuerto = "";

for(PreNominaAnticipo emplista : row){

monto = emplista.getMonto_ingresado();

double amount_monto = Integer.parseInt(monto);   
String total_monto = String.format("%,.0f", amount_monto);
nombre_trabajador = emplista.getNombre_trabajador();
rut_trabajador = emplista.getRut_trabajador();
medio_de_pago = emplista.getMedio_pago();
banco_trabajador = emplista.getNombre_banco();
numero_cuenta = emplista.getNumero_cuenta();
NombreHuerto = emplista.getNombrehuerto();

nombre_trabajador = nombre_trabajador.toUpperCase();
rut_trabajador = rut_trabajador.toUpperCase();
medio_de_pago = medio_de_pago.toUpperCase();
banco_trabajador = banco_trabajador.toUpperCase();
nombre_oficina = nombre_oficina.toUpperCase();


String[] titulo7 = {""+nombre_trabajador+"",""+rut_trabajador+"",""+medio_de_pago+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+"",""+NombreHuerto+""};


Cell celda = fila.createCell(0);
if(numeroFor == 7){celda.setCellStyle(style3);}
celda.setCellValue(titulo7[0]);

Cell celdau = fila.createCell(1);
if(numeroFor == 7){celdau.setCellStyle(style3);}
celdau.setCellValue(titulo7[1]);


Cell celda3 = fila.createCell(2);
if(numeroFor == 7){celda3.setCellStyle(style3);}
celda3.setCellValue(titulo7[2]);


Cell celda4 = fila.createCell(3);
if(numeroFor == 7){celda4.setCellStyle(style3);}
celda4.setCellValue(titulo7[3]);


Cell celda5 = fila.createCell(4);
if(numeroFor == 7){celda5.setCellStyle(style3);}
celda5.setCellValue(titulo7[4]);


Cell celda6 = fila.createCell(5);
celda6.setCellValue(titulo7[5]);
if(numeroFor == 7){celda6.setCellStyle(combined);}
else{
celda6.setCellStyle(style2); 
}
Cell celda7 = fila.createCell(6);
if(numeroFor == 7){celda7.setCellStyle(style3);}
celda7.setCellValue(titulo7[6]);





numeroFor = numeroFor +1;
fila = pagina.createRow(numeroFor);


}









pagina.autoSizeColumn(0);
pagina.autoSizeColumn(1);
pagina.autoSizeColumn(2);
pagina.autoSizeColumn(3);
pagina.autoSizeColumn(4);
pagina.autoSizeColumn(5);
pagina.autoSizeColumn(6);
pagina.autoSizeColumn(7);
pagina.autoSizeColumn(8);
pagina.autoSizeColumn(9);

FileOutputStream salida = new FileOutputStream(ruta+archivo);
RutaArchivo = ruta+Nombrearchivo;






//Almacenamos el libro de 
//Excel via ese 
//flujo de datos
workbook.write(salida);

//Cerramos el libro para concluir operaciones
workbook.close();


SWDB.sw_AsignacionAnticiposDB.InsertRutaPre_nomina_excel(RutaArchivo);
PreNominaAnticipo var = SWDB.sw_AsignacionAnticiposDB.getUltimoIdPreNomina();
	ultimoID = var.getId();
	String idString = Integer.toString(ultimoID);
	



LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", archivo.getAbsolutePath());
}
catch (FileNotFoundException ex) {
LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
} 
catch (IOException e) {
e.printStackTrace();
LOGGER.log(Level.SEVERE, "Error de entrada/salida");
return "";
}
return Integer.toString(ultimoID);
//return null;

}

public static void InsertRutaPre_nomina_excel(String ruta) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	
	ConnectionDB db = new ConnectionDB();
	String rutafinal = ruta.replace("\\", "\\\\");
//	String rutafinal = ruta;
	
	try{
		
		sql ="INSERT INTO sw_preNomina (ruta) VALUES ('"+rutafinal+"');";
		ps = db.conn.prepareStatement(sql);
		ps.execute();
	
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
}

public static PreNominaAnticipo getUltimoIdPreNomina() throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	PreNominaAnticipo permiso = new PreNominaAnticipo();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select max(id)as id from sw_preNomina";

		ps = db.conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery(sql);
		if(rs.next()){
			
			permiso.setId(rs.getInt("id"));
			
			
		}
		
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return permiso;
}

public static PreNominaAnticipo getRutaArchivoPreNomina(int ultimoID) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	PreNominaAnticipo permiso = new PreNominaAnticipo();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select ruta from sw_preNomina where id = "+ultimoID+"";

		ps = db.conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery(sql);
		if(rs.next()){
			
			permiso.setRuta(rs.getString("ruta"));
		}
		
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return permiso;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean SendMailExcelPreNominaAnticipos (ArrayList<PreNominaAnticipo> row) throws Exception{

String ruta = utils.PreNominaAnticipo();

Date fechaActual = new Date();
System.out.println(fechaActual);

//Formateando la fecha:
DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
try {

String TotalMontoPre_Nomina = "";
String rut_empresa = "";
String fecha_pago_pre_nomina = "";
int total_registro = 0;
String periodo = "";
String empresatext = "";
String huertotext = "";
String zonatext = "";

for (PreNominaAnticipo emp1 : row) {

TotalMontoPre_Nomina = emp1.getTotal_monto();
rut_empresa = emp1.getRut_empresa();
fecha_pago_pre_nomina = emp1.getFecha_pago();
total_registro = total_registro +1;
periodo = emp1.getPeriodo();
empresatext = emp1.getEmpresatext();
huertotext = emp1.getHuertotext();
zonatext = emp1.getZonatext();
}


//////////////////////EXCEL///////////////////////////

//Creamos el archivo donde almacenaremos la hoja
//de calculo, recuerde usar la extension correcta,
//en este caso .xlsx

String horaf = formatoHora.replaceAll("[:]", "");
String Nombrearchivo = "PRE_NOMINA_ANTICIPO_"+horaf+".xlsx";
File archivo = new File("PRE_NOMINA_ANTICIPO_"+horaf+".xlsx");

//Creamos el libro de trabajo de Excel formato OOXML
Workbook workbook = new XSSFWorkbook(); 

//La hoja donde pondremos los datos
Sheet pagina = workbook.createSheet("Pre-Nomina");

//Creamos el estilo paga las celdas del encabezado
CellStyle style = workbook.createCellStyle();
CellStyle style2 = workbook.createCellStyle();
CellStyle style3 = workbook.createCellStyle();
CellStyle style4 = workbook.createCellStyle();

style2.setAlignment(CellStyle.ALIGN_RIGHT);
style3.setBorderTop(CellStyle.BORDER_DOUBLE);

style4.setBorderTop(CellStyle.BORDER_DOUBLE);

//Indicamos que tendra un fondo azul aqua
//con patron solido del color indicado
style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
style.setFillPattern(CellStyle.SOLID_FOREGROUND);

String[] titulos = {"DETALLE NOMINA"};
//Creamos una fila en la hoja en la posicion 0
Row fila = pagina.createRow(0);
//Indicamos el estilo que deseamos usar en la celda, en este caso el unico que hemos creado
Cell celda1 = fila.createCell(0);
celda1.setCellStyle(style); 
celda1.setCellValue(titulos[0]);

fila = pagina.createRow(1);
String[] TituloFiltros = {"PERIODO PROCESO","FECHA ANTICIPO","EMPRESA","HUERTO","ZONA"};
for(int i = 0; i < TituloFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellStyle(style3); 
celda.setCellValue(TituloFiltros[i]);
}


String EmpresaMAYU = empresatext.toUpperCase();
fila = pagina.createRow(2);
String[] DetalleFiltros = {""+periodo+"",""+fecha_pago_pre_nomina+"",""+EmpresaMAYU+"",""+huertotext+"",""+zonatext+""};
for(int i = 0; i < DetalleFiltros.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(DetalleFiltros[i]);
}


rut_empresa = rut_empresa.toUpperCase();

String[] titulo2 = {"RUT",""+rut_empresa+"","","","NOMINA","PRE-NOMINA","","","FECHA PAGO",""+fecha_pago_pre_nomina+"" };
fila = pagina.createRow(4);
for(int i = 0; i < titulo2.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellStyle(style4); 
celda.setCellValue(titulo2[i]);
}
fila = pagina.createRow(5);
String[] titulo3 = {"FECHA ACTUAL",""+formatoFecha+"","","","TIPO","REMUNERACIONES","","","MONTO TOTAL",""+TotalMontoPre_Nomina+"" };

for(int i = 0; i < titulo3.length; i++) 
{
Cell celda = fila.createCell(i);

celda.setCellValue(titulo3[i]);

if(i == 9){
celda.setCellStyle(style2);
}

}

fila = pagina.createRow(6);
String[] titulo4 = {"HORA",""+formatoHora+"","","","CONVENIO","001","    ","    ","TIPO DE PROCESO","PAGOS" };

for(int i = 0; i < titulo4.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(titulo4[i]);
}

fila = pagina.createRow(7);

String[] titulo5 = {"N° DE REGISTROS",""+total_registro+""};

for(int i = 0; i < titulo5.length; i++) 
{
Cell celda = fila.createCell(i);
celda.setCellValue(titulo5[i]);
}
fila = pagina.createRow(8);
fila = pagina.createRow(9);

fila = pagina.createRow(10);
String[] titulo6 = {"NOMBRE","RUT","MEDIO DE PAGO","OF. DESTINO","BANCO", "N° CUENTA","MONTO"};

for(int i = 0; i < titulo6.length; i++) 
{

Cell celda = fila.createCell(i);
celda.setCellStyle(style3); 
celda.setCellValue(titulo6[i]);
}

fila = pagina.createRow(11);

int numeroFor = 11;

String monto = "";
String nombre_trabajador = "";
String rut_trabajador = "";
String medio_de_pago = "";
String banco_trabajador = "";
String numero_cuenta = "";
String nombre_oficina = "";


for(PreNominaAnticipo emplista : row){

monto = emplista.getMonto_ingresado();

double amount_monto = Integer.parseInt(monto);   
String total_monto = String.format("%,.0f", amount_monto);
nombre_trabajador = emplista.getNombre_trabajador();
rut_trabajador = emplista.getRut_trabajador();
medio_de_pago = emplista.getMedio_pago();
banco_trabajador = emplista.getNombre_banco();
numero_cuenta = emplista.getNumero_cuenta();


nombre_trabajador = nombre_trabajador.toUpperCase();
rut_trabajador = rut_trabajador.toUpperCase();
medio_de_pago = medio_de_pago.toUpperCase();
banco_trabajador = banco_trabajador.toUpperCase();
nombre_oficina = nombre_oficina.toUpperCase();


String[] titulo7 = {""+nombre_trabajador+"",""+rut_trabajador+"",""+medio_de_pago+"",""+nombre_oficina+"",""+banco_trabajador+"", ""+numero_cuenta+"",""+total_monto+""};


Cell celda = fila.createCell(0);
celda.setCellValue(titulo7[0]);

Cell celda2 = fila.createCell(1);
celda2.setCellValue(titulo7[1]);

Cell celda3 = fila.createCell(2);
celda3.setCellValue(titulo7[2]);

Cell celda4 = fila.createCell(3);
celda4.setCellValue(titulo7[3]);

Cell celda5 = fila.createCell(4);
celda5.setCellValue(titulo7[4]);

Cell celda6 = fila.createCell(5);
celda6.setCellValue(titulo7[5]);

Cell celda7 = fila.createCell(6);

celda7.setCellStyle(style2); 
celda7.setCellValue(titulo7[6]);




numeroFor = numeroFor +1;
fila = pagina.createRow(numeroFor);


}

pagina.autoSizeColumn(0);
pagina.autoSizeColumn(1);
pagina.autoSizeColumn(2);
pagina.autoSizeColumn(3);
pagina.autoSizeColumn(4);
pagina.autoSizeColumn(5);
pagina.autoSizeColumn(6);
pagina.autoSizeColumn(7);
pagina.autoSizeColumn(8);
pagina.autoSizeColumn(9);

FileOutputStream salida = new FileOutputStream(ruta+archivo);
String RutaArchivo = ruta+Nombrearchivo;

//Almacenamos el libro de 
//Excel via ese 
//flujo de datos
workbook.write(salida);

//Cerramos el libro para concluir operaciones
workbook.close();

SWDB.sw_AsignacionAnticiposDB.InsertRutaPre_nomina_excel(RutaArchivo);
PreNominaAnticipo var = SWDB.sw_AsignacionAnticiposDB.getUltimoIdPreNomina();
int ultimoID = var.getId();

EnviarMailPreNominaAnticipo obj = new EnviarMailPreNominaAnticipo();
obj.MailPreNominaAnticipo(ultimoID,Nombrearchivo);

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

//------------cargar-------------------------------------------------------------------------
public static ArrayList<AnticiposIndividuales> getBuscarAsignacionSimpleImprimir(String per,String fec,String cod,String emp,String divi,String subd,String gru,String tipocuenta,int rolPrivado) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select distinct sw.id,sw.monto_ingresado,sw.periodo, "
				+ "sw.fecha, sw.cod_trabajador,sw.empresa,tr.nombre,"
				+ " tr.apellidoPaterno,tr.apellidoMaterno,tr.division,tr.rut,"
				+ "tr.idSubDivision as division ,tr.idSubDivision as subDivision,"
				+ "tr.grupo as grupo,tr.idSubGrupo as subgrupo ,"
				+ "(select descripcion from parametros pa inner join cuentaBancaria ca  where llave = ca.idTipoCuenta and pa.codigo = 'TIPO_DE_CUENTA' and ca.cuentaPrimaria = 1 and ca.codigoTrabajador = cod_trabajador ) as tipo_cuenta,"
				+ "(select nCuenta from cuentaBancaria ca where ca.cuentaPrimaria = 1 and ca.codigoTrabajador = cod_trabajador ) as numerocuenta,"
				+ "(select descripcion from parametros pa inner join cuentaBancaria ca  where llave = ca.idBanco and pa.codigo = 'BANCO' and ca.cuentaPrimaria = 1 and ca.codigoTrabajador = cod_trabajador ) as nombrebanco,"
				+ "(select rut from sociedad where idSociedad = sw.empresa ) as rutempresa "
				+ "from sw_asignacionAnticipos sw "
				+ "inner join trabajadores tr on tr.codigo = sw.cod_trabajador "
				+ "INNER JOIN cuentaBancaria CA on sw.cod_trabajador = CA.codigoTrabajador"
				+ " where  1 = 1 ";
		
		if("null".equals(per)){}else{sql += " and sw.periodo = "+per+"";}
		if("null".equals(fec)){}else{sql += " and sw.fecha = '"+fec+"'";}
		if("null".equals(cod)){}else{sql += " and sw.cod_trabajador = "+cod+"";}
		if("null".equals(emp)){}else{sql += " and sw.empresa = "+emp+"";}
		if("null".equals(divi)){}else{sql += " and tr.idHuerto = '"+divi+"'";}
		if("null".equals(subd)){}else{sql += " and tr.idZona = '"+subd+"'";}
		if("null".equals(gru)){}else{sql += " and tr.idCECO = '"+gru+"'";}
		if("null".equals(tipocuenta)){}else if("0".equals(tipocuenta)){sql += " AND CA.idTipoCuenta  NOT IN (2,4,3)";}else{sql += " and CA.idTipoCuenta = "+tipocuenta+"";}
				
		if(rolPrivado == 1){
			sql += " and  tr.rolPrivado in (1,0) ";
		}else{
			sql += " and  tr.rolPrivado in (0) ";
		}
		
		sql += " and sw.estado = 1";

		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			AnticiposIndividuales hd = new AnticiposIndividuales();
			
			hd.setId(rs.getInt("id"));
			hd.setPeriodo(rs.getInt("periodo"));
			hd.setFecha(rs.getString("fecha"));
			hd.setCodtrabajador(rs.getInt("cod_trabajador"));
			hd.setMontoingresado(rs.getInt("monto_ingresado"));
			hd.setEmpresa(rs.getInt("empresa"));
			hd.setDivision(rs.getInt("division"));
			hd.setSubDivision(rs.getInt("subDivision"));
			hd.setGrupo(rs.getInt("grupo"));
			hd.setSubgrupo(rs.getInt("subgrupo"));
			hd.setNombre(rs.getString("nombre"));
			hd.setAppaterno(rs.getString("apellidoPaterno"));
			hd.setAppmaterno(rs.getString("apellidoMaterno"));
			hd.setRut(rs.getString("rut"));
			hd.setTipo_cuenta(rs.getString("tipo_cuenta"));
			hd.setNumerocuenta(rs.getString("numerocuenta"));
			hd.setNombrebanco(rs.getString("nombrebanco"));
			hd.setRutempresa(rs.getString("rutempresa"));
			
			lista.add(hd);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return lista;
}


public static AnticiposIndividuales obtenerDatosTrabajadorAnticipos(String idtabla) throws Exception{
	Statement ps = null;		
	String sql="";
	ConnectionDB db = new ConnectionDB();
	AnticiposIndividuales tr = new AnticiposIndividuales();
	
	Date fechaActual = new Date();

	//Formateando la fecha:
	DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
	DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
	
	String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
	String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
	
	String horaf = formatoHora.replaceAll("[:]", "");;
	
	try{
		sql ="SELECT *,"
				+ "(SELECT nombre FROM trabajadores WHERE codigo = sw.cod_trabajador ) as nombreTrabajador,"
				+ "(SELECT apellidoPaterno FROM trabajadores WHERE codigo = sw.cod_trabajador ) as apellidoPate,"
				+ "(SELECT apellidoMaterno FROM trabajadores WHERE codigo = sw.cod_trabajador ) as apellidoMate,"
				+ "(SELECT fechaInicio_actividad FROM contratos where id = sw.idContrato) as fechaini,"
				+ "(select denominacionSociedad from sociedad WHERE idSociedad = sw.empresa ) as nombresociedad,"
				+ "(select rut from sociedad WHERE idSociedad = sw.empresa) as rutsociedad,"
				+ "(SELECT rut FROM trabajadores WHERE codigo = sw.cod_trabajador ) as ruttrab,"
				+"(select idTipoCuenta from cuentaBancaria where cuentaPrimaria = 1 and codigoTrabajador = sw.cod_trabajador) as idtipocuenta,"
				+ "(select descripcion from parametros where codigo = 'TIPO_DE_CUENTA' and llave = idtipocuenta) as tipocuentatrab"
				+ " FROM sw_asignacionAnticipos sw where id = "+idtabla+"";;
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		
		while(rs.next()){	
			tr.setCodigoTrabajador(rs.getString("cod_trabajador").toUpperCase());
			
			String fechaAnticipoSplit = rs.getString("fecha").toUpperCase();
			String[] fechaASplit = fechaAnticipoSplit.split("-");
			tr.setFechaPagoAnticipo(fechaASplit[2]+"-"+fechaASplit[1]+"-"+fechaASplit[0]);
			
			String text = rs.getString("monto_ingresado").toUpperCase(); 
			double value = Double.parseDouble(text);
		    
			String totalf = String.format("%,.0f", value); 
			
			tr.setMonto(totalf);
			
			tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
			tr.setAppPatTrabajador(rs.getString("apellidoPate").toUpperCase());
			tr.setAppMaternoTrabajador(rs.getString("apellidoMate").toUpperCase());
			tr.setTipo(rs.getString("tipocuentatrab").toUpperCase());
			tr.setFecha(formatoFecha);
			
			String fechaInicioSplit = rs.getString("fechaini").toUpperCase();
			String[] fechaISplit = fechaInicioSplit.split("-");
			tr.setFechaInicio(fechaISplit[2]+"-"+fechaISplit[1]+"-"+fechaISplit[0]);
			
			tr.setNombreEmpresa(rs.getString("nombresociedad").toUpperCase());
			tr.setRutCompletoEmpresa(rs.getString("rutsociedad").toUpperCase());
			tr.setRuttrabajador(rs.getString("ruttrab").toUpperCase());
			
			String per = rs.getString("periodo");
			String anio = per.substring(0,4);
			
			String per2 = per.substring(4);//mes
			int meses;
			 
            String mesPalabra = "";
			meses=Integer.parseInt(per2);
			 
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
			
		
			tr.setNombreperiod(mesPalabra+ " " + anio);
			
			
			
			return tr;
		}
		
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return tr;
}

public static ArrayList<AnticiposIndividuales> obtenerDatosTrabajadorAnticiposMasivos(String idtabla) throws Exception{
	Statement ps = null;		
	String sql="";
	ConnectionDB db = new ConnectionDB();
	ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
	
	Date fechaActual = new Date();

	//Formateando la fecha:
	DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
	DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
	
	String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
	String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
	
	String horaf = formatoHora.replaceAll("[:]", "");;
	
	try{
		sql ="SELECT *,"
				+ "(SELECT nombre FROM trabajadores WHERE codigo = sw.cod_trabajador ) as nombreTrabajador,"
				+ "(SELECT apellidoPaterno FROM trabajadores WHERE codigo = sw.cod_trabajador ) as apellidoPate,"
				+ "(SELECT apellidoMaterno FROM trabajadores WHERE codigo = sw.cod_trabajador ) as apellidoMate,"
				+ "(SELECT fechaInicio_actividad FROM contratos where id = sw.idContrato) as fecha_contrato,"
				+ "(select denominacionSociedad from sociedad WHERE idSociedad = sw.empresa ) as nombresociedad,"
				+ "(select rut from sociedad WHERE idSociedad = sw.empresa) as rutsociedad,"
				+ "(SELECT rut FROM trabajadores WHERE codigo = sw.cod_trabajador ) as ruttrab,"
				+"(select idTipoCuenta from cuentaBancaria where cuentaPrimaria = 1 and codigoTrabajador = sw.cod_trabajador) as idtipocuenta,"
				+ "(select descripcion from parametros where codigo = 'TIPO_DE_CUENTA' and llave = idtipocuenta) as tipocuentatrab"
				+ " FROM sw_asignacionAnticipos sw where id IN ("+idtabla+") order by apellidoPate";
		
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		
		while(rs.next()){	
					
			AnticiposIndividuales tr = new AnticiposIndividuales();
			 
			tr.setCodigoTrabajador(rs.getString("cod_trabajador").toUpperCase());
			
			String fechaAnticipoSplit = rs.getString("fecha").toUpperCase();
			String[] fechaASplit = fechaAnticipoSplit.split("-");
			tr.setFechaPagoAnticipo(fechaASplit[2]+"-"+fechaASplit[1]+"-"+fechaASplit[0]);
			
			
		
			String text = rs.getString("monto_ingresado").toUpperCase(); 
			double value = Double.parseDouble(text);
			
		    
			String totalf = String.format("%,.0f", value); 
			
			tr.setMonto(totalf);
			
			tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
			tr.setAppPatTrabajador(rs.getString("apellidoPate").toUpperCase());
			tr.setAppMaternoTrabajador(rs.getString("apellidoMate").toUpperCase());
//            tr.setTipo("TRANSFER");
			tr.setTipo(rs.getString("tipocuentatrab").toUpperCase());
			tr.setFecha(formatoFecha);
			
			String fechaInicioSplit = rs.getString("fecha_contrato").toUpperCase();
			String[] fechaISplit = fechaInicioSplit.split("-");
			tr.setFechaInicio(fechaISplit[2]+"-"+fechaISplit[1]+"-"+fechaISplit[0]);
			tr.setRuttrabajador(rs.getString("ruttrab").toUpperCase());
			tr.setNombreEmpresa(rs.getString("nombresociedad").toUpperCase());
			tr.setRutCompletoEmpresa(rs.getString("rutsociedad").toUpperCase());
			
			String per = rs.getString("periodo");
			String anio = per.substring(0,4);
			
			String per2 = per.substring(4);//mes
			int meses;
			 
            String mesPalabra = "";
			meses=Integer.parseInt(per2);
			 
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
			
		
			tr.setNombreperiod(mesPalabra+ " " + anio);
			
			
			
			
     	
			lista.add(tr);
			
		}
		
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return lista;
}

public static ArrayList<LoadTrabajadorSociedad> getTrabajadoresFecha(String sociedad,String splitFecha) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
	ConnectionDB db = new ConnectionDB();
	
	try{
		
		 
		
				sql = "select TR.codigo,TR.nombre,TR.apellidoPaterno,"
						+ "TR.apellidoMaterno,TR.rut, TR.division,TR.idSubDivision,"
						+ "TR.grupo,TR.idSubgrupo "
						+ "from contratos CO  left join trabajadores TR "
						+ "on TR.codigo = CO.codigo_trabajador left "
						+ "join sw_asignacionAnticipos ant on TR.codigo = ant.cod_trabajador where 1 = 1 ";
			
	
		
		
		if("null".equals(sociedad)){}else{sql += "and CO.idSociedad = "+sociedad+"";}
		if("null".equals(splitFecha)){}else{sql += " and ant.fecha != '"+splitFecha+"'";}
		
		
					
		
	sql += " and CO.EstadoContrato = 1;";
		
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();
			
			e.setCodigotrabajador(rs.getInt("codigo"));
			e.setNombre(rs.getString("nombre"));
			e.setApellidoPaterno(rs.getString("apellidoPaterno"));
			e.setApellidoMaterno(rs.getString("apellidoMaterno"));
			e.setIddivision(rs.getInt("division"));
			e.setIdsubdivision(rs.getInt("idSubDivision"));
			e.setIdgrupo(rs.getInt("grupo"));
		    e.setIdsubgrupo(rs.getInt("idSubgrupo"));
		    e.setRut(rs.getString("rut"));
		
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}

//////////////////////////TODOS LOS TIPOS DE CONTRATO///////////////////////////////////////////////////////////
public static ArrayList<TipoContrato> getTipoContrato()  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<TipoContrato> lista = new ArrayList<TipoContrato>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "SELECT * FROM parametros where codigo = 'TIPO_CONTRATO' and activo = 1 order by descripcion asc ";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			TipoContrato cr = new TipoContrato();
			cr.setDescripcion(rs.getString("descripcion"));
			cr.setLlave(rs.getInt("llave"));
			lista.add(cr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}
/////////////GET DESCRIPCION HUERTO ////////////////////////////////////////////////////////////////////////

public static ArrayList<Campo> getCampoByCampo(String CodCampo)  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<Campo> lista = new ArrayList<Campo>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "select descripcion from campo where campo = '"+CodCampo+"'";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			Campo cr = new Campo();
			cr.setDescripcion(rs.getString("descripcion"));
			lista.add(cr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}

//-----------MONTO GANADO-------------------------------------------------------------------------
	public static ArrayList<AnticiposIndividuales> getMontoGanadoTrabajador(int codigo, String periodo, int idcontrato,int diaPago) throws Exception{
		
	
		String dia = "01";
	    String periodoFinal = periodo+"-"+dia;
	    String[] periodoInt = periodo.split("-");
	    
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;

		ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
		ConnectionDB db = new ConnectionDB();
		try{
			
			sql = "SELECT SUM(rd.valor_liquido) AS montoGanado,"
					+ "(SELECT agro FROM trabajadores WHERE codigo = "+codigo+") as agro,"
					+ "(SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = "+idcontrato+") AS sueldoBase,"
					+ "((SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = "+idcontrato+") / 30) as valorDia,"
					+ "(((SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = "+idcontrato+") / 30)* "+diaPago+") as valor_actual_a_la_fecha,"
					+ "(select  ifnull(SUM((REPLACE(DATEDIFF(fecha_desde,fecha_hasta),'-','') +1)),0) from SAN_CLEMENTE.permiso_licencia "
					+ "where codigo_trabajador = "+codigo+"  and idContrato = "+idcontrato+" "
					+ "and (fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') or (fecha_hasta BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') ))) as diasMenos,"
					+ "(((SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = "+idcontrato+") / 30)* "
					+ "("+diaPago+" - (select  ifnull(SUM((REPLACE(DATEDIFF(fecha_desde,fecha_hasta),'-','') +1)),0) as diasMenos  from SAN_CLEMENTE.permiso_licencia "
					+ "where codigo_trabajador = "+codigo+" and idContrato = "+idcontrato+" "
					+ "and (fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') or (fecha_hasta BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') ))) )) as total,"
					+ "(SELECT descripcion FROM parametros WHERE codigo = 'TIPO_TRAB' and llave = (SELECT agro FROM trabajadores WHERE codigo = "+codigo+")) AS nombreTipoTrabajador "
					+ "FROM rendimiento_diario rd "
					+ "LEFT JOIN rendimiento_general rg ON(rg.codigo_rg = rd.codigo_rg) "
					+ "WHERE (EXTRACT(YEAR_MONTH FROM rd.fecha_i) =  EXTRACT(YEAR_MONTH FROM '"+periodoFinal+"')"
					+ "OR EXTRACT(YEAR_MONTH FROM rg.fecha) =  EXTRACT(YEAR_MONTH FROM '"+periodoFinal+"')) AND rd.trabajador = (select id from trabajadores where codigo = "+codigo+") and "
					+ "idContrato = "+idcontrato+" and rd.estado = 3;";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				AnticiposIndividuales hd = new AnticiposIndividuales();
				
				hd.setMontoganado(rs.getInt("montoGanado"));
				hd.setIdagro(rs.getInt("agro"));
				hd.setTotalalafecha(rs.getInt("total"));
				hd.setNombreTipoTrabajador(rs.getString("nombreTipoTrabajador"));
				hd.setDiasmenos(rs.getInt("diasMenos"));

				lista.add(hd);
				
			}		
			
			if(lista.get(0).getIdagro() == 0){
				// calcula el liquido mensual 
				sql2="select getLiquido("+codigo+","+idcontrato+","+periodoInt[0]+periodoInt[1]+",0) as liquido";
			   
					ps2 = db.conn.prepareStatement(sql2);
					ResultSet rs2 = ps2.executeQuery(sql2);
					
				while (rs2.next()) {

					lista.get(0).setTotalalafecha(((rs2.getInt("liquido") / 30) * diaPago) - ((rs2.getInt("liquido") / 30) * lista.get(0).getDiasmenos()) );
					lista.get(0).setMontoganadoactual(((rs2.getInt("liquido") / 30) * diaPago) - ((rs2.getInt("liquido") / 30) * lista.get(0).getDiasmenos()) );
				
				}	
			
				  sql3 = "select monto_ingresado from sw_asignacionAnticipos WHERE cod_trabajador = "+codigo+" and idContrato = "+idcontrato+" and periodo = "+periodoInt[0]+periodoInt[1]+"";
			      
				  ps3 = db.conn.prepareStatement(sql3);
				  ResultSet rs3 = ps3.executeQuery(sql3);
				  while (rs3.next()) {

						lista.get(0).setTotalalafecha(lista.get(0).getTotalalafecha()- rs3.getInt("monto_ingresado"));
						lista.get(0).setAnticipo(rs3.getInt("monto_ingresado"));
					}	
			}else if(lista.get(0).getIdagro() == 1){
				
				  sql4 = "select monto_ingresado from sw_asignacionAnticipos WHERE cod_trabajador = "+codigo+" and idContrato = "+idcontrato+" and periodo = "+periodoInt[0]+periodoInt[1]+"";
				  ps4 = db.conn.prepareStatement(sql4);
				  ResultSet rs4 = ps4.executeQuery(sql4);
				  
				  if (!rs4.isBeforeFirst()) {
					  if(lista.get(0).getMontoganado() == 0) {
					    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado());
					     }else{
					    	 lista.get(0).setMontoganadoactual(lista.get(0).getMontoganado());
					    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado());
					    	 lista.get(0).setAnticipo(rs4.getInt("monto_ingresado"));
					    	 
					     }
					  
				  }else
				  {
				  
					  while (rs4.next()) {
						  
						     if(lista.get(0).getMontoganado() == 0) {
						    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado());
						     }else{
						    	 lista.get(0).setMontoganadoactual(lista.get(0).getMontoganado());
						    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado()- rs4.getInt("monto_ingresado"));
						    	 lista.get(0).setAnticipo(rs4.getInt("monto_ingresado"));
						    	 
						     }
						}	
				  }// end else
			}
			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	
	//-----------MONTO GANADO-------------------------------------------------------------------------
		public static ArrayList<AnticiposIndividuales> getMontoGanadosinidcontrato(int codigo, String periodo,int diaPago) throws Exception{
			
		
			String dia = "01";
		    String periodoFinal = periodo+"-"+dia;
		    String[] periodoInt = periodo.split("-");
		    
			String sql = "";
			String sql2 = "";
			String sql3 = "";
			String sql4 = "";
			
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			PreparedStatement ps4 = null;
		
			ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
			ConnectionDB db = new ConnectionDB();
			try{
				
				sql = "SELECT SUM(rd.valor_liquido) AS montoGanado,"
						+ " (select max(id) from contratos where codigo_trabajador = "+codigo+") as idContrato,"
						+ "(select fechaInicio_actividad from contratos where codigo_trabajador = "+codigo+" and  id=(SELECT max(id) FROM contratos where codigo_trabajador = "+codigo+"))as fechaInicio_actividad,"
						+ "(SELECT agro FROM trabajadores WHERE codigo = "+codigo+") as agro,"
						+ "(SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = (select max(id) from contratos where codigo_trabajador = "+codigo+" )) AS sueldoBase,"
						+ "((SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = (select max(id) from contratos where codigo_trabajador = "+codigo+" )) / 30) as valorDia,"
						+ "(((SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = (select max(id) from contratos where codigo_trabajador = "+codigo+" )) / 30)* "+diaPago+") as valor_actual_a_la_fecha,"
						+ "(select  ifnull(SUM((REPLACE(DATEDIFF(fecha_desde,fecha_hasta),'-','') +1)),0) from SAN_CLEMENTE.permiso_licencia "
						+ "where codigo_trabajador = "+codigo+"  and idContrato = (select max(idContrato) from permiso_licencia where codigo_trabajador = "+codigo+" ) "
						+ "and (fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') or (fecha_hasta BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') ))) as diasMenos,"
						+ "(((SELECT sueldoBase FROM contratos WHERE codigo_trabajador = "+codigo+" and id = (select max(id) from contratos where codigo_trabajador = "+codigo+" )) / 30)* "
						+ "("+diaPago+" - (select  ifnull(SUM((REPLACE(DATEDIFF(fecha_desde,fecha_hasta),'-','') +1)),0) as diasMenos  from SAN_CLEMENTE.permiso_licencia "
						+ "where codigo_trabajador = "+codigo+" and idContrato = (select max(idContrato) from permiso_licencia where codigo_trabajador = "+codigo+" ) "
						+ "and (fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') or (fecha_hasta BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') ))) )) as total,"
						+ "(SELECT descripcion FROM parametros WHERE codigo = 'TIPO_TRAB' and llave = (SELECT agro FROM trabajadores WHERE codigo = "+codigo+")) AS nombreTipoTrabajador "
						+ "FROM rendimiento_diario rd "
						+ "LEFT JOIN rendimiento_general rg ON(rg.codigo_rg = rd.codigo_rg) "
						+ "WHERE (EXTRACT(YEAR_MONTH FROM rd.fecha_i) =  EXTRACT(YEAR_MONTH FROM '"+periodoFinal+"')"
						+ "OR EXTRACT(YEAR_MONTH FROM rg.fecha) =  EXTRACT(YEAR_MONTH FROM '"+periodoFinal+"')) AND rd.trabajador = (SELECT id FROM trabajadores WHERE codigo = "+codigo+") and idContrato = (select max(id) from contratos where codigo_trabajador = "+codigo+" ) and rd.estado = 3;";

				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					AnticiposIndividuales hd = new AnticiposIndividuales();
					
					hd.setMontoganado(rs.getInt("montoGanado"));
					hd.setIdagro(rs.getInt("agro"));
					hd.setTotalalafecha(rs.getInt("total"));
					hd.setNombreTipoTrabajador(rs.getString("nombreTipoTrabajador"));
					hd.setDiasmenos(rs.getInt("diasMenos"));
					hd.setIdcontrato(rs.getInt("idContrato"));
				    hd.setFecha(rs.getString("fechaInicio_actividad"));

					lista.add(hd);
				}		
				
				if(lista.get(0).getIdagro() == 0){
					// calcula el liquido mensual 
					sql2="select getLiquido("+codigo+","+lista.get(0).getIdcontrato()+","+periodoInt[0]+periodoInt[1]+",0) as liquido";
				   
						ps2 = db.conn.prepareStatement(sql2);
						ResultSet rs2 = ps2.executeQuery(sql2);
						
					while (rs2.next()) {

						lista.get(0).setTotalalafecha(((rs2.getInt("liquido") / 30) * diaPago) - ((rs2.getInt("liquido") / 30) * lista.get(0).getDiasmenos()) );
						lista.get(0).setMontoganadoactual(((rs2.getInt("liquido") / 30) * diaPago) - ((rs2.getInt("liquido") / 30) * lista.get(0).getDiasmenos()) );
					}	
				
					  sql3 = "select monto_ingresado from sw_asignacionAnticipos WHERE cod_trabajador = "+codigo+" and idContrato = "+lista.get(0).getIdcontrato()+" and periodo = "+periodoInt[0]+periodoInt[1]+"";
				      
					  ps3 = db.conn.prepareStatement(sql3);
					  ResultSet rs3 = ps3.executeQuery(sql3);
					  while (rs3.next()) {

							lista.get(0).setTotalalafecha(lista.get(0).getTotalalafecha()- rs3.getInt("monto_ingresado"));
							lista.get(0).setAnticipo(rs3.getInt("monto_ingresado"));
							
						}	
					  
					  
				}else if(lista.get(0).getIdagro() == 1){
					
					sql4 = "select monto_ingresado from sw_asignacionAnticipos WHERE cod_trabajador = "+codigo+" and idContrato = "+lista.get(0).getIdcontrato()+" and periodo = "+periodoInt[0]+periodoInt[1]+"";
					  ps4 = db.conn.prepareStatement(sql4);
					  ResultSet rs4 = ps4.executeQuery(sql4);
					  
					  if (!rs4.isBeforeFirst()) {
						  if(lista.get(0).getMontoganado() == 0) {
						    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado());
						     }else{
						    	 lista.get(0).setMontoganadoactual(lista.get(0).getMontoganado());
						    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado());
						    	 lista.get(0).setAnticipo(rs4.getInt("monto_ingresado"));
						    	 
						     }
						  
					  }else{
					  
					  while (rs4.next()) {
						  
						     if(lista.get(0).getMontoganado() == 0) {
						    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado());
						     }else{
						    	 lista.get(0).setMontoganadoactual(lista.get(0).getMontoganado());
						    	 lista.get(0).setMontoganado(lista.get(0).getMontoganado()- rs4.getInt("monto_ingresado"));
						    	 lista.get(0).setAnticipo(rs4.getInt("monto_ingresado"));
						    	 
						     }
						}	
					  }	
					
				}
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}
			return lista;
		}
		
		
		public static int obtenerNCuentaSapNominaAnticipo() throws Exception {

			int total = 0;
			Statement stmt = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {

				stmt = db.conn.createStatement();

				sql = "select n_cuenta from Cuentas_SAP where id = 1";

				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					total = rs.getInt(1);
				}
				rs.close();
				stmt.close();
				db.conn.close();
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: " + e.getMessage());
				System.out.println("sql: " + sql);
				throw new Exception("getLimitesAll: " + e.getMessage());
			} finally {
				db.close();
			}
			return total;
		}
		
		public static ArrayList<AnticiposIndividuales> getAllfechasxPereiodo(int periodo, String empresa) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql ="select DATE_FORMAT(fecha, '%e-%c-%Y') as fecha  from sw_asignacionAnticipos where periodo = "+periodo+" and estado = 0 and empresa = "+empresa+" group by fecha";
	          
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					AnticiposIndividuales hd = new AnticiposIndividuales();
					
					hd.setFecha(rs.getString("fecha"));
					lista.add(hd);
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}
			return lista;
		}
		
		public static String insertExcelAnticipos (AnticiposIndividuales r) throws Exception{
			
			
//			String escaped = r.getRuta().replaceAll("\\\\", "//");
			String escaped = r.getRuta().replace("\\", "\\\\");
			String respuesta = "";
			String codigosTrabajadores = "";
			ConnectionDB db = new ConnectionDB();
			
			try{
	        	db.conn.setAutoCommit(false);
	            PreparedStatement ps = null ;
	            OPCPackage pkg = OPCPackage.open(new File(escaped));
	           
	            XSSFWorkbook wb = new XSSFWorkbook(pkg);
	            XSSFSheet sheet = wb.getSheetAt(0);
	            Row row;
	            for(int i=1; i<=sheet.getLastRowNum(); i++){
	                row = sheet.getRow(i);
	                
	                int codTrabajador = (int)row.getCell(0).getNumericCellValue();
	                //int idContrato = (int)row.getCell(1).getNumericCellValue();
	                
	                int periodo = (int)row.getCell(1).getNumericCellValue();

	                int montoIngresado = (int)row.getCell(3).getNumericCellValue();
	                
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                String fechaformat = sdf.format(row.getCell(2).getDateCellValue());
	                
	                HorasAsistencia idContratos = sw_HorasAsistenciaDB.getIdContratoSleccion(codTrabajador,fechaformat);
	              	int idContrato2 = idContratos.getId_contrato();
	              	int sociedad = idContratos.getEmpresa();
	              	
	              	if(idContrato2 == 0){
	              		
	              		codigosTrabajadores = codigosTrabajadores+ " " +codTrabajador;
	              	}else{
	              		
	              		String sql = "INSERT INTO sw_asignacionAnticipos (periodo,fecha,cod_trabajador,monto_ingresado,empresa,estado,idContrato) VALUES"
	                    		+ "  ("+periodo+",'"+fechaformat+"',"+codTrabajador+", "+montoIngresado+", "+sociedad+",0,"+idContrato2+")";
	                    
	                    ps = (PreparedStatement) db.conn.prepareStatement(sql);
	                    ps.execute();
	                    System.out.println("Import rows "+i);
	              	}
	            }
	            db.conn.commit();
	            ps.close();
	            db.close();
	            pkg.close();
	            
	            File archivo = new File(escaped);

	            boolean estatus = archivo.delete();;

	            if (!estatus) {

	                System.out.println("Error no se ha podido eliminar el  archivo");

	           }else{

	                System.out.println("Se ha eliminado el archivo exitosamente");

	           }
	            
	            if(codigosTrabajadores == ""){
	            	 return respuesta = "Datos Ingresado con Exito";
	            }else{
	            	 return respuesta = "Codigo Trabajador "+ codigosTrabajadores+" no se encuentran Registrado o la Fecha Ingresada No Corresponde a su Contrato";
	            }
	           
			}catch (SQLException e){
				respuesta = "Error: "+ e.getMessage();
			}catch (Exception e){
				respuesta = "Error: "+ e.getMessage();
			}finally {
			
			
				db.close();
			}
			return respuesta;
		}

}
