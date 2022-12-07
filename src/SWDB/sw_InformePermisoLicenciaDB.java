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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.ImpuestoUnicoExcel;
import lib.classSW.InformePermisoLi;
import lib.db.ConnectionDB;
import wordCreator.utils;

public class sw_InformePermisoLicenciaDB {
	
	
	public static ArrayList<InformePermisoLi> buscartrabajadoresInformeP_L_F(String empresa,String periodo,String huerto,int rolPrivado) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<InformePermisoLi> Lista = new ArrayList<InformePermisoLi>();
		
		String anio = periodo.substring(0,4);
		String mes = periodo.substring(4,6);
		String dia = "01";
	    String periodoFinal = anio+"-"+mes+"-"+dia;	

		try {

			
		
			sql = "select *,(DATEDIFF(fecha_fin_previred, fecha_inicio_previred) +1) as TOTAL_DIAS "
					+ "from ( "
					+"SELECT "
					    +"CASE "
					    +"WHEN pe.accion = 1 THEN 'PERMISO CON GOCE DE SUELDO' "
					    +"WHEN pe.accion = 2 THEN 'LICENCIA' "
					    +"WHEN pe.accion = 3 THEN 'FALTA' "
					    +"WHEN pe.accion = 4 THEN 'PERMISO SIN GOCE DE SUELDO' "
					    +"ELSE '' "
					    +"END AS ACCION,"
					
						+"CASE "
					    +"WHEN upper((SELECT descripcion FROM parametros where codigo = 'TIPO_LICENCIA' and id =  pe.tipo_licencia)) IS NULL THEN ' ' "
					    +"ELSE upper((SELECT descripcion FROM parametros where codigo = 'TIPO_LICENCIA' and id =  pe.tipo_licencia)) "
					    +"END AS  TIPO_LICENCIA,"
					
						+"CASE "
					    +"WHEN upper((SELECT descripcion FROM parametros where codigo = 'Sub_Tipo_Licencia' and id =  pe.subtipo_licencia)) IS NULL THEN ' ' "
					    +"ELSE upper((SELECT descripcion FROM parametros where codigo = 'Sub_Tipo_Licencia' and id =  pe.subtipo_licencia)) "
					    +"END AS  SUBTIPO_LICENCIA,"
					
					    +"upper((select descripcion from campo where campo = co.idHuertoContrato)) as HUERTO,"
					    +"pe.codigo_trabajador as CODIGO_TRABAJADOR,"
						+"case when t.rut = '' then t.rutTemporal else t.rut end AS RUT,"
						+"upper(CONCAT( t.apellidoPaterno,' ', t.apellidoMaterno, ' ', t.nombre )) as NOMBRE,"
					    +"pe.fecha_desde AS FECHA_DESDE,"
					    +"pe.fecha_hasta AS FECHA_HASTA,"
					    
					    +"CASE "
					    +"WHEN pe.fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"') THEN pe.fecha_desde "
					    +"ELSE FIRST_DAY('"+periodoFinal+"') "
					    +"END AS fecha_inicio_previred,"
					    
					    +"CASE "
					    +"WHEN (pe.fecha_hasta BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"')) THEN pe.fecha_hasta "
					    +"ELSE LAST_DAY('"+periodoFinal+"') "
					    +"END AS fecha_fin_previred,"
					    +"(select idCECOContrato from contratos where id =  pe.idContrato) as CENTRO_COSTO,"
			            +"if( (select EstadoContrato from contratos where id =  pe.idContrato) = 1,'ACTIVO','INACTIVO')  as ESTADO_CONTRATO "
					 +"FROM permiso_licencia pe "
					 +"INNER JOIN trabajadores t ON pe.codigo_trabajador = t.codigo "
					 +"inner join contratos co on co.id = pe.idContrato "
					 +"WHERE "
					        +"t.rut != '' ";
							if(rolPrivado == 1){
								sql += " and  t.rolPrivado in (1,0) ";
							}else{
								sql += " and  t.rolPrivado in (0) ";
							}
					        sql +=" AND pe.id_empresa = (SELECT idSociedad FROM sociedad  where sociedad = '"+empresa+"') "
					        +"AND t.tipoTrabajador !=4 ";
							if("null".equals(huerto)){}else{sql += " and co.idHuertoContrato = '"+huerto+"'";}
							sql += " AND (LAST_DAY('"+periodoFinal+"') BETWEEN pe.fecha_desde AND pe.fecha_hasta "
					        +"OR '"+periodoFinal+"' BETWEEN pe.fecha_desde AND pe.fecha_hasta "
					        +"OR pe.fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"')) order by NOMBRE) as l";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();

			InformePermisoLi tr1 = new InformePermisoLi();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			  System.out.println(name);
			  	
			    
				if(i == 1){tr1.setAccion(name);}
				else if(i == 2){tr1.setTipolicencia(name);}
				else if(i == 3){tr1.setSubtipolicencia(name);}
				else if(i == 4){tr1.setHuerto(name);}
				else if(i == 5){tr1.setCodigo(name);}
				else if(i == 6){tr1.setRut(name);}
				else if(i == 7){tr1.setNombre(name);}
				else if(i == 8){tr1.setFechadesde(name);}
				else if(i == 9){tr1.setFechahasta(name);}
				else if(i == 12){tr1.setCeco(name);}
				else if(i == 13){tr1.setEstadocontrato(name);}
				else if(i == 14){tr1.setTotaldias(name);}
				
			}
			
