package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.Provincia;
import lib.db.ConnectionDB;

public class ProvinciaDB {


	//Obtener Todos las Provinciaes
	public static ArrayList<Provincia> getAllProvincia() throws Exception {

		PreparedStatement ps = null;
		String sql="";
		ArrayList<Provincia> lista = new ArrayList<Provincia>();
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "select * from provincia order by nombre asc";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				Provincia p = new Provincia();
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setIdRegion(rs.getInt("idRegion"));
				lista.add(p);
			}


		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		

		return lista;

	}



	//Obtener Provincia por Id Region
	public static ArrayList<Provincia> getProvinciaByIdRegion(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		ArrayList<Provincia> lista = new ArrayList<Provincia>();

		try{
			sql = "SELECT * FROM provincia WHERE idRegion = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				Provincia provincia = new Provincia(); 


				provincia.setId(rs.getInt("id"));
				provincia.setIdRegion(rs.getInt("idRegion"));
				provincia.setNombre(rs.getString("nombre"));

				lista.add(provincia);

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
