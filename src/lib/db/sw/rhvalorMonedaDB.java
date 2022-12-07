package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import lib.classSW.rhvalorMoneda;
import lib.db.ConnectionDB;

public class rhvalorMonedaDB {

	public static ArrayList<rhvalorMoneda> getLastByPeriodo(String periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<rhvalorMoneda> lista = new ArrayList<rhvalorMoneda>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select max(fecha) as fecha, valor, idMoneda, idValor from sw_rhvalorMoneda where fecha like '"+periodo+"%' and idMoneda=3 group by idValor";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				rhvalorMoneda v= new rhvalorMoneda();				
			
				v.setFecha(rs.getString("fecha"));;
				v.setValor(rs.getDouble("valor"));
				v.setIdMoneda(rs.getString("idMoneda"));
				v.setIdValor(rs.getInt("idValor"));
				lista.add(v);
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
