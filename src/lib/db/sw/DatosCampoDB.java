package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.DatosCampo;

import lib.db.ConnectionDB;

public class DatosCampoDB {

	public static ArrayList<DatosCampo> getDatosCampo() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<DatosCampo> Lista=new ArrayList<DatosCampo>();
		try {
			sql="select codigo, campo, sociedad, descripcion, direccion_huerto, representante_legal_nombre, representante_legal_apPaterno, representante_legal_apMaterno, representante_legal_rut, numero_telefono, ciudad_huerto, comuna_huerto from campo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
			
				DatosCampo dt= new DatosCampo();
				dt.setCampo(rs.getString("campo"));
				dt.setCodigo(rs.getInt("codigo"));
				dt.setSociedad(rs.getString("sociedad"));
				dt.setDescripcion(rs.getString("descripcion"));
				dt.setDireccion_huerto(rs.getString("direccion_huerto"));
				dt.setRepresentante_legal_nombre(rs.getString("representante_legal_nombre"));
				dt.setRepresentante_legal_apPaterno(rs.getString("representante_legal_apPaterno"));
				dt.setRepresentante_legal_apMaterno(rs.getString("representante_legal_apMaterno"));
				dt.setRepresentante_legal_rut(rs.getString("representante_legal_rut"));
				dt.setNumero_telefono(rs.getString("numero_telefono"));
				dt.setCiudad_huerto(rs.getString("ciudad_huerto"));
				dt.setComuna_huerto(rs.getString("comuna_huerto"));
				
				Lista.add(dt);
				
				
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
		return Lista;		
	}

	public static DatosCampo getDatosCampoById(int id) throws SQLException {
		PreparedStatement ps = null;

		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		
		DatosCampo dc=new DatosCampo();
		try {
			sql="select codigo, campo, sociedad, descripcion, direccion_huerto, representante_legal_nombre, representante_legal_apPaterno, representante_legal_apMaterno, representante_legal_rut, numero_telefono, ciudad_huerto, comuna_huerto from campo WHERE codigo="+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				DatosCampo dt= new DatosCampo();
				dt.setCampo(rs.getString("campo"));
				dt.setCodigo(rs.getInt("codigo"));
				dt.setSociedad(rs.getString("sociedad"));
				dt.setDescripcion(rs.getString("descripcion"));
				dt.setDireccion_huerto(rs.getString("direccion_huerto"));
				dt.setRepresentante_legal_nombre(rs.getString("representante_legal_nombre"));
				dt.setRepresentante_legal_apPaterno(rs.getString("representante_legal_apPaterno"));
				dt.setRepresentante_legal_apMaterno(rs.getString("representante_legal_apMaterno"));
				dt.setRepresentante_legal_rut(rs.getString("representante_legal_rut"));
				dt.setNumero_telefono(rs.getString("numero_telefono"));
				dt.setCiudad_huerto(rs.getString("ciudad_huerto"));
				dt.setComuna_huerto(rs.getString("comuna_huerto"));
				
				dc=dt;
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
		return dc;	
	}

	public static DatosCampo getDatosCampoBySociedad(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		DatosCampo dc=new DatosCampo();
		try {
			sql="select codigo, campo, sociedad, descripcion, direccion_huerto, representante_legal_nombre, representante_legal_apPaterno, representante_legal_apMaterno, representante_legal_rut, numero_telefono, ciudad_huerto, comuna_huerto from campo WHERE sociedad='"+soc+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				DatosCampo dt= new DatosCampo();
				dt.setCampo(rs.getString("campo"));
				dt.setCodigo(rs.getInt("codigo"));
				dt.setSociedad(rs.getString("sociedad"));
				dt.setDescripcion(rs.getString("descripcion"));
				dt.setDireccion_huerto(rs.getString("direccion_huerto"));
				dt.setRepresentante_legal_nombre(rs.getString("representante_legal_nombre"));
				dt.setRepresentante_legal_apPaterno(rs.getString("representante_legal_apPaterno"));
				dt.setRepresentante_legal_apMaterno(rs.getString("representante_legal_apMaterno"));
				dt.setRepresentante_legal_rut(rs.getString("representante_legal_rut"));
				dt.setNumero_telefono(rs.getString("numero_telefono"));
				dt.setCiudad_huerto(rs.getString("ciudad_huerto"));
				dt.setComuna_huerto(rs.getString("comuna_huerto"));
				
				dc=dt;
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
		return dc;		
	}

	public static boolean updateDatosCampo(DatosCampo d) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual
			

			sql ="";
		
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
