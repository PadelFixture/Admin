package lib.db.sw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.Division;
import lib.db.ConnectionDB;

public class DivisionDB {

	public static Connection db;      
	
	//Obtener Todos las Divisiones
	public static ArrayList<Division> getDivision() throws Exception {

		PreparedStatement ps = null;
		String sql="";
		ArrayList<Division> lista = new ArrayList<Division>();
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "select * from sw_m_division";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				Division sc = new Division();
				sc.setIdDivision(rs.getInt("idDivision"));
				sc.setNombre(rs.getString("nombre"));
				sc.setDescripcion(rs.getString("descripcion"));
				sc.setEstado(rs.getString("estado"));
				lista.add(sc);
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
