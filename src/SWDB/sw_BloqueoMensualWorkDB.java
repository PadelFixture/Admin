package SWDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lib.db.ConexionBD;
import lib.db.ConnectionDB;
import lib.db.ConexionBD;

public class sw_BloqueoMensualWorkDB {
	public static Connection db;
public static String insertarBloqueoMensualWork (int empr, String huerto,int periodo, int workagro, int UD) throws Exception{
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql="";
		String sql2="";
		ResultSet rs2 = null;
		String respuesta = "";
		ConnectionDB db = new ConnectionDB();
		

		try{
			
			
			if(UD == 2){
				
				sql = "DELETE FROM sw_cierre_periodos WHERE empresa = "+empr+" "
						+ "AND huerto = '"+huerto+"' AND periodo = "+periodo+" "
						+ "AND work_agro = "+workagro+"";
				
				ps2 = db.conn.prepareStatement(sql);
				
				System.out.println(sql);
				ps2.execute();
				
				respuesta = "Periodo Abierto";
	
			}
			else if (UD == 1){
				 sql2 = "INSERT INTO sw_cierre_periodos (empresa, huerto, periodo, work_agro) VALUES ("+empr+",'"+huerto+"',"+periodo+","+workagro+")";
					ps2 = db.conn.prepareStatement(sql2);
					
					System.out.println(sql2);
					ps2.execute();
					
					respuesta = "Periodo Cerrado";
			}
			
	
			
			
			return respuesta;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
		
		
			db.close();
		}
		return "no Enviado";
	}

public static String SelectBloqueoMensualWorkData (int empr, String huerto,int periodo, int workagro) throws Exception{
	
	
	PreparedStatement ps = null;
	String sql="";
	ResultSet rs2 = null;
	
	ConnectionDB db = new ConnectionDB();
	

	try{
		 
			sql = "select * from sw_cierre_periodos WHERE empresa = "+empr+" "
					+ "AND huerto = '"+huerto+"' AND periodo = "+periodo+" "
							+ "AND work_agro = "+workagro+"";
			
			
			System.out.println(sql);
			
			
			ps = db.conn.prepareStatement(sql);
			 rs2 = ps.executeQuery(sql);
			
			 String respuesta = "";
			if (!rs2.next() ) {
			    System.out.println("no data");
			    respuesta = "0";
			}else{
				System.out.println("si data");
				respuesta = "1";
			}
			
			
			
			
			
			
			
		
		
		return respuesta;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
	
	
		db.close();
	}
	return "no Enviado";
}

}

