package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.Region;
import lib.db.ConnectionDB;

public class RegionDB {


	//Obtener Todos las Regiones
	public static ArrayList<Region> getAllRegion() throws Exception {

		PreparedStatement ps = null;
		String sql="";
		ArrayList<Region> lista = new ArrayList<Region>();
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "select * from region order by region DESC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				Region p = new Region();
				p.setIdregion(rs.getInt("idregion"));
				p.setRegion(rs.getString("region"));
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


	
	
	
	
}

