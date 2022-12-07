package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.ConnectionDB;

public class CorreosDB {

	public static boolean createCorreo(parametros e) {
		// TODO Auto-generated method stub
		return false;
	}

	public static ArrayList<parametros> getCorreos() throws SQLException {
		// 
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT * FROM SAN_CLEMENTE.parametros WHERE codigo LIKE '%CORREO%' AND llave=1 AND activo=1 order by descripcion ASC";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				parametros pm = new parametros();
				pm.setCodigo(rs.getString("codigo"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setLlave(rs.getString("llave"));
				pm.setId(rs.getInt("id"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static parametros getCorreoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql ="";
		parametros par = new parametros();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * from parametros WHERE id='"+id+"' and activo=1";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				parametros pm = new parametros();
				pm.setId(rs.getInt("id"));
				pm.setCodigo(rs.getString("codigo"));
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
			
				pm.setId(rs.getInt("id"));
				par=pm;
			}
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return par;
	}

	public static boolean updateCorreo(parametros e) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = " UPDATE parametros SET descripcion=? where codigo='"+e.getCodigo() +"' AND llave='"+e.getLlave()+"' AND activo=1" ;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);			
			ps.setString(1, e.getDescripcion());
			
			ps.executeUpdate();
			return true;
		}catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			return false;
		}finally {

			ps.close();
		
			db.close();
		}	
	}

	public static boolean deleteCorreoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = " UPDATE parametros SET activo=0 where id="+id ;
			ps = db.conn.prepareStatement(sql);			
			
			ps.executeUpdate();
			return true;
		}catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			return false;
		}finally {

			ps.close();
		
			db.close();
		}	
	}

}
