package SWDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.ImpuestoUnicoExcel;
import lib.db.ConnectionDB;
import wordCreator.utils;

public class sw_ImpuestoUnicoExcelDB {
	public static ArrayList<ImpuestoUnicoExcel> buscartrabajadores(int idSociedad, int periodo,int rolPrivado) throws Exception {
		Statement ps = null;
		String sql = "";
		Statement ps2 = null;
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<ImpuestoUnicoExcel> Lista = new ArrayList<ImpuestoUnicoExcel>();

		try {

			sql = "SELECT * FROM ( SELECT   distinct "
					+"de.codTrabajador AS CODIGO_TRABAJADOR,"
					+"UPPER(CONCAT(tr.apellidoPaterno,  ' ', tr.apellidoMaterno, ' ',tr.nombre)) as NOMBRE,"
					+"CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END AS RUT,"
					+"CONVERT(de.valor,SIGNED ) AS IMPUESTO "
					+"from sw_liquidacionDetalle de "
					+"INNER join sw_liquidacion li on li.cod_trabajador = de.codTrabajador "
					+"inner join trabajadores tr on tr.codigo = de.codTrabajador "
					+"where ";
					if(rolPrivado == 1){
						sql += " tr.rolPrivado in (1,0) and ";
					}else{
						sql += " tr.rolPrivado in (0) and ";
					}
					sql += " "+idSociedad+" = (select idSociedad from contratos where codigo_trabajador = de.codTrabajador and id = (select max(id) from contratos where codigo_trabajador = de.codTrabajador)) "
					+ "and de.idConcepto = 39 and de.periodo = "+periodo+" "
					+ "and li.id_sociedad = "+idSociedad+" ) AS T1 ";
					sql += "order by NOMBRE asc";
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();

			ImpuestoUnicoExcel tr1 = new ImpuestoUnicoExcel();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			  System.out.println(name);
			  	
			    
				if(i == 1){tr1.setCodigo(name);}
				else if(i == 2){tr1.setNombre(name);}
				else if(i == 3){tr1.setRut(name);}
				else if(i == 4){tr1.setImpuesto(name);}
				
				
			}
			
			Lista.add(tr1);

			while (rs.next()) {

				// añadir codigo de la comuna segun archivo de la AFC

				// public String articulocausal;
				//

				ImpuestoUnicoExcel tr = new ImpuestoUnicoExcel();
				tr.setCodigo(rs.getString("CODIGO_TRABAJADOR"));
				tr.setNombre(rs.getString("NOMBRE"));
				tr.setRut(rs.getString("RUT"));
				tr.setImpuesto(rs.getString("IMPUESTO"));
				

				Lista.add(tr);

			}
		

			sql2 = "SELECT 'TOTAL' AS CODIGO_TRABAJADOR ,'' AS NOMBRE,'' AS RUT,SUM(IMPUESTO) AS IMPUESTO FROM ( "
					+"select   distinct "
					+"de.codTrabajador AS CODIGO_TRABAJADOR,"
					+"UPPER(CONCAT(tr.apellidoPaterno,  ' ', tr.apellidoMaterno, ' ',tr.nombre)) as NOMBRE,"
					+"CASE WHEN tr.rut = '' THEN tr.rutTemporal ELSE tr.rut END AS RUT,"
					+"CONVERT(de.valor,SIGNED ) AS IMPUESTO "
					+"from sw_liquidacionDetalle de "
					+"INNER join sw_liquidacion li on li.cod_trabajador = de.codTrabajador "
					+"inner join trabajadores tr on tr.codigo = de.codTrabajador "
					+"where "
					+ ""+idSociedad+" = (select idSociedad from contratos where codigo_trabajador = de.codTrabajador and id = (select max(id) from contratos where codigo_trabajador = de.codTrabajador)) "
					+ "and de.idConcepto = 39 and de.periodo = "+periodo+" and li.id_sociedad = "+idSociedad+" "
					+") AS TOTAL";
			
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery(sql2);
			
			
			while (rs2.next()) {



				ImpuestoUnicoExcel tr2 = new ImpuestoUnicoExcel();
				
				tr2.setCodigo(rs2.getString("CODIGO_TRABAJADOR"));
				tr2.setNombre(rs2.getString("NOMBRE"));
				tr2.setRut(rs2.getString("RUT"));
				tr2.setImpuesto(rs2.getString("IMPUESTO"));
				

				Lista.add(tr2);

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			ps2.close();
			db.close();
		}
		return Lista;
	}
	
