package SWDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.CCAFLosAndes;
import lib.classSW.DatosAvisoInspeccionTrabajo;
import lib.classSW.DatosTrabajadorFiniquito;
import lib.classSW.DeclaracionJurada;
import lib.classSW.Diferencialiquidacion;
import lib.classSW.Liquidacion;
import lib.classSW.PreNominaAnticipo;
import lib.db.ConnectionDB;
import lib.db.sw.FiniquitosBD;
import wordCreator.utils;

public class AvisoInspeccionTrabajoDB {

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String generarExcelAvisoInspeccion(int idSociedad,ArrayList<Object> row) throws Exception {

		String RutaArchivo = "";
		int ultimoID = 0;
		String ruta = utils.FiniquitoTxt();

		Date fechaActual = new Date();
		System.out.println(fechaActual);

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";
		System.out.println(formatoHora);
		System.out.println(formatoFecha);

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx

			String horaf = formatoHora.replaceAll("[:]", "");
			String Nombrearchivo = "AVISOTERMINOCONTRATO" + horaf + ".xls";
			File archivo = new File("AVISOTERMINOCONTRATO" + horaf + ".xls");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			fila = pagina.createRow(0);

			String[] titulo6 = { "rut_tr", "dv_tr", "nombres_tr", "ap_paterno_tr", "ap_materno_tr", "comuna_tr", "sexo",
					"fecha_notificacion", "medio_notificacion", "oficina_correos", "fecha_inicio", "fecha_termino",
					"monto_anio_servicio", "monto_aviso_previo", "CodigoTipoCausal", "ArticuloCausal", "HechosCausal",
					"EstadoCotizaciones", "TipoDocCotizaciones" };

			for (int i = 0; i < titulo6.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellValue(titulo6[i]);
			}

			

			int numeroFor = 1;
			int valorAnio = 0;
			int valorMesAviso = 0;
			int rut;
			String dvtrab = "";
			String NombreTrab = "";
			String apellidopaterno = "";
			String apellidomaterno = "";
			int comuna;
			String sexo = "";
			String fechaNotificacion = "";
			String medionotificacion = "";
			String oficinaCorreo = "";
			String fechainicio = "";
			String fechatermino = "";
			int codigotipocausal;
			String Articulocausal;
			String hechos = "";
			String estadocotizaciones = "";
			int tipodoccotizaciones;
			fila = pagina.createRow(1);
			
			for (Object datos : row) {
				
				@SuppressWarnings("unchecked")
				ArrayList<DatosAvisoInspeccionTrabajo> datos2 = (ArrayList<DatosAvisoInspeccionTrabajo>) datos;
				
				for (DatosAvisoInspeccionTrabajo emplista : datos2) {

					if (emplista.getIdconcepto() == 2007) {
						valorAnio = emplista.getValor();
						System.out.println("valor año " + valorAnio);
					} else if (emplista.getIdconcepto() == 2008) {
						valorMesAviso = emplista.getValor();
						System.out.println("valor mes " + valorMesAviso);

					}

					else if (emplista.getIdconcepto() == 3) {
						hechos = "" + emplista.getHechoscausal() + "";
						System.out.println("hechos " + hechos);
					}
					// Trabajador.get(0).getRut();
					rut = emplista.getRut();
					dvtrab = emplista.getDv();
					NombreTrab = emplista.getNombre();
					apellidopaterno = emplista.getAp_paterno();
					apellidomaterno = emplista.getAp_materno();
					comuna = emplista.getComuna();
					sexo = emplista.getSexo();
					fechaNotificacion = emplista.getFecha_notificacion();
					String fechaNotificacionsplit[]  = fechaNotificacion.split("-");
					String fechaNotificacion2 = fechaNotificacionsplit[2]+"/"+fechaNotificacionsplit[1]+"/"+fechaNotificacionsplit[0];
					medionotificacion = emplista.getMedio_notficacion();
					oficinaCorreo = emplista.getOficina_correos();
					fechainicio = emplista.getFecha_inicio();
					String fechainiciosplit[]  = fechainicio.split("-");
					String fechainicio2 = fechainiciosplit[2]+"/"+fechainiciosplit[1]+"/"+fechainiciosplit[0];
					fechatermino = emplista.getFecha_termino();
					String fechaterminosplit[]  = fechatermino.split("-");
					String fechatermino2 = fechaterminosplit[2]+"/"+fechaterminosplit[1]+"/"+fechaterminosplit[0];
					codigotipocausal = emplista.getCodigotipocausal();
					Articulocausal = emplista.getArticulocausal();
					estadocotizaciones = emplista.getEstadocotizaciones();
					tipodoccotizaciones = emplista.getTipodoccotizaciones();

					String[] titulo7 = { "" + rut + "", "" + dvtrab + "", "" + NombreTrab + "", "" + apellidopaterno + "",
							"" + apellidomaterno + "", "" + comuna + "", "" + sexo + "", "" + fechaNotificacion2 + "",
							"" + medionotificacion + "", "" + oficinaCorreo + "", "" + fechainicio2 + "",
							"" + fechatermino2 + "", "" + valorAnio + "", "" + valorMesAviso + "",
							"" + codigotipocausal + "", "" + Articulocausal + "", "" + hechos + "",
							"" + estadocotizaciones + "", "" + tipodoccotizaciones + "" };

					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);

					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);

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

					Cell celda11 = fila.createCell(11);
					celda11.setCellValue(titulo7[11]);

					Cell celda12 = fila.createCell(12);
					celda12.setCellValue(titulo7[12]);

					Cell celda13 = fila.createCell(13);
					celda13.setCellValue(titulo7[13]);

					Cell celda14 = fila.createCell(14);
					celda14.setCellValue(titulo7[14]);

					Cell celda15 = fila.createCell(15);
					celda15.setCellValue(titulo7[15]);

					Cell celda16 = fila.createCell(16);
					celda16.setCellValue(titulo7[16]);

					Cell celda17 = fila.createCell(17);
					celda17.setCellValue(titulo7[17]);

					Cell celda18 = fila.createCell(18);
					celda18.setCellValue(titulo7[18]);
					
					

				}
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
			pagina.autoSizeColumn(10);
			pagina.autoSizeColumn(11);
			pagina.autoSizeColumn(12);
			pagina.autoSizeColumn(13);
			pagina.autoSizeColumn(14);
			pagina.autoSizeColumn(15);
			pagina.autoSizeColumn(16);
			pagina.autoSizeColumn(17);
			pagina.autoSizeColumn(18);
		

					
					
			

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
		// return Integer.toString(ultimoID);
		// return null;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////// OBTENER DATOS PARA AGREGARLOS AL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////// EXCEL///////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static ArrayList<DatosAvisoInspeccionTrabajo> obtenerDatosAviso(int idSociedad, int cod , int idcont) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<DatosAvisoInspeccionTrabajo> Lista = new ArrayList<DatosAvisoInspeccionTrabajo>();

