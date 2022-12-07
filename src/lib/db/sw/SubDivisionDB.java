package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.SubDivision;
import lib.db.ConnectionDB;

public class SubDivisionDB {

	//Obtener Todos las SubDivisiones
		public static ArrayList<SubDivision> getSubDivision() throws Exception {

			PreparedStatement ps = null;
			String sql="";
			ArrayList<SubDivision> lista = new ArrayList<SubDivision>();
			ConnectionDB db = new ConnectionDB();

			try{

				sql = "select * from sw_m_subDivision order by nombre asc";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){
					SubDivision sc = new SubDivision();
					sc.setIdSubDivision(rs.getInt("idSubDivision"));
					sc.setIdDivision(rs.getInt("idDivision"));
					sc.setNombre(rs.getString("nombre"));
					sc.setDescripcion(rs.getString("descripcion"));
					sc.setEstado(rs.getInt("estado"));
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
