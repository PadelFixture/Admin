package SWDB;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import lib.classSW.IncidentesMutualidad;
import lib.classSW.LoadTrabajadorSociedad;
import lib.db.ConexionBD;
import lib.db.ConnectionDB;
import wordCreator.utils;

public class sw_IncidentesMutualidadDB {
	
	
	 private static final Logger LOGGER = Logger.getLogger("newexcel.ExcelOOXML");
	
	public static ArrayList<IncidentesMutualidad> getIncidentes(int incidente) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<IncidentesMutualidad> data = new ArrayList<IncidentesMutualidad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			if(incidente == 1){
				sql = "select * from parametros where codigo = 'INCIDENTE_MUTUALIDAD_SEGURIDAD'";
			}else{
				sql = "select * from parametros where codigo = 'INCIDENTE_MUTUALIDAD_HIGIENE'";
			}
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				IncidentesMutualidad e = new IncidentesMutualidad();
				e.setNombre(rs.getString("descripcion"));
				e.setLlave(rs.getInt("llave"));
				e.setId(rs.getInt("id"));
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
	
	
	public static boolean updateIncidentesMutualidad(IncidentesMutualidad r) throws  Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {

			sql = "UPDATE parametros SET descripcion ='"+r.getNombre()+"' WHERE id='"+r.getId()+"'";  
				ps = db.conn.prepareStatement(sql);
				System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
					
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}
	
	public static boolean eliminarIncidenteMutualidad(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM parametros WHERE id=" + id + "";
			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static String insertarIncidenciaM(IncidentesMutualidad r) throws Exception {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		String respuesta = "";
		ConnectionDB db = new ConnectionDB();
		int llave_ = 0;
		try {
			
			if (r.getNumero() == 1) {
				sql2 = "select llave from parametros where codigo = 'INCIDENTE_MUTUALIDAD_SEGURIDAD' and id = (SELECT max(id) FROM parametros where codigo = 'INCIDENTE_MUTUALIDAD_SEGURIDAD')";
			}else{
				sql2 = "select llave from parametros where codigo = 'INCIDENTE_MUTUALIDAD_HIGIENE' and id = (SELECT max(id) FROM parametros where codigo = 'INCIDENTE_MUTUALIDAD_HIGIENE')";
			}
				ps2 = db.conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery(sql2);

			
					while (rs2.next()) {

						llave_ = rs2.getInt("llave");
						
					}

				
			if (r.getNumero() == 1) {
				
				sql = "INSERT INTO parametros (codigo, llave, descripcion, activo) VALUES (?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				

				ps.setString(1, "INCIDENTE_MUTUALIDAD_SEGURIDAD");
				ps.setInt(2, llave_ + 1);
				ps.setString(3, r.getNombre());
				ps.setInt(4, 1);

				ps.execute();

				respuesta = "Enviado";
			}else{
				
				sql = "INSERT INTO parametros (codigo, llave, descripcion, activo) VALUES (?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				
				ps.setString(1, "INCIDENTE_MUTUALIDAD_HIGIENE");
				ps.setInt(2, llave_ + 1);
				ps.setString(3, r.getNombre());
				ps.setInt(4, 1);

				ps.execute();

				respuesta = "Enviado";
				
			}
			

			return respuesta;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			db.close();
		}
		return "no";
	}
	
	public static ArrayList<IncidentesMutualidad> getCausas() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<IncidentesMutualidad> data = new ArrayList<IncidentesMutualidad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			
			sql = "select * from parametros where codigo in('INCIDENTE_MUTUALIDAD_SEGURIDAD','INCIDENTE_MUTUALIDAD_HIGIENE')";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				IncidentesMutualidad e = new IncidentesMutualidad();
				e.setNombre(rs.getString("descripcion"));
				e.setLlave(rs.getInt("llave"));
				e.setId(rs.getInt("id"));
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
	
	// registro de accidentes - detalle
	public static ArrayList<IncidentesMutualidad> 
	getregistrodeincidentesdetalle(String soc, String huerto_, String zona_, String ceco_,
			                       String periodo,String estado_proceso,String tipo_accidente,
			                       	String clasificacion_accidente, String causa_accidente,
			                       	String anio_inicio,String anio_fin, String cod) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<IncidentesMutualidad> data = new ArrayList<IncidentesMutualidad>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select "
					+"CONVERT(DATE_FORMAT(sw.fechaHoraAccidente, '%Y'),UNSIGNED INTEGER)  as anio,"
					+"CONVERT(DATE_FORMAT(sw.fechaHoraAccidente, '%m'),UNSIGNED INTEGER)  as Mes,"
					+ "CASE WHEN tr.rut = '' then tr.rutTemporal else tr.rut end as rut,"
					+"upper(concat(tr.apellidoPaterno, ' ' , tr.apellidoMaterno , ' ' , tr.nombre)) as nombre,"
					+"DATE_FORMAT(sw.fechaHoraAccidente, '%d-%m-%Y') as Fecha_del_Accidente,"
					+"DATE_FORMAT(sw.fechaHasta, '%d-%m-%Y') as Fecha_de_Alta,"
					+"IF(pe.numero_folio is null, 'N/A', pe.numero_folio) as Folio_accidente,"
					+"IF(sw.subtipo_licencia = 184, 'Del Trabajo', 'Trayecto') as Tipo_de_Accidente,"
					+"(CASE "
					        +"WHEN sw.clasificacion_accidente = 1 THEN 'Grave' "
					        +"WHEN sw.clasificacion_accidente = 2 THEN 'Fatal' "
					        +"WHEN sw.clasificacion_accidente = 3 THEN  'Otro' "
					        +"else 'N/A'"
					    +"END) AS Clasificacion_del_Accidente,"
					+"IF((select descripcion from parametros where  id = sw.causaAccidente) is null, 'N/A', (select descripcion from parametros where  id = sw.causaAccidente)) as Causa_Accidente,"
					+"TIMESTAMPDIFF(day, sw.fechaHoraAccidente, sw.fechaHasta) +1 AS Dias_de_Reposo "
					+"from sw_m_eventos_mutualidad sw "
					+"left join trabajadores tr on tr.codigo = sw.codigo_trabajador "
					+"left join permiso_licencia pe on pe.id = sw.idLicencia "
					+"left join contratos co on co.id = sw.id_contrato "
					+"left join campo ca on ca.campo = co.idHuertoContrato where 1 = 1 ";

			if ("null".equals(soc)) {
			} else {
				sql += " and sw.id_sociedad = " + soc + "";
			}
			if ("null".equals(huerto_)) {
				} else {
					sql += " and tr.idHuerto = '" + huerto_ + "'";
				}
			if ("null".equals(zona_)) {
			} else {
				sql += " and tr.idZona = '" + zona_ + "'";
			}
			if ("null".equals(ceco_)) {
			} else {
				sql += " and tr.idCECO = '" + ceco_ + "'";
			}
			if ("null".equals(periodo)) {
			} else {
				sql += " and CONVERT(DATE_FORMAT(sw.fechaHoraAccidente, '%m'),UNSIGNED INTEGER)  = " + periodo + "";
			}
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso  = " + estado_proceso + "";
			}
			if ("null".equals(tipo_accidente)) {
			} else {
				sql += " and sw.subtipo_licencia  = " + tipo_accidente + "";
			}
			if ("null".equals(clasificacion_accidente)) {
			} else {
				sql += " and sw.clasificacion_accidente  = " + clasificacion_accidente + "";
			}
			if ("null".equals(causa_accidente)) {
			} else {
				sql += " and sw.causaAccidente  = " + causa_accidente + "";
			}
			if ("null".equals(cod)) {
			} else {
				sql += " and sw.codigo_trabajador = " + cod + "";
			}
			if ("null".equals(anio_inicio) && "null".equals(anio_fin)) {
			} else {
				sql += " and DATE_FORMAT(sw.fechaHoraAccidente, '%Y') BETWEEN  "+anio_inicio+" and "+anio_fin+" ";
			}

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				IncidentesMutualidad e = new IncidentesMutualidad();

