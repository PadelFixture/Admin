package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.MAESTRO_MOTIVO_INGRESO;
import lib.db.ConnectionDB;

public class MAESTRO_MOTIVOINGRESO {
	public static ArrayList<MAESTRO_MOTIVO_INGRESO> GET_MaestroMotivoIngreso()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<MAESTRO_MOTIVO_INGRESO> lista = new ArrayList<MAESTRO_MOTIVO_INGRESO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from maestro_motivos_ingreso where estado=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				MAESTRO_MOTIVO_INGRESO ob = new MAESTRO_MOTIVO_INGRESO();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setDescripcion(rs.getString("descripcion"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	public static boolean ADD_MaestroMotivoIngreso(MAESTRO_MOTIVO_INGRESO m)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql= "insert into maestro_motivos_ingreso(descripcion, estado) values(?,1)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, m.getDescripcion());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}
		return false;
	}
	
	public static boolean UP_MaestroMotivoIngreso(MAESTRO_MOTIVO_INGRESO m) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE maestro_motivos_ingreso set descripcion='"+m.getDescripcion()+"' where codigo='"+m.getCodigo()+"'";
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
	
	
	public static boolean UP_MaestroMotivoIngreso_Estado(MAESTRO_MOTIVO_INGRESO m) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE maestro_motivos_ingreso set estado =0 where codigo='"+m.getCodigo()+"'";
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
