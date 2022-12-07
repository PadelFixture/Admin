package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.Faena;
import lib.db.ConnectionDB;

public class FaenaDB {

	public static boolean createFaena(Faena f) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = "INSERT INTO sw_m_faena (nombreFaena, idEmpresa) VALUES (?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, f.getNombreFaena());
			ps.setInt(2, f.getIdEmpresa());
			
			ps.execute();
			return true;

		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}

	public static ArrayList<Faena> getFaenas() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<Faena> lista=new ArrayList<Faena>();
		try {
		sql="SELECT * FROM sw_m_faena "+
			"left join (select idSociedad, denominacionSociedad FROM sociedad ) as a on sw_m_faena.idEmpresa=a.idSociedad "+
			"order by nombreFaena ASC";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				Faena f= new Faena();
				f.setIdFaena(rs.getInt("idFaena"));
				f.setNombreFaena(rs.getString("nombreFaena"));
				f.setIdEmpresa(rs.getInt("idEmpresa"));
				f.setNombreEmpresa(rs.getString("denominacionSociedad"));
				lista.add(f);
			}
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;		
	}

	public static Faena getFaenaById(int id) throws SQLException {
		PreparedStatement ps = null;

		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		
		Faena emp=new Faena();
		try {
			sql="SELECT * FROM sw_m_faena "+
				"left join (select idSociedad, denominacionSociedad FROM sociedad ) as a on sw_m_faena.idEmpresa=a.idSociedad "+
				"WHERE idFaena="+id;
				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				Faena m= new Faena();
				m.setIdFaena(rs.getInt("idFaena"));
				m.setNombreFaena(rs.getString("nombreFaena"));
				m.setIdEmpresa(rs.getInt("idEmpresa"));
				m.setNombreEmpresa(rs.getString("denominacionSociedad"));
			
				emp=m;
			}
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return emp;	
	}

	public static ArrayList<Faena> getFaenaBySociedad(String soc) throws SQLException {

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		ArrayList<Faena> lista = new ArrayList<Faena>();

		try{
			sql = "SELECT * FROM sw_m_faena "
					+ " left join (select idSociedad, denominacionSociedad FROM sociedad ) as a on sw_m_faena.idEmpresa=a.idSociedad  "
					+ " WHERE idEmpresa = '"+soc+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				Faena f= new Faena();
				f.setIdFaena(rs.getInt("idFaena"));
				f.setNombreFaena(rs.getString("nombreFaena"));
				f.setIdEmpresa(rs.getInt("idEmpresa"));
				f.setNombreEmpresa(rs.getString("denominacionSociedad"));
				lista.add(f);
				
			}			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}	
		
		return lista;
		
	}

	public static boolean updateFaena(Faena f) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_m_faena SET nombreFaena = ?, idEmpresa = ?  WHERE idFaena ="+f.getIdFaena();
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,f.getNombreFaena());
			ps.setInt(2, f.getIdEmpresa());
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}	
		
		
	}

	public static boolean deleteFaenaById(int id) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_m_faena WHERE idFaena ="+id;
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
