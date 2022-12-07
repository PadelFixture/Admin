package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.haberesDescuentos;
import lib.db.ConnectionDB;

public class sw_MaestroDescuentosDB {
//---------Lista codigo---------------------------------------------------------  
	public static ArrayList<haberesDescuentos> getCodigoM()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<haberesDescuentos> lista = new ArrayList<haberesDescuentos>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select codigo from sw_p_haberesDescuentos";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				haberesDescuentos cr = new haberesDescuentos();
				cr.setCodigo(rs.getInt("codigo"));
				
			
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
}
