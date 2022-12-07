package SWDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.AFC;
import lib.classSW.DatosAvisoInspeccionTrabajo;
import lib.classSW.NominaAnticipos;
import lib.db.ConnectionDB;
import wordCreator.utils;



public class sw_nominaAFCDB {
	
	 public static String truncateCadena(String str, int maxWidth) {
	        if (null == str) {
	            return null;
	        }
	    
	        if (str.length() <= maxWidth) {
	            return str;
	        }

	        return str.substring(0, maxWidth);
	    }
	
	public static String getTextAFC (ArrayList<AFC> row, int idSociedad, int totalregistros, int tipocese) throws Exception{
	    
		 
		String ruta = utils.NominaAFC();
		Date fechaActual = new Date();
		
        //Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
		String formatoFecha = ""+formatFecha.format(fechaActual).toString()+"";
		String[] fechaFinal = formatoFecha.split("-");
		String fechaFinal2 = fechaFinal[2]+fechaFinal[1]+fechaFinal[0];
		
		String nombre_nominaT = "";
		
		String horaf = formatoHora.replaceAll("[:]", "");;
		if(tipocese == 1){
			nombre_nominaT = "ARCHIVO_AFC_INICIO";
		}else{
			nombre_nominaT = "ARCHIVO_AFC_CESE";
		}
		
		String nombreArchivo = nombre_nominaT+horaf+".txt";
		String outputFile = ruta+nombreArchivo;

		boolean alreadyExists = new File(outputFile).exists();

		if(alreadyExists){
		    File ArchivoEmpleados = new File(outputFile);
		    ArchivoEmpleados.delete();
		}        


	try {
	
		
	
		
//		obtener rut empresa DB
		NominaAnticipos ru = sw_NominaAnticiposDB.getRutEmpresa(idSociedad);
		
				String rutEmpresa1 = ru.getRut();
				String rutEmpresa12 = rutEmpresa1.toUpperCase();
				String rut1 = rutEmpresa12.replaceAll("[.]", "");
				String rutINT = rut1.replaceAll("[-]", "");
				


		String nuevalinea = "\r\n";

		
		int length = 6;
		
		String fechaFinal3 = truncateCadena(fechaFinal2,8);
		
		
		String totalregistros2 = String.format("%0" + length + "d", totalregistros);
	    
		/////-------------------PRIMERA LINEA DEL TXT--------------------------\\\\\
		String nombre = String.format("%-14s", fechaFinal3+totalregistros2);
		nombre += nuevalinea;
		nombre = String.format("%-14s", fechaFinal3+totalregistros2);
		nombre += nuevalinea;
		///------------------- VARIABLES--------------------------------------\\\\
		String rut = "";
		String dv = "";
		String apPaterno = "";
		String apMaterno = "";
		String NombreTrab = "";
		String fechaNacimiento = "";
		String NumeroContratos = "";
		String TipoAviso = "";
		String fechaInicio = "";
		String cese = "";
		String tipoContrato = "";
		
		
		/////-------------------MAS LINEAS DEL TXT--------------------------\\\\\ 
		
		 for(AFC emp : row)
		 {

			 rut = ""+emp.getRut()+"";
			 dv = emp.getDv().toUpperCase();
			 apPaterno = emp.getApPaterno().toUpperCase();
			 String apPaterno2 = truncateCadena(apPaterno,20);
			 
			 apMaterno = emp.getApMaterno().toUpperCase();
			 String apMaterno2 = truncateCadena(apMaterno,20);
			 
			 NombreTrab = emp.getNombre().toUpperCase();
			 String NombreTrab2 = truncateCadena(NombreTrab,20);
			 
			 fechaNacimiento = ""+emp.getFechaNacimiento()+"";
			 NumeroContratos =  emp.getNcontrato();
			 tipoContrato =  ""+emp.getTipocontrato()+"";
			 TipoAviso = ""+emp.getInicioAviso()+"";
			 fechaInicio = ""+emp.getFechaInicio()+"";
			 cese = ""+emp.getCese()+"";
			
			 if( emp.getFechaNacimiento() == 0){
				 fechaNacimiento = "00000000";
			 }
			 if(emp.getCese() == 0){
				 cese = "00000000";
			 }
			 
			 if(emp.getInicioAviso() == 2){
				 fechaInicio = "00000000";
			 }
			 
			 int sizeRut = rut.length();
			 
			 if(sizeRut == 7  ){
			  nombre += String.format("%-9s", "0"+rut+dv);	 
			 }else{
				 nombre += String.format("%-9s", rut+dv);
			 }
			 
			 if(emp.getTipocontrato() == 2){
				 fechaInicio = ""+emp.getFechaInicio()+"";
				 
				 
			 }
			 
			 String rutEmpresa = rutINT.toUpperCase();
			 String rutEmpresa2 = String.format("%-9s", "0"+rutEmpresa);	
			 nombre += String.format("%-20s", apPaterno2);
			 nombre += String.format("%-20s", apMaterno2);
			 nombre += String.format("%-20s", NombreTrab2);
			 nombre += String.format("%-38s", fechaNacimiento+rutEmpresa2+NumeroContratos+tipoContrato+TipoAviso+fechaInicio+cese);
			 
			 
			 nombre += "\r\n";
	   	 
	 
	    }
	  //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
	    File f= new File(outputFile);
	    FileUtils.writeStringToFile(f,nombre, "ISO-8859-1");
	  	//FileWriter escribir=new FileWriter(outputFile,true);

	  	//Escribimos en el archivo con el metodo write 
	  	//escribir.write(nombre);

	  	//Cerramos la conexion
	  	//escribir.close();
	
	

	
	}
	catch (FileNotFoundException ex) {
	  
	} 
	catch (IOException e) {
	    e.printStackTrace();
	   
	    return "";
	}
	return outputFile;
	

	}
	
