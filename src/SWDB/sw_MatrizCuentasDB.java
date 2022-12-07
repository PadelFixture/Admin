package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.IncidentesMutualidad;
import lib.db.ConexionBD;
import lib.db.ConnectionDB;

public class sw_MatrizCuentasDB {
	
	public static ArrayList<IncidentesMutualidad> getdatosceco(String ceco, int anio) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<IncidentesMutualidad> data = new ArrayList<IncidentesMutualidad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			
			sql = "select * from sw_centralizacionCuentaPeriodo where ceco = '"+ceco+"' and SUBSTRING(CAST(periodo as CHAR(50)),1,4) = "+anio+"";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				IncidentesMutualidad e = new IncidentesMutualidad();
				e.setCeco(rs.getString("ceco"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setCuenta(rs.getString("cuenta"));
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
	
	public static ArrayList<IncidentesMutualidad> getFactorAnioDeclaracionJurada(int anio) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<IncidentesMutualidad> data = new ArrayList<IncidentesMutualidad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			
			sql = "select * from sw_factorDeclaracionJurada where SUBSTRING(CAST(periodo as CHAR(50)),1,4) = "+anio+" ";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				IncidentesMutualidad e = new IncidentesMutualidad();
				e.setPeriodo(rs.getInt("periodo"));
				e.setCuenta(rs.getString("cuenta"));
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
	
	
	public static String insertRupdateMatriz(IncidentesMutualidad r) throws Exception {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		String respuesta = "";
		ConnectionDB db = new ConnectionDB();
		
		
		try {
			sql = "select * from sw_centralizacionCuentaPeriodo where ceco = '"+r.getCeco()+"' and periodo = "+r.getPeriodos()+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			System.out.println(sql);
			
			if(!rs.isBeforeFirst()){
				sql3="INSERT INTO sw_centralizacionCuentaPeriodo (ceco, periodo,cuenta) VALUES ('"+r.getCeco()+"', "+r.getPeriodos()+", '"+r.getCuenta()+"')";
				System.out.println(sql3);
				ps3 = db.conn.prepareStatement(sql3);
				ps3.execute();
				
						
			}else{
				
				sql2="UPDATE sw_centralizacionCuentaPeriodo SET cuenta='"+r.getCuenta()+"' WHERE ceco='"+r.getCeco()+"' and periodo="+r.getPeriodos()+"";
				System.out.println(sql2);
				ps2 = db.conn.prepareStatement(sql2);
				ps2.execute();
			
			}

			return "Enviado con Exito";
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			db.close();
		}
		return "no";
	}
	
	public static String insertUpdateFactorDeclaracionjurada(IncidentesMutualidad r) throws Exception {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		String respuesta = "";
		ConnectionDB db = new ConnectionDB();
		
		
		try {
			sql = "select * from sw_factorDeclaracionJurada where  periodo = "+r.getPeriodos()+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			System.out.println(sql);
			
			if(!rs.isBeforeFirst()){
				sql3="INSERT INTO sw_factorDeclaracionJurada (periodo,cuenta) VALUES ("+r.getPeriodos()+", '"+r.getCuenta()+"')";
				System.out.println(sql3);
				ps3 = db.conn.prepareStatement(sql3);
				ps3.execute();
				
						
			}else{
				
				sql2="UPDATE sw_factorDeclaracionJurada SET cuenta='"+r.getCuenta()+"' WHERE periodo="+r.getPeriodos()+"";
				System.out.println(sql2);
				ps2 = db.conn.prepareStatement(sql2);
				ps2.execute();
			
			}

			return "Enviado con Exito";
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			db.close();
		}
		return "no";
	}

}
