package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import lib.classSW.GrupoINE;
import lib.db.ConnectionDB;

public class GrupoINEDB {

	public static boolean createGrupoINE(GrupoINE g) throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO sw_gruposine (idGrupoOcupacional, idCargo, idEmpresa, activo) VALUES (?,?,?,?)";
		ps = db.conn.prepareStatement(sql);
		
		
		ps.setInt(1, g.getIdGrupoOcupacional());
		ps.setInt(2, g.getIdCargo());
		ps.setInt(3, g.getIdEmpresa());
		ps.setInt(4, 1);
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

	public static ArrayList<GrupoINE> getGruposINE() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<GrupoINE> lista = new ArrayList<GrupoINE>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_gruposine "+
				  "left join (SELECT descripcion AS nombreGrupoOcupacional, llave AS id FROM parametros WHERE codigo='GRUPOS_OCUPACIONALES') AS z on sw_gruposine.idGrupoOcupacional=z.id "+
				  "left join (SELECT id_cargo, cargos FROM cargos WHERE activo=1) as a on sw_gruposine.idCargo=a.id_cargo "+
				  "left join (SELECT sociedad, denominacionSociedad, idSociedad FROM sociedad WHERE idSociedad!=-1) as b on sw_gruposine.idEmpresa=b.idSociedad "+
				  "WHERE sw_gruposine.activo=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				GrupoINE grupo= new GrupoINE();				
				grupo.setIdGrupoINE(rs.getInt("idGrupoINE"));		
				grupo.setIdGrupoOcupacional(rs.getInt("idGrupoOcupacional"));
				grupo.setNombreGrupoOcupacional(rs.getString("nombreGrupoOcupacional"));
				grupo.setIdCargo(rs.getInt("idCargo"));
				grupo.setNombreCargo(rs.getString("cargos"));
				grupo.setIdEmpresa(rs.getInt("idEmpresa"));
				grupo.setNombreEmpresa(rs.getString("denominacionSociedad"));
				grupo.setActivo(rs.getInt("activo"));
				
				lista.add(grupo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static GrupoINE getGrupoINEById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		GrupoINE lista = new GrupoINE();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_gruposine "+
				  "left join (SELECT descripcion AS nombreGrupoOcupacional, llave AS id FROM parametros WHERE codigo='GRUPOS_OCUPACIONALES') AS z on sw_gruposine.idGrupoOcupacional=z.id "+
				  "left join (SELECT id_cargo, cargos FROM cargos WHERE activo=1) as a on sw_gruposine.idCargo=a.id_cargo "+
				  "left join (SELECT sociedad, denominacionSociedad, idSociedad FROM sociedad WHERE idSociedad!=-1) as b on sw_gruposine.idEmpresa=b.idSociedad "+
				  "WHERE sw_gruposine.activo=1 and idGrupoINE="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				GrupoINE grupo= new GrupoINE();				
				grupo.setIdGrupoINE(rs.getInt("idGrupoINE"));
				grupo.setIdGrupoOcupacional(rs.getInt("idGrupoOcupacional"));
				grupo.setNombreGrupoOcupacional(rs.getString("nombreGrupoOcupacional"));
				grupo.setIdCargo(rs.getInt("idCargo"));
				grupo.setNombreCargo(rs.getString("cargos"));
				grupo.setIdEmpresa(rs.getInt("idEmpresa"));
				grupo.setNombreEmpresa(rs.getString("denominacionSociedad"));
				grupo.setActivo(rs.getInt("activo"));
				lista=grupo;
			
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static ArrayList<GrupoINE> getGruposINEBySociedad(int idEmp) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<GrupoINE> lista = new ArrayList<GrupoINE>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_gruposine "+
				  "left join (SELECT descripcion AS nombreGrupoOcupacional, llave AS id FROM parametros WHERE codigo='GRUPOS_OCUPACIONALES') AS z on sw_gruposine.idGrupoOcupacional=z.id "+
				  "left join (SELECT id_cargo, cargos FROM cargos WHERE activo=1) as a on sw_gruposine.idCargo=a.id_cargo "+
				  "left join (SELECT sociedad, denominacionSociedad, idSociedad FROM sociedad WHERE idSociedad!=-1) as b on sw_gruposine.idEmpresa=b.idSociedad "+
				  "WHERE sw_gruposine.activo=1 AND sw_gruposine.idEmpresa="+idEmp;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				GrupoINE grupo= new GrupoINE();				
				grupo.setIdGrupoINE(rs.getInt("idGrupoINE"));				
				grupo.setNombreGrupoOcupacional(rs.getString("nombreGrupoINE"));
				grupo.setIdCargo(rs.getInt("idCargo"));
				grupo.setNombreCargo(rs.getString("cargos"));
				grupo.setIdEmpresa(rs.getInt("idEmpresa"));
				grupo.setNombreEmpresa(rs.getString("denominacionSociedad"));
				grupo.setActivo(rs.getInt("activo"));
				
				lista.add(grupo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static boolean updateGrupoINE(GrupoINE g) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "UPDATE sw_gruposine set idGrupoOcupacional=?, idCargo=?, idEmpresa=? WHERE idGrupoINE="+g.getIdGrupoINE();
			ps = db.conn.prepareStatement(sql);
				ps.setInt(1, g.getIdGrupoOcupacional());
				ps.setInt(2, g.getIdCargo());
				ps.setInt(3, g.getIdEmpresa());
				
			ps.executeUpdate();
			return true;
				
			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			return false;
		}finally {
			ps.close();
			db.close();
		}		
		
	}

	public static boolean deleteGrupoINEById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_gruposine set activo=0 WHERE idGrupoINE ="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.executeUpdate();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

	public static boolean deleteGrupoINEBySociedad(int soc) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_gruposine set activo=0 WHERE idEmpresa ="+soc;
			ps = db.conn.prepareStatement(sql);					
			ps.executeUpdate();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

}
