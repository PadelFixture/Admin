package lib.db.sw;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import lib.classSW.Cargo;
import lib.db.ConnectionDB;

public class CargosDB {

	//Obtener Todos las Cargos
	public static ArrayList<Cargo> getAllCargos() throws Exception {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Cargo> lista = new ArrayList<Cargo>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from cargos "+
				  "left join sociedad on cargos.sociedad=sociedad.sociedad "+
				  "where cargos.activo=1 group by id_cargo, cargos, sociedad.sociedad order by cargos ASC ";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Cargo c = new Cargo();
				c.setId_cargo(rs.getInt("id_cargo"));
				c.setCargos(rs.getString("cargos"));
				c.setSueldoBase(rs.getBigDecimal("sueldoBase"));
				c.setSociedad(rs.getString("sociedad"));
				c.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				lista.add(c);
			}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static boolean createCargo(Cargo c) throws SQLException
	{
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		try{
			String sql="INSERT INTO cargos (cargos, sueldoBase,activo, sociedad ) VALUES (?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getCargos());
			ps.setBigDecimal(2, c.getSueldoBase());
			ps.setInt(3,1);
			ps.setString(4, c.getSociedad());
			
			return ps.execute();
			}
			catch(SQLException ex){
				return false;
			}
			catch(Exception ex){
				return false;
			}
			finally{
				ps.close();
				db.conn.close();
				
			}
	}

	public static Cargo getCargoById(int id) throws Exception 
	{
		PreparedStatement ps = null;
		String sql="";
		Cargo ca = new Cargo();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from cargos where activo=1 and id_cargo="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Cargo c = new Cargo();
				c.setId_cargo(rs.getInt("id_cargo"));
				c.setCargos(rs.getString("cargos"));
				c.setSueldoBase(rs.getBigDecimal("sueldoBase"));
				c.setSociedad(rs.getString("sociedad"));
				ca=c;
			}
			
			return ca;
		}
		catch(SQLException ex){
			
			return null;
		}
		catch(Exception ex){
			return null;
		}
		finally{
			ps.close();
			db.conn.close();
		}
	}
	public static boolean deleteCargoById(int id) throws SQLException {
		
		
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual
			sql ="UPDATE cargos SET activo = 0 WHERE id_cargo="+id;
			ps = db.conn.prepareStatement(sql);
			ps.executeUpdate();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

	public static boolean updateCargo(Cargo c) throws SQLException {
		
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual

			Gson g=new Gson();
			sql ="UPDATE cargos SET cargos = ?, sueldoBase = ?, sociedad=? WHERE id_Cargo="+c.getId_cargo();
		System.out.println(g.toJson(c));
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,c.getCargos());
			ps.setBigDecimal(2, c.getSueldoBase());
			ps.setString(3, c.getSociedad());
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			
			return false;
		}finally{
			db.conn.close();
		}
	}

	public static ArrayList<Cargo> getCargoBySoc(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Cargo> lista = new ArrayList<Cargo>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from cargos "+
				  "left join sociedad on cargos.sociedad=sociedad.sociedad "+
				  "where cargos.activo=1 and sociedad.sociedad='"+soc+"' group by id_cargo, cargos, sociedad.sociedad order by cargos ASC ";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Cargo c = new Cargo();
				c.setId_cargo(rs.getInt("id_cargo"));
				c.setCargos(rs.getString("cargos"));
				c.setSueldoBase(rs.getBigDecimal("sueldoBase"));
				c.setSociedad(rs.getString("sociedad"));
				c.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				lista.add(c);
			}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	
	public static ArrayList<Cargo> getCargoByIdSociedad(String idSociedad) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Cargo> lista = new ArrayList<Cargo>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from cargos "+
				  "left join sociedad on cargos.sociedad=sociedad.sociedad "+
				  "where cargos.activo=1 and sociedad.idSociedad ='"+idSociedad+"' group by id_cargo, cargos, sociedad.sociedad order by cargos ASC ";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Cargo c = new Cargo();
				c.setId_cargo(rs.getInt("id_cargo"));
				c.setCargos(rs.getString("cargos"));
				c.setSueldoBase(rs.getBigDecimal("sueldoBase"));
				c.setSociedad(rs.getString("sociedad"));
				c.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				lista.add(c);
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
