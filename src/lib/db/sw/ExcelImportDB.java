package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import lib.db.ConnectionDB;
import lib.utils.GeneralUtility;
import lib.utils.TimeUtility;

public class ExcelImportDB {

	
	public static String insertDatos(String idImportador, LinkedHashMap<Integer,ArrayList<String>> datos, String process_error) throws Exception {
		
    	PreparedStatement ps = null;
		String sql = "";
		String sqlInsert = "";
		String tabla = "";
		ConnectionDB db = new ConnectionDB(); 
        
		switch (idImportador) {
		case "1":
			tabla += " sw_haberesDescuentos "; 
			break;
		case "2":
			tabla += " sw_asignacionAnticipos ";
			break;
		case "3":
			tabla += " sw_horasAsistencia ";
			break;
		case "4":
			tabla += " sw_horasAsistencia ";
			break;
		default:
			tabla += " sw_haberesDescuentos ";
			break;
		}
		
		sql = " SELECT * FROM " + tabla + " LIMIT 1 ";
		
		ps = db.conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		
		int count = metaData.getColumnCount();
		
		
		sqlInsert = " INSERT INTO " +tabla+ " ( ";
		//Obtener los Titulos
		for (int i = 1; i <= count; i++) {
			
			if(i == count){
				sqlInsert += metaData.getColumnName(i) + " ) ";
			}else{
				sqlInsert += metaData.getColumnName(i) + " , ";
			}
					
		}	
		
		for( Entry<Integer, ArrayList<String>> entry : datos.entrySet()){
			
			
			//Swith case 
	        switch (idImportador) {
			case "1":
				ps = datosToHaberesDescuentos(sqlInsert, count, ps, db, entry.getValue());
				break;
			case "2":
				tabla += " sw_asignacionanticipos ";
				break;
			case "3":
				tabla += " sw_horasasistencia ";
				break;
			case "4":
				tabla += " sw_horasasistencia ";
				break;
			default:
				tabla += " sw_haberesDescuentos ";
				break;
			}
			
		     try {
		     ps.execute();
		     }catch(Exception e){
		    	 process_error = " \t "+" error al Insertar el trabajador con codigo:" + entry.getValue().get(1) + " - " + e.getMessage(); 
		    	 
		     }
			
		}
		
		ps.close();
        db.close();
		
        return process_error;
        
	}

	private static PreparedStatement datosToHaberesDescuentos(String sqlInsert, Integer count, PreparedStatement ps, ConnectionDB db, List<String> datos) throws Exception {
		
		int i = 1;
		
		sqlInsert += " VALUES ( ";
		
		for (int k = 1; k <= count; k++) {
			switch (k) {
			case 3:
				sqlInsert += " ( SELECT tipo FROM sw_p_haberesDescuentos WHERE codigo = ? ) , "; 
				break;
			case 11:
				sqlInsert += " ( SELECT id FROM contratos WHERE codigo_trabajador = ? AND fechaInicio_actividad <= DATE ( ? ) AND ( FechaTerminoContrato IS NULL OR FechaTerminoContrato >= DATE ( ? ) ) ) , "; 
				break;
			default:
				if(k == count){
					sqlInsert += " ? ) ";
				}else{
					sqlInsert += " ? , ";
				}
				break;
			}
			
		}	
		
		ps = db.conn.prepareStatement(sqlInsert);
		
		String periodo = TimeUtility.convertStringToYYYYMM(datos.get(5).replaceAll("/", "-"));
		String fecha = GeneralUtility.convertStringToYYYYMMDD(datos.get(5).replaceAll("/", "-"));
		String valor = datos.get(4);
		String codigoTrabajador = datos.get(1);
		String codigoHD = datos.get(3);
	
			  
		ps.setString(i++, null);
		ps.setString(i++, periodo);
		ps.setString(i++, codigoHD);
		ps.setString(i++, codigoHD);
		ps.setString(i++, valor);
		ps.setString(i++, codigoTrabajador);
		ps.setString(i++, "180");
		ps.setString(i++, "0");
		ps.setString(i++, periodo);
		ps.setString(i++, "0");
		ps.setString(i++, codigoTrabajador);
		ps.setString(i++, fecha);
		ps.setString(i++, fecha);
		ps.setString(i++, "4");
		ps.setString(i++, "2");
		ps.setString(i++, null);
			
		
		return ps;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