	public static ArrayList<AFC> buscartrabajadoresAFC(int idSociedad, String fechainicio,String fechatermino, String huerto, int cese, int rolPrivado) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<AFC> Lista = new ArrayList<AFC>();

		try {

			
			if(cese == 1){
			sql = "SELECT "
					+" case when TR.rut = '' then SUBSTRING_INDEX(REPLACE(TR.rutTemporal, '.', ''), '-', 1) else SUBSTRING_INDEX(REPLACE(TR.rut, '.', ''), '-', 1) end as rut,"
					+"case when TR.rut = '' then SUBSTRING_INDEX(TR.rutTemporal, '-', - 1) else SUBSTRING_INDEX(TR.rut, '-', - 1) end as dv,"
					+"case WHEN TR.apellidoPaterno = '' THEN ',' "
					+ "WHEN TR.apellidoPaterno = '.' THEN ','  "
					+ "ELSE TR.apellidoPaterno end as apellidoPaterno,"
					+"case WHEN TR.apellidoMaterno = '' THEN ',' "
					+ "WHEN TR.apellidoMaterno = '.' THEN ','  "
					+ "ELSE TR.apellidoMaterno end as apellidoMaterno,"
					+ "TR.nombre,"
					+"REPLACE(TR.fNacimiento, '-', '') as fechaNacimiento,"
					+"'01' as nContrato,"
					+"(select AFC FROM parametros WHERE codigo = 'TIPO_CONTRATO' AND llave = CO.tipoContrato) AS tipocontrato,"
					+"1 as inicioAviso,"
					+"REPLACE(CO.fechaInicio_actividad, '-', '') as fechaInicio,"
					+"'00000000' AS cese "
					+"FROM contratos CO "
					+"INNER JOIN trabajadores TR on TR.codigo = CO.codigo_trabajador "
					+"WHERE CO.idSociedad = "+idSociedad+" "
					+ "AND NOT TR.tipoTrabajador = 4 " // tipo contratista 4
					+ " AND (fechaInicio_actividad BETWEEN '"+fechainicio+"' AND '"+fechatermino+"') ";
			
					if(rolPrivado == 1){
						sql += " and  TR.rolPrivado in (1,0) ";
					}else{
						sql += " and  TR.rolPrivado in (0) ";
					}
			
			 		if("null".equals(huerto)){}else{sql += " and TR.idHuerto = '"+huerto+"'";}
			}
			else{
			sql = "SELECT "
					+" case when TR.rut = '' then SUBSTRING_INDEX(REPLACE(TR.rutTemporal, '.', ''), '-', 1) else SUBSTRING_INDEX(REPLACE(TR.rut, '.', ''), '-', 1) end as rut,"
					+"case when TR.rut = '' then SUBSTRING_INDEX(TR.rutTemporal, '-', - 1) else SUBSTRING_INDEX(TR.rut, '-', - 1) end as dv,"
					+"case WHEN TR.apellidoPaterno = '' THEN ',' "
					+ "WHEN TR.apellidoPaterno = '.' THEN ','  "
					+ "ELSE TR.apellidoPaterno end as apellidoPaterno,"
					+"case WHEN TR.apellidoMaterno = '' THEN ',' "
					+ "WHEN TR.apellidoMaterno = '.' THEN ','  "
					+ "ELSE TR.apellidoMaterno end as apellidoMaterno,"
					+ "TR.nombre,"
					+"REPLACE(TR.fNacimiento, '-', '') as fechaNacimiento,"
					+"'01' as nContrato,"
					+"(select AFC FROM parametros WHERE codigo = 'TIPO_CONTRATO' AND llave = CO.tipoContrato) AS tipocontrato,"
					+"2 as inicioAviso,"
					+"'00000000' AS fechaInicio,"
					+"REPLACE(CO.FechaTerminoContrato, '-', '') AS cese "
					+"FROM contratos CO "
					+"INNER JOIN trabajadores TR on TR.codigo = CO.codigo_trabajador "
					+"WHERE CO.idSociedad = "+idSociedad+" AND CO.paraFiniquitar = 1 "
					+ "AND NOT TR.tipoTrabajador = 4 "
					+"AND (CO.FechaTerminoContrato BETWEEN '"+fechainicio+"' AND '"+fechatermino+"')";
			
					if(rolPrivado == 1){
						sql += " and  TR.rolPrivado in (1,0) ";
					}else{
						sql += " and  TR.rolPrivado in (0) ";
					}
					if("null".equals(huerto)){}else{sql += " and TR.idHuerto = '"+huerto+"'";}
			}
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				AFC tr = new AFC();
				
				tr.setRut(rs.getInt("rut"));
				tr.setDv(rs.getString("dv"));
				tr.setApPaterno(rs.getString("apellidoPaterno"));
				tr.setApMaterno(rs.getString("apellidoMaterno"));
				tr.setNombre(rs.getString("nombre"));
				tr.setFechaNacimiento(rs.getInt("fechaNacimiento"));
				tr.setNcontrato(rs.getString("nContrato"));
				tr.setTipocontrato(rs.getInt("tipocontrato"));
				tr.setInicioAviso(rs.getInt("inicioAviso"));
				tr.setFechaInicio(rs.getInt("fechaInicio"));
				tr.setCese(rs.getInt("cese"));
				
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

}