		try {

						
			sql = "select "
			+"co.codigo_trabajador,"
			+"co.articuloTerminoContrato as co1 ,co.incisoTerminoContrato as co2,"
			+"idSociedad,id,co.fechaInicio_actividad,co.FechaTerminoContrato,co.fechaNotificacion,"
			+"(SELECT codPrevired FROM parametros WHERE codigo = 'SEXO' AND llave  = (select idGenero from trabajadores where codigo = co.codigo_trabajador)) as sexo,"
			+"(select inspecionTrabajo from comuna WHERE id = (select idComuna from trabajadores where codigo = co.codigo_trabajador)) as comuna,"
			+"(select nombre from trabajadores where codigo = co.codigo_trabajador) as nombre,"
			+"(select apellidoMaterno from trabajadores where codigo = co.codigo_trabajador) as apma,"
			+"(select apellidoPaterno from trabajadores where codigo = co.codigo_trabajador) as appa,"
			+"(select SUBSTRING_INDEX(REPLACE(rut, '.', ''), '-', 1) from trabajadores where codigo = co.codigo_trabajador) as rut,"
			+"(select SUBSTRING_INDEX(rut, '-', -1) from trabajadores where codigo = co.codigo_trabajador) as dv,"
			+"'P' as medio_notificacion,'' as ofiCorreo,'Pagado' as EstadoCotizaciones,1  as TipoDocCotizaciones,"
			+"co.descripcion   as HechosCausal,"
			+"(select codigo_afc from sw_m_incisoTerminoContrato WHERE idArticuloTerminoContrato = co1 AND idIncisoTerminoContrato =  co2) AS CodigoTipoCausal,"
			+"(select ArticuloCausal from sw_m_incisoTerminoContrato WHERE idArticuloTerminoContrato = co1 AND idIncisoTerminoContrato =  co2) AS ArticuloCausal "
			+"from contratos co "
			+"WHERE co.paraFiniquitar = 1 and co.idSociedad = "+idSociedad+" AND co.codigo_trabajador = "+cod+" AND co.id = "+idcont+"";
        

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				// añadir codigo de la comuna segun archivo de la AFC

				// public String articulocausal;
				//

				DatosAvisoInspeccionTrabajo tr = new DatosAvisoInspeccionTrabajo();
				//tr.setIdconcepto(rs.getInt("idConcepto"));
				tr.setRut(rs.getInt("rut"));
				tr.setDv(rs.getString("dv"));
				tr.setNombre(rs.getString("nombre"));
				tr.setAp_paterno(rs.getString("appa"));
				tr.setAp_materno(rs.getString("apma"));
				tr.setComuna(rs.getInt("comuna"));
				tr.setSexo(rs.getString("sexo"));
				tr.setFecha_notificacion(rs.getString("fechaNotificacion"));
				tr.setMedio_notficacion(rs.getString("medio_notificacion"));
				tr.setOficina_correos(rs.getString("ofiCorreo"));
				tr.setFecha_inicio(rs.getString("fechaInicio_actividad"));
				tr.setFecha_termino(rs.getString("FechaTerminoContrato"));
				tr.setCodigotipocausal(rs.getInt("CodigoTipoCausal"));
				tr.setTipodoccotizaciones(rs.getInt("TipoDocCotizaciones"));
				tr.setEstadocotizaciones(rs.getString("EstadoCotizaciones"));
				tr.setEstadocotizaciones(rs.getString("EstadoCotizaciones"));
				tr.setHechoscausal(rs.getString("HechosCausal"));
				tr.setArticulocausal(rs.getString("ArticuloCausal"));
				//tr.setValor(rs.getInt("valor"));

				Lista.add(tr);

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}
	
	
	public static ArrayList<DatosAvisoInspeccionTrabajo> buscartrabajadores(int idSociedad, String fechainicio,String fechatermino, String huerto,int rolPrivado) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<DatosAvisoInspeccionTrabajo> Lista = new ArrayList<DatosAvisoInspeccionTrabajo>();

		try {

			sql = "SELECT "
					+ "co.codigo_trabajador, co.id"
					+" FROM "
					+"contratos co "
					+ "INNER JOIN trabajadores TR on TR.codigo = co.codigo_trabajador "
					+"WHERE "
					    +"co.paraFiniquitar = 1 "
					        +"AND co.idSociedad = "+idSociedad+" and (co.FechaTerminoContrato BETWEEN '"+fechainicio+"' AND '"+fechatermino+"') "
					        + "AND NOT TR.tipoTrabajador = 4 ";
					if("null".equals(huerto)){}else{sql += " and TR.idHuerto = '"+huerto+"'";}
					
					if(rolPrivado == 1){
						sql += " and  TR.rolPrivado in (1,0) ";
					}else{
						sql += " and  TR.rolPrivado in (0) ";
					}
					
					sql += "GROUP BY co.codigo_trabajador , co.id ";
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				// añadir codigo de la comuna segun archivo de la AFC

				// public String articulocausal;
				//

				DatosAvisoInspeccionTrabajo tr = new DatosAvisoInspeccionTrabajo();
				tr.setCodigotrabajador(rs.getInt("codigo_trabajador"));
				tr.setIdcontrato(rs.getInt("id"));
				

				Lista.add(tr);

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}
	
	public static ArrayList<CCAFLosAndes> buscartrabajadoresCCAF(int idSociedad,int periodo, int rolPrivado) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<CCAFLosAndes> Lista = new ArrayList<CCAFLosAndes>();

		try {

			sql = "select "
					+"case when tr.rut = '' then "
					+"UPPER(SUBSTRING_INDEX(REPLACE(tr.rutTemporal, '.', ''),'-',1)) "
					+"else " 
					+"UPPER(SUBSTRING_INDEX(REPLACE(tr.rut, '.', ''),'-',1)) end "
					+"as rut,"
					+"case when tr.rut = '' then " 
					+"UPPER(SUBSTRING_INDEX(REPLACE(tr.rutTemporal, '.', ''),'-',-1)) "
					+"else " 
					+"UPPER(SUBSTRING_INDEX(REPLACE(tr.rut, '.', ''),'-',-1)) end "
					+"as dv,"
					+"trim(UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ',tr.nombre))) as nombrecompleto,"
					+"SUBSTRING(UPPER(pa.descripcion),1,1) as sexo,"
					+"case " 
					+"when tr.idIsapre = 7 then 2 else 1 end as regimensalud,"
					+"case "
					+"when tr.idAFP = 1 then 20 "
					+"when tr.idAFP = 2 then 18 "
					+"when tr.idAFP = 3 then 27 "
					+"when tr.idAFP = 4 then 22 "
					+"when tr.idAFP = 5 then 24 "
					+"when tr.idAFP = 6 then 19 "
					+"else 0 end as regimenprevisional,"
					+"REPLACE(tr.fNacimiento, '-', '') as fechanacimiento,"
					+"SUBSTRING(trim(UPPER(CONCAT(tr.calle, ' ', tr.ndireccion))),1,50) as direccion,"
					+"SUBSTRING(UPPER(comuna.nombre),1,25) as comuna,"
					+"SUBSTRING(UPPER(provincia.nombre),1,25) as provincia,"
					+"SUBSTRING_INDEX(tr.telefono,' ',1) as codigoarea,"
					+"SUBSTRING_INDEX(tr.telefono,' ',-1) as telefono,"
					+"SUBSTRING(REPLACE(REPLACE(tr.celular,' ',''),'-',''),1,9)as celular,"
					+"SUBSTRING(UPPER(tr.email),1,40) as email,"
					+"REPLACE(con.fechaInicio_actividad, '-', '')  as fechacontrato "
					+"from trabajadores tr "
					+"inner join parametros pa on tr.idGenero = pa.llave "
					+"inner join comuna comuna on comuna.id = tr.idComuna "
					+"inner join provincia provincia on provincia.id = tr.idProvincia "
					+"inner join contratos con on con.codigo_trabajador = tr.codigo "
					+"where tr.tipoTrabajador != 4 and  ";
					
					if(rolPrivado == 1){
						sql += " and  tr.rolPrivado in (1,0) ";
					}else{
						sql += " and  tr.rolPrivado in (0) ";
					}
					sql += " pa.codigo = 'SEXO' AND tr.idIsapre !=0 AND con.idSociedad = "+idSociedad+" "
					+ "and date_format(fechaInicio_actividad, '%Y%m') <= "+periodo+" "
					+"and (date_format(FechaTerminoContrato, '%Y%m') >= "+periodo+" or FechaTerminoContrato is null)" ;
				
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();

			CCAFLosAndes tr1 = new CCAFLosAndes();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			  System.out.println(name);
			  	
			    
				if(i == 1){tr1.setRut_t(name);}
				else if(i == 2){tr1.setDv_t(name);}
				else if(i == 3){tr1.setNombrecompleto_t(name);}
				else if(i == 4){tr1.setSexo_t(name);}
				else if(i == 5){tr1.setRegimensalud_t(name);}
				else if(i == 6){tr1.setRegimenprevisional_t(name);}
				else if(i == 7){tr1.setFechanacimiento_t(name);}
				else if(i == 8){tr1.setDireccion_t(name);}
				else if(i == 9){tr1.setComuna_t(name);}
				else if(i == 10){tr1.setProvincia_t(name);}
				else if(i == 11){tr1.setCodigoarea_t(name);}
				else if(i == 12){tr1.setTelefono_t(name);}
				else if(i == 13){tr1.setCelular_t(name);}
				else if(i == 14){tr1.setEmail_t(name);}
				else if(i == 15){tr1.setFechacontrato_t(name);}
				
			}
			
