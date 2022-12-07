package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CONTROL_AP;
import lib.classSA.LABOR;
import lib.classSA.faena;
import lib.db.ConnectionDB;

public class FAENA {
	public static ArrayList<faena> GET_FAENA()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<faena> lista = new ArrayList<faena>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from faena where estado=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				faena ob = new faena();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setFaena(rs.getString("faena"));
				ob.setZona(rs.getString("zona"));
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
	public static ArrayList<faena> GET_FAENA_ZONA(String zona)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<faena> lista = new ArrayList<faena>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM faena WHERE estado = 1 AND zona = '"+zona+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				faena ob = new faena();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setFaena(rs.getString("faena"));
				ob.setZona(rs.getString("zona"));
				ob.setClasificacion(rs.getString("clasificacion"));
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
	public static boolean ADD_FAENA(faena f) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO faena (faena, estado, zona, clasificacion, cuenta_prd, cuenta_noprd) VALUES(?,'1',?, ?, ?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, f.getDescripcion());
			ps.setString(2, f.getZona());
			ps.setString(3, f.getClasificacion());
			ps.setString(4, f.getCuenta());
			ps.setString(5, f.getCuenta());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static boolean UPDATEFAENA(faena f) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE faena SET faena = ?, zona = ?, clasificacion = ?,  cuenta_prd = ?, cuenta_noprd = ?  WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, f.getDescripcion());
			ps.setString(2, f.getZona());
			ps.setString(3, f.getClasificacion());
			ps.setString(4, f.getCuenta());
			ps.setString(5, f.getCuenta());
			ps.setInt(6, f.getCodigo());
			System.out.println(sql);
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
	
	public static boolean UPFAENA_ESTADO (faena fa) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE faena set estado =0 where codigo='"+fa.getCodigo()+"'";
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
	public static ArrayList<LABOR> GET_LABOR_ZONA(String zona)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LABOR> lista = new ArrayList<LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM labor WHERE estado = 1 AND zona = '"+zona+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				LABOR ob = new LABOR();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getString("labor"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setRebaja(rs.getInt("rebaja"));
				ob.setZona(rs.getString("zona"));
				ob.setTipo_labor(rs.getInt("tipo_labor"));
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
//---------------FIN FAENA----------------------------------------------


}