	public static String generarExcelImpuestoUnico(ArrayList<ImpuestoUnicoExcel> detalleEmpresa) throws Exception {

		String RutaArchivo = "";
		
		String ruta = utils.FiniquitoTxt();


		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			
			
			String Nombrearchivo = "InspuestoUnico.xlsx";
			File archivo = new File("InspuestoUnico.xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();
            
			CellStyle style2 = workbook.createCellStyle();
			 DataFormat format=workbook.createDataFormat();
			style2.setDataFormat(format.getFormat("#,##0"));
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			//style2.setAlignment(CellStyle.ALIGN_RIGHT);
	       
			
			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			
			
			   	int numeroFor = 0;
			    String  cod;
				String 	nombrecompleto = "";
				String 	rut = "";
				String  impuesto;
			
				for (ImpuestoUnicoExcel emplista : detalleEmpresa) {
					
					cod= emplista.getCodigo();
					nombrecompleto = emplista.getNombre();
					rut= emplista.getRut();
					impuesto= emplista.getImpuesto();
					
					
					
					String[] titulo7 = { "" + cod + "", "" + nombrecompleto + "", "" + rut + "",
							"" + impuesto + "" };
					
					if(numeroFor == 0){
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					celda.setCellStyle(style);
					
					
					}else{
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					
				    }
					
					if(numeroFor == 0){
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					celda1.setCellStyle(style);
					}else{
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					}
				
					if(numeroFor == 0){
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2]);
					celda2.setCellStyle(style);
					}else{
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2]);
					}
					
					if(numeroFor == 0){
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3]);
					celda3.setCellStyle(style);
					}else{
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(Integer.parseInt(titulo7[3]));
					celda3.setCellStyle(style2);
					
					}
					
					
					numeroFor = numeroFor + 1;
					fila = pagina.createRow(numeroFor);
				}
			
			

			pagina.autoSizeColumn(0);
			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			pagina.autoSizeColumn(3);
			pagina.autoSizeColumn(4);
			
		

			FileOutputStream salida = new FileOutputStream(ruta + archivo);
			RutaArchivo = ruta + Nombrearchivo;

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();

			// LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}",
			// archivo.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			// LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de
			// archivos");
		} catch (IOException e) {
			e.printStackTrace();
			// LOGGER.log(Level.SEVERE, "Error de entrada/salida");
			return "";
		}

		return RutaArchivo;
		
	}
	
	
	public static ArrayList<ImpuestoUnicoExcel> Empresas(int idSociedad, int periodo, String mes , int anio) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		String nombre_mes = "";
		switch(mes)
	     {
	        case "01": nombre_mes = "ENERO";  break;
	        case "02": nombre_mes = "FEBRERO";  break;
	        case "03": nombre_mes = "MARZO";  break;
	        case "04": nombre_mes = "ABRIL";  break;
	        case "05": nombre_mes = "MAYO";  break;
	        case "06": nombre_mes = "JUNIO";  break;
	        case "07": nombre_mes = "JULIO";  break;
	        case "08": nombre_mes = "AGOSTO";  break;
	        case "09": nombre_mes = "SEPTIEMBRE";  break;
	        case "10": nombre_mes = "OCTUBRE";  break;
	        case "11": nombre_mes = "NOVIEMBRE";  break;
	        case "12": nombre_mes = "DICIEMBRE";  break;
		 

	        default:   break;
		 
	      }
		
		ArrayList<ImpuestoUnicoExcel> Lista = new ArrayList<ImpuestoUnicoExcel>();

		try {

			sql = "SELECT "
					+"NOMBRE_SOCIEDAD AS EMPRESA,"
					+"SUM(IMPUESTO) AS MONTO "
					 	+"FROM "
					 		+"(SELECT DISTINCT "
					 			+"UPPER((SELECT denominacionSociedad FROM sociedad "
					 			+"where idSociedad =  li.id_sociedad)) as NOMBRE_SOCIEDAD , li.id_sociedad,"
					 			+"CONVERT( de.valor , SIGNED) AS IMPUESTO,"
					 			+ "li.cod_trabajador "
					 		 +"FROM sw_liquidacionDetalle de "
					 		 +"INNER JOIN sw_liquidacion li ON li.cod_trabajador = de.codTrabajador "
					 		 +"INNER JOIN trabajadores tr ON tr.codigo = de.codTrabajador "
					 		 +"WHERE "
					 		 	+"de.idConcepto = 39 "
					 		 	+"AND de.periodo = "+periodo+" ) AS TOTAL "
					 		 	+"WHERE ";
					if(idSociedad != 0){
						sql +="id_sociedad = "+idSociedad+" and "
								+ " "+idSociedad+" = (select idSociedad from contratos where codigo_trabajador = cod_trabajador and id = (select max(id) from contratos where codigo_trabajador = cod_trabajador)) "
								+ "group by id_sociedad";
					}else if(idSociedad == 0){
						sql +="id_sociedad  = (select idSociedad from contratos where codigo_trabajador = cod_trabajador and id = "
								+"(select max(id) from contratos where codigo_trabajador = cod_trabajador)) and "
								+ "id_sociedad IN (select idSociedad as id from sociedad  WHERE idSociedad NOT IN (-1)) group by id_sociedad ";
					}
					 		 			
				
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();
			
			ImpuestoUnicoExcel tr3 = new ImpuestoUnicoExcel();
			tr3.setNombre("IMPUESTO UNICO");
			tr3.setImpuesto(nombre_mes+" "+anio);
			Lista.add(tr3);
			

			ImpuestoUnicoExcel tr1 = new ImpuestoUnicoExcel();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			  System.out.println(name);
			  	
			    
				
				if(i == 1){tr1.setNombre(name);}
				else if(i == 2){tr1.setImpuesto(name);}
				
				
			}
			
			Lista.add(tr1);
            
			int montoTotal = 0;
			while (rs.next()) {

				ImpuestoUnicoExcel tr = new ImpuestoUnicoExcel();
			
				tr.setNombre(rs.getString("EMPRESA"));
				tr.setImpuesto(rs.getString("MONTO"));
				montoTotal = montoTotal + Integer.parseInt(rs.getString("MONTO"));

				Lista.add(tr);

			}
			
			ImpuestoUnicoExcel tr2 = new ImpuestoUnicoExcel();
			tr2.setNombre("TOTAL");
			tr2.setImpuesto(""+montoTotal);
			Lista.add(tr2);

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
		}
		return Lista;
	}
