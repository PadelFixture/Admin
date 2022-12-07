package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.FORMA_APLICA;
import lib.db.ConnectionDB;


public class FORMA_APLICACION {
	
	public static ArrayList<FORMA_APLICA> GETFA () throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<FORMA_APLICA> lista = new ArrayList<FORMA_APLICA>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM forma_aplicacion where estado='1'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				FORMA_APLICA ob = new FORMA_APLICA();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setDescripcion(rs.getString("descripcion"));
				ob.setEstado(rs.getString("estado"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			 db.close();
		}
		return lista;
		}
	
//	Insert
		public static boolean insertFA (FORMA_APLICA fa) throws Exception {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "INSERT INTO parametros (codigo, llave, descripcion, activo) VALUES('FORMA APLICACION', '22', ?, '1')";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, fa.getDescripcion());		
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
		
		public static boolean updateFA (FORMA_APLICA fa) throws Exception {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "UPDATE forma_aplicacion set descripcion ='"+fa.getDescripcion()+"' where codigo='"+fa.getCodigo()+"'";
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
		
//		public static boolean deleteFA (FORMA_APLICA fa) throws Exception {
//			PreparedStatement ps = null;
//			String sql = "";
//			ConnectionDB db = new ConnectionDB();
//			try {
//				sql = "DELETE FROM forma_aplicacion where  codigo='" +fa.getCodigo()+ "'";
//				ps = db.conn.prepareStatement(sql);
//				ps.execute();
//				return true;
//			} catch (SQLException e) {
//				System.out.println("Error:" + e.getMessage());
//			} catch (Exception e) {
//				System.out.println("Error:" + e.getMessage());
//			} finally {
//				 ps.close();
//				 db.close();
//			}
//			return false;
//		}
		
		public static boolean UPFORMA_APLICACION_ESTADO (FORMA_APLICA fa) throws Exception {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "UPDATE forma_aplicacion set estado =0 where codigo='"+fa.getCodigo()+"'";
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


