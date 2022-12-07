package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.calificacion_campo;
import lib.db.ConnectionDB;

public class CALIFICACION_RENIDIMIENTO {
	//-----------CALIFICACION_CAMPO-------------------
//	SELECT
	public static ArrayList<calificacion_campo> GETCALIFICACION () throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<calificacion_campo> lista = new ArrayList<calificacion_campo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from calificacion_campo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				calificacion_campo ob = new calificacion_campo();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCampo(rs.getString("campo"));
				ob.setBajo_max(rs.getFloat("bajo_max"));
				ob.setPromedio_max(rs.getFloat("promedio_max"));
				ob.setBueno_max(rs.getFloat("bueno_max"));
				ob.setLabor(rs.getInt("labor"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setFaena(rs.getInt("faena"));
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
	public static boolean insertCC (calificacion_campo c)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO calificaion_campo (campo, bajo_max, promedio_max, bueno_max, labor)";
			sql += "VALUES (?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getCampo());
			ps.setFloat(2, c.getBajo_max());
			ps.setFloat(3, c.getPromedio_max());
			ps.setFloat(4, c.getBueno_max());
			ps.setInt(5, c.getLabor());
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
	public static boolean updateCC(calificacion_campo c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE calificacion_campo set bajo_max='"+c.getBajo_max()+"',"
				+ " promedio_max='"+c.getPromedio_max()+"', bueno_max='"+c.getBueno_max()+"' "
				+ "where codigo='"+c.getCodigo()+"'";
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
	
	
//	SELECT CAMPO Y LABOR
	public static ArrayList<calificacion_campo> GETCREAR_CALIFICACION (calificacion_campo row) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<calificacion_campo> lista = new ArrayList<calificacion_campo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select bajo_max, promedio_max, bueno_max from calificacion_campo  "
				+ " WHERE campo ='" +row.getCampo()+ "'and labor = '"+row.getLabor()+ "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				calificacion_campo ob = new calificacion_campo();
				ob.setBajo_max(rs.getInt("bajo_max"));
				ob.setPromedio_max(rs.getInt("promedio_max"));
				ob.setBueno_max(rs.getInt("bueno_max"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
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
	public static boolean insertCAMPO_LABOR (calificacion_campo c)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO calificacion_campo(campo, bajo_max, promedio_max, bueno_max, labor, especie, faena)";
			sql += "VALUES (?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getCampo());
			ps.setFloat(2, c.getBajo_max());
			ps.setFloat(3, c.getPromedio_max());
			ps.setFloat(4, c.getBueno_max());
			ps.setInt(5, c.getLabor());
			ps.setInt(6, c.getEspecie());
			ps.setInt(7, c.getFaena());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
//----------FIN CALIFICACION_CAMPO-----------------


}
