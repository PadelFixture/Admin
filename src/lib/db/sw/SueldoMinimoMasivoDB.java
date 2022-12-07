package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import lib.classSW.SueldoMinimoMasivo;
import lib.db.ConnectionDB;

public class SueldoMinimoMasivoDB {

	public static boolean createSueldoMinimoMasivo(SueldoMinimoMasivo sueldo) throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO sw_m_sueldoMinimo (valorSueldoMinimo, fechaSueldoMinimo,idUsuario) VALUES (?,?,?)";
		ps = db.conn.prepareStatement(sql);
		ps.setBigDecimal(1, sueldo.getValorSueldoMinimo());
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		ps.setDate(2, date);
		ps.setInt(3,sueldo.getIdUsuario());
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
	public static ArrayList<SueldoMinimoMasivo> getSueldosMinimosMasivos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<SueldoMinimoMasivo> lista = new ArrayList<SueldoMinimoMasivo>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT *  FROM sw_m_sueldoMinimo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				SueldoMinimoMasivo sueldominimo= new SueldoMinimoMasivo();				
				sueldominimo.setIdSueldoMinimo(rs.getInt("idSueldoMinimo"));				
				sueldominimo.setValorSueldoMinimo(rs.getBigDecimal("valorSueldoMinimo"));
				sueldominimo.setFechaSueldoMinimo(rs.getDate("fechaSueldoMinimo"));
				sueldominimo.setIdUsuario(rs.getInt("idUsuario"));
				
				lista.add(sueldominimo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<SueldoMinimoMasivo> getSueldosMinimosByContract() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<SueldoMinimoMasivo> lista = new ArrayList<SueldoMinimoMasivo>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT distinct(horasSemanales) FROM SAN_CLEMENTE.contratos ORDER BY horasSemanales DESC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				SueldoMinimoMasivo sueldominimo= new SueldoMinimoMasivo();				
				sueldominimo.setHorasSemanales(rs.getInt("horasSemanales"));
				lista.add(sueldominimo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	
	public static SueldoMinimoMasivo getSueldoMinimoMasivoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		SueldoMinimoMasivo lista = new SueldoMinimoMasivo();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT *  FROM sw_m_sueldoMinimo WHERE idSueldoMinimo="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);	
			SueldoMinimoMasivo sueldominimo= new SueldoMinimoMasivo();	
			while(rs.next()){				
							
				sueldominimo.setIdSueldoMinimo(rs.getInt("idSueldoMinimo"));				
				sueldominimo.setValorSueldoMinimo(rs.getBigDecimal("valorSueldoMinimo"));
				sueldominimo.setFechaSueldoMinimo(rs.getDate("fechaSueldoMinimo"));
				sueldominimo.setIdUsuario(rs.getInt("idUsuario"));
				
				lista=sueldominimo;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static boolean updateSueldoMinimoMasivo(SueldoMinimoMasivo param) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_m_sueldoMinimo SET valorSueldoMinimo = ?, fechaSueldoMinimo = ?, idUsuario = ? WHERE idSueldoMinimo="+param.getIdSueldoMinimo();
		
			ps = db.conn.prepareStatement(sql);
			ps.setBigDecimal(1,param.getValorSueldoMinimo());
			ps.setDate(2, param.getFechaSueldoMinimo());
			ps.setInt(3,param.getIdUsuario());
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static boolean updateSueldoMinimoMasivoContract(SueldoMinimoMasivo param) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE contratos SET sueldoBase= ? WHERE horasSemanales="+param.getHorasSemanales();
		
			ps = db.conn.prepareStatement(sql);
			ps.setBigDecimal(1,param.getValorSueldoMinimo());
			ps.executeUpdate();
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static boolean deleteSueldoMinimoMasivoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_m_sueldoMinimo WHERE idSueldoMinimo ="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	

	public static SueldoMinimoMasivo getLastSueldoMinimoMasivoById() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		SueldoMinimoMasivo lista = new SueldoMinimoMasivo();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT *  FROM sw_m_sueldoMinimo ORDER BY fechaSueldoMinimo DESC LIMIT 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);	
			SueldoMinimoMasivo sueldominimo= new SueldoMinimoMasivo();	
			while(rs.next()){				
							
				sueldominimo.setIdSueldoMinimo(rs.getInt("idSueldoMinimo"));				
				sueldominimo.setValorSueldoMinimo(rs.getBigDecimal("valorSueldoMinimo"));
				sueldominimo.setFechaSueldoMinimo(rs.getDate("fechaSueldoMinimo"));
				sueldominimo.setIdUsuario(rs.getInt("idUsuario"));
				
				lista=sueldominimo;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static boolean updateSueldos(ArrayList<SueldoMinimoMasivo> param, int sueldoBase) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		
		ConnectionDB db = new ConnectionDB();
		try{
		for(int i=0;i<param.size();i++)
		{
			
				SueldoMinimoMasivo sueldo = new SueldoMinimoMasivo();
				sql = "UPDATE contratos  set sueldoBase="+param.get(i).getValorSueldoMinimo()+"WHERE horasSemanales="+param.get(i).getHorasSemanales();
				ps = db.conn.prepareStatement(sql);
				ps.executeUpdate(sql);
		}
		return true;
		}catch(SQLException sqle)
		{
			
			System.out.println("Error: " + sqle.getMessage());
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e.getMessage());
			return false;
			
		}
		
		
		
		
		
	
	}
}

