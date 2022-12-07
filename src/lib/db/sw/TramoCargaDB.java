package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.TramoCarga;
import lib.db.ConnectionDB;

public class TramoCargaDB {

	public static boolean createTramo(TramoCarga tramo) throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		try{
		String sql="INSERT INTO sw_m_tramo_carga (descripcionTramoCarga, inicioTramoCarga, finTramoCarga, montoTramoCarga, periodoTramoCarga, letraTramoCarga) VALUES (?,?,?,?,?,?)";
		ps = db.conn.prepareStatement(sql);
		
		ps.setString(1, tramo.getDescripcionTramoCarga());
		ps.setInt(2,tramo.getInicioTramoCarga());
		ps.setInt(3,tramo.getFinTramoCarga());
		ps.setInt(4, tramo.getMontoTramoCarga());
		ps.setInt(5, tramo.getPeriodoTramoCarga());
		ps.setString(6, tramo.getLetraTramoCarga());
		
		ps.execute();
		return true;
		}
		catch(Exception ex){
			return false;
		}
		finally{
			db.conn.close();
		}
	}
	public static ArrayList<TramoCarga> getAllTramos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<TramoCarga> lista = new ArrayList<TramoCarga>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_m_tramo_carga order by descripcionTramoCarga ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				TramoCarga tramo= new TramoCarga();
				tramo.setIdTramoCarga(rs.getInt("idTramoCarga"));
				tramo.setDescripcionTramoCarga(rs.getString("descripcionTramoCarga"));				
				tramo.setInicioTramoCarga(rs.getInt("inicioTramoCarga"));
				tramo.setFinTramoCarga(rs.getInt("finTramoCarga"));
				tramo.setMontoTramoCarga(rs.getInt("montoTramoCarga"));
				tramo.setPeriodoTramoCarga(rs.getInt("periodoTramoCarga"));
				tramo.setLetraTramoCarga(rs.getString("letraTramoCarga"));
				lista.add(tramo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<TramoCarga> getTramosByPeriodo(int periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<TramoCarga> lista = new ArrayList<TramoCarga>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_m_tramo_carga WHERE periodoTramoCarga="+periodo+" order by descripcionTramoCarga ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				TramoCarga tramo= new TramoCarga();
				tramo.setIdTramoCarga(rs.getInt("idTramoCarga"));
				tramo.setDescripcionTramoCarga(rs.getString("descripcionTramoCarga"));				
				tramo.setInicioTramoCarga(rs.getInt("inicioTramoCarga"));
				tramo.setFinTramoCarga(rs.getInt("finTramoCarga"));
				tramo.setMontoTramoCarga(rs.getInt("montoTramoCarga"));
				tramo.setPeriodoTramoCarga(rs.getInt("periodoTramoCarga"));
				tramo.setLetraTramoCarga(rs.getString("letraTramoCarga"));
				lista.add(tramo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static boolean deleteTramoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_m_tramo_carga WHERE idTramoCarga ="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static TramoCarga getBlankTramo() {
		TramoCarga tramo = new TramoCarga();

		return tramo.createBlankTramo();
	}
	public static TramoCarga getTramoCargaById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		TramoCarga tramo=new TramoCarga(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_m_tramo_carga WHERE idTramoCarga="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				TramoCarga tramocarga= new TramoCarga();				
				tramocarga.setIdTramoCarga(rs.getInt("idTramoCarga"));				
				tramocarga.setDescripcionTramoCarga(rs.getString("descripcionTramoCarga"));
				tramocarga.setInicioTramoCarga(rs.getInt("inicioTramoCarga"));
				tramocarga.setFinTramoCarga(rs.getInt("finTramoCarga"));
				tramocarga.setMontoTramoCarga(rs.getInt("montoTramoCarga"));
				tramocarga.setPeriodoTramoCarga(rs.getInt("periodoTramoCarga"));
				tramocarga.setLetraTramoCarga(rs.getString("letraTramoCarga"));
				tramo=tramocarga;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return tramo;
	}
	public static boolean updateTramoCarga(TramoCarga tramocarga) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_m_tramo_carga SET descripcionTramoCarga = ?, inicioTramoCarga = ?, finTramoCarga=?, montoTramoCarga=?, periodoTramoCarga=?, letraTramoCarga=?  WHERE idTramoCarga="+tramocarga.getIdTramoCarga();
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,tramocarga.getDescripcionTramoCarga());
			ps.setInt(2,tramocarga.getInicioTramoCarga());
			ps.setInt(3,tramocarga.getFinTramoCarga());
			ps.setInt(4, tramocarga.getMontoTramoCarga());
			ps.setInt(5, tramocarga.getPeriodoTramoCarga());
			ps.setString(6, tramocarga.getLetraTramoCarga());
			
			ps.executeUpdate();
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static boolean updateTramoCargaMasivo(ArrayList<TramoCarga> tramocarg, int periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual

		for(TramoCarga tramocarga :tramocarg ){
			sql ="UPDATE sw_m_tramo_carga SET inicioTramoCarga = ?, finTramoCarga=?, montoTramoCarga=?  WHERE idTramoCarga="+tramocarga.getIdTramoCarga()+ " AND periodoTramoCarga="+tramocarga.getPeriodoTramoCarga() ;
		
			ps = db.conn.prepareStatement(sql);
			
			ps.setInt(1,tramocarga.getInicioTramoCarga());
			ps.setInt(2,tramocarga.getFinTramoCarga());
			ps.setInt(3, tramocarga.getMontoTramoCarga());
			
			
			
			ps.executeUpdate();
		}
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static ArrayList<TramoCarga> getPeriodos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<TramoCarga> lista = new ArrayList<TramoCarga>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT distinct(periodoTramoCarga) as periodoTramoCarga FROM sw_m_tramo_carga order by periodoTramoCarga ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				TramoCarga tramo= new TramoCarga();
				
				tramo.setPeriodoTramoCarga(rs.getInt("periodoTramoCarga"));
				
				lista.add(tramo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<TramoCarga> getTramosByLastPeriod() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<TramoCarga> lista = new ArrayList<TramoCarga>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_m_tramo_carga "+
				  "inner join (select max(sw_m_tramo_carga.periodoTramoCarga) as maximo FROM sw_m_tramo_carga) as d on sw_m_tramo_carga.periodoTramoCarga=d.maximo";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				TramoCarga tramo= new TramoCarga();
				tramo.setIdTramoCarga(rs.getInt("idTramoCarga"));
				tramo.setDescripcionTramoCarga(rs.getString("descripcionTramoCarga"));				
				tramo.setInicioTramoCarga(rs.getInt("inicioTramoCarga"));
				tramo.setFinTramoCarga(rs.getInt("finTramoCarga"));
				tramo.setMontoTramoCarga(rs.getInt("montoTramoCarga"));
				tramo.setPeriodoTramoCarga(rs.getInt("periodoTramoCarga"));
				tramo.setLetraTramoCarga(rs.getString("letraTramoCarga"));
				lista.add(tramo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
}