				e.setAnio(rs.getInt("anio"));
				e.setMes(rs.getInt("Mes"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setFechaaccidente(rs.getString("Fecha_del_Accidente"));
				e.setFechaalta(rs.getString("Fecha_de_Alta"));
				e.setFolio(rs.getString("Folio_accidente"));
				e.setClasificacionaccidente(rs.getString("Clasificacion_del_Accidente"));
				e.setCausaaccidente(rs.getString("Causa_Accidente"));
				e.setDiareposo(rs.getInt("Dias_de_Reposo"));
				e.setTipoaccidente(rs.getString("Tipo_de_Accidente"));
				
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	
	///////////////////////////////// excel registro de accidentes detalle
	///////////////////////////////// /////////////////////////////////////////////////////
	public static String getExelDetalleregistroAccidente(ArrayList<IncidentesMutualidad> row) throws Exception {

		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		String ruta3 = "";

		try {
		
			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("REGISTRO_ACCIDENTES_DETALLE" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("REGISTRO_ACCIDENTES_DETALLE");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle stylenew = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			
			
			
		   
			
			
			CellStyle style5 = workbook.createCellStyle();
		    style5.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		    style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    Font font2 = workbook.createFont();
	            font2.setColor(IndexedColors.BLACK.getIndex());
	            style5.setFont(font2);
			
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
//			combined.setBorderTop(CellStyle.BORDER_DOUBLE);
//			combined.setFont(font); // color de letra
			
			
			
			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
//			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			
			

			String empresaMys = "Registro de Accidentes - Detalle";
			String[] titulos = { empresaMys };

			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);

			celda1.setCellValue(titulos[0]);

			

			fila = pagina.createRow(1);
			String[] TituloFiltros = { "Año","Mes", "Rut Trabajador", "Nombre Trabajador", "Fecha del Accidente",
									   "Fecha de Alta", "FFolio Accidente","Tipo de Accidente",
									   "Clasificación del Accidente","Causa Accidente","Dias de Reposo"};
			
			
			for (int i = 0; i < TituloFiltros.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellValue(TituloFiltros[i]);
				celda.setCellStyle(style5);
			}

			fila = pagina.createRow(2);

			int anio_ = 0;
			int mes_ = 0;
			String rut_ = "";
			String nombre_ = "";
			String fecha_ac_ = "";
			String fecha_alt = "";
			String folio_ = "";
			String tipo_ac = "";
			String clasificacion_ = "";
			String causa_ = "";
			int dias_ = 0;

			
			
			int numeroFor = 2;
			for (IncidentesMutualidad emp1 : row) {
				anio_ = emp1.getAnio();
				mes_ =  emp1.getMes();
				rut_ = emp1.getRut();
				nombre_ = emp1.getNombre();
				fecha_ac_ = emp1.getFechaaccidente();
				fecha_alt = emp1.getFechaalta();
				folio_ = emp1.getFolio();
				tipo_ac = emp1.getTipoaccidente();
				clasificacion_ = emp1.getClasificacionaccidente();
				causa_ = emp1.getCausaaccidente();
				dias_ = emp1.getDiareposo();
				
			

				String[] titulo7 = { "" + anio_ + "", "" + mes_ + "", "" + rut_ + "",
						"" + nombre_ + "", "" + fecha_ac_ + "", "" + fecha_alt + "",
						"" + folio_ + "", "" + tipo_ac + "", "" + clasificacion_ + "", "" + causa_ + "", "" + dias_ + "" };

				Cell celda0 = fila.createCell(0);
				celda0.setCellValue(titulo7[0]);
				celda0.setCellStyle(combined);
				
				Cell celda1_1 = fila.createCell(1);
				celda1_1.setCellValue(titulo7[1]);
				celda1_1.setCellStyle(combined);
				
				Cell celda2 = fila.createCell(2);
				celda2.setCellValue(titulo7[2]);
				
				Cell celda3 = fila.createCell(3);
				celda3.setCellValue(titulo7[3]);
				
				Cell celda4 = fila.createCell(4);
				celda4.setCellValue(titulo7[4]);
				
				Cell celda5 = fila.createCell(5);
				celda5.setCellValue(titulo7[5]);
				
				Cell celda6 = fila.createCell(6);
				celda6.setCellValue(titulo7[6]);
				
				Cell celda7 = fila.createCell(7);
				celda7.setCellValue(titulo7[7]);
				
				Cell celda8 = fila.createCell(8);
				celda8.setCellValue(titulo7[8]);
				
				Cell celda9 = fila.createCell(9);
				celda9.setCellValue(titulo7[9]);
				
				Cell celda10 = fila.createCell(10);
				celda10.setCellValue(titulo7[10]);
				celda10.setCellStyle(combined);

				numeroFor = numeroFor + 1;
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
			pagina.autoSizeColumn(10);

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
	
	
	///////////////////////////////// excel registro de accidentes resumen
	///////////////////////////////// /////////////////////////////////////////////////////
	public static String getExelDetalleregistroAccidenteResumen(ArrayList<IncidentesMutualidad> row) throws Exception {

		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		String ruta3 = "";

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("REGISTRO_ACCIDENTES_RESUMEN" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("REGISTRO_ACCIDENTES_RESUMEN");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle stylenew = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());

			CellStyle style5 = workbook.createCellStyle();
			style5.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font font2 = workbook.createFont();
			font2.setColor(IndexedColors.BLACK.getIndex());
			style5.setFont(font2);

			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			// combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			// combined.setFont(font); // color de letra

			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);

			String empresaMys = "Registro de Accidentes - Resumen";
			String[] titulos = { empresaMys };

			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);

			celda1.setCellValue(titulos[0]);

			fila = pagina.createRow(1);
			String[] TituloFiltros = {"Rut Trabajador", "Nombre Trabajador", "Fecha del Accidente",
					"Fecha de Alta", "FFolio Accidente", "Tipo de Accidente", "Clasificación del Accidente",
					"Causa Accidente", "Dias de Reposo" };

			for (int i = 0; i < TituloFiltros.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellValue(TituloFiltros[i]);
				celda.setCellStyle(style5);
			}

			fila = pagina.createRow(2);

			String rut_ = "";
			String nombre_ = "";
			String fecha_ac_ = "";
			String fecha_alt = "";
			String folio_ = "";
			String tipo_ac = "";
			String clasificacion_ = "";
			String causa_ = "";
			int dias_ = 0;

			int numeroFor = 2;
			for (IncidentesMutualidad emp1 : row) {
				rut_ = emp1.getRut();
				nombre_ = emp1.getNombre();
				fecha_ac_ = emp1.getFechaaccidente();
				fecha_alt = emp1.getFechaalta();
				folio_ = emp1.getFolio();
				tipo_ac = emp1.getTipoaccidente();
				clasificacion_ = emp1.getClasificacionaccidente();
				causa_ = emp1.getCausaaccidente();
				dias_ = emp1.getDiareposo();

				String[] titulo7 = {"" + rut_ + "", "" + nombre_ + "",
						"" + fecha_ac_ + "", "" + fecha_alt + "", "" + folio_ + "", "" + tipo_ac + "",
						"" + clasificacion_ + "", "" + causa_ + "", "" + dias_ + "" };

				

				Cell celda2 = fila.createCell(0);
				celda2.setCellValue(titulo7[0]);

				Cell celda3 = fila.createCell(1);
				celda3.setCellValue(titulo7[1]);

				Cell celda4 = fila.createCell(2);
				celda4.setCellValue(titulo7[2]);

				Cell celda5 = fila.createCell(3);
				celda5.setCellValue(titulo7[3]);

				Cell celda6 = fila.createCell(4);
				celda6.setCellValue(titulo7[4]);

				Cell celda7 = fila.createCell(5);
				celda7.setCellValue(titulo7[5]);

				Cell celda8 = fila.createCell(6);
				celda8.setCellValue(titulo7[6]);

				Cell celda9 = fila.createCell(7);
				celda9.setCellValue(titulo7[7]);

				Cell celda10 = fila.createCell(8);
				celda10.setCellValue(titulo7[8]);
				celda10.setCellStyle(combined);

				numeroFor = numeroFor + 1;
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
	
	public static ArrayList<LoadTrabajadorSociedad> getallTrabajaInformeAcc(String soc,String huerto_, String zona_,String ceco_, String estado_proceso,String tipo_accidente,String clasificacion_accidente,String causa_accidente) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select "
					+ "CASE WHEN tr.rut = '' then tr.rutTemporal else tr.rut end as rut,"
					+"upper(concat(tr.apellidoPaterno, ' ' , tr.apellidoMaterno , ' ' , tr.nombre)) as nombre,"
					+ "sw.codigo_trabajador "
					+"from sw_m_eventos_mutualidad sw "
					+"left join trabajadores tr on tr.codigo = sw.codigo_trabajador "
					+"left join permiso_licencia pe on pe.id = sw.idLicencia "
					+"left join contratos co on co.id = sw.id_contrato "
					+"left join campo ca on ca.campo = co.idHuertoContrato where 1 = 1 ";
			
			if ("null".equals(soc)) {
			} else {
				sql += " and sw.id_sociedad = " + soc + "";
			}
			if ("null".equals(huerto_)) {
				} else {
					sql += " and tr.idHuerto = '" + huerto_ + "'";
				}
			if ("null".equals(zona_)) {
			} else {
				sql += " and tr.idZona = '" + zona_ + "'";
			}
			if ("null".equals(ceco_)) {
			} else {
				sql += " and tr.idCECO = '" + ceco_ + "'";
			}
			
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso  = " + estado_proceso + "";
			}
			if ("null".equals(tipo_accidente)) {
			} else {
				sql += " and sw.subtipo_licencia  = " + tipo_accidente + "";
			}
			if ("null".equals(clasificacion_accidente)) {
			} else {
				sql += " and sw.clasificacion_accidente  = " + clasificacion_accidente + "";
			}
			if ("null".equals(causa_accidente)) {
			} else {
				sql += " and sw.causaAccidente  = " + causa_accidente + "";
			}

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();

				e.setCodigotrabajador(rs.getInt("codigo_trabajador"));
				e.setNombre(rs.getString("nombre"));
				e.setRut(rs.getString("rut"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	
	///////////////////////////////// excel registro de accidentes trabajador
	///////////////////////////////// /////////////////////////////////////////////////////
	public static String getExelDetalleregistroAccidenteTrabajador(ArrayList<IncidentesMutualidad> row) throws Exception {

		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		String ruta3 = "";

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("REGISTRO_ACCIDENTES_TRABAJADOR" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("REGISTRO_ACCIDENTES_TRABAJADOR");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle stylenew = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());

			CellStyle style5 = workbook.createCellStyle();
			style5.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font font2 = workbook.createFont();
			font2.setColor(IndexedColors.BLACK.getIndex());
			style5.setFont(font2);

			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			// combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			// combined.setFont(font); // color de letra

			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);

			String empresaMys = "Registro de Accidentes - Trabajador";
			String[] titulos = { empresaMys };

			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);

			celda1.setCellValue(titulos[0]);
			
			
			String nombre2_ = row.get(0).getNombre();
          
			fila = pagina.createRow(1);
			String[] TituloFiltros1 = { "Trabajador:",""+nombre2_+""};

			for (int i = 0; i < TituloFiltros1.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellValue(TituloFiltros1[i]);
				
			}
			
			fila = pagina.createRow(2);
			
           
			fila = pagina.createRow(3);
			String[] TituloFiltros = { "Rut Trabajador", "Nombre Trabajador", "Fecha del Accidente", "Fecha de Alta",
					"FFolio Accidente", "Tipo de Accidente", "Clasificación del Accidente", "Causa Accidente",
					"Dias de Reposo" };

			for (int i = 0; i < TituloFiltros.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellValue(TituloFiltros[i]);
				celda.setCellStyle(style5);
			}

			fila = pagina.createRow(4);

			String rut_ = "";
			String nombre_ = "";
			String fecha_ac_ = "";
			String fecha_alt = "";
			String folio_ = "";
			String tipo_ac = "";
			String clasificacion_ = "";
			String causa_ = "";
			int dias_ = 0;

			int numeroFor = 4;
			for (IncidentesMutualidad emp1 : row) {
				rut_ = emp1.getRut();
				nombre_ = emp1.getNombre();
				fecha_ac_ = emp1.getFechaaccidente();
				fecha_alt = emp1.getFechaalta();
				folio_ = emp1.getFolio();
				tipo_ac = emp1.getTipoaccidente();
				clasificacion_ = emp1.getClasificacionaccidente();
				causa_ = emp1.getCausaaccidente();
				dias_ = emp1.getDiareposo();

				String[] titulo7 = { "" + rut_ + "", "" + nombre_ + "", "" + fecha_ac_ + "", "" + fecha_alt + "",
						"" + folio_ + "", "" + tipo_ac + "", "" + clasificacion_ + "", "" + causa_ + "",
						"" + dias_ + "" };

				Cell celda2 = fila.createCell(0);
				celda2.setCellValue(titulo7[0]);

				Cell celda3 = fila.createCell(1);
				celda3.setCellValue(titulo7[1]);

				Cell celda4 = fila.createCell(2);
				celda4.setCellValue(titulo7[2]);

				Cell celda5 = fila.createCell(3);
				celda5.setCellValue(titulo7[3]);

				Cell celda6 = fila.createCell(4);
				celda6.setCellValue(titulo7[4]);

				Cell celda7 = fila.createCell(5);
				celda7.setCellValue(titulo7[5]);

				Cell celda8 = fila.createCell(6);
				celda8.setCellValue(titulo7[6]);

				Cell celda9 = fila.createCell(7);
				celda9.setCellValue(titulo7[7]);

				Cell celda10 = fila.createCell(8);
				celda10.setCellValue(titulo7[8]);
				celda10.setCellStyle(combined);

				numeroFor = numeroFor + 1;
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
	
	
public static List<IncidentesMutualidad> getDatosporanio(int anio, int empresa) throws Exception {
		
	
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		List<IncidentesMutualidad> lista = new ArrayList<>();
		try{
				sql = "SELECT "
					    +"(SELECT " 
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" " 
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 01) AS EneroAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                  +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 01) / (SELECT " 
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-01-01') AND LAST_DAY('"+anio+"-01-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-01-01') AND LAST_DAY('"+anio+"-01-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT " 
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 01) / (SELECT " 
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-01-01') AND LAST_DAY('"+anio+"-01-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-01-01') AND LAST_DAY('"+anio+"-01-01'))))) * 100)) AS tasa_enero, "
					    +"(SELECT " 
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 02) AS FebreroAccidentes,"
					    +"(IF(((SELECT " 
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
 					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 02) / (SELECT " 
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-02-01') AND LAST_DAY('"+anio+"-02-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-02-01') AND LAST_DAY('"+anio+"-02-01'))))) * 100 IS NULL, "
					        +"0.0000,"
					        +"((SELECT " 
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 02) / (SELECT  "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-02-01') AND LAST_DAY('"+anio+"-02-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-02-01') AND LAST_DAY('"+anio+"-02-01'))))) * 100)) AS tasa_Febrero,"
					    +"(SELECT " 
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 03) AS MarzoAccidentes,"
					    +"(IF(((SELECT " 
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 03) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-03-01') AND LAST_DAY('"+anio+"-03-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-03-01') AND LAST_DAY('"+anio+"-03-01'))))) * 100 IS NULL,"
					        +"0.0000, "
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" " 
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 03) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-03-01') AND LAST_DAY('"+anio+"-03-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-03-01') AND LAST_DAY('"+anio+"-03-01'))))) * 100)) AS tasa_marzo, "
					    +"(SELECT " 
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 04) AS AbrilAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 04) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-04-01') AND LAST_DAY('"+anio+"-04-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-04-01') AND LAST_DAY('"+anio+"-04-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 04) / (SELECT  "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-04-01') AND LAST_DAY('"+anio+"-04-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-04-01') AND LAST_DAY('"+anio+"-04-01'))))) * 100)) AS tasa_Abril, "
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 05) AS MayoAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 05) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-05-01') AND LAST_DAY('"+anio+"-05-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-05-01') AND LAST_DAY('"+anio+"-05-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 05) / (SELECT  "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                   +" AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-05-01') AND LAST_DAY('"+anio+"-05-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-05-01') AND LAST_DAY('"+anio+"-05-01'))))) * 100)) AS tasa_Mayo,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 06) AS JunioAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 06) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-06-01') AND LAST_DAY('"+anio+"-06-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-06-01') AND LAST_DAY('"+anio+"-06-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 06) / (SELECT  "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-06-01') AND LAST_DAY('"+anio+"-06-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-06-01') AND LAST_DAY('"+anio+"-06-01'))))) * 100)) AS tasa_Junio,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
 					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 07) AS JulioAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad)"
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 07) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-07-01') AND LAST_DAY('"+anio+"-07-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-07-01') AND LAST_DAY('"+anio+"-07-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 07) / (SELECT  "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-07-01') AND LAST_DAY('"+anio+"-07-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-07-01') AND LAST_DAY('"+anio+"-07-01'))))) * 100)) AS tasa_Julio,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 08) AS AgostoAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 08) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-08-01') AND LAST_DAY('"+anio+"-08-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-08-01') AND LAST_DAY('"+anio+"-08-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 08) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-08-01') AND LAST_DAY('"+anio+"-08-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-08-01') AND LAST_DAY('"+anio+"-08-01'))))) * 100)) AS tasa_Agosto,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 09) AS SeptiembreAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 09) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-09-01') AND LAST_DAY('"+anio+"-09-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-09-01') AND LAST_DAY('"+anio+"-09-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 09) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-09-01') AND LAST_DAY('"+anio+"-09-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-09-01') AND LAST_DAY('"+anio+"-09-01'))))) * 100)) AS tasa_Septiembre,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 10) AS OctubreAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 10) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-10-01') AND LAST_DAY('"+anio+"-10-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-10-01') AND LAST_DAY('"+anio+"-10-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT " 
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 10) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-10-01') AND LAST_DAY('"+anio+"-10-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-10-01') AND LAST_DAY('"+anio+"-10-01'))))) * 100)) AS tasa_Octubre,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM "
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 11) AS NoviembreAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 11) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-11-01') AND LAST_DAY('"+anio+"-11-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-11-01') AND LAST_DAY('"+anio+"-11-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 11) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-11-01') AND LAST_DAY('"+anio+"-11-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-11-01') AND LAST_DAY('"+anio+"-11-01'))))) * 100)) AS tasa_Noviembre,"
					    +"(SELECT "
					            +"COUNT(idEventosMutualidad) "
					        +"FROM " 
					            +"sw_m_eventos_mutualidad "
					        +"WHERE "
					            +"id_sociedad = "+empresa+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                +"AND DATE_FORMAT(fechaRegistro, '%m') = 12) AS DiciembreAccidentes,"
					    +"(IF(((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 12) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-12-01') AND LAST_DAY('"+anio+"-12-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-12-01') AND LAST_DAY('"+anio+"-12-01'))))) * 100 IS NULL,"
					        +"0.0000,"
					        +"((SELECT "
					                +"COUNT(idEventosMutualidad) "
					            +"FROM "
					                +"sw_m_eventos_mutualidad "
					            +"WHERE "
					                +"id_sociedad = "+empresa+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
					                    +"AND DATE_FORMAT(fechaRegistro, '%m') = 12) / (SELECT "
					                +"COUNT(DISTINCT codigo_trabajador) "
					            +"FROM "
					                +"contratos "
					            +"WHERE "
					                +"idSociedad IN ("+empresa+") "
					                    +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
					                    +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-12-01') AND LAST_DAY('"+anio+"-12-01')) "
					                    +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-12-01') AND LAST_DAY('"+anio+"-12-01'))))) * 100)) AS tasa_Diciembre,  "
					                    		+ ""+anio+" AS anio";
		    
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					IncidentesMutualidad p = new IncidentesMutualidad();
					
				    p.setEnero_Accidentes(rs.getInt("EneroAccidentes"));
				    p.setFebrero_Accidentes(rs.getInt("FebreroAccidentes"));
				    p.setMarzo_Accidentes(rs.getInt("MarzoAccidentes"));
				    p.setAbril_Accidentes(rs.getInt("AbrilAccidentes"));
				    p.setMayo_Accidentes(rs.getInt("MayoAccidentes"));
				    p.setJunio_Accidentes(rs.getInt("JunioAccidentes"));
				    p.setJulio_Accidentes(rs.getInt("JulioAccidentes"));
				    p.setAgosto_Accidentes(rs.getInt("AgostoAccidentes"));
				    p.setSeptiembre_Accidentes(rs.getInt("SeptiembreAccidentes"));
				    p.setOctubre_Accidentes(rs.getInt("OctubreAccidentes"));
				    p.setNoviembre_Accidentes(rs.getInt("NoviembreAccidentes"));
				    p.setDiciembre_Accidentes(rs.getInt("DiciembreAccidentes"));
				    
				    p.setTasa_Enero(rs.getDouble("tasa_enero"));
				    p.setTasa_Febrero(rs.getDouble("tasa_Febrero"));
				    p.setTasa_Marzo(rs.getDouble("tasa_marzo"));
				    p.setTasa_Abril(rs.getDouble("tasa_Abril"));
				    p.setTasa_Mayo(rs.getDouble("tasa_Mayo"));
				    p.setTasa_Junio(rs.getDouble("tasa_Junio"));
				    p.setTasa_Julio(rs.getDouble("tasa_Julio"));
				    p.setTasa_Agosto(rs.getDouble("tasa_Agosto"));
				    p.setTasa_Septiembre(rs.getDouble("tasa_Septiembre"));
				    p.setTasa_Octubre(rs.getDouble("tasa_Octubre"));
				    p.setTasa_Noviembre(rs.getDouble("tasa_Noviembre"));
				    p.setTasa_Diciembre(rs.getDouble("tasa_Diciembre"));
				
				    p.setAnio(rs.getInt("anio"));
				
					lista.add(p);
				
				}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}


	///////////////////////////////// excel registro de accidentes trabajador
	///////////////////////////////// /////////////////////////////////////////////////////
	public static String getExeltasaAccidentabilidad(List<IncidentesMutualidad> incidente)
			throws Exception {

		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		String ruta3 = "";

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("ACCIDENTES_ACCIDENTABILIDAD" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("ACCIDENTES_ACCIDENTABILIDAD");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle stylenew = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			 
			CellStyle style5 = workbook.createCellStyle();
			style5.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font font2 = workbook.createFont();
			font2.setColor(IndexedColors.BLACK.getIndex());
			style5.setFont(font2);
			
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			// combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			// combined.setFont(font); // color de letra

			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);

			String empresaMys = "Accidentes - Tasa Accidentabilidad";
			String[] titulos = { empresaMys };

			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);

			celda1.setCellValue(titulos[0]);

			

			

			fila = pagina.createRow(1);
			
			
			int size = incidente.size();
			System.out.println(size);
			
			String datos = "MES / AÑO,";
			
			for (IncidentesMutualidad emp1 : incidente) {
			
				
				System.out.println(emp1.getAnio());
				datos = datos+"ACC "+ emp1.getAnio()+",";
			}
			
			int for2 = 1;
			for (IncidentesMutualidad emp1 : incidente) {
			
				
				System.out.println(emp1.getAnio());
				
				if(for2 == size){
					datos = datos+"TASA ACC "+ emp1.getAnio()+"";
				}else{
					datos = datos+"TASA ACC "+ emp1.getAnio()+",";
				}
				
				for2 = for2 +1;
			}
		 
			String datosnew = datos.replaceAll("'", "\"");
            String strArray[] = datosnew.split(",");

			for (int i = 0; i < strArray.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellValue(strArray[i]);
				celda.setCellStyle(style5);
			}

			fila = pagina.createRow(2);

			
			int enero_ = 0;
			int febrero_ = 0;
			int marzo_ = 0;
			int abril_ = 0;
			int mayo_ = 0;
			int junio_ = 0;
			int julio_ = 0;
			int agosto_ = 0;
			int septiembre_ = 0;
			int octubre_ = 0;
			int noviembre_ = 0;
			int diciembre_ = 0;
			
			double tasa_Enero;
			double tasa_Febrero;
			double tasa_Marzo;
			double tasa_Abril;
			double tasa_Mayo;
			double tasa_Junio;
			double tasa_Julio;
			double tasa_Agosto;
			double tasa_Septiembre;
			double tasa_Octubre;
			double tasa_Noviembre;
			double tasa_Diciembre;
			
			

			int numeroFor = 1;
			for (IncidentesMutualidad emp1 : incidente) {
				enero_ = emp1.getEnero_Accidentes();
				tasa_Enero = emp1.getTasa_Enero();
				BigDecimal numberBigDecimal = new BigDecimal(tasa_Enero);
				numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
				
				String[] titulo7 = { "Ene", "" + enero_ + "", "" + numberBigDecimal + ""};

				Cell celda0 = fila.createCell(0);
				celda0.setCellValue(titulo7[0]);

				Cell celda1_1 = fila.createCell(numeroFor);
				celda1_1.setCellValue(titulo7[1]);
				
				
				Cell celda3 = fila.createCell(size + numeroFor);
				celda3.setCellValue(titulo7[2]);

				numeroFor = numeroFor + 1;


			}
			// febrero
			fila = pagina.createRow(3);
			int numeroFor2 = 1;
			for (IncidentesMutualidad emp1 : incidente) {
				febrero_ = emp1.getFebrero_Accidentes();
				tasa_Febrero = emp1.getTasa_Febrero();
				BigDecimal numberBigDecimal = new BigDecimal(tasa_Febrero);
				numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
				
				String[] titulo7 = { "Feb", "" + febrero_ + "", "" + numberBigDecimal + ""};

				Cell celda0 = fila.createCell(0);
				celda0.setCellValue(titulo7[0]);

				Cell celda1_1 = fila.createCell(numeroFor2);
				celda1_1.setCellValue(titulo7[1]);
				
				Cell celda3 = fila.createCell(size + numeroFor2);
				celda3.setCellValue(titulo7[2]);
                
				numeroFor2 = numeroFor2 + 1;


			}
			// marzo
						fila = pagina.createRow(4);
						int numeroFor3 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							marzo_ = emp1.getMarzo_Accidentes();
							tasa_Marzo = emp1.getTasa_Marzo();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Marzo);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Mar", "" + marzo_ + "", "" + numberBigDecimal + ""};

							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);

							Cell celda1_1 = fila.createCell(numeroFor3);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor3);
							celda3.setCellValue(titulo7[2]);

							numeroFor3 = numeroFor3 + 1;


						}
						// abril
						fila = pagina.createRow(5);
						int numeroFor4 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							abril_ = emp1.getAbril_Accidentes();
							tasa_Abril = emp1.getTasa_Abril();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Abril);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Abr", "" + abril_ + "", "" + numberBigDecimal + ""};

							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);

							Cell celda1_1 = fila.createCell(numeroFor4);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor4);
							celda3.setCellValue(titulo7[2]);

							numeroFor4 = numeroFor4 + 1;


						}
						// mayo
						fila = pagina.createRow(6);
						int numeroFor5 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							mayo_ = emp1.getMayo_Accidentes();
							tasa_Mayo = emp1.getTasa_Mayo();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Mayo);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "May", "" + mayo_ + "", "" + numberBigDecimal + ""};

							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);

							Cell celda1_1 = fila.createCell(numeroFor5);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor5);
							celda3.setCellValue(titulo7[2]);

							numeroFor5 = numeroFor5 + 1;


						}
						// junio
						fila = pagina.createRow(7);
						int numeroFor6 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							junio_ = emp1.getJunio_Accidentes();
							tasa_Junio = emp1.getTasa_Junio();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Junio);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Jun", "" + junio_ + "", "" + numberBigDecimal + ""};
							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);
							Cell celda1_1 = fila.createCell(numeroFor6);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor6);
							celda3.setCellValue(titulo7[2]);

							numeroFor6 = numeroFor6 + 1;


						}
						// julio
						fila = pagina.createRow(8);
						int numeroFor7 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							julio_ = emp1.getJulio_Accidentes();
							tasa_Julio = emp1.getTasa_Julio();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Julio);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Jul", "" + julio_ + "", "" + numberBigDecimal + ""};

							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);

							Cell celda1_1 = fila.createCell(numeroFor7);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor7);
							celda3.setCellValue(titulo7[2]);

							numeroFor7 = numeroFor7 + 1;


						}
						// agosto
						fila = pagina.createRow(9);
						int numeroFor8 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							agosto_ = emp1.getAgosto_Accidentes();
							tasa_Agosto = emp1.getTasa_Agosto();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Agosto);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Ago", "" + agosto_ + "", "" + numberBigDecimal + ""};

							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);

							Cell celda1_1 = fila.createCell(numeroFor8);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor8);
							celda3.setCellValue(titulo7[2]);

							numeroFor8 = numeroFor8 + 1;


						}
						// septiembre
						fila = pagina.createRow(10);
						int numeroFor9 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							septiembre_ = emp1.getSeptiembre_Accidentes();
							tasa_Septiembre = emp1.getTasa_Septiembre();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Septiembre);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Sep", "" + septiembre_ + "", "" + numberBigDecimal + ""};
							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);
							Cell celda1_1 = fila.createCell(numeroFor9);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor9);
							celda3.setCellValue(titulo7[2]);

							numeroFor9 = numeroFor9 + 1;

						}
						// octubre
						fila = pagina.createRow(11);
						int numeroFor10 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							octubre_ = emp1.getOctubre_Accidentes();
							tasa_Octubre = emp1.getTasa_Octubre();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Octubre);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Oct", "" + octubre_ + "", "" + numberBigDecimal + ""};
							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);
							Cell celda1_1 = fila.createCell(numeroFor10);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor10);
							celda3.setCellValue(titulo7[2]);

							numeroFor10 = numeroFor10 + 1;


						}
						// noviembre
						fila = pagina.createRow(12);
						int numeroFor11 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							noviembre_ = emp1.getNoviembre_Accidentes();
							tasa_Noviembre = emp1.getTasa_Noviembre();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Noviembre);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Nov", "" + noviembre_ + "", "" + numberBigDecimal + ""};
							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);
							Cell celda1_1 = fila.createCell(numeroFor11);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor11);
							celda3.setCellValue(titulo7[2]);

							numeroFor11 = numeroFor11 + 1;


						}
						// diciembre
						fila = pagina.createRow(13);
						int numeroFor12 = 1;
						for (IncidentesMutualidad emp1 : incidente) {
							diciembre_ = emp1.getDiciembre_Accidentes();
							tasa_Diciembre = emp1.getTasa_Diciembre();
							BigDecimal numberBigDecimal = new BigDecimal(tasa_Diciembre);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							String[] titulo7 = { "Dic", "" + diciembre_ + "", "" + numberBigDecimal + ""};

							Cell celda0 = fila.createCell(0);
							celda0.setCellValue(titulo7[0]);

							Cell celda1_1 = fila.createCell(numeroFor12);
							celda1_1.setCellValue(titulo7[1]);
							
							Cell celda3 = fila.createCell(size + numeroFor12);
							celda3.setCellValue(titulo7[2]);

							numeroFor12 = numeroFor12 + 1;


						}
			
						for (int i = 0; i < (size +1) * 2; i++) {
							pagina.autoSizeColumn(i);
							}
			
			

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
	
	public static List<IncidentesMutualidad> getDatosDiasPerdidos(int anio, int empresa) throws Exception {
		
		
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		List<IncidentesMutualidad> lista = new ArrayList<>();
		try{
				sql = "SELECT "
						    +"IF(SUM(d.diasperdido) IS NULL,"
						        +"0,"
						        +"SUM(d.diasperdido)) AS dias,"
						    +"DATE_FORMAT(f.fecha, '%Y%m') AS mes,"
						    +"(IF(SUM(d.diasperdido) IS NULL,"
						            +"0,"
						                  +"((SUM(d.diasperdido) /  (SELECT "
						                        +"COUNT(DISTINCT codigo_trabajador) "
						                    +"FROM "
						                        +"contratos "
						                    +"WHERE "
						                        +"idSociedad = "+empresa+" "
						                            +"AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+") )*100) "
						                  
						    	+")) as TSIT,"
						    +""+anio+" AS anio "
						+"FROM "
						    +"sw_fechas f "
						        +"LEFT JOIN "
						    +"(SELECT  "
						        +"CASE "
						                +"WHEN "
						                    +"FIRST_DAY(fechaRegistro) != FIRST_DAY(t.mes) "
						                        +"&& FIRST_DAY(t.mes) != FIRST_DAY(fechaHasta) "
						                +"THEN "
						                    +"(TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), LAST_DAY(t.mes)) + 1) "
						                +"WHEN "
						                    +"(FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta)) "
						                        +"&& (fechaRegistro = fechaHasta) "
						                +"THEN "
						                    +"(TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
						                +"WHEN FIRST_DAY(fechaRegistro) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
						                +"WHEN FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), fechaHasta) + 1) "
						                +"ELSE (TIMESTAMPDIFF(DAY, fechaRegistro, LAST_DAY(t.mes)) + 1) "
						            +"END AS diasperdido,"
						            +"t.mes "
						    +"FROM "
						        +"sw_m_eventos_mutualidad "
						    +"LEFT JOIN (SELECT DISTINCT "
						        +"(DATE_FORMAT(fecha, '%Y-%m-%01')) AS mes "
						    +"FROM "
						        +"sw_fechas "
						    +"WHERE "
						        +"DATE_FORMAT(fecha, '%Y') = "+anio+") t ON (DATE_FORMAT(t.mes, '%Y') = "+anio+") "
						    +"WHERE "
						        +"id_sociedad = "+empresa+" "
						            +"AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" "
						            +"AND ((fechaRegistro BETWEEN fechaRegistro AND LAST_DAY(t.mes)) "
						            +"OR (fechaHasta BETWEEN fechaRegistro AND LAST_DAY(t.mes))) "
						            +"AND fechaHasta >= FIRST_DAY(t.mes)) d ON (d.mes = f.fecha) "
						+"WHERE "
						    +"DATE_FORMAT(f.fecha, '%Y') = "+anio+" "
						+"GROUP BY 2 ";
		    
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					IncidentesMutualidad p = new IncidentesMutualidad();
					
				    p.setDias(rs.getInt("dias"));
				    p.setFebrero_Accidentes(rs.getInt("mes"));
				    p.setTsit(rs.getDouble("TSIT"));
				    p.setAnio(rs.getInt("anio"));
				
					lista.add(p);
				
				}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}
	
	public static String getExeltasaDiasPerdidos(List<IncidentesMutualidad> incidente)
			throws Exception {

		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		String horaf = formatoHora.replaceAll("[:]", "");
		String ruta3 = "";

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("DIAS_PERDIDOS_TSIT" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("DIAS_PERDIDOS_TSIT");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle stylenew = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			 
			CellStyle style5 = workbook.createCellStyle();
			style5.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font font2 = workbook.createFont();
			font2.setColor(IndexedColors.BLACK.getIndex());
			style5.setFont(font2);
			
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
			// combined.setBorderTop(CellStyle.BORDER_DOUBLE);
			// combined.setFont(font); // color de letra

			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);

			String empresaMys = "Días Perdidos - TSIT";
			String[] titulos = { empresaMys };

			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);

			celda1.setCellValue(titulos[0]);
			fila = pagina.createRow(1);
			
			
			int size = incidente.size();
			int totalobjetos = size / 12;
			
			System.out.println(totalobjetos);
			
			
			int contador = 1;
			
			List<String> myList = new ArrayList<String>();
			List<String> ListENERO = new ArrayList<String>();
			List<String> ListFEBRERO = new ArrayList<String>();
			List<String> ListMARZO = new ArrayList<String>();
			List<String> ListABRIL = new ArrayList<String>();
			List<String> ListMAYO = new ArrayList<String>();
			List<String> ListJUNIO = new ArrayList<String>();
			List<String> ListJULIO = new ArrayList<String>();
			List<String> ListAGOSTO = new ArrayList<String>();
			List<String> ListSEPTIEMBRE = new ArrayList<String>();
			List<String> ListOCTUBRE = new ArrayList<String>();
			List<String> ListNOVIEMBRE = new ArrayList<String>();
			List<String> ListDICIEMBRE = new ArrayList<String>();
			
			 myList.add("MES / AÑO");
			 ListENERO.add("Ene");
			 ListFEBRERO.add("Feb");
			 ListMARZO.add("Mar");
			 ListABRIL.add("Abr");
			 ListMAYO.add("May");
			 ListJUNIO.add("Jun");
			 ListJULIO.add("Jul");
			 ListAGOSTO.add("Ago");
			 ListSEPTIEMBRE.add("Sep");
			 ListOCTUBRE.add("Oct");
			 ListNOVIEMBRE.add("Nov");
			 ListDICIEMBRE.add("Dic");
			for (IncidentesMutualidad emp1 : incidente) {
		
		
			if(contador == 1){
				 myList.add("DP "+ emp1.getAnio()+"");
			}
			contador = contador + 1;
			
			if(contador == 13){
				contador = 1;
			}
				
			}
			
			
			for (IncidentesMutualidad emp12 : incidente) {
				
				if(contador == 1){
					 myList.add("TSIT "+ emp12.getAnio()+"");
				}
				contador = contador + 1;
				
				if(contador == 13){
					contador = 1;
				}
			}
			
			// ENERO
			int contadorEnero = 1;
			for (IncidentesMutualidad emp12 : incidente) {
				
				if(contadorEnero == 1){
					ListENERO.add(""+emp12.getDias()+"");
				}
				contadorEnero = contadorEnero + 1;
				
				if(contadorEnero == 13){
					contadorEnero = 1;
				}
			}
			
			// ENERO TSIT
						int contadorEneroTSIT = 1;
						for (IncidentesMutualidad emp12 : incidente) {
							
							if(contadorEneroTSIT == 1){
								BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
								numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
								ListENERO.add(""+numberBigDecimal);
							}
							contadorEneroTSIT = contadorEneroTSIT + 1;
							
							if(contadorEneroTSIT == 13){
								contadorEneroTSIT = 1;
							}
						}
						
						
						// FEBRERO
						int contadorFebreo = 1;
						for (IncidentesMutualidad emp12 : incidente) {
							
							if(contadorFebreo == 2){
								ListFEBRERO.add(""+emp12.getDias()+"");
							}
							contadorFebreo = contadorFebreo + 1;
							
							if(contadorFebreo == 13){
								contadorFebreo = 1;
							}
						}
						
						// FEBRERO TSIT
									int contadorFebreroTSIT = 1;
									for (IncidentesMutualidad emp12 : incidente) {
										
										if(contadorFebreroTSIT == 2){
											BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
											numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
											ListFEBRERO.add(""+numberBigDecimal);
										}
										contadorFebreroTSIT = contadorFebreroTSIT + 1;
										
										if(contadorFebreroTSIT == 13){
											contadorFebreroTSIT = 1;
										}
									}
									// MARZO
									int contadorMarzo = 1;
									for (IncidentesMutualidad emp12 : incidente) {
										
										if(contadorMarzo == 3){
											ListMARZO.add(""+emp12.getDias()+"");
										}
										contadorMarzo = contadorMarzo + 1;
										
										if(contadorMarzo == 13){
											contadorMarzo = 1;
										}
									}
									
									// MARZO TSIT
												int contadorMarzoTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) {
													
													if(contadorMarzoTSIT == 3){
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListMARZO.add(""+numberBigDecimal);
													}
													contadorMarzoTSIT = contadorMarzoTSIT + 1;
													
													if(contadorMarzoTSIT == 13){
														contadorMarzoTSIT = 1;
													}
												}
												
												// ABRIL
												int contadorAbril = 1;
												for (IncidentesMutualidad emp12 : incidente) {
													
													if(contadorAbril == 4){
														ListABRIL.add(""+emp12.getDias()+"");
													}
													contadorAbril = contadorAbril + 1;
													
													if(contadorAbril == 13){
														contadorAbril = 1;
													}
												}
												
												// ABRIL TSIT
															int contadorAbrilTSIT = 1;
															for (IncidentesMutualidad emp12 : incidente) {
																
																if(contadorAbrilTSIT == 4){
																	BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
																	numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
																	ListABRIL.add(""+numberBigDecimal);
																}
																contadorAbrilTSIT = contadorAbrilTSIT + 1;
																
																if(contadorAbrilTSIT == 13){
																	contadorAbrilTSIT = 1;
																}
															}
												// MAYO
												int contadorMayo = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorMayo == 5)
													{
														ListMAYO.add(""+emp12.getDias()+"");
													}
													contadorMayo = contadorMayo + 1;
																	
													if(contadorMayo == 13)
													{
														contadorMayo = 1;
													}
												}
															
												// MAYO TSIT
												int contadorMayoTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorMayoTSIT == 5)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListMAYO.add(""+numberBigDecimal);
													}
													contadorMayoTSIT = contadorMayoTSIT + 1;
																			
													if(contadorMayoTSIT == 13)
													{
														contadorMayoTSIT = 1;
													}
												}
												// JUNIO
												int contadorJunio = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorJunio == 6)
													{
														ListJUNIO.add(""+emp12.getDias()+"");
													}
													contadorJunio = contadorJunio + 1;
																	
													if(contadorJunio == 13)
													{
														contadorJunio = 1;
													}
												}
															
												// JUNIO TSIT
												int contadorJunioTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorJunioTSIT == 6)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListJUNIO.add(""+numberBigDecimal);
													}
													contadorJunioTSIT = contadorJunioTSIT + 1;
																			
													if(contadorJunioTSIT == 13)
													{
														contadorJunioTSIT = 1;
													}
												}
												// JULIO
												int contadorJulio = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorJulio == 7)
													{
														ListJULIO.add(""+emp12.getDias()+"");
													}
													contadorJulio = contadorJulio + 1;
																	
													if(contadorJulio == 13)
													{
														contadorJulio = 1;
													}
												}
															
												// JULIO TSIT
												int contadorJulioTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorJulioTSIT == 7)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListJULIO.add(""+numberBigDecimal);
													}
													contadorJulioTSIT = contadorJulioTSIT + 1;
																			
													if(contadorJulioTSIT == 13)
													{
														contadorJulioTSIT = 1;
													}
												}
												// AGOSTO
												int contadorAgosto = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorAgosto == 8)
													{
														ListAGOSTO.add(""+emp12.getDias()+"");
													}
													contadorAgosto = contadorAgosto + 1;
																	
													if(contadorAgosto == 13)
													{
														contadorAgosto = 1;
													}
												}
															
												// AGOSTO TSIT
												int contadorAgostoTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorAgostoTSIT == 8)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListAGOSTO.add(""+numberBigDecimal);
													}
													contadorAgostoTSIT = contadorAgostoTSIT + 1;
																			
													if(contadorAgostoTSIT == 13)
													{
														contadorAgostoTSIT = 1;
													}
												}
												// SEPTIEMBRE
												int contadorSeptiembre = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorSeptiembre == 9)
													{
														ListSEPTIEMBRE.add(""+emp12.getDias()+"");
													}
													contadorSeptiembre = contadorSeptiembre + 1;
																	
													if(contadorSeptiembre == 13)
													{
														contadorSeptiembre = 1;
													}
												}
															
												// SEPTIEMBRE TSIT
												int contadorSeptiembreTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorSeptiembreTSIT == 9)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListSEPTIEMBRE.add(""+numberBigDecimal);
													}
													contadorSeptiembreTSIT = contadorSeptiembreTSIT + 1;
																			
													if(contadorSeptiembreTSIT == 13)
													{
														contadorSeptiembreTSIT = 1;
													}
												}
												// OCTUBRE
												int contadorOctubre = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorOctubre == 10)
													{
														ListOCTUBRE.add(""+emp12.getDias()+"");
													}
													contadorOctubre = contadorOctubre + 1;
																	
													if(contadorOctubre == 13)
													{
														contadorOctubre = 1;
													}
												}
															
												// OCTUBRE TSIT
												int contadorOctubreTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorOctubreTSIT == 10)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListOCTUBRE.add(""+numberBigDecimal);
													}
													contadorOctubreTSIT = contadorOctubreTSIT + 1;
																			
													if(contadorOctubreTSIT == 13)
													{
														contadorOctubreTSIT = 1;
													}
												}
												
												// NOVIEMBRE
												int contadorNoviembre = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorNoviembre == 11)
													{
														ListNOVIEMBRE.add(""+emp12.getDias()+"");
													}
													contadorNoviembre = contadorNoviembre + 1;
																	
													if(contadorNoviembre == 13)
													{
														contadorNoviembre = 1;
													}
												}
															
												// NOVIEMBRE TSIT
												int contadorNoviembreTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorNoviembreTSIT == 11)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListNOVIEMBRE.add(""+numberBigDecimal);
													}
													contadorNoviembreTSIT = contadorNoviembreTSIT + 1;
																			
													if(contadorNoviembreTSIT == 13)
													{
														contadorNoviembreTSIT = 1;
													}
												}
												// DICIEMBRE
												int contadorDiciembre = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorDiciembre == 12)
													{
														ListDICIEMBRE.add(""+emp12.getDias()+"");
													}
													contadorDiciembre = contadorDiciembre + 1;
																	
													if(contadorDiciembre == 13)
													{
														contadorDiciembre = 1;
													}
												}
															
												// DICIEMBRE TSIT
												int contadorDiciembreTSIT = 1;
												for (IncidentesMutualidad emp12 : incidente) 
												{
													if(contadorDiciembreTSIT == 12)
													{
														BigDecimal numberBigDecimal = new BigDecimal(emp12.getTsit());
														numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
														ListDICIEMBRE.add(""+numberBigDecimal);
													}
													contadorDiciembreTSIT = contadorDiciembreTSIT + 1;
																			
													if(contadorDiciembreTSIT == 13)
													{
														contadorDiciembreTSIT = 1;
													}
												}
												 


												fila = pagina.createRow(2);
												
												for (int i = 0; i < myList.size(); i++) {
													System.out.println(myList.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(myList.get(i));
													celda0.setCellStyle(style5);
												}
												fila = pagina.createRow(3);
												//enero
												for (int i = 0; i < ListENERO.size(); i++) {
													System.out.println(ListENERO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListENERO.get(i));
												}
												fila = pagina.createRow(4);
												//febrero
												for (int i = 0; i < ListFEBRERO.size(); i++) {
													System.out.println(ListFEBRERO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListFEBRERO.get(i));
												}
												fila = pagina.createRow(5);
												//marzo
												for (int i = 0; i < ListMARZO.size(); i++) {
													System.out.println(ListMARZO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListMARZO.get(i));
												}
												fila = pagina.createRow(6);
												//abril
												for (int i = 0; i < ListABRIL.size(); i++) {
													System.out.println(ListABRIL.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListABRIL.get(i));
												}
												fila = pagina.createRow(7);
												// mayo
												for (int i = 0; i < ListMAYO.size(); i++) {
													System.out.println(ListMAYO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListMAYO.get(i));
												}
												fila = pagina.createRow(8);
												//junio
												for (int i = 0; i < ListJUNIO.size(); i++) {
													System.out.println(ListJUNIO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListJUNIO.get(i));
												}
												fila = pagina.createRow(9);
												// julio
												for (int i = 0; i < ListJULIO.size(); i++) {
													System.out.println(ListJULIO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListJULIO.get(i));
												}
												fila = pagina.createRow(10);
												//agosto
												for (int i = 0; i < ListAGOSTO.size(); i++) {
													System.out.println(ListAGOSTO.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListAGOSTO.get(i));
												}
												fila = pagina.createRow(11);
												//septiembre
												for (int i = 0; i < ListSEPTIEMBRE.size(); i++) {
													System.out.println(ListSEPTIEMBRE.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListSEPTIEMBRE.get(i));
												}
												fila = pagina.createRow(12);
												//octubre
												for (int i = 0; i < ListOCTUBRE.size(); i++) {
													System.out.println(ListOCTUBRE.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListOCTUBRE.get(i));
												}
												fila = pagina.createRow(13);
												//noviembre
												for (int i = 0; i < ListNOVIEMBRE.size(); i++) {
													System.out.println(ListNOVIEMBRE.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListNOVIEMBRE.get(i));
												}
												fila = pagina.createRow(14);
												//diciembre
												for (int i = 0; i < ListDICIEMBRE.size(); i++) {
													System.out.println(ListDICIEMBRE.get(i));
													Cell celda0 = fila.createCell(i);
													celda0.setCellValue(ListDICIEMBRE.get(i));
												}
												
					
												for (int i = 0; i < myList.size(); i++) {
													pagina.autoSizeColumn(i);
												}
					
			
					
			
			

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
	// ANALISIS PERIODOS MOVILES
	
	public static List<IncidentesMutualidad> getDatosMoviles(int anio,int anio2, int empresa, String mes, String mes2, String sino) throws Exception {
		
		System.out.println(mes2);
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		List<IncidentesMutualidad> lista = new ArrayList<>();
		try{
				sql = "SELECT "
						    +"(SELECT " 
						            +"COUNT(idEventosMutualidad) "
						        +"FROM "
						            +"sw_m_eventos_mutualidad "
						        +"WHERE "
						            +"id_sociedad = "+empresa+" ";
						            
						               if(anio2 == 0 || anio == anio2 ){
						            	  sql += "AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" ";
						               }else{
						            	   sql +="AND DATE_FORMAT(fechaRegistro, '%Y') BETWEEN "+anio+" AND "+anio2+" ";
						               }
						                
						               if(mes2.equals("0")){
						            	   sql +="AND DATE_FORMAT(fechaRegistro, '%m') = "+mes+") AS accidentes,";
						               }else{
						            	   if(anio2 == 0){
						            		   sql +="AND DATE_FORMAT(fechaRegistro, '%Y-%m-%d') BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes+"-01')) AS accidentes,";
						            		   System.out.println("aqui1");
						            	   }else{
						            		   System.out.println("aqui2");
						            		   sql +="AND DATE_FORMAT(fechaRegistro, '%Y-%m-%d') BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio2+"-"+mes2+"-01')) AS accidentes,";
						            	   }
						            	  
						               }
						               
						    sql+="(((SELECT "
						            +"COUNT(idEventosMutualidad) "
						        +"FROM "
						            +"sw_m_eventos_mutualidad "
						        +"WHERE "
						            +"id_sociedad = "+empresa+" ";
						                
							            if(anio2 == 0 || anio == anio2 )
							            {
							            	
							            	  sql +="AND DATE_FORMAT(fechaRegistro, '%Y')  = "+anio+" ";
							            }else{
							            	   sql +="AND DATE_FORMAT(fechaRegistro, '%Y') BETWEEN "+anio+" AND "+anio2+" ";
							            	   
							            }
						                
										if (mes2.equals("0")) {
											sql +="AND DATE_FORMAT(fechaRegistro, '%m') = "+mes+" )) ";
										} else {
											
											if(anio2 == 0){
												sql +="AND DATE_FORMAT(fechaRegistro, '%Y-%m-%d') BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes+"-01') )) ";
											}else{
											}
											
											sql +="AND DATE_FORMAT(fechaRegistro, '%Y-%m-%d') BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio2+"-"+mes2+"-01') )) ";
										}
							               
						            
						               
							            sql +=" / (SELECT " 
						            +"COUNT(DISTINCT codigo_trabajador) AS trabajadores "
						        +"FROM "
						            +"contratos "
						        +"WHERE "
						            +"idSociedad = "+empresa+" ";
						            
						            	 if(anio2 == 0  || anio == anio2)
							            {
						            		 if(mes2.equals("0")){
						            			 sql +="AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
											                +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes+"-01')) ";
						            		 }else{
						            			 sql +="AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
											                +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes2+"-01')) ";
						            		 }
						            		 
						            		 if(mes2.equals("0")){
						            			 sql+="OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes+"-01')))) * 100) AS tasa_accidentabilidad,";
						            		 }else{
						            			 sql+="OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes2+"-01')))) * 100) AS tasa_accidentabilidad,";
						            		 }
							            }else{
							            	 sql +="AND DATE_FORMAT(fechaInicio_actividad, '%Y') BETWEEN "+anio+" AND "+anio2+" "
									                +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio2+"-"+mes2+"-01')) "
									                +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio2+"-"+mes2+"-01')))) * 100) AS tasa_accidentabilidad,";
							            	   
							            }
						   sql +="((SELECT "
						            +" if(SUM(suma) is null, 0, SUM(suma) ) AS dias_perdidos "
						        +"FROM "
						            +"(SELECT "
						                +"SUM(diasperdido) AS suma, mes "
						            +"FROM "
						                +"(SELECT "
						                +"CASE "
						                        +"WHEN "
						                            +"FIRST_DAY(fechaRegistro) != FIRST_DAY(t.mes) "
						                                +"&& FIRST_DAY(t.mes) != FIRST_DAY(fechaHasta) "
						                        +"THEN "
						                            +"(TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), LAST_DAY(t.mes)) + 1) "
						                        +"WHEN "
						                            +"(FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta)) "
						                                +"&& (fechaRegistro = fechaHasta) "
						                        +"THEN "
						                            +"(TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
						                        +"WHEN FIRST_DAY(fechaRegistro) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
						                        +"WHEN FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), fechaHasta) + 1) "
						                        +"ELSE (TIMESTAMPDIFF(DAY, fechaRegistro, LAST_DAY(t.mes)) + 1) "
						                    +"END AS diasperdido,"
						                    +"t.mes "
						            +"FROM "
						                +"sw_m_eventos_mutualidad "
						            +"INNER JOIN (SELECT DISTINCT "
						                +"(DATE_FORMAT(fecha, '%Y-%m-%01')) AS mes "
						            +"FROM "
						                +"sw_fechas "
						            +"WHERE ";
						                
									   if(anio2 == 0 || anio == anio2 )
							            {
										   sql+= "DATE_FORMAT(fecha, '%Y') = "+anio+") t ON (DATE_FORMAT(t.mes, '%Y') = "+anio+") ";
							            }else{
							            	sql+="DATE_FORMAT(fecha, '%Y') BETWEEN "+anio+" AND "+anio2+") t ON (DATE_FORMAT(t.mes, '%Y') BETWEEN "+anio+" AND "+anio2+") ";  
							            }
						            sql+="WHERE "
						                +"id_sociedad = "+empresa+" ";
						                
						            if(anio2 == 0 || anio == anio2)
						            {
						            	 sql+="AND DATE_FORMAT(fechaRegistro, '%Y')= "+anio+" ";
						            }else{
						            	sql+="AND DATE_FORMAT(fechaRegistro, '%Y') BETWEEN "+anio+" AND "+anio2+" ";
						            }
						                   
						                    sql+="AND ((fechaRegistro BETWEEN fechaRegistro AND LAST_DAY(t.mes)) "
						                    +"OR (fechaHasta BETWEEN fechaRegistro AND LAST_DAY(t.mes))) "
						                    +"AND fechaHasta >= FIRST_DAY(t.mes)) aa "
						            +"GROUP BY aa.mes) ee "
						        +"WHERE ";
						            
						        if(anio2 == 0 || anio == anio2 )
					            {
						        	if(mes2.equals("0")){
						        		 sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio+"-"+mes+"-01')) AS dias_perdidos, ";
						        	}else{
						        		 sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio+"-"+mes2+"-01')) AS dias_perdidos, ";
						        	}
						        	
					            }else{
					            	 sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio2+"-"+mes2+"-01')) AS dias_perdidos, ";
					            }
						        
						        // del mes 
						        if(anio2 == 0 && mes2.equals("0")){
						        	sql+="(SELECT "
								            +"SUM(suma) AS dias_perdidos "
								        +"FROM "
								            +"(SELECT "
								                +"SUM(diasperdido) AS suma, mes "
								            +"FROM "
								                +"(SELECT "
								                +"CASE "
								                        +"WHEN "
								                            +"FIRST_DAY(fechaRegistro) != FIRST_DAY(t.mes) "
								                                +"&& FIRST_DAY(t.mes) != FIRST_DAY(fechaHasta) "
								                        +"THEN "
								                            +"(TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), LAST_DAY(t.mes)) + 1) "
								                        +"WHEN "
								                            +"(FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta)) "
								                                +"&& (fechaRegistro = fechaHasta) "
								                        +"THEN "
								                            +"(TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
								                        +"WHEN FIRST_DAY(fechaRegistro) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
								                        +"WHEN FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), fechaHasta) + 1) "
								                        +"ELSE (TIMESTAMPDIFF(DAY, fechaRegistro, LAST_DAY(t.mes)) + 1) "
								                    +"END AS diasperdido,"
								                    +"t.mes "
								            +"FROM "
								                +"sw_m_eventos_mutualidad "
								            +"INNER JOIN (SELECT DISTINCT "
								                +"(DATE_FORMAT(fecha, '%Y-%m-%01')) AS mes "
								            +"FROM "
								                +"sw_fechas "
								            +"WHERE ";
								            if(anio2 == 0 || anio == anio2)
								            {
								            	   sql+="DATE_FORMAT(fecha, '%Y') = "+anio+") t ON (DATE_FORMAT(t.mes, '%Y') = "+anio+") ";
								            }else{
								            	sql+="DATE_FORMAT(fecha, '%Y') BETWEEN "+anio+" AND "+anio2+") t ON (DATE_FORMAT(t.mes, '%Y') BETWEEN "+anio+" AND "+anio2+") ";
								            }
								             
								            sql+="WHERE "
								                +"id_sociedad = "+empresa+" ";
								                
								            if(anio2 == 0 || anio == anio2)
								            {
								            	 sql+="AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" ";
								            }else{
								            	 sql+="AND DATE_FORMAT(fechaRegistro, '%Y') BETWEEN "+anio+" AND "+anio2+" ";
								            }
								                   
								                    sql+="AND ((fechaRegistro BETWEEN fechaRegistro AND LAST_DAY(t.mes)) "
								                    +"OR (fechaHasta BETWEEN fechaRegistro AND LAST_DAY(t.mes))) "
								                    +"AND fechaHasta >= FIRST_DAY(t.mes)) aa "
								            +"GROUP BY aa.mes) ee "
								        +"WHERE ";
								        if(anio2 == 0 || anio == anio2 )
							            {
								        	if(mes2.equals("0")){
								        		 sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio+"-"+mes+"-01') AS del_mes,";
								        	}else{
								        		 sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio+"-"+mes2+"-01') AS del_mes,";
								        	}
								        	 
							            }else{
							            	  sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio2+"-"+mes2+"-01') AS del_mes,";
							            }
						        }else{
						        	sql+="'' AS del_mes,";
						        }
						        // end del mes  
						    
						        
						         if(mes2.equals("0") && anio2 == 0){
						        	 sql+="(SELECT "
									            +"SUM(suma) AS dias_perdidos "
									        +"FROM "
									            +"(SELECT "
									                +"SUM(diasperdido) AS suma, mes "
									            +"FROM "
									                +"(SELECT "
									                +"CASE "
									                        +"WHEN "
									                            +"FIRST_DAY(fechaRegistro) != FIRST_DAY(t.mes) "
									                                +"&& FIRST_DAY(t.mes) != FIRST_DAY(fechaHasta) "
									                        +"THEN "
									                            +"(TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), LAST_DAY(t.mes)) + 1) "
									                        +"WHEN "
									                            +"(FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta)) "
									                                +"&& (fechaRegistro = fechaHasta) "
									                        +"THEN "
									                            +"(TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
									                        +"WHEN FIRST_DAY(fechaRegistro) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
									                        +"WHEN FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), fechaHasta) + 1) "
									                        +"ELSE (TIMESTAMPDIFF(DAY, fechaRegistro, LAST_DAY(t.mes)) + 1) "
									                    +"END AS diasperdido,"
									                    +"t.mes "
									            +"FROM "
									                +"sw_m_eventos_mutualidad "
									            +"INNER JOIN (SELECT DISTINCT "
									                +"(DATE_FORMAT(fecha, '%Y-%m-%01')) AS mes "
									            +"FROM "
									                +"sw_fechas "
									            +"WHERE ";
									            if(anio2 == 0 || anio == anio2){
									            	 sql+="DATE_FORMAT(fecha, '%Y') = "+anio+") t ON (DATE_FORMAT(t.mes, '%Y') = "+anio+") ";
									            }else{
									            	 sql+="DATE_FORMAT(fecha, '%Y') BETWEEN "+anio+" AND "+anio2+") t ON (DATE_FORMAT(t.mes, '%Y') BETWEEN "+anio+" AND "+anio2+") ";
									            }
									            sql+="WHERE "
									                +"id_sociedad = "+empresa+" ";
									                if(anio2 == 0 || anio == anio2 ){
									                	sql+="AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" ";
										            }else{
										            	sql+="AND DATE_FORMAT(fechaRegistro, '%Y') BETWEEN "+anio+" AND "+anio2+" ";
										            }
									                    
									                    sql+="AND ((fechaRegistro BETWEEN fechaRegistro AND LAST_DAY(t.mes)) "
									                    +"OR (fechaHasta BETWEEN fechaRegistro AND LAST_DAY(t.mes))) "
									                    +"AND fechaHasta >= FIRST_DAY(t.mes)) aa "
									            +"GROUP BY aa.mes) ee "
									        +"WHERE ";
									        if(anio2 == 0 || anio == anio2 ){
									        	if(mes2.equals("0")){
									        		sql+="mes BETWEEN '"+anio+"-01-01' AND '"+anio+"-"+mes+"-01') AS de_meses_anteriores,";
									        	}else{
									        		sql+="mes BETWEEN '"+anio+"-01-01' AND '"+anio+"-"+mes2+"-01') AS de_meses_anteriores,";
									        	}
									        	
								            }else{
								            	sql+="mes BETWEEN '"+anio+"-01-01' AND '"+anio+"-"+mes+"-01') AS de_meses_anteriores,";
								            }
						         } else{
							        	sql+="'' as de_meses_anteriores,";
						         }
						        
						        // end de meses anteriores
						            
						    sql+="((SELECT "
						            +"SUM(suma) AS dias_perdidos "
						        +"FROM "
						            +"(SELECT "
						                +"SUM(diasperdido) AS suma, mes "
						            +"FROM "
						                +"(SELECT "
						                +"CASE "
						                        +"WHEN "
						                            +"FIRST_DAY(fechaRegistro) != FIRST_DAY(t.mes) "
						                                +"&& FIRST_DAY(t.mes) != FIRST_DAY(fechaHasta) "
						                        +"THEN "
						                            +"(TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), LAST_DAY(t.mes)) + 1) "
						                        +"WHEN "
						                            +"(FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta)) "
						                                +"&& (fechaRegistro = fechaHasta) "
						                        +"THEN "
						                            +"(TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
						                        +"WHEN FIRST_DAY(fechaRegistro) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, fechaRegistro, fechaHasta) + 1) "
						                        +"WHEN FIRST_DAY(t.mes) = FIRST_DAY(fechaHasta) THEN (TIMESTAMPDIFF(DAY, FIRST_DAY(t.mes), fechaHasta) + 1) "
						                        +"ELSE (TIMESTAMPDIFF(DAY, fechaRegistro, LAST_DAY(t.mes)) + 1) "
						                    +"END AS diasperdido,"
						                    +"t.mes "
						            +"FROM "
						                +"sw_m_eventos_mutualidad "
						            +"INNER JOIN (SELECT DISTINCT "
						                +"(DATE_FORMAT(fecha, '%Y-%m-%01')) AS mes "
						            +"FROM "
						                +"sw_fechas "
						            +"WHERE ";
						            if(anio2 == 0 || anio == anio2){
							        	sql+= "DATE_FORMAT(fecha, '%Y') = "+anio+") t ON (DATE_FORMAT(t.mes, '%Y') = "+anio+") ";
						            }else{
						            	sql+= "DATE_FORMAT(fecha, '%Y')BETWEEN "+anio+" AND "+anio2+") t ON (DATE_FORMAT(t.mes, '%Y') BETWEEN "+anio+" AND "+anio2+") ";
						            }
						               
						            sql+="WHERE "
						                +"id_sociedad = "+empresa+" ";
						                if(anio2 == 0 || anio == anio2 ){
						                	 sql+="AND DATE_FORMAT(fechaRegistro, '%Y') = "+anio+" ";
							            }else{
							            	 sql+="AND DATE_FORMAT(fechaRegistro, '%Y') BETWEEN "+anio+" AND "+anio2+" ";
							            }
						                   
						                    sql+="AND ((fechaRegistro BETWEEN fechaRegistro AND LAST_DAY(t.mes)) "
						                    +"OR (fechaHasta BETWEEN fechaRegistro AND LAST_DAY(t.mes))) "
						                    +"AND fechaHasta >= FIRST_DAY(t.mes)) aa "
						            +"GROUP BY aa.mes) ee "
						        +"WHERE ";
						        if(anio2 == 0 || anio == anio2){
						        	if(mes2.equals("0")){
						        		sql+="mes = '"+anio+"-"+mes+"-01' ) / (SELECT ";
						        	}else{
						        		sql+="mes BETWEEN '"+anio+"-"+mes+"-01' and '"+anio+"-"+mes2+"-01' ) / (SELECT ";
						        	}
						        	 
						        	
					            }else{
					            	sql+="mes BETWEEN '"+anio+"-"+mes+"-01' AND '"+anio2+"-"+mes2+"-01') / (SELECT ";
					            }
						            
						            sql+="COUNT(DISTINCT codigo_trabajador) AS trabajadores "
						        +"FROM "
						            +"contratos "
						        +"WHERE "
						            +"idSociedad = "+empresa+" ";
						            if(anio2 == 0 || anio == anio2 ){
						            	
						            	if(mes2.equals("0")){
						            		sql+="AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
									                +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes+"-01')) "
									                +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes+"-01')))) * 100) AS tasa_siniestrabilidad,";
						            	}else{
						            		sql+="AND DATE_FORMAT(fechaInicio_actividad, '%Y') = "+anio+" "
									                +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes2+"-01')) "
									                +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio+"-"+mes2+"-01')))) * 100) AS tasa_siniestrabilidad,";
						            	}
						            	
						            }else{
						            	sql+="AND DATE_FORMAT(fechaInicio_actividad, '%Y') BETWEEN 2019 AND "+anio2+" "
								                +"AND ((fechaInicio_actividad BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio2+"-"+mes2+"-01')) "
								                +"OR (FechaTerminoContrato BETWEEN FIRST_DAY('"+anio+"-"+mes+"-01') AND LAST_DAY('"+anio2+"-"+mes2+"-01')))) * 100) AS tasa_siniestrabilidad,";
						            }
						                
								sql+=" "+anio+" as anio_1, "+anio2+" as anio_2, "
										+ " CASE "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'january' THEN 'Enero' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'february' THEN 'Febrero' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'march' THEN 'Marzo' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'april' THEN 'Abril' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'may' THEN 'Mayo' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'june' THEN 'Junio' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'july' THEN 'Julio' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'august' THEN 'Agosto' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'september' THEN 'Septiembre' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'october' THEN 'Octubre' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'november' THEN 'Noviembre' "
											    +"WHEN  MONTHNAME('"+anio+"-"+mes+"-01') = 'december' THEN 'Diciembre' "
											    +"ELSE MONTHNAME('"+anio+"-"+mes+"-01') "
											    +"END as nombremes, "
											    
												+ " CASE "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'january' THEN 'Enero' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'february' THEN 'Febrero' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'march' THEN 'Marzo' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'april' THEN 'Abril' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'may' THEN 'Mayo' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'june' THEN 'Junio' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'july' THEN 'Julio' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'august' THEN 'Agosto' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'september' THEN 'Septiembre' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'october' THEN 'Octubre' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'november' THEN 'Noviembre' "
												+"WHEN  MONTHNAME('"+anio2+"-"+mes2+"-01') = 'december' THEN 'Diciembre' "
												+"ELSE MONTHNAME('"+anio2+"-"+mes2+"-01') "
												+"END as nombremes2 ";
												
												   
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					IncidentesMutualidad p = new IncidentesMutualidad();
					
				   
				    p.setNaccidentes(rs.getInt("accidentes"));
				    p.setTasa_accidentabilidad(rs.getDouble("tasa_accidentabilidad"));
				    p.setDiasperdidos(rs.getInt("dias_perdidos"));
				    p.setDelmes(rs.getInt("del_mes"));
				    p.setDemesesanteriores(rs.getInt("de_meses_anteriores"));
				    p.setTasa_siniestrabilidad(rs.getDouble("tasa_siniestrabilidad"));
				    p.setAnio1S(rs.getInt("anio_1"));
				    p.setAnio2S(rs.getInt("anio_2"));
				    p.setNombremes(rs.getString("nombremes"));
				    p.setNombremes2(rs.getString("nombremes2"));
					lista.add(p);
				
				}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}
	
	
	public static String getExelAnalisisPeriodosMoviles(List<IncidentesMutualidad> incidente) throws Exception {

		String ruta = utils.csvDetalleNomina();
		Date fechaActual = new Date();

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		String ruta3 = "";

		try {
		
			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			File archivo = new File("ANALISIS_PERIODOS_MOVILES" + horaf + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("ANALISIS_PERIODOS_MOVILES");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			// Creamos el estilo paga las celdas del encabezado
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle stylenew = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle combined2 = workbook.createCellStyle();
			CellStyle combined = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			
			
			
		   
			
			
			CellStyle style5 = workbook.createCellStyle();
		    style5.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		    style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    Font font2 = workbook.createFont();
	            font2.setColor(IndexedColors.BLACK.getIndex());
	            style5.setFont(font2);
			
			combined.setAlignment(CellStyle.ALIGN_RIGHT);
//			combined.setBorderTop(CellStyle.BORDER_DOUBLE);
//			combined.setFont(font); // color de letra
			
			
			
			style3.setBorderTop(CellStyle.BORDER_DOUBLE);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
//			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			
			

			String empresaMys = "Análisis Periodos Móviles";
			String[] titulos = { empresaMys };

			// Creamos una fila en la hoja en la posicion 0
			Row fila = pagina.createRow(0);
			// Indicamos el estilo que deseamos usar en la celda, en este caso
			// el unico que hemos creado
			Cell celda1 = fila.createCell(0);

			celda1.setCellValue(titulos[0]);

			List<String> linea1 = new ArrayList<String>();
			List<String> linea2 = new ArrayList<String>();
			List<String> linea3 = new ArrayList<String>();
			List<String> linea4 = new ArrayList<String>();
			List<String> linea5 = new ArrayList<String>();
			List<String> linea6 = new ArrayList<String>();
			List<String> linea7 = new ArrayList<String>();
			linea1.add("Concepto");
			linea2.add("N°Accidentes");
			linea3.add("Tasa Accidentabilidad");
			linea4.add("Días Pérdidos");
			linea5.add("Del mes ");
			linea6.add("De meses anteriores");
			linea7.add("Tasa de Siniestralidad");
			
			for (IncidentesMutualidad emp1 : incidente) {
				
				String mes2name = emp1.getNombremes2();
				System.out.println(emp1.getNombremes2());
				if(emp1.getAnio2S() == 0  && mes2name == null){
					linea1.add(emp1.getNombremes() + " de " +emp1.getAnio1S());
				}else if(emp1.getAnio2S() == 0 && emp1.getNombremes2() != null){
					linea1.add("Año Móvil "+ emp1.getNombremes() + " " +emp1.getAnio1S()+ " - " + emp1.getNombremes2()+ " " + emp1.getAnio2S());
				}else if(emp1.getAnio2S() == emp1.getAnio1S() && emp1.getNombremes() !=  emp1.getNombremes2()){
					linea1.add("Año Móvil "+ emp1.getNombremes() + " " +emp1.getAnio1S()+ " - " + emp1.getNombremes2() + " " + emp1.getAnio1S());
				}else{
					linea1.add("Año Móvil "+ emp1.getNombremes() + " " +emp1.getAnio1S()+ " - " + emp1.getNombremes2()+ " " + emp1.getAnio2S());
				}
			}
						// linea 2
						for (IncidentesMutualidad emp1 : incidente) {
							linea2.add(""+emp1.getNaccidentes());
						}
						// linea 3
						for (IncidentesMutualidad emp1 : incidente) {
							double tasa;
							tasa = emp1.getTasa_accidentabilidad();
	
							BigDecimal numberBigDecimal = new BigDecimal(tasa);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							
							linea3.add(""+numberBigDecimal);
						}
						// linea 4
						for (IncidentesMutualidad emp1 : incidente) {
							linea4.add(""+emp1.getDiasperdidos());
						}
						// linea 5
						for (IncidentesMutualidad emp1 : incidente) {
							linea5.add(""+emp1.getDelmes());
						}
						// linea 6
						for (IncidentesMutualidad emp1 : incidente) {
							linea6.add(""+emp1.getDemesesanteriores());
						}
						
						// linea 7
						for (IncidentesMutualidad emp1 : incidente) {
							
							double tasa;
							tasa = emp1.getTasa_siniestrabilidad();
	
							BigDecimal numberBigDecimal = new BigDecimal(tasa);
							numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
							linea7.add(""+numberBigDecimal);
						}
						
						fila = pagina.createRow(1);
						// linea 1
						for (int i = 0; i < linea1.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea1.get(i));
							celda0.setCellStyle(style5);
						}
						fila = pagina.createRow(2);
						// linea 2
						for (int i = 0; i < linea2.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea2.get(i));
						}
						fila = pagina.createRow(3);
						// linea 3
						for (int i = 0; i < linea3.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea3.get(i));
						}
						fila = pagina.createRow(4);
						// linea 4
						for (int i = 0; i < linea4.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea4.get(i));
						}
						fila = pagina.createRow(5);
						// linea 5
						for (int i = 0; i < linea5.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea5.get(i));
						}
						fila = pagina.createRow(6);
						// linea 6
						for (int i = 0; i < linea6.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea6.get(i));
						}
						fila = pagina.createRow(7);
						// linea 7
						for (int i = 0; i < linea7.size(); i++) {
							Cell celda0 = fila.createCell(i);
							celda0.setCellValue(linea7.get(i));
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
			pagina.autoSizeColumn(10);

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