			Lista.add(tr1);

			while (rs.next()) {

				InformePermisoLi tr = new InformePermisoLi();
				
				tr.setAccion(rs.getString("ACCION"));
				tr.setTipolicencia(rs.getString("TIPO_LICENCIA"));
				tr.setSubtipolicencia(rs.getString("SUBTIPO_LICENCIA"));
				tr.setHuerto(rs.getString("HUERTO"));
				tr.setCodigo(rs.getString("CODIGO_TRABAJADOR"));
				tr.setRut(rs.getString("RUT"));
				tr.setNombre(rs.getString("NOMBRE"));
				tr.setFechadesde(rs.getString("FECHA_DESDE"));
				tr.setFechahasta(rs.getString("FECHA_HASTA"));
				tr.setTotaldias(rs.getString("TOTAL_DIAS"));
				tr.setCeco(rs.getString("CENTRO_COSTO"));
				tr.setEstadocontrato(rs.getString("ESTADO_CONTRATO"));
			
				
				
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
	
	
	public static String generarExcelplf(ArrayList<InformePermisoLi> detalleExcel,String nombrearchivo) throws Exception {

		String RutaArchivo = "";
		
		String ruta = utils.FiniquitoTxt();


		try {

			////////////////////// EXCEL///////////////////////////

			// Creamos el archivo donde almacenaremos la hoja
			// de calculo, recuerde usar la extension correcta,
			// en este caso .xlsx
			
			
			String Nombrearchivo = nombrearchivo+".xlsx";
			File archivo = new File(nombrearchivo+".xlsx");

			// Creamos el libro de trabajo de Excel formato OOXML
			Workbook workbook = new XSSFWorkbook();
            

			 DataFormat format=workbook.createDataFormat();

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
			    String  accion_;
				String 	tipolicencia_ = "";
				String 	subtipolicencia_ = "";
				String  huerto_;
				String  codigo_;
				String  rut_;
				String  nombre_;
				String  fechadesde_;
				String  fechahasta_;
				String  totaldias_;
				String  ceco_;
				String  estadocontrato_;
			
				for (InformePermisoLi emplista : detalleExcel) {
					
					accion_= emplista.getAccion();
					tipolicencia_ = emplista.getTipolicencia();
					subtipolicencia_= emplista.getSubtipolicencia();
					huerto_= emplista.getHuerto();
					
					codigo_ = emplista.getCodigo();
					rut_= emplista.getRut();
					nombre_= emplista.getNombre();
					
					fechadesde_ = emplista.getFechadesde();
					fechahasta_= emplista.getFechahasta();
					totaldias_= emplista.getTotaldias();
					
					ceco_ = emplista.getCeco();
				    estadocontrato_ = emplista.getEstadocontrato();
					
					String[] titulo7 = { "" + accion_ + "", "" + tipolicencia_ + "", "" + subtipolicencia_ + "",
							"" + huerto_ + "","" + codigo_ + "" ,"" + rut_ + "" ,"" + nombre_ + "" ,"" + fechadesde_ + "" 
							,"" + fechahasta_ + "" ,"" + totaldias_ + "","" + ceco_ + "","" + estadocontrato_ + "" };
					
					if(numeroFor == 0){
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0].replaceAll("\\_"," "));
					celda.setCellStyle(style);
					
					
					}else{
					Cell celda = fila.createCell(0);
					celda.setCellValue(titulo7[0]);
					
				    }
					
					if(numeroFor == 0){
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1].replaceAll("\\_"," "));
					celda1.setCellStyle(style);
					}else{
					Cell celda1 = fila.createCell(1);
					celda1.setCellValue(titulo7[1]);
					}
				
