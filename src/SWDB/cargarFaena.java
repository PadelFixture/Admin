package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.LoadFaena;
import lib.db.ConnectionDB;

public class cargarFaena {
	
	public static ArrayList<LoadFaena> loadDatosFaena() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadFaena> data = new ArrayList<LoadFaena>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from faena";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LoadFaena e = new LoadFaena();
				e.setCodigo(rs.getInt("codigo"));
				e.setFaena(rs.getString("faena"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}

}
