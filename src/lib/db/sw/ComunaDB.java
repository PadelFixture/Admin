package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.Comuna;
import lib.db.ConnectionDB;

public class ComunaDB {


	//Obtener Todos las Comunaes
	public static ArrayList<Comuna> getAllComuna() throws Exception {

		PreparedStatement ps = null;
		String sql="";
		ArrayList<Comuna> lista = new ArrayList<Comuna>();
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "select * from comuna";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				Comuna c = new Comuna();
				c.setId(rs.getInt("id"));
				c.setNombre(rs.getString("nombre"));
				c.setIdProvincia(rs.getInt("idProvincia"));
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


	//Obtener Provincia por Id Provincia
	public static ArrayList<Comuna> getComunaByIdProvincia(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		ArrayList<Comuna> lista = new ArrayList<Comuna>();

		try{
			sql = "SELECT * FROM comuna WHERE idProvincia = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				Comuna comuna = new Comuna(); 


				comuna.setId(rs.getInt("id"));
				comuna.setIdProvincia(rs.getInt("idProvincia"));
				comuna.setNombre(rs.getString("nombre"));

				lista.add(comuna);

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