					if(numeroFor == 0){
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2].replaceAll("\\_"," "));
					celda2.setCellStyle(style);
					}else{
					Cell celda2 = fila.createCell(2);
					celda2.setCellValue(titulo7[2]);
					}
					
					if(numeroFor == 0){
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3].replaceAll("\\_"," "));
					celda3.setCellStyle(style);
					}else{
					Cell celda3 = fila.createCell(3);
					celda3.setCellValue(titulo7[3]);
					
					}
					
					if(numeroFor == 0){
						Cell celda4 = fila.createCell(4);
						celda4.setCellValue(titulo7[4].replaceAll("\\_"," "));
						celda4.setCellStyle(style);
						}else{
						Cell celda4 = fila.createCell(4);
						celda4.setCellValue(titulo7[4]);
						
						}
					
					if(numeroFor == 0){
						Cell celda5 = fila.createCell(5);
						celda5.setCellValue(titulo7[5].replaceAll("\\_"," "));
						celda5.setCellStyle(style);
						}else{
						Cell celda5 = fila.createCell(5);
						celda5.setCellValue(titulo7[5]);
						
						}
					
					if(numeroFor == 0){
						Cell celda6 = fila.createCell(6);
						celda6.setCellValue(titulo7[6].replaceAll("\\_"," "));
						celda6.setCellStyle(style);
						}else{
						Cell celda6 = fila.createCell(6);
						celda6.setCellValue(titulo7[6]);
						
						}
					
					if(numeroFor == 0){
						Cell celda7 = fila.createCell(7);
						celda7.setCellValue(titulo7[7].replaceAll("\\_"," "));
						celda7.setCellStyle(style);
						}else{
						Cell celda7 = fila.createCell(7);
						celda7.setCellValue(titulo7[7]);
						
						}
					
					if(numeroFor == 0){
						Cell celda8 = fila.createCell(8);
						celda8.setCellValue(titulo7[8].replaceAll("\\_"," "));
						celda8.setCellStyle(style);
						}else{
						Cell celda8 = fila.createCell(8);
						celda8.setCellValue(titulo7[8]);
						
						}
					
					if(numeroFor == 0){
						Cell celda9 = fila.createCell(9);
						celda9.setCellValue(titulo7[9].replaceAll("\\_"," "));
						celda9.setCellStyle(style);
						}else{
						Cell celda9 = fila.createCell(9);
						celda9.setCellValue(Integer.parseInt(titulo7[9]));
						
						}
					
					if(numeroFor == 0){
						Cell celda10 = fila.createCell(10);
						celda10.setCellValue(titulo7[10].replaceAll("\\_"," "));
						celda10.setCellStyle(style);
						}else{
						Cell celda10 = fila.createCell(10);
						celda10.setCellValue(titulo7[10]);
						
						}
					
					if(numeroFor == 0){
						Cell celda11 = fila.createCell(11);
						celda11.setCellValue(titulo7[11].replaceAll("\\_"," "));
						celda11.setCellStyle(style);
						}else{
						Cell celda11 = fila.createCell(11);
						celda11.setCellValue(titulo7[11]);
						
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
