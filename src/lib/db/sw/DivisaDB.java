package lib.db.sw;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import lib.classSW.Divisas;
import lib.db.ConnectionDB;

public class DivisaDB {

	public static boolean createDivisa(Divisas moneda) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean updateDivisa(Divisas moneda) {
		// TODO Auto-generated method stub
		return false;
	}

	public static Divisas getDivisaById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Divisas getBlankDivisa() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Divisas> getAllDivisas() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Divisas> lista = new ArrayList<Divisas>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM SAN_CLEMENTE.sw_rhmoneda WHERE visible=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Divisas mon= new Divisas();				
				mon.setIdMoneda(rs.getInt("idMoneda"));				
				mon.setMoneda(rs.getString("moneda"));
				mon.setVisible(rs.getInt("visible"));
				lista.add(mon);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}

	public static boolean deleteDivisaById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}

