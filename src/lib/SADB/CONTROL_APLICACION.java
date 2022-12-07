package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CONTROL_AP;
import lib.db.ConnectionDB;


public class CONTROL_APLICACION {
	
	public static ArrayList<CONTROL_AP> getControlAplicacion () throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CONTROL_AP> lista = new ArrayList<CONTROL_AP>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " select c.control_aplicacion, c.especie, c.codigo, c.estado, e.especie nVespecie from control_aplicacion c  "
				+ " left join especie e on c.especie = e.codigo where c.estado=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				CONTROL_AP ob = new CONTROL_AP();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setControl_aplicacion(rs.getString("control_aplicacion"));
				ob.setEstado(rs.getInt("estado"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setnVespecie(rs.getString("nVespecie"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error :" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
		} finally {
			 db.close();
		}
		return lista;
		}
	
//	Insert
		public static boolean insertCA (CONTROL_AP fa) throws Exception {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "INSERT INTO control_aplicacion (control_aplicacion, especie, estado) VALUES(?,?, '1')";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, fa.getControl_aplicacion());
				ps.setInt(2, fa.getEspecie());
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
		
		public static boolean updateCA (CONTROL_AP fa) throws Exception {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = " UPDATE control_aplicacion set control_aplicacion ='"+fa.getControl_aplicacion() +"', "
					+ " especie='"+fa.getEspecie()+"' where codigo='"+fa.getCodigo()+"'";
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
		
		public static boolean UP_ControlAplicacion_Estado (CONTROL_AP fa) throws Exception {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try {
				sql = "UPDATE control_aplicacion set estado=0 where codigo='"+fa.getCodigo()+"'";
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