			Lista.add(tr1);

			while (rs.next()) {

				
				CCAFLosAndes tr = new CCAFLosAndes();
				tr.setRut_t(rs.getString("rut"));
				tr.setDv_t(rs.getString("dv"));
				tr.setNombrecompleto_t(rs.getString("nombrecompleto"));
				tr.setSexo_t(rs.getString("sexo"));
				tr.setRegimensalud_t(rs.getString("regimensalud"));
				tr.setRegimenprevisional_t(rs.getString("regimenprevisional"));
				tr.setFechanacimiento_t(rs.getString("fechanacimiento"));
				tr.setDireccion_t(rs.getString("direccion"));
				tr.setComuna_t(rs.getString("comuna"));
				tr.setProvincia_t(rs.getString("provincia"));
				tr.setCodigoarea_t(rs.getString("codigoarea"));
				tr.setTelefono_t(rs.getString("telefono"));
				tr.setCelular_t(rs.getString("celular"));
				tr.setEmail_t(rs.getString("email"));
				tr.setFechacontrato_t(rs.getString("fechacontrato"));
				     
				

				Lista.add(tr);

			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}
	
	public static ArrayList<DeclaracionJurada> buscartrabajadoresCCAFDeclaracion(int idSociedad,int anio, int rolPrivado) throws Exception {
		Statement ps = null;
		String sql = "";
		Statement ps2 = null;
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<CCAFLosAndes> Lista = new ArrayList<CCAFLosAndes>();
		ArrayList<DeclaracionJurada> Lista2 = new ArrayList<DeclaracionJurada>();

		try {

			sql = "select distinct sw.cod_trabajador  from sw_liquidacion sw "
					+ "where sw.id_sociedad = "+idSociedad+" and  "
					+ "sw.periodo in ('"+anio+"01' , '"+anio+"02', '"+anio+"03', '"+anio+"04', '"+anio+"05', '"+anio+"06', '"+anio+"07', '"+anio+"08', '"+anio+"09', '"+anio+"10', '"+anio+"11', '"+anio+"12')";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			String enero_ = anio+"01";
			String febrero_ = anio+"02";
			String marzo_ = anio+"03";
			String abril_ = anio+"04";
			String mayo_ = anio+"05";
			String junio_ = anio+"06";
			String julio_ = anio+"07";
			String agosto_ = anio+"08";
			String septiembre_ = anio+"09";
			String octubre_ = anio+"10";
			String noviembre_ = anio+"11";
			String diciembre_ = anio+"12";

			while (rs.next()) {
				System.out.println(rs.getInt("cod_trabajador"));
			
				sql2 = "SELECT  "
						    +"CASE "
						        +"WHEN "
						            +"tr.rut = '' "
						        +"THEN "
						            +"REPLACE(REPLACE(tr.rutTemporal, '.', ''),"
						                +"'-',"
						                +"'') "
						        +"ELSE REPLACE(REPLACE(tr.rut, '.', ''),"
						            +"'-',"
						            +"'') "
						    +"END AS rut,"
						    +"CAST(totalhaberimponible AS SIGNED) AS totalhaberimponible,"
						    +"CAST(inspuestounico AS SIGNED) AS inspuestounico,"
						    +"0 AS columna4,"
						    +"0 AS columna5,"
						    +"0 AS columna6,"
						    +"0 AS columna7,"
						    +"IF(enero IS NOT NULL,"
						        +"IF(enero <= 0, '', 'C'),"
						        +"'') AS enero,"
						    +"IF(febrero IS NOT NULL,"
						        +"IF(febrero <= 0, '', 'C'),"
						        +"'') AS febrero,"
						    +"IF(marzo IS NOT NULL,"
						        +"IF(marzo <= 0, '', 'C'),"
						        +"'') AS marzo,"
						    +"IF(abril IS NOT NULL,"
						        +"IF(abril <= 0, '', 'C'),"
						        +"'') AS abril,"
						    +"IF(mayo IS NOT NULL,"
						        +"IF(mayo <= 0, '', 'C'),"
						        +"'') AS mayo,"
						    +"IF(junio IS NOT NULL,"
						        +"IF(junio <= 0, '', 'C'),"
						        +"'') AS junio,"
						    +"IF(julio IS NOT NULL,"
						        +"IF(julio <= 0, '', 'C'),"
						        +"'') AS julio,"
						    +"IF(agosto IS NOT NULL,"
						        +"IF(agosto <= 0, '', 'C'),"
						        +"'') AS agosto,"
						    +"IF(septiembre IS NOT NULL,"
						        +"IF(septiembre <= 0, '', 'C'),"
						        +"'') AS septiembre,"
						    +"IF(octubre IS NOT NULL,"
						        +"IF(octubre <= 0, '', 'C'),"
						        +"'') AS octubre,"
						    +"IF(noviembre IS NOT NULL,"
						        +"IF(noviembre <= 0, '', 'C'),"
						        +"'') AS noviembre,"
						    +"IF(diciembre IS NOT NULL,"
						        +"IF(diciembre <= 0, '', 'C'),"
						        +"'') AS diciembre "
						+"FROM "
						    +"trabajadores tr "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw1.codTrabajador,"
						            +"sw1.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw1.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) enero "
						    +"FROM "
						       +"sw_liquidacionDetalle sw1 "
						    +"INNER JOIN contratos co ON sw1.idContrato = co.id "
						    +"WHERE "
						       +"sw1.periodo = "+enero_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw1.codTrabajador , sw1.periodo) AS xenero ON tr.codigo = xenero.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw2.codTrabajador,"
						            +"sw2.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw2.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) febrero "
						    +"FROM "
						        +"sw_liquidacionDetalle sw2 "
						    +"INNER JOIN contratos co ON sw2.idContrato = co.id "
						    +"WHERE "
						        +"sw2.periodo = "+febrero_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw2.codTrabajador , sw2.periodo) AS xfebrero ON tr.codigo = xfebrero.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw3.codTrabajador,"
						            +"sw3.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw3.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) marzo "
						    +"FROM "
						        +"sw_liquidacionDetalle sw3 "
						    +"INNER JOIN contratos co ON sw3.idContrato = co.id "
						    +"WHERE "
						        +"sw3.periodo = "+marzo_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw3.codTrabajador , sw3.periodo) AS xmarzo ON tr.codigo = xmarzo.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw4.codTrabajador,"
						            +"sw4.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw4.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) abril "
						    +"FROM "
						        +"sw_liquidacionDetalle sw4 "
						    +"INNER JOIN contratos co ON sw4.idContrato = co.id "
						    +"WHERE "
						        +"periodo = "+abril_+" AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw4.codTrabajador , sw4.periodo) AS xabril ON tr.codigo = xabril.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw5.codTrabajador,"
						            +"sw5.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw5.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) mayo "
						    +"FROM "
						        +"sw_liquidacionDetalle sw5 "
						    +"INNER JOIN contratos co ON sw5.idContrato = co.id "
						    +"WHERE "
						        +"periodo = "+mayo_+" AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw5.codTrabajador , sw5.periodo) AS xmayo ON tr.codigo = xmayo.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw6.codTrabajador,"
						            +"sw6.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw6.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) junio "
						    +"FROM "
						        +"sw_liquidacionDetalle sw6 "
						    +"INNER JOIN contratos co ON sw6.idContrato = co.id "
						    +"WHERE "
						        +"periodo = "+junio_+" AND co.idSociedad = 4"+idSociedad+" "
						    +"GROUP BY sw6.codTrabajador , sw6.periodo) AS xjunio ON tr.codigo = xjunio.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw7.codTrabajador,"
						            +"sw7.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw7.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) julio "
						    +"FROM "
						        +"sw_liquidacionDetalle sw7 "
						    +"INNER JOIN contratos co ON sw7.idContrato = co.id "
						    +"WHERE "
						        +"periodo = "+julio_+" AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw7.codTrabajador , sw7.periodo) AS xjulio ON tr.codigo = xjulio.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw8.codTrabajador,"
						            +"sw8.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw8.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) agosto "
						    +"FROM "
						        +"sw_liquidacionDetalle sw8 "
						    +"INNER JOIN contratos co ON sw8.idContrato = co.id "
						    +"WHERE "
						        +"periodo = "+agosto_+" AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw8.codTrabajador , sw8.periodo) AS xagosto ON tr.codigo = xagosto.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw9.codTrabajador,"
						            +"sw9.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw9.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) septiembre "
						    +"FROM "
						        +"sw_liquidacionDetalle sw9 "
						    +"INNER JOIN contratos co ON sw9.idContrato = co.id "
						    +"WHERE "
						        +"sw9.periodo = "+septiembre_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw9.codTrabajador , sw9.periodo) AS xseptiembre ON tr.codigo = xseptiembre.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw10.codTrabajador,"
						            +"sw10.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw10.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) octubre "
						    +"FROM "
						        +"sw_liquidacionDetalle sw10 "
						    +"INNER JOIN contratos co ON sw10.idContrato = co.id "
						    +"WHERE "
						        +"sw10.periodo = "+octubre_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw10.codTrabajador , sw10.periodo) AS xoctubre ON tr.codigo = xoctubre.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw11.codTrabajador,"
						            +"sw11.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw11.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) noviembre "
						    +"FROM "
						        +"sw_liquidacionDetalle sw11 "
						    +"INNER JOIN contratos co ON sw11.idContrato = co.id "
						    +"WHERE "
						        +"sw11.periodo = "+noviembre_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw11.codTrabajador , sw11.periodo) AS xnoviembre ON tr.codigo = xnoviembre.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw12.codTrabajador,"
						            +"sw12.periodo,"
						            +"SUM(CASE "
						                +"WHEN sw12.idConcepto = 257 THEN valor "
						                +"ELSE 0 "
						            +"END) diciembre "
						    +"FROM "
						        +"sw_liquidacionDetalle sw12 "
						    +"INNER JOIN contratos co ON sw12.idContrato = co.id "
						    +"WHERE "
						        +"sw12.periodo = "+diciembre_+" "
						            +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY sw12.codTrabajador , sw12.periodo) AS xdiciembre ON tr.codigo = xdiciembre.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw13.codTrabajador,"
						            +"SUM(CASE "
						                +"WHEN sw13.idConcepto = 257 THEN sw13.valor "
						                +"ELSE 0 "
						            +"END) totalhaberimponible "
						    +"FROM "
						        +"sw_liquidacionDetalle sw13 "
						        + "INNER JOIN contratos co ON sw13.idContrato = co.id "
						    +"WHERE "
						        +"sw13.periodo IN ('"+anio+"01' , '"+anio+"02', '"+anio+"03', '"+anio+"04', '"+anio+"05', '"+anio+"06', '"+anio+"07', '"+anio+"08', '"+anio+"09', '"+anio+"10', '"+anio+"11', '"+anio+"12') "
						        +"AND co.idSociedad = "+idSociedad+" "
						    +"GROUP BY codTrabajador) AS xtotalhaberimponible ON tr.codigo = xtotalhaberimponible.codTrabajador "
						        +"LEFT JOIN "
						    +"(SELECT "
						        +"sw14.codTrabajador,"
						            +"SUM(CASE "
						                +"WHEN sw14.idConcepto = 39 THEN sw14.valor "
						                +"ELSE 0 "
						            +"END) inspuestounico "
						    +"FROM "
						        +"sw_liquidacionDetalle sw14 "
						        + "INNER JOIN contratos co ON sw14.idContrato = co.id "
						        +"AND co.idSociedad = "+idSociedad+" "
						    +"WHERE "
						        +"periodo IN ('"+anio+"01' , '"+anio+"02', '"+anio+"03', '"+anio+"04', '"+anio+"05', '"+anio+"06', '"+anio+"07', '"+anio+"08', '"+anio+"09', '"+anio+"10', '"+anio+"11', '"+anio+"12') "
						    +"GROUP BY codTrabajador) AS xinspuestounico ON tr.codigo = xinspuestounico.codTrabajador "
						+"WHERE "
						    +"tr.codigo = "+rs.getInt("cod_trabajador")+" ";
		
				System.out.println(sql2);
				ps2 = db.conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery(sql2);
				
				while (rs2.next()) {
					DeclaracionJurada tr2 = new DeclaracionJurada();
					
					tr2.setRut(rs2.getString("rut"));
					tr2.setTotalimponible(rs2.getInt("totalhaberimponible"));
					tr2.setInpuestounico(rs2.getInt("inspuestounico"));
					tr2.setColumna4(rs2.getInt("columna4"));
					tr2.setColumna5(rs2.getInt("columna5"));
					tr2.setColumna6(rs2.getInt("columna6"));
					tr2.setColumna7(rs2.getInt("columna7"));
					tr2.setEnero(rs2.getString("enero"));
					tr2.setFebrero(rs2.getString("febrero"));
					tr2.setMarzo(rs2.getString("marzo"));
					tr2.setAbril(rs2.getString("abril"));
					tr2.setMayo(rs2.getString("mayo"));
					tr2.setJunio(rs2.getString("junio"));
					tr2.setJulio(rs2.getString("julio"));
					tr2.setAgosto(rs2.getString("agosto"));
					tr2.setSeptiembre(rs2.getString("septiembre"));
					tr2.setOctubre(rs2.getString("octubre"));
					tr2.setNoviembre(rs2.getString("noviembre"));
					tr2.setDiciembre(rs2.getString("diciembre"));
					Lista2.add(tr2);
					
				}
			
			
			}
			

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
			
		}
		return Lista2;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String generarExcelBDCCAF(int idSociedad, ArrayList<CCAFLosAndes> buscarTrabajadorCCAF) throws Exception {

		String RutaArchivo = "";
		
		String ruta = utils.AvisoInspeccionTrabajo();


		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			CCAFLosAndes ru = AvisoInspeccionTrabajoDB.getRutEmpresa(idSociedad);
			String rut_empresa = ru.getRutEmpresa();
			
			String Nombrearchivo = rut_empresa + ".xlsx";
			File archivo = new File(rut_empresa + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();
            
			CellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			
			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			
			
			   	int numeroFor = 0;
			    String  rut;
				String 	dv = "";
				String 	nombrecompleto = "";
				String 	sexo = "";
				String  regimensalud;
				String  regimenprevisional = "";
				String  fechanacimiento = "";
				String 	direccion = "";
				String 	comuna = "";
				String 	provincia = "";
				String 	codigoarea = "";
				String 	telefono = "";
				String 	celular = "";
				String 	email = "";
				String 	fechacontrato = "";
				
			
				for (CCAFLosAndes emplista : buscarTrabajadorCCAF) {
					
					rut = emplista.getRut_t();
					dv= emplista.getDv_t();
					nombrecompleto= emplista.getNombrecompleto_t();
					sexo= emplista.getSexo_t();
					regimensalud = emplista.getRegimensalud_t();
					regimenprevisional= emplista.getRegimenprevisional_t();
					fechanacimiento= emplista.getFechanacimiento_t();
					direccion= emplista.getDireccion_t();
					comuna= emplista.getComuna_t();
					provincia= emplista.getProvincia_t();
					codigoarea= emplista.getCodigoarea_t();
					telefono= emplista.getTelefono_t();
					celular= emplista.getCelular_t();
					email= emplista.getEmail_t();
					fechacontrato= emplista.getFechacontrato_t();
					
					String[] titulo7 = { "" + rut + "", "" + dv + "", "" + nombrecompleto + "",
							"" + sexo + "", "" + regimensalud + "", "" + regimenprevisional + "", "" + fechanacimiento + "",
							"" + direccion + "", "" + comuna + "", "" + provincia + "",
							"" + codigoarea + "", "" + telefono + "", "" + celular + "",
							"" + email + "", "" + fechacontrato + "" };
					
					if(numeroFor == 0){
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					}else{
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					celda.setCellStyle(style2);
				    }
					
					if(numeroFor == 0){
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);	
					}else{
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					celda1.setCellStyle(style2);
					}
				
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2]);

					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3]);
					
					if(numeroFor == 0){
					Cell celda4 = fila.createCell(4);
					celda4.setCellValue(titulo7[4]);	
					}else{
					Cell celda4 = fila.createCell(4);
					celda4.setCellValue(titulo7[4]);
					celda4.setCellStyle(style2);
					}
					
					if(numeroFor == 0){
					Cell celda5 = fila.createCell(5);
					celda5.setCellValue(titulo7[5]);
					}else{
					Cell celda5 = fila.createCell(5);
					celda5.setCellValue(titulo7[5]);
					celda5.setCellStyle(style2);
					}
					
					if(numeroFor == 0){
					Cell celda6 = fila.createCell(6);
					celda6.setCellValue(titulo7[6]);
					}else{
					Cell celda6 = fila.createCell(6);
					celda6.setCellValue(titulo7[6]);
					celda6.setCellStyle(style2);
					}
					
					Cell celda7 = fila.createCell(7);
					celda7.setCellValue(titulo7[7]);

					Cell celda8 = fila.createCell(8);
					celda8.setCellValue(titulo7[8]);

					Cell celda9 = fila.createCell(9);
					celda9.setCellValue(titulo7[9]);

					Cell celda10 = fila.createCell(10);
					celda10.setCellValue(titulo7[10]);

					Cell celda11 = fila.createCell(11);
					celda11.setCellValue(titulo7[11]);

					Cell celda12 = fila.createCell(12);
					celda12.setCellValue(titulo7[12]);

					Cell celda13 = fila.createCell(13);
					celda13.setCellValue(titulo7[13]);

					if(numeroFor == 0){
					Cell celda14 = fila.createCell(14);
					celda14.setCellValue(titulo7[14]);
					}else{
					Cell celda14 = fila.createCell(14);
					celda14.setCellValue(titulo7[14]);
					celda14.setCellStyle(style2);
					}

					
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
			pagina.autoSizeColumn(11);
			pagina.autoSizeColumn(12);
			pagina.autoSizeColumn(13);
			pagina.autoSizeColumn(14);
			pagina.autoSizeColumn(15);
		

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
	
	public static CCAFLosAndes getRutEmpresa(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		CCAFLosAndes permiso = new CCAFLosAndes();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select REPLACE(rut,'.','') AS rut from sociedad where idSociedad = "+id+"";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {

				permiso.setRutEmpresa(rs.getString("rut"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return permiso;

	}
	
	
	///////////////////////////// TRABAJADORES SIN LIQUIDACION ///////////////////////////////////////////////
	
	public static ArrayList<Liquidacion> buscartrabajoresSinLiqui(int idSociedad,String periodo,int rolPrivado) throws Exception {
		Statement ps = null;
		String[] parts = periodo.split("-");
		String periodo1 = parts[1]+"-"+parts[0];
		String periodo2 = parts[0]+parts[1];
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<Liquidacion> Lista = new ArrayList<Liquidacion>();

		try {

			sql = "select co.codigo_trabajador as Codigo, tr.rut as Rut,"
				+ "upper(concat(tr.apellidoPaterno,' ',tr.apellidoMaterno,' ',tr.nombre)) as Nombre, "
				+"CASE "
				+"WHEN 1 = 1 THEN '"+periodo1+"' "
				+"END as Periodo,"
				+"UPPER((select descripcion from campo where campo = tr.idHuerto)) AS Huerto,"
				+ "case when co.fechaInicio_actividad is null then '' else  DATE_FORMAT(co.fechaInicio_actividad,'%d-%m-%Y') end as Fecha_Contrato,"
				+"case when co.FechaTerminoContrato is null then '' else DATE_FORMAT(co.FechaTerminoContrato,'%d-%m-%Y') end as Fecha_Termino_Contrato,"
				+"(select denominacionSociedad from sociedad where idSociedad = co.idSociedad) as Empresa "
				+"from contratos co "
				+"inner join trabajadores tr on tr.codigo = co.codigo_trabajador "
				+"where co.idSociedad = "+idSociedad+"  and tr.tipoTrabajador !=4 ";
				
				if(rolPrivado == 1){
					sql += " and  tr.rolPrivado in (1,0) ";
				}else{
					sql += " and  tr.rolPrivado in (0) ";
				}
			
				sql+=" AND DATE_format(fechaInicio_actividad,'%Y-%m') <= DATE_format('"+periodo+"-01', '%Y-%m' ) and "
				+"co.id NOT IN "
				+"(select id_contrato from sw_liquidacion where  id_sociedad = "+idSociedad+" and periodo = "+periodo2+") and co.EstadoContrato = 1"
				+ " AND co.EstadoContrato = 1 "
                +"AND (FechaTerminoContrato between '"+periodo+"-01' AND LAST_DAY('"+periodo+"-01') or FechaTerminoContrato is null )" ;
				
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();

			Liquidacion tr1 = new Liquidacion();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			  System.out.println(name);
			  
				if(i == 1){tr1.setCodigoTrab(name);}
				else if(i == 2){tr1.setRut(name);}
				else if(i == 3){tr1.setNombre(name);}
				else if(i == 4){tr1.setPeriodostring(name);}
				else if(i == 5){tr1.setHuerto(name);}
				else if(i == 6){tr1.setFechaInicio(name);}
				else if(i == 7){tr1.setFechaTermino(name);}
				else if(i == 8){tr1.setNombreempresa(name);}
				
				
			}
			
			Lista.add(tr1);

			while (rs.next()) {

				
				Liquidacion tr = new Liquidacion();
				
				tr.setCodigoTrab(rs.getString("Codigo"));
				tr.setRut(rs.getString("Rut"));
				tr.setNombre(rs.getString("Nombre"));
				tr.setPeriodostring(rs.getString("Periodo"));
				tr.setHuerto(rs.getString("Huerto"));
				tr.setFechaInicio(rs.getString("Fecha_Contrato"));
				tr.setFechaTermino(rs.getString("Fecha_Termino_Contrato"));
				tr.setNombreempresa(rs.getString("Empresa"));

				Lista.add(tr);

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String generarExcelSinLiqui(int idSociedad, ArrayList<Liquidacion> BuscarTrabajadorSN)
			throws Exception {

		String RutaArchivo = "";

		String ruta = utils.AvisoInspeccionTrabajo();

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			CCAFLosAndes ru = AvisoInspeccionTrabajoDB.getRutEmpresa(idSociedad);
			String rut_empresa = ru.getRutEmpresa();

			String Nombrearchivo = rut_empresa + ".xlsx";
			File archivo = new File(rut_empresa + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			CellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_RIGHT);

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			int numeroFor = 0;
			String codigo_t = "";
			String rut_t = "";
			String nombrecompleto = "";
			String periodo_t;
			String huerto_t = "";
			String empresa_t = "";
			String fechainicio = "";
			String fechatermino = "";
			

			for (Liquidacion emplista : BuscarTrabajadorSN) {

				codigo_t = emplista.getCodigoTrab();
				rut_t = emplista.getRut();
				nombrecompleto = emplista.getNombre();
				periodo_t = emplista.getPeriodostring();
				huerto_t = emplista.getHuerto();
				empresa_t = emplista.getNombreempresa();
				fechainicio = emplista.getFechaInicio();
				fechatermino = emplista.getFechaTermino();

				String[] titulo7 = { "" + codigo_t + "", "" + rut_t + "", "" + nombrecompleto + "", "" + periodo_t + "",
						"" + huerto_t + "", "" + fechainicio + "", "" + fechatermino + "", "" + empresa_t + "" };

				if (numeroFor == 0) {
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0].replaceAll("\\_"," "));
				} else {
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					celda.setCellStyle(style2);
				}

				if (numeroFor == 0) {
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1].replaceAll("\\_"," "));
				}else{
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
				}
				
				if (numeroFor == 0) {
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2].replaceAll("\\_"," "));
				}else{
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2]);
				}
				
				if (numeroFor == 0) {
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3].replaceAll("\\_"," "));
				}else{
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3]);
				}
				
				if (numeroFor == 0) {
					Cell celda4 = fila.createCell(4);
					celda4.setCellValue(titulo7[4].replaceAll("\\_"," "));
				}else{
					Cell celda4 = fila.createCell(4);
					celda4.setCellValue(titulo7[4]);
				}
				
				if (numeroFor == 0) {
					Cell celda5 = fila.createCell(5);
					celda5.setCellValue(titulo7[5].replaceAll("\\_"," "));
				}else{
					Cell celda5 = fila.createCell(5);
					celda5.setCellValue(titulo7[5]);
				}
				
				if (numeroFor == 0) {
					Cell celda6 = fila.createCell(6);
					celda6.setCellValue(titulo7[6].replaceAll("\\_"," "));
				}else{
					Cell celda6 = fila.createCell(6);
					celda6.setCellValue(titulo7[6]);
				}
				
				if (numeroFor == 0) {
					Cell celda7 = fila.createCell(7);
					celda7.setCellValue(titulo7[7].replaceAll("\\_"," "));
				}else{
					Cell celda7 = fila.createCell(7);
					celda7.setCellValue(titulo7[7]);
				}
				

				numeroFor = numeroFor + 1;
				fila = pagina.createRow(numeroFor);
			}

			pagina.autoSizeColumn(0);
			pagina.autoSizeColumn(1);
			pagina.autoSizeColumn(2);
			pagina.autoSizeColumn(3);
			pagina.autoSizeColumn(4);
			pagina.autoSizeColumn(5);
			

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
	
	//---------------GENERAR EXCEL DIFERENCIA LIQUIDACION--------------------------------------------------------

		public static ArrayList<Diferencialiquidacion> buscartrabajadoresDiferenciaLiquidacion(int idSociedad,int periodo,int rolPrivado) throws Exception {
			Statement ps = null;
			String sql = "";
			Statement ps2 = null;
			String sql2 = "";
			
			ConnectionDB db = new ConnectionDB();
			ArrayList<Diferencialiquidacion> Lista = new ArrayList<Diferencialiquidacion>();

			try {

				sql = "select " 
						+"sw_li.fecha_pago AS FECHA_PAGO,"
						+"an.cod_trabajador AS COD_TRABAJADOR,an.idContrato AS ID_CONTRATO,an.periodo AS PERIODO,"
						+"UPPER((select denominacionSociedad from sociedad where idSociedad = an.empresa)) as EMPRESA,"
						+"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ',tr.nombre)) as NOMBRE,"
						+"case when tr.rut = '' then tr.rutTemporal else tr.rut end as RUT,"
						+"UPPER((select descripcion from campo where campo = tr.idHuerto)) as HUERTO,"
						+"UPPER((select descripcion from parametros where codigo = 'TIPO_CONTRATO' and llave = co.tipoContrato)) as TIPO_CONTRATO,"
						+"DATE_FORMAT(co.fechaInicio_actividad,'%d-%m-%Y') AS FECHA_CONTRATO ,"
						+"an.monto_ingresado as MONTO,"
						+"case when li.valor is null then 0 else CAST(li.valor AS SIGNED)  end as DESCUENTO_LIQUIDACION,"
						+"(an.monto_ingresado - case when li.valor is null then 0 else CAST(li.valor AS SIGNED)  end) as DIFERENCIA, "
						+ "'ANTICIPO' AS CONCEPTO,"
						+ "' ' AS NOMBRE_HD "
						+"from sw_asignacionAnticipos an "
						+"left join sw_liquidacionDetalle li on li.idContrato = an.idContrato "
						+"and li.idConcepto = 43 and an.monto_ingresado = li.valor and  li.periodo = "+periodo+" " 
						+"left join trabajadores tr on tr.codigo = an.cod_trabajador "
						+"left JOIN contratos co on co.id = an.idContrato "
						+"left JOIN sw_liquidacion sw_li on sw_li.id_contrato = an.idContrato and sw_li.periodo = "+periodo+" " 
						+"where an.empresa = "+idSociedad+" and an.periodo = "+periodo+" ";
						if(rolPrivado == 1){
							sql += " and  tr.rolPrivado in (1,0) ";
						}else{
							sql += " and  tr.rolPrivado in (0) ";
						}
						
						sql+="group by  an.id,an.idContrato, an.monto_ingresado,li.valor,sw_li.fecha_pago order by NOMBRE;";
					
				
				
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				
				int columnCount = rsmd.getColumnCount();

				Diferencialiquidacion tr1 = new Diferencialiquidacion();
				for (int i = 1; i <= columnCount; i++ ) {
				  String name = rsmd.getColumnName(i);
				  System.out.println(name);
				  	


					if(i == 2){tr1.setCod(name);}
					else if(i == 3){tr1.setId_contrato(name);}
					else if(i == 4){tr1.setPeriodo(name);}
					else if(i == 5){tr1.setEmpresa(name);}
					else if(i == 6){tr1.setNombre(name);}
					else if(i == 7){tr1.setRut(name);}
					else if(i == 8){tr1.setHurto(name);}
					else if(i == 9){tr1.setTipo_contrato(name);}
					else if(i == 10){tr1.setFecha_contrato(name);}
					else if(i == 11){tr1.setAnticipo(name);}
					else if(i == 12){tr1.setDescuento_liquidacion(name);}
					else if(i == 13){tr1.setDiferencia(name);}
					else if(i == 14){tr1.setConcepto(name);}
					else if(i == 15){tr1.setNombreHD(name);}
					
				}
				
				Lista.add(tr1);

				while (rs.next()) {

					
					Diferencialiquidacion tr = new Diferencialiquidacion();
					tr.setCod(rs.getString("COD_TRABAJADOR"));
					tr.setId_contrato(rs.getString("ID_CONTRATO"));
					tr.setPeriodo(rs.getString("PERIODO"));
					tr.setEmpresa(rs.getString("EMPRESA"));
					tr.setNombre(rs.getString("NOMBRE"));
					tr.setRut(rs.getString("RUT"));
					tr.setHurto(rs.getString("HUERTO"));
					tr.setTipo_contrato(rs.getString("TIPO_CONTRATO"));
					tr.setFecha_contrato(rs.getString("FECHA_CONTRATO"));
					tr.setAnticipo(rs.getString("MONTO"));
					tr.setDescuento_liquidacion(rs.getString("DESCUENTO_LIQUIDACION"));
					tr.setDiferencia(rs.getString("DIFERENCIA"));
					tr.setConcepto(rs.getString("CONCEPTO"));
					tr.setNombreHD(rs.getString("NOMBRE_HD"));
					
					
					Lista.add(tr);

				}
				
				
				sql2 = "SELECT " 
						+"sw_li.fecha_pago AS FECHA_PAGO,"
					    +"HD.codigo_trabajador AS COD_TRABAJADOR,"
					    +"HD.idContrato AS ID_CONTRATO,"
					    +"sw_li.periodo AS PERIODO,"
						+"UPPER((select denominacionSociedad from sociedad where idSociedad = (select idSociedad from contratos where id = HD.idContrato))) as EMPRESA,"
					    +"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ',tr.nombre)) as NOMBRE,"
					    +"case when tr.rut = '' then tr.rutTemporal else tr.rut end as RUT,"
					    +"UPPER((select descripcion from campo where campo = tr.idHuerto)) as HUERTO,"
					    +"UPPER((select descripcion from parametros where codigo = 'TIPO_CONTRATO' and llave = co.tipoContrato)) as TIPO_CONTRATO,"
						+"DATE_FORMAT(co.fechaInicio_actividad,'%d-%m-%Y') AS FECHA_CONTRATO ,"
						+"IF(HD.llave_moneda = 2, "
							+"CAST(((select valor from sw_rhvalorMoneda where idMoneda = 2 and DATE_FORMAT(fecha,'%Y%m') = "+periodo+") * HD.monto) AS SIGNED ),"
					        +"HD.monto) as MONTO,"
							+"case when li.valor IS NULL THEN 0 ELSE CAST(li.valor AS SIGNED)  END as DESCUENTO_LIQUIDACION,"
					      +"CASE "
					        +"WHEN li.valor IS NULL THEN 0 ELSE CAST(li.valor AS SIGNED) "
					        +"END AS DESCUENTO_LIQUIDACION,"
					        +"IF(HD.llave_moneda = 2," 
							+"CAST(( "
							+"(select valor from sw_rhvalorMoneda where idMoneda = 2 and DATE_FORMAT(fecha,'%Y%m') = "+periodo+") * HD.monto) AS SIGNED ),"
					        +"CAST(HD.monto AS SIGNED)) - CASE "
					        +"WHEN li.valor IS NULL THEN 0 "
					        +"ELSE CAST(li.valor AS SIGNED) "
					      +"END AS DIFERENCIA,"
					      +"'DESCUENTO HD' AS CONCEPTO,"
					      +"(select descripcion from sw_p_haberesDescuentos where codigo = HD.codigo_hd) as NOMBRE_HD "
						+"from sw_haberesDescuentos HD "
						+"left join sw_liquidacionDetalle li on li.idContrato = HD.idContrato "
						+"and  CAST(((select valor from sw_rhvalorMoneda where idMoneda = 2 and DATE_FORMAT(fecha,'%Y%m') = "+periodo+")* HD.monto) AS SIGNED )"
						+"=  CAST(li.valor AS SIGNED ) and  li.periodo = "+periodo+" "
					    +"left JOIN sw_liquidacion sw_li on sw_li.id_contrato = HD.idContrato and sw_li.periodo = "+periodo+" "
					    +"left join trabajadores tr on tr.codigo = HD.codigo_trabajador "
					    +"left JOIN contratos co on co.id = HD.idContrato "
						+"where HD.periodo = "+periodo+" and HD.tipo = 'd' and " 
					    +"co.idSociedad = "+idSociedad+" ";
						if(rolPrivado == 1){
							sql += " and  tr.rolPrivado in (1,0) ";
						}else{
							sql += " and  tr.rolPrivado in (0) ";
						}
						sql+="group by   sw_li.fecha_pago,HD.id,MONTO,DESCUENTO_LIQUIDACION,DIFERENCIA;";
				
				
				System.out.println(sql2);
				ps2 = db.conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery(sql2);
				
				while (rs2.next()) {

					
					Diferencialiquidacion tr2 = new Diferencialiquidacion();
					tr2.setCod(rs2.getString("COD_TRABAJADOR"));
					tr2.setId_contrato(rs2.getString("ID_CONTRATO"));
					tr2.setPeriodo(rs2.getString("PERIODO"));
					tr2.setEmpresa(rs2.getString("EMPRESA"));
					tr2.setNombre(rs2.getString("NOMBRE"));
					tr2.setRut(rs2.getString("RUT"));
					tr2.setHurto(rs2.getString("HUERTO"));
					tr2.setTipo_contrato(rs2.getString("TIPO_CONTRATO"));
					tr2.setFecha_contrato(rs2.getString("FECHA_CONTRATO"));
					tr2.setAnticipo(rs2.getString("MONTO"));
					tr2.setDescuento_liquidacion(rs2.getString("DESCUENTO_LIQUIDACION"));
					tr2.setDiferencia(rs2.getString("DIFERENCIA"));
					tr2.setConcepto(rs2.getString("CONCEPTO"));
					tr2.setNombreHD(rs2.getString("NOMBRE_HD"));
					
					Lista.add(tr2);

				}
				

			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return Lista;
		}
		
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String generarExcelDescuentosLiq(int idSociedad, ArrayList<Diferencialiquidacion> BuscarTrabajadorDiferencia)
			throws Exception {

		String RutaArchivo = "";

		String ruta = utils.FiniquitoTxt();

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			CCAFLosAndes ru = AvisoInspeccionTrabajoDB.getRutEmpresa(idSociedad);
			String rut_empresa = ru.getRutEmpresa();

			String Nombrearchivo = rut_empresa + ".xlsx";
			File archivo = new File(rut_empresa + ".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
//			Workbook workbook = new XSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();
			CellStyle stylenew;
			stylenew=workbook.createCellStyle();

	        DataFormat format=workbook.createDataFormat();
	        stylenew.setDataFormat(format.getFormat("#,##0"));
	    
			// La hoja donde pondremos los datos
	        XSSFSheet pagina = workbook.createSheet("BASE DATO");
//			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			int numeroFor = 0;
			String cod;
			String id_contrato;
			String periodo;
			String empresa;
			String nombre;
			String rut;
			String hurto;
			String tipo_contrato;
			String fecha_contrato;
			String anticipo;
			String descuento_liquidacion;
			String diferencia;
			String concepto;
			String nombreHD;

			for (Diferencialiquidacion emplista : BuscarTrabajadorDiferencia) {

				cod = emplista.getCod();
				id_contrato = emplista.getId_contrato();
				periodo = emplista.getPeriodo();
				empresa = emplista.getEmpresa();
				nombre = emplista.getNombre();
				rut = emplista.getRut();
				hurto = emplista.getHurto();
				tipo_contrato = emplista.getTipo_contrato();
				fecha_contrato = emplista.getFecha_contrato();
				anticipo = emplista.getAnticipo();
				descuento_liquidacion = emplista.getDescuento_liquidacion();
				diferencia = emplista.getDiferencia();
				concepto = emplista.getConcepto();
				nombreHD = emplista.getNombreHD();

				String[] titulo7 = { "" + cod + "", "" + id_contrato + "", "" + periodo + "", "" + empresa + "",
						"" + nombre + "", "" + rut + "", "" + hurto + "",
						"" + tipo_contrato + "", "" + fecha_contrato + "", "" + anticipo + "", "" + descuento_liquidacion + "",
						"" + diferencia + "", "" + concepto + "", "" + nombreHD};
					
					if(numeroFor == 0){
						Cell celda = fila.createCell(0);
						celda.setCellValue(titulo7[0].replaceAll("\\_"," "));
					}else{
						int cod_trabj = Integer.parseInt(titulo7[0]);	
						Cell celda = fila.createCell(0);
						celda.setCellValue(cod_trabj);
					}
					
					
					
					if(numeroFor == 0){
						Cell celda1 = fila.createCell(1);
						celda1.setCellValue(titulo7[1].replaceAll("\\_"," "));
					}else{
						int idcontrato_trabj = Integer.parseInt(titulo7[1]);	
						Cell celda1 = fila.createCell(1);
						celda1.setCellValue(idcontrato_trabj);
					}

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
					
					
					if(numeroFor == 0){
						Cell celda7 = fila.createCell(7);
						celda7.setCellValue(titulo7[7].replaceAll("\\_"," "));
					}else{
						Cell celda7 = fila.createCell(7);
						celda7.setCellValue(titulo7[7]);
					}
					
					if(numeroFor == 0){
						Cell celda8 = fila.createCell(8);
						celda8.setCellValue(titulo7[8].replaceAll("\\_"," "));
					}else{
						Cell celda8 = fila.createCell(8);
						celda8.setCellValue(titulo7[8]);
					}
					
					if(numeroFor == 0){
						Cell celda9 = fila.createCell(9);
						celda9.setCellValue(titulo7[9]);
					}else{
						BigDecimal total0 = new BigDecimal(titulo7[9]).setScale(4, BigDecimal.ROUND_HALF_UP);
						double totalfinal0 = total0.doubleValue();
						Cell celda9 = fila.createCell(9);
						celda9.setCellValue(totalfinal0);
						celda9.setCellStyle(stylenew);
					}
					
					
					if(numeroFor == 0){
						Cell celda10 = fila.createCell(10);
						celda10.setCellValue(titulo7[10].replaceAll("\\_"," "));
					}else{
						 BigDecimal total1 = new BigDecimal(titulo7[10]).setScale(4, BigDecimal.ROUND_HALF_UP);
						 double totalfinal1 = total1.doubleValue();
						Cell celda10 = fila.createCell(10);
						celda10.setCellValue(totalfinal1);
						celda10.setCellStyle(stylenew);
					}

					if(numeroFor == 0){
						Cell celda11 = fila.createCell(11);
						celda11.setCellValue(titulo7[11]);
					}else{
						BigDecimal total2 = new BigDecimal(titulo7[11]).setScale(4, BigDecimal.ROUND_HALF_UP);
						double totalfinal2 = total2.doubleValue();
						Cell celda11 = fila.createCell(11);
						celda11.setCellValue(totalfinal2);
						celda11.setCellStyle(stylenew);
					}
					
					Cell celda12 = fila.createCell(12);
					celda12.setCellValue(titulo7[12]);
					
					Cell celda1223 = fila.createCell(13);
					celda1223.setCellValue(titulo7[13]);


				numeroFor = numeroFor + 1;
				fila = pagina.createRow(numeroFor);
			}
			
			int numeroFilas = BuscarTrabajadorDiferencia.size();
			
			/* Definir una referencia de área para la tabla dinámica
             * deben estar todos los registros que se generan en la informacion de la tabla */
            AreaReference a=new AreaReference("A1:N"+numeroFilas+"");
            /*Defina la referencia de celda inicial para la tabla dinámica */
            CellReference b=new CellReference("P4");
            /* crear el Pivot Table */
            XSSFPivotTable pivotTable = pagina.createPivotTable(a,b);
            /* Primero crear filtro de informe: queremos filtrar la tabla dinámica por nombre de estudiante*/
            pivotTable.addReportFilter(3);
            
             /* Second - Row Labels - Once a student is filtered all subjects to be displayed in pivot table */
            pivotTable.addRowLabel(0);
            pivotTable.addRowLabel(1);
            pivotTable.addRowLabel(2);
            pivotTable.addRowLabel(4);
            pivotTable.addRowLabel(5);
            pivotTable.addRowLabel(6);
            pivotTable.addRowLabel(7);
            pivotTable.addRowLabel(8);
            pivotTable.addRowLabel(9);
            pivotTable.addRowLabel(11);
            pivotTable.addRowLabel(12);
            pivotTable.addRowLabel(13);
            
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
			pagina.autoSizeColumn(11);
			pagina.autoSizeColumn(12);
			pagina.autoSizeColumn(13);
		

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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String generarExcelBDeclaracion(int idSociedad, ArrayList<DeclaracionJurada> buscarTrabajadorCCAF)
			throws Exception {

		String RutaArchivo = "";

		String ruta = utils.AvisoInspeccionTrabajo();

		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			CCAFLosAndes ru = AvisoInspeccionTrabajoDB.getRutEmpresa(idSociedad);
			String rut_empresa = ru.getRutEmpresa();

			String Nombrearchivo = rut_empresa + "DECLARACION_JURADA.csv";
			File archivo = new File(rut_empresa + "DECLARACION_JURADA.csv");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();

			CellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_RIGHT);

			// La hoja donde pondremos los datos
			Sheet pagina = workbook.createSheet("Hoja1");
			pagina.getPrintSetup().setLandscape(true);
			pagina.setFitToPage(true);
			PrintSetup ps = pagina.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 0);

			Row fila = pagina.createRow(0);

			int numeroFor = 0;
			String rut;
			String imponible = "";
			String inpuesto = "";
			String columa4_;
			String columa5_ = "";
			String columa6_ = "";
			String columa7_ = "";
			String enero_ = "";
			String febrero_ = "";
			String marzo_ = "";
			String abril_ = "";
			String mayo_ = "";
			String junio_ = "";
			String julio_ = "";
			String agosto_ = "";
			String septiembre_ = "";
			String octubre_ = "";
			String noviembre_ = "";
			String diciembre_ = "";

			for (DeclaracionJurada emplista : buscarTrabajadorCCAF) {

				rut = emplista.getRut();
				imponible = ""+emplista.getTotalimponible();
				inpuesto = ""+emplista.getInpuestounico();
				columa4_ = ""+emplista.getColumna4();
				columa5_ = ""+emplista.getColumna5();
				columa6_ = ""+emplista.getColumna6();
				columa7_ = ""+emplista.getColumna7();
				enero_ =  emplista.getEnero();
				febrero_ =  emplista.getFebrero();
				marzo_ =  emplista.getMarzo();
				abril_ =  emplista.getAbril();
				mayo_ =  emplista.getMayo();
				junio_ =  emplista.getJunio();
				julio_ =  emplista.getJulio();
				agosto_ =  emplista.getAgosto();
				septiembre_ =  emplista.getSeptiembre();
				octubre_ =  emplista.getOctubre();
				noviembre_ =  emplista.getNoviembre();
				diciembre_ = emplista.getDiciembre();
				

				String[] titulo7 = { "" + rut + "", "" + imponible + "", "" + inpuesto + "", "" + columa4_ + "",
						"" + columa5_ + "", "" + columa6_ + "", "" + columa7_ + "",
						"" + enero_ + "", "" + febrero_ + "", "" + marzo_ + "", "" + abril_ + "",
						"" + mayo_ + "", "" + junio_ + "", "" + julio_ + "", "" + agosto_ + "", "" + septiembre_,
						"" + octubre_ + "", "" + noviembre_ + "", "" + diciembre_
				};

				Cell celda = fila.createCell(0);
				celda.setCellValue(titulo7[0]);

				Cell celda1 = fila.createCell(1);
				celda1.setCellValue(titulo7[1]);

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

				Cell celda11 = fila.createCell(11);
				celda11.setCellValue(titulo7[11]);

				Cell celda12 = fila.createCell(12);
				celda12.setCellValue(titulo7[12]);

				Cell celda13 = fila.createCell(13);
				celda13.setCellValue(titulo7[13]);
				
				Cell celda14 = fila.createCell(14);
				celda14.setCellValue(titulo7[14]);
				
				Cell celda15 = fila.createCell(15);
				celda15.setCellValue(titulo7[15]);
				
				Cell celda16 = fila.createCell(16);
				celda16.setCellValue(titulo7[16]);
				
				Cell celda17 = fila.createCell(17);
				celda17.setCellValue(titulo7[17]);
				
				Cell celda18 = fila.createCell(18);
				celda18.setCellValue(titulo7[18]);
				
				Cell celda19 = fila.createCell(19);
				celda19.setCellValue(numeroFor);
				

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
			pagina.autoSizeColumn(11);
			pagina.autoSizeColumn(12);
			pagina.autoSizeColumn(13);
			pagina.autoSizeColumn(14);
			pagina.autoSizeColumn(15);
			pagina.autoSizeColumn(16);
			pagina.autoSizeColumn(17);
			pagina.autoSizeColumn(18);
			pagina.autoSizeColumn(19);

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




