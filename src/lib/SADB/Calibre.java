package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CALIBRE;
import lib.classSA.CATEGORIA;
import lib.classSA.estado_fenologico;
import lib.db.ConnectionDB;

public class Calibre {

//	INSERT
	public static boolean ADD_Calibre(CALIBRE c) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			 sql = "INSERT INTO mantenedor_sa(categoria, descripcion, cod_campo, cod_especie, estado) VALUES('CALIBRE',?,'1',?,'1')";
			 ps = db.conn.prepareStatement(sql);
			 ps.setString(1, c.getDescripcion());
			 ps.setInt(2, c.getCod_especie());
			 ps.execute();
			 return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return false;
	}
	
	public static boolean UPDATE_Calibre(CALIBRE c) throws Exception {
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql = " UPDATE mantenedor_sa set descripcion='" +c.getDescripcion()+ "', "
				+ " cod_especie='"+c.getCod_especie()+"' where codigo='" +c.getCodigo()+ "'";
			ps = db.conn.prepareStatement(sql);
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
	
	public static boolean UP_Calibre_Estado(CALIBRE es) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE mantenedor_sa set estado=0 where codigo='"+es.getCodigo()+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
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
}
