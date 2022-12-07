package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.LABOR;
import lib.db.ConnectionDB;

public class labor {
	public static ArrayList<LABOR> GET_LABOR(String codigo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LABOR> lista = new ArrayList<LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from labor where faena = '"+codigo+"' and estado = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				LABOR ob = new LABOR();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getString("labor"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setRebaja(rs.getInt("rebaja"));
				ob.setTipo_labor(rs.getInt("tipo_labor"));
				ob.setEstado(rs.getInt("estado"));
				ob.setZona(rs.getString("zona"));
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
	public static boolean insertLabor(LABOR l) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO labor (faena, labor, maquinaria, rebaja, tipo_labor, estado, zona) VALUES(?,?,?,?,0,1,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, l.getFaena());
			ps.setString(2, l.getLabor());
			ps.setInt(3, l.getMaquinaria());
			ps.setInt(4, l.getRebaja());
			ps.setString(5, l.getZona());
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
	
	public static boolean updateLAbor(LABOR l) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE labor  SET  faena='"+l.getFaena()+"', labor='"+l.getLabor()+"',"
				+ " maquinaria='"+l.getMaquinaria()+"', rebaja='"+l.getRebaja()+"', "
				+ " tipo_labor ='"+l.getTipo_labor()+"' WHERE codigo='"+l.getCodigo()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute(sql);
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
	
	public static ArrayList<LABOR> GET_LABOR_FAENA (int codigo, String zona) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LABOR> lista = new ArrayList<LABOR>();
		ConnectionDB db =  new ConnectionDB();
		try {
			sql = "SELECT * FROM labor WHERE faena = " +codigo+" AND zona = '"+zona+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LABOR e = new LABOR();
				e.setCodigo(rs.getInt("codigo"));
				e.setFaena(rs.getInt("faena"));
				e.setLabor(rs.getString("labor"));
				e.setMaquinaria(rs.getInt("maquinaria"));
				e.setRebaja(rs.getInt("rebaja"));
				e.setTipo_labor(rs.getInt("tipo_labor"));
				lista.add(e);
			}
			ps.close();
			rs.close();
			db.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			db.close();
		}
		return lista;
	}
	
	public static boolean UP_LABOR_FAENA_ESTADO (LABOR fa) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE labor set estado =0 where codigo='"+fa.getCodigo()+"'";
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
	public static ArrayList<LABOR> GET_LABOR_FAENA_MAQ (int codigo, String zona) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LABOR> lista = new ArrayList<LABOR>();
		ConnectionDB db =  new ConnectionDB();
		try {
			sql = "SELECT * FROM labor WHERE codigo = " +codigo+" AND zona = '"+zona+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LABOR e = new LABOR();
				e.setCodigo(rs.getInt("codigo"));
				e.setFaena(rs.getInt("faena"));
				e.setLabor(rs.getString("labor"));
				e.setMaquinaria(rs.getInt("maquinaria"));
				e.setRebaja(rs.getInt("rebaja"));
				e.setTipo_labor(rs.getInt("tipo_labor"));
				lista.add(e);
			}
			ps.close();
			rs.close();
			db.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			db.close();
		}
		return lista;
	}
}