////////////////////////////////////EXCEL IMPUSTO UNICO DETALLE EMPRESAS//////////////////////////////////////////
	
	public static String generarExcelImpuestoUnicoEmpresa(ArrayList<ImpuestoUnicoExcel> detalleEmpresa) throws Exception {

		String RutaArchivo = "";
		
		String ruta = utils.FiniquitoTxt();


		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			
			
			String Nombrearchivo = "InspuestoUnicoEmpresa.xlsx";
			File archivo = new File("InspuestoUnicoEmpresa.xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();
            
			CellStyle style2 = workbook.createCellStyle();
			 DataFormat format=workbook.createDataFormat();
			style2.setDataFormat(format.getFormat("#,##0"));
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			//style2.setAlignment(CellStyle.ALIGN_RIGHT);
	       
			
			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			
			
			   	int numeroFor = 0;
				String 	nombrecompleto = "";
				String  impuesto;
			
				for (ImpuestoUnicoExcel emplista : detalleEmpresa) {
					
					
					nombrecompleto = emplista.getNombre();
					impuesto= emplista.getImpuesto();
					
					
					
					String[] titulo7 = { "" + nombrecompleto + "", "" + impuesto + "" };
					
					if(numeroFor == 0  || numeroFor == 1){
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					celda.setCellStyle(style);
					
					
					}else{
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					
				    }
					
					if(numeroFor == 0 || numeroFor == 1){
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					celda1.setCellStyle(style);
					}else{
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(Integer.parseInt(titulo7[1]));
					celda1.setCellStyle(style2);
					}
				
					
					
					
					numeroFor = numeroFor + 1;
					fila = pagina.createRow(numeroFor);
				}
			
			

			pagina.autoSizeColumn(0);
			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			
			
		

			FileOutputStream salida = new FileOutputStream(ruta + archivo);
			RutaArchivo = ruta + Nombrearchivo;

			// Almacenamos el libro de
			// Excel via ese
			// flujo de datos
			workbook.write(salida);

			// Cerramos el libro para concluir operaciones
			workbook.close();

			// LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}",
			// archivo.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			// LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de
			// archivos");
		} catch (IOException e) {
			e.printStackTrace();
			// LOGGER.log(Level.SEVERE, "Error de entrada/salida");
			return "";
		}

		return RutaArchivo;
		
	}
}
