package lib.db.sw;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lib.classSW.Feriado;
import lib.db.ConnectionDB;

public class FeriadoDB {
	/*------------------Debug For SQL SENTENCES------------------------------*/
	public static ResultSet getQuery(String query) throws SQLException
	{
		PreparedStatement ps = null;
		String sql=query;
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;
		
		try{
			ps = db.conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);

			return rs;		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			
		}finally {
			ps.close();
			db.close();
		}		
		return rs;
	}
	/*------------------blanks------------------------------*/	
	public static Feriado getBlankHoliday()  throws Exception{

		
		Feriado holiday = new Feriado();

		return holiday.createBlankHoliday();
	}
	
	
	/*------------------CRUD------------------------------*/	
	
	
	
	/*------------------CREATE---------------------*/	
	
	public static boolean createHoliday(Feriado holiday) throws SQLException
	{
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO sw_m_feriados (nombreFeriado, fechaFeriado,descripcionFeriado, idRegion) VALUES (?,?,?,?)";
		ps = db.conn.prepareStatement(sql);
		
		
		ps.setString(1, holiday.getNombreFeriado());
		ps.setDate(2, holiday.getFechaFeriado());
		ps.setString(3,holiday.getDescripcionFeriado());
		ps.setInt(4, holiday.getIdRegion());
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
	
	/*------------------READ---------------------*/	
	
	public static ArrayList<Feriado> getAllHolidays() throws Exception 
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Feriado> lista = new ArrayList<Feriado>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM SAN_CLEMENTE.sw_m_feriados join region on sw_m_feriados.idRegion=region.idregion ORDER BY nombreFeriado ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Feriado holiday= new Feriado();				
				holiday.setIdFeriado(rs.getInt("idFeriado"));				
				holiday.setNombreFeriado(rs.getString("nombreFeriado"));
				holiday.setFechaFeriado(rs.getDate("fechaFeriado"));
				holiday.setDescripcionFeriado(rs.getString("descripcionFeriado"));
				holiday.setIdRegion(rs.getInt("idRegion"));
				holiday.setNombreRegion(rs.getString("region"));
				lista.add(holiday);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	public static ArrayList<Feriado> getAllHolidaysByPeriod(String periodo) throws Exception 
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Feriado> lista = new ArrayList<Feriado>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM SAN_CLEMENTE.sw_m_feriados join region on sw_m_feriados.idRegion=region.idregion "+
				  "WHERE fechaFeriado like '"+periodo+"%' ORDER BY nombreFeriado ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Feriado holiday= new Feriado();				
				holiday.setIdFeriado(rs.getInt("idFeriado"));				
				holiday.setNombreFeriado(rs.getString("nombreFeriado"));
				holiday.setFechaFeriado(rs.getDate("fechaFeriado"));
				holiday.setDescripcionFeriado(rs.getString("descripcionFeriado"));
				holiday.setIdRegion(rs.getInt("idRegion"));
				holiday.setNombreRegion(rs.getString("region"));
				lista.add(holiday);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	public static Feriado getHolidayById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		Feriado holidays=new Feriado(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_m_feriados join region on sw_m_feriados.idRegion= region.idregion WHERE idFeriado="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Feriado holiday= new Feriado();				
				holiday.setIdFeriado(rs.getInt("idFeriado"));				
				holiday.setNombreFeriado(rs.getString("nombreFeriado"));
				holiday.setFechaFeriado(rs.getDate("fechaFeriado"));
				holiday.setDescripcionFeriado(rs.getString("descripcionFeriado"));
				holiday.setIdRegion(rs.getInt("idRegion"));
				holidays=holiday;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return holidays;	
	}
	
	/*------------------UPDATE---------------------*/
	
	public static boolean updateHoliday(Feriado holiday) throws SQLException
	{
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_m_feriados SET nombreFeriado = ?, fechaFeriado = ?, descripcionFeriado = ?, idRegion = ?  WHERE idFeriado ="+holiday.getIdFeriado();
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,holiday.getNombreFeriado());
			ps.setDate(2, holiday.getFechaFeriado());
			ps.setString(3,holiday.getDescripcionFeriado());
			ps.setInt(4,holiday.getIdRegion());
			
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
		
	}
	
	/*------------------DELETE---------------------*/
	
	public static boolean deleteHolidayById(int idholiday) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_m_feriados WHERE idFeriado ="+idholiday;
			ps = db.conn.prepareStatement(sql);					
			ps.executeUpdate();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static boolean validateHoliday(Feriado holiday) throws SQLException {
	
		Date fecha= holiday.getFechaFeriado();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String text = df.format(fecha);
		

		System.out.println(text);    
		
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		boolean retorno=false;
		int total=0;	
		try{
			sql = "SELECT count(*) AS QTTY FROM SAN_CLEMENTE.sw_m_feriados WHERE sw_m_feriados.fechaFeriado='"+text+"' AND idRegion="+holiday.getIdRegion()+" AND sw_m_feriados.idFeriado!="+holiday.getIdFeriado();
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				total=rs.getInt("QTTY");
				System.out.println(total);
			}
			if(total>=1 )
			{
				retorno= false;
			}
			else
			{
				retorno= true;
			}
			return retorno;
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return retorno;
	}
	
}
