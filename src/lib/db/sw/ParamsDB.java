package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import lib.classSW.Param;
import lib.db.ConnectionDB;

public class ParamsDB {
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
	public static Param getBlankAFP()  throws Exception{

		new ConnectionDB();
		Param parametros = new Param();

		return parametros.createBlankParam();
	}
	public static boolean createParam(Param p) throws SQLException
	{
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO parametros (codigo, llave, descripcion, activo) VALUES (?,?,?,?)";
		ps = db.conn.prepareStatement(sql);
		
		
		ps.setString(1, p.getCodigo());
		ps.setString(2, p.getLlave());
		ps.setString(3,p.getDescripcion());
		ps.setInt(4, p.getActivo());
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
	public static ArrayList<Param> getAllParams() throws Exception 
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Param> lista = new ArrayList<Param>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from parametros";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Param param= new Param();				
				param.setId(rs.getInt("id"));				
				param.setCodigo(rs.getString("codigo"));
				param.setLlave(rs.getString("llave"));
				param.setDescripcion(rs.getString("descripcion"));
				param.setActivo(rs.getInt("activo"));
				lista.add(param);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	public static boolean updateParam(Param p) throws SQLException
	{
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE parametros SET codigo = ?, llave = ?, descipcion = ?, activo = ?  WHERE id="+p.getId();
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,p.getCodigo());
			ps.setString(2, p.getLlave());
			ps.setString(3,p.getDescripcion());
			ps.setInt(4,p.getActivo());
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
		
	}
	public static boolean deleteParamById(int id) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM parametros WHERE id ="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static Param getParamById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		Param p=new Param(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from parametros WHERE id="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Param param= new Param();				
				param.setId(rs.getInt("id"));				
				param.setCodigo(rs.getString("codigo"));
				param.setLlave(rs.getString("llave"));
				param.setDescripcion(rs.getString("descripcion"));
				param.setActivo(rs.getInt("activo"));
				p=param;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return p;	
	}
	
	
	
}
