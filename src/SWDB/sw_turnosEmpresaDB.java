package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.TurnosEmpresa;
import lib.db.ConnectionDB;

public class sw_turnosEmpresaDB {
	
	public static ArrayList<TurnosEmpresa> getTurnosE(int sociedad,String huerto, String zona) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<TurnosEmpresa> data = new ArrayList<TurnosEmpresa>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = "select id,sociedad,huerto,zona,nombre"
					+ ",(select upper(denominacionSociedad) from sociedad where idSociedad = "+sociedad+")as nombre_E,"
					+ "upper((select descripcion from campo where campo = huerto)) as nombre_huerto"
					+ " from sw_turnos where  sociedad = "+sociedad+" ";
					
					if("null".equals(huerto)){}else{sql += " and huerto = '"+huerto+"'";}
			        if("null".equals(zona)){}else{sql += " and zona = '"+zona+"'";}
			
			
			System.out.println(sql);
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				TurnosEmpresa e = new TurnosEmpresa();
				e.setId(rs.getInt("id"));
				e.setEmpresa(rs.getString("sociedad"));
				e.setHuerto(rs.getString("huerto"));
				e.setZona(rs.getString("zona"));
				e.setNombre(rs.getString("nombre"));
				e.setNombre_empresa(rs.getString("nombre_E"));
				e.setNombre_huerto(rs.getString("nombre_huerto"));
				
				
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
	
	public static boolean eliminarTE(int id) throws  Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = "DELETE FROM sw_turnos WHERE id="+id+"";
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
	
	public static boolean updateTurnose(TurnosEmpresa r) throws  Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {

			sql = "Update sw_turnos set "
					+ "nombre= '"+r.getNombre()+"' "
					+ " where id = "+r.getId()+"";  
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

	public static String insertturnosemp(TurnosEmpresa r) throws Exception {

		PreparedStatement ps3 = null;
		String sql3 = "";
		String respuesta = "";
		ConnectionDB db = new ConnectionDB();

		try {
			

				sql3 = "INSERT INTO sw_turnos (sociedad, huerto, zona, nombre) VALUES (?,?,?,?)";
				ps3 = db.conn.prepareStatement(sql3);

				ps3.setString(1, r.getEmpresa());
				ps3.setString(2, r.getHuerto());
				ps3.setString(3, r.getZona());
				ps3.setString(4, r.getNombre());
				

				ps3.execute();

				respuesta = "Enviado";

			

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
}
