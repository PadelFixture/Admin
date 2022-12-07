package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import lib.classSW.ImpuestoUnico;

import lib.db.ConnectionDB;

@Controller
public class ImpuestoUnicoDB {

	public static boolean createImpuestoUnico(ImpuestoUnico impuesto) {
		
				return false;
	}

	public static ArrayList<ImpuestoUnico> getImpuestosUnicos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ImpuestoUnico> lista = new ArrayList<ImpuestoUnico>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_rhImpuestoUnico "+
				  "inner join (SELECT max(fecha) as fechaMaxima FROM sw_rhImpuestoUnico) as j on sw_rhImpuestoUnico.fecha=j.fechaMaxima "+
				  "left join (select idMoneda as id, moneda from sw_rhmoneda where visible=1) as k on sw_rhImpuestoUnico.idMoneda=k.id "+
				  "order by rebaja ASC";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				ImpuestoUnico impuesto= new ImpuestoUnico();				
				impuesto.setIdImpuesto(rs.getInt("idImpuesto"));	
				impuesto.setFecha(rs.getDate("fecha"));
				impuesto.setIdMoneda(rs.getInt("idMoneda"));
				impuesto.setDesde(rs.getBigDecimal("desde"));
				impuesto.setHasta(rs.getBigDecimal("hasta"));
				impuesto.setFactor(rs.getBigDecimal("factor"));
				impuesto.setRebaja(rs.getBigDecimal("rebaja"));
				impuesto.setTasaMaxima(rs.getBigDecimal("tasaMaxima"));
				impuesto.setMoneda(rs.getString("moneda"));
				
				lista.add(impuesto);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<ImpuestoUnico> getPeriodos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ImpuestoUnico> lista = new ArrayList<ImpuestoUnico>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT distinct(fecha) FROM sw_rhImpuestoUnico";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				ImpuestoUnico impuesto= new ImpuestoUnico();				
			
				impuesto.setFecha(rs.getDate("fecha"));
				
				
				lista.add(impuesto);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<ImpuestoUnico> getImpuestosUnicosByPeriodo(String periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ImpuestoUnico> lista = new ArrayList<ImpuestoUnico>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_rhImpuestoUnico "+
				  "inner join (SELECT max(fecha) as fechaMaxima FROM sw_rhImpuestoUnico WHERE fecha like '"+periodo+"%') as j on sw_rhImpuestoUnico.fecha=j.fechaMaxima "+
				  "left join (select idMoneda as id, moneda from sw_rhmoneda where visible=1) as k on sw_rhImpuestoUnico.idMoneda=k.id "+
				  "order by rebaja ASC";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				ImpuestoUnico impuesto= new ImpuestoUnico();				
				impuesto.setIdImpuesto(rs.getInt("idImpuesto"));	
				impuesto.setFecha(rs.getDate("fecha"));
				impuesto.setIdMoneda(rs.getInt("idMoneda"));
				impuesto.setDesde(rs.getBigDecimal("desde"));
				impuesto.setHasta(rs.getBigDecimal("hasta"));
				impuesto.setFactor(rs.getBigDecimal("factor"));
				impuesto.setRebaja(rs.getBigDecimal("rebaja"));
				impuesto.setTasaMaxima(rs.getBigDecimal("tasaMaxima"));
				impuesto.setMoneda(rs.getString("moneda"));
				
				lista.add(impuesto);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<ImpuestoUnico> getImpuestoByLastPeriod() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ImpuestoUnico> lista = new ArrayList<ImpuestoUnico>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_rhImpuestoUnico "+
				  "inner join (SELECT max(fecha) as fechaMaxima FROM sw_rhImpuestoUnico ) as j on sw_rhImpuestoUnico.fecha=j.fechaMaxima "+
				  "left join (select idMoneda as id, moneda from sw_rhmoneda where visible=1) as k on sw_rhImpuestoUnico.idMoneda=k.id "+
				  "order by rebaja ASC";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				ImpuestoUnico impuesto= new ImpuestoUnico();				
				impuesto.setIdImpuesto(rs.getInt("idImpuesto"));	
				impuesto.setFecha(rs.getDate("fecha"));
				impuesto.setIdMoneda(rs.getInt("idMoneda"));
				impuesto.setDesde(rs.getBigDecimal("desde"));
				impuesto.setHasta(rs.getBigDecimal("hasta"));
				impuesto.setFactor(rs.getBigDecimal("factor"));
				impuesto.setRebaja(rs.getBigDecimal("rebaja"));
				impuesto.setTasaMaxima(rs.getBigDecimal("tasaMaxima"));
				impuesto.setMoneda(rs.getString("moneda"));
				
				lista.add(impuesto);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static boolean updateImpuestoUnicoMasivo(ArrayList<ImpuestoUnico> impuestos) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual
			Gson g= new Gson();
			
			System.out.println(g.toJson(impuestos));
		for(ImpuestoUnico impuesto :impuestos ){
			
			
			
			sql ="UPDATE sw_rhImpuestoUnico SET desde = ?, hasta=?, factor=?, rebaja=?, tasaMaxima=?  WHERE idImpuesto="+impuesto.getIdImpuesto() ;
		
			ps = db.conn.prepareStatement(sql);
			
			ps.setBigDecimal(1,impuesto.getDesde());
			ps.setBigDecimal(2,impuesto.getHasta());
			ps.setBigDecimal(3,impuesto.getFactor());
			ps.setBigDecimal(4,impuesto.getRebaja());
			ps.setBigDecimal(5,impuesto.getTasaMaxima());
			
			ps.executeUpdate();
		}
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	

	public static ImpuestoUnico getImpuestoUnicoById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean updateImpuestoUnicoById(ImpuestoUnico impuesto) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean deleteImpuestoUnicoById(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	//traer periodo especifico
	public static ArrayList<ImpuestoUnico> getPeriodo(String periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ImpuestoUnico> lista = new ArrayList<ImpuestoUnico>();
		ConnectionDB db = new ConnectionDB();
		String p= periodo.substring(0, 7);	
		
		try{
			sql = "select max(fecha) as fecha from sw_rhImpuestoUnico WHERE fecha like '"+p+"%'";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				ImpuestoUnico impuesto= new ImpuestoUnico();				
			
				impuesto.setFecha(rs.getDate("fecha"));
				
				
				lista.add(impuesto);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	//traer ultimo periodo de la tabla de la base de datos
	public static ArrayList<ImpuestoUnico> getLastPeriodo() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ImpuestoUnico> lista = new ArrayList<ImpuestoUnico>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select max(fecha) as fecha from sw_rhImpuestoUnico";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				ImpuestoUnico impuesto= new ImpuestoUnico();				
			
				impuesto.setFecha(rs.getDate("fecha"));
				
				
				lista.add(impuesto);
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
