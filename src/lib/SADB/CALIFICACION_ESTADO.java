package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.ESTADO;
import lib.classSA.calificacion_campo;
import lib.db.ConnectionDB;

public class CALIFICACION_ESTADO {
	//-----------CALIFICACION_ESTADO-------------------
//	SELECT
	public static ArrayList<ESTADO> Get_Calificacion_Estado(ESTADO row) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<ESTADO> lista = new ArrayList<ESTADO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select verde_hasta, amarillo_hasta from calificacion_estado where campo='"+row.getCampo()+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				ESTADO ob = new ESTADO();
				ob.setVerde_hasta(rs.getInt("verde_hasta"));
				ob.setAmarillo_hasta(rs.getInt("amarillo_hasta"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	
	public static ArrayList<ESTADO> Get_CalificacionEstado() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<ESTADO> lista = new ArrayList<ESTADO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from calificacion_estado";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				ESTADO ob = new ESTADO();
				ob.setCampo(rs.getString("campo"));
				ob.setVerde_hasta(rs.getInt("verde_hasta"));
				ob.setAmarillo_hasta(rs.getInt("amarillo_hasta"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
//	INSERT
	public static boolean Insert_Calificacion_Estado(ESTADO c)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO calificacion_estado(campo, verde_hasta, amarillo_hasta)";
			sql += "VALUES (?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getCampo());
			ps.setFloat(2, c.getVerde_hasta());
			ps.setFloat(3, c.getAmarillo_hasta());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
//	UPDATE
	public static boolean Update_Calificacion_Estado(ESTADO c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE calificacion_estado set verde_hasta='"+c.getVerde_hasta()+"', "
				+ " amarillo_hasta='"+c.getAmarillo_hasta()+"' where campo='"+c.getCampo()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
//----------FIN CALIFICACION_ESTADO-----------------


}